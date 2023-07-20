<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户id" prop="userId">
        <el-input v-model="queryParams.userId" placeholder="请输入用户id" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="附加信息" prop="message">
        <el-input v-model="queryParams.message" placeholder="请输入附加信息" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.PRACTICE_APPLY_STATUS)" :key="dict.value"
                     :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="申请/创建时间" prop="createTime">
        <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss"
                        type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"
                        :default-time="['00:00:00', '23:59:59']" />
      </el-form-item>
      <el-form-item label="申请实践id" prop="practiceId">
        <el-input v-model="queryParams.practiceId" placeholder="请输入申请实践id" clearable @keyup.enter.native="handleQuery" />
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
                   v-hasPermi="['system:practice-apply:create']">申请</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['system:practice-apply:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="实践申请编号" align="center" prop="id" />
      <el-table-column label="用户id" align="center" prop="userId" />
      <el-table-column label="资历" align="center" prop="resume" />
      <el-table-column label="附加信息" align="center" prop="message" />
      <el-table-column label="状态" align="center" prop="status">
        <template v-slot="scope">
          <dict-tag :type="DICT_TYPE.PRACTICE_APPLY_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="申请/创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="申请实践id" align="center" prop="practiceId" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template v-slot="scope">
          <el-button v-show="scope.row.status === 0" size="mini" type="text" @click="handleReview(scope.row)"
                     v-hasPermi="['system:practice-apply:update']">审核</el-button>
          <el-button size="mini" type="text" @click="handleDelete(scope.row)"
                     v-hasPermi="['system:practice-apply:delete']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList" />

    <el-dialog :title="title" :visible.sync="showReview" width="500px" v-dialogDrag append-to-body center>
      <div v-show="rejectList.length != 0">
        <div v-for="(item, index) in rejectList" :key="index">
          <div>创建时间{{ item.createTime }}</div>
          <div>驳回意见{{ item.suggestion }}</div>
          <div style="height: 10px;"></div>
        </div>
      </div>
      <div v-show="rejectList.length === 0">是否通过{{ applyPractice.id }}</div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="reviewPass">审核通过</el-button>
        <el-button @click="reject">驳 回</el-button>
      </div>
    </el-dialog>

    <!-- 对话框（拒绝）-->
    <el-dialog :title="title" :visible.sync="showReject" width="500px" v-dialogDrag append-to-body>

      <el-input v-model="rejectReason" placeholder="请输入驳回意见" />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmReject">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户id" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户id" />
        </el-form-item>
        <el-form-item label="简历url">
          <fileUpload v-model="form.resume" />
        </el-form-item>
        <el-form-item label="附加信息" prop="message">
          <el-input v-model="form.message" placeholder="请输入附加信息" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择状态">
            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.PRACTICE_APPLY_STATUS)" :key="dict.value"
                       :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="申请实践id" prop="practiceId">
          <el-input v-model="form.practiceId" placeholder="请输入申请实践id" />
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
  createPracticeApply, updatePracticeApply, deletePracticeApply,reviewFailurePracticeApply,
  getPracticeApply, getPracticeApplyPage, exportPracticeApplyExcel,studentGetPracticeApplyPage,
  reviewPracticeApply
} from "@/api/system/practiceApply";
import FileUpload from '@/components/FileUpload';
import { getDictDatas, DICT_TYPE } from '@/utils/dict'

export default {
  name: "PracticeApply",
  components: {
    FileUpload,
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
      // 实践申请列表
      list: [],
      // 实践记录-被拒绝记录
      rejectList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 显示dialog
      showReview: false,
      // 实践申请
      applyPractice: {},
      // 驳回意见
      rejectReason: '',
      // 拒绝弹窗
      showReject: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        userId: null,
        resume: null,
        message: null,
        status: null,
        createTime: [],
        practiceId: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        resume: [{ required: true, message: "简历url不能为空", trigger: "blur" }],
        message: [{ required: true, message: "附加信息不能为空", trigger: "blur" }],
        practiceId: [{ required: true, message: "申请实践id不能为空", trigger: "blur" }],
      }
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
      studentGetPracticeApplyPage(this.queryParams).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.reset();
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        userId: undefined,
        resume: undefined,
        message: undefined,
        status: undefined,
        practiceId: undefined,
      };
      this.resetForm("form");
    },
    /** 点击审核 */
    handleReview(row) {
      this.applyPractice = row;
      this.showReview = true;
      this.title = '审核提示';
      console.log('123', row.id);
      reviewPracticeApply(row.id).then(res => {
        this.rejectList = res.data;
        console.log('123', this.rejectList, length);
      })
    },
    /** 审核通过 */
    reviewPass() {
      this.showReview = false;
      reviewPass(this.applyPractice.id).then(res => {
        this.$modal.msgSuccess('审核通过');
        this.getList();
      });
    },
    /** 审核驳回 */
    reject() {
      this.showReview = false;
      this.showReject = true;
      this.title = '驳回申请';
    },
    /** 驳回 */
    confirmReject(){
      reviewFailurePracticeApply(this.practice.id, this.rejectReason).then(res => {
        this.$modal.msgSuccess('驳回成功');
        this.showReject = false;
      });
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
      this.title = "添加实践申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id;
      getPracticeApply(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改实践申请";
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
          updatePracticeApply(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createPracticeApply(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除实践申请编号为"' + id + '"的数据项?').then(function () {
        return deletePracticeApply(id);
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
      this.$modal.confirm('是否确认导出所有实践申请数据项?').then(() => {
        this.exportLoading = true;
        return exportPracticeApplyExcel(params);
      }).then(response => {
        this.$download.excel(response, '实践申请.xls');
        this.exportLoading = false;
      }).catch(() => { });
    }
  }
};
</script>
