<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="组织编号" prop="id">
        <el-input v-model="queryParams.id" placeholder="请输入企业编号" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="组织名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入企业名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="组织简称" prop="abbreviation">
        <el-input v-model="queryParams.abbreviation" placeholder="请输入企业简称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
          :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
          v-hasPermi="['system:organization-other:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
          v-hasPermi="['system:organization-other:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="组织编号" align="center" prop="id" />
      <el-table-column label="组织名称" align="center" prop="name" />
      <el-table-column label="入口地址" align="center" prop="ref" />
      <el-table-column label="联系邮箱" align="center" prop="email" width="200" />
      <el-table-column label="联系电话" align="center" prop="phone" />
      <el-table-column label="负责人" align="center" prop="principal" />
      <el-table-column label="联系微信" align="center" prop="principalWechat" />
      <el-table-column label="统一社会信用代码" align="center" prop="code" />
      <el-table-column label="地址" align="center" prop="address" width="180" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding" width="300">
        <template v-slot="scope">
          <el-button size="mini" v-show="!scope.row.isEnd" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"
            v-hasPermi="['system:organization-other:update']">编辑</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
            v-hasPermi="['system:organization-other:delete']">删除</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleService(scope.row)"
            v-hasPermi="['system:organization-other:delete']">服务约定</el-button>
          <el-button size="mini" v-show="!scope.row.isEnd" type="text" icon="el-icon-delete" @click="handleInvite(scope.row)"
            v-hasPermi="['system:organization-other:delete']">发送邀请</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <!-- 对话框(服务约定) -->
    <el-dialog :title="title" :visible.sync="showSign" width="500px" v-dialogDrag append-to-body center>

      <div class="text-container">
        <div>客户名称：</div>
        <div>{{ other.name }}</div>
      </div>
      <div class="text-container">
        <div>客户编号：</div>
        <div>{{ other.id }}</div>
      </div>

      <div class="text-container">
        <div>生效日期：</div>
        <div v-show="showDatePicker"> <el-date-picker clearable v-model="other.startTime" value-format="yyyy-MM-dd"
            type="date" placeholder="选择开始时间" />
        </div>
        <div v-show="!showDatePicker">{{ other.startTime }}</div>
      </div>
      <div class="text-container">
        <div>终止日期：</div>
        <div v-show="showDatePicker"> <el-date-picker clearable v-model="other.endTime" value-format="yyyy-MM-dd"
            type="date" placeholder="选择终止时间" />
        </div>
        <div v-show="!showDatePicker">{{ other.endTime }}</div>
      </div>


      <div v-show="!other.isEnd" slot="footer" class="dialog-footer">
        <el-button :type="showDatePicker ? 'primary': 'info'" @click="confirmSign">{{ showDatePicker ? '确定' : '服务变更' }}</el-button>
        <el-button @click="cancel">取消</el-button>
        <el-button v-show="!showDatePicker" type="danger" @click="stopService">终止服务</el-button>
      </div>
    </el-dialog>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="组织名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入企业名称" />
        </el-form-item>
        <el-form-item label="组织简称" prop="abbreviation">
          <el-input v-model="form.abbreviation" placeholder="请输入企业简称" />
        </el-form-item>
        <el-form-item label="入口地址" prop="ref">
          <el-input v-model="form.ref" placeholder="请输入入口地址" />
        </el-form-item>
        <el-form-item label="联系邮箱" prop="email">
          <el-input v-model="form.email" placeholder="请输入联系邮箱" />
        </el-form-item>
        <el-form-item label="联系电话" prop="phone">
          <el-input v-model="form.phone" placeholder="请输入联系电话" />
        </el-form-item>
        <el-form-item label="负责人" prop="principal">
          <el-input v-model="form.principal" placeholder="请输入负责人" />
        </el-form-item>
        <el-form-item label="负责人性别" prop="principalSex">
          <el-select v-model="form.sex" placeholder="请选择">
            <el-option v-for="dict in sexDictDatas" :key="parseInt(dict.value)" :label="dict.label"
              :value="parseInt(dict.value)" />
          </el-select>
        </el-form-item>
        <el-form-item label="联系微信" prop="principalWechat">
          <el-input v-model="form.principalWechat" placeholder="请输入联系微信" />
        </el-form-item>
        <el-form-item label="负责人职务" prop="principalPosition">
          <el-input v-model="form.principalPosition" placeholder="请输入负责人职务" />
        </el-form-item>
        <el-form-item label="统一社会信用代码" prop="code">
          <el-input v-model="form.code" placeholder="请输入统一社会信用代码" />
        </el-form-item>
        <el-form-item label="地址" prop="address">
          <el-input v-model="form.address" placeholder="请输入地址" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  createOrganizationOther,
  updateOrganizationOther,
  deleteOrganizationOther,
  getOrganizationOther,
  getOrganizationOtherPage,
  exportOrganizationOtherExcel,
  confirmSign,
  stopService,
} from "@/api/system/organizationOther";
import { DICT_TYPE, getDictDatas } from "@/utils/dict";

export default {
  name: "OrganizationOther",
  components: {
  },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 社会企业列表
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示服务约定
      showSign: false,
      // 是否显示时间选择器
      showDatePicker: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        id: null,
        name: null,
        abbreviation: null,
        createTime: [],
      },
      // 服务约定
      other: {},
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "组织名称不能为空", trigger: "blur" }],
        phone: [{ required: true, message: "联系电话不能为空", trigger: "blur" }],
        principal: [{ required: true, message: "负责人不能为空", trigger: "blur" }],
        code: [{ required: true, message: "统一社会信用代码不能为空", trigger: "blur" }],
        address: [{ required: true, message: "地址不能为空", trigger: "blur" }],
      },
      sexDictDatas: getDictDatas(DICT_TYPE.SYSTEM_USER_SEX),
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      // 执行查询
      getOrganizationOtherPage(this.queryParams).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      if (this.showSign) {
        this.showSign = false;
      }
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        abbreviation: undefined,
        ref: undefined,
        email: undefined,
        phone: undefined,
        principal: undefined,
        principalSex: undefined,
        principalWechat: undefined,
        principalPosition: undefined,
        code: undefined,
        address: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNo = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加组织信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getOrganizationOther(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改组织信息";
      });
    },
    handleService(row) {
      this.other = row;
      this.title = '服务约定';
      this.showSign = true;
      if(this.other.startTime === '' || this.other.startTime === null){
        this.showDatePicker = true;
      }else{
        this.showDatePicker = false;
      }
    },
    confirmSign() {
      if(this.showDatePicker){
        this.loading = true;
        updateOrganizationOther(this.other).then(() => {
        console.log('123', '签署成功');
        this.loading = false;
        this.showSign = false;
        this.getList();
      });
        this.showSign = false;
      }else{
        this.showDatePicker = true;
      }
    },
    stopService(){
      this.loading = true;
      stopService(this.other.id).then(res => {
        this.showSign = false;
        this.loading = false;
        this.getList();
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updateOrganizationOther(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createOrganizationOther(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除组织编号为"' + id + '"的数据项?').then(function () {
        return deleteOrganizationOther(id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => { });
    },
    /** 导出按钮操作 */
    handleExport() {
      // 处理查询参数
      let params = { ...this.queryParams };
      params.pageNo = undefined;
      params.pageSize = undefined;
      this.$modal.confirm('是否确认导出所有组织数据项?').then(() => {
        this.exportLoading = true;
        return exportOrganizationOtherExcel(params);
      }).then(response => {
        this.$download.excel(response, '其他组织.xls');
        this.exportLoading = false;
      }).catch(() => { });
    }
  }
};
</script>

<style lang="scss">
.text-container {
  display: flex;
  flex-direction: row;
  line-height: 50px;
}
</style>
