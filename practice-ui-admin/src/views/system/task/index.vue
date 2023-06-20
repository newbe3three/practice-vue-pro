<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="企业名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入名字" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="任务名称" prop="deptName">
        <el-input v-model="queryParams.deptName" placeholder="请输入部门名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
          <el-option v-for="dict in this.getDictDatas(DICT_TYPE.SYSTEM_TASK_STATUS)" :key="dict.value" :label="dict.label"
            :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 操作工具栏 -->
    <!-- <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd"
                   v-hasPermi="['system:task:create']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                   v-hasPermi="['system:task:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row> -->

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="企业名称" align="center" prop="name" />
      <el-table-column label="任务名称" align="center" prop="deptName" />
      <el-table-column label="任务报酬" align="center" prop="amount">
        <template v-slot="scope">
          <span>{{ parseFloat(scope.row.amount) + "元" }}</span>
        </template>
      </el-table-column>

      <el-table-column label="开始时间" align="center" prop="startTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" align="center" prop="endTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="需要人数" align="center" prop="numberPeople" />
      <el-table-column label="状态" align="center" prop="status">
        <template v-slot="scope">
          <dict-tag :type="DICT_TYPE.SYSTEM_TASK_STATUS" :value="scope.row.status" />
        </template>
      </el-table-column>
      <el-table-column label="驳回意见" align="center" prop="suggestion" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template v-slot="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="240">
        <template v-slot="scope">
          <el-button v-show="scope.row.status !== 0" size="mini" type="text" icon="el-icon-edit"
            @click="handleLookTask(scope.row)" v-hasPermi="['system:task:update']">查看任务</el-button>
          <el-button v-show="scope.row.status === 2" size="mini" type="text" icon="el-icon-edit"
            @click="handleLookPeople(scope.row)" v-hasPermi="['system:task:update']">查看人选</el-button>
          <el-button v-show="scope.row.status === 0" size="mini" type="text" icon="el-icon-edit"
            @click="handleCheck(scope.row)" v-hasPermi="['system:task:update']">审核</el-button>
          <!--审核按钮-->
          <!--          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)"-->
          <!--                     v-hasPermi="['system:task:update']">审核</el-button>-->
          <el-button v-show="scope.row.status === 1 || scope.row.status === 2" size="mini" type="text"
            icon="el-icon-delete" @click="handleClose(scope.row)" v-hasPermi="['system:task:delete']">关闭</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
      @pagination="getList" />

    <el-dialog :title="title" :visible.sync="showApply" v-dialogDrag append-to-body center>
      <el-row>
        <div style="font-size: 18px; font-weight: 600; ">任务名称:{{ taskName }}</div>
      </el-row>
      <el-table v-loading="loading" :data="applyList">
        <el-table-column label="姓名" align="center" prop="name" />
        <el-table-column label="手机号" align="center" prop="phone" />
        <el-table-column label="所属院校" align="center" prop="school" />
        <el-table-column label="专业" align="center" prop="major" />
        <el-table-column label="申请时间" align="center" prop="time" />
      </el-table>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="showCheck" width="500px" v-dialogDrag append-to-body center>
      <div style="line-height: 30px; font-size: 14px; text-align: center;">针对“{{ taskName }}”任务需求审核是否通过</div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="passTask">通 过</el-button>
        <el-button @click="rejectTask">驳 回</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="showReview" width="500px" v-dialogDrag append-to-body>
      
      <el-input v-model="rejectReason" placeholder="请输入驳回意见" />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="confirmReject">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 对话框(添加 / 修改) -->
    <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="名字" prop="name">
          <el-input v-model="form.name" placeholder="请输入名字" />
        </el-form-item>
        <el-form-item label="部门名称" prop="deptName">
          <el-input v-model="form.deptName" placeholder="请输入部门名称" />
        </el-form-item>
        <el-form-item label="任务报酬" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入任务报酬" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker clearable v-model="form.startTime" type="date" value-format="timestamp" placeholder="选择开始时间" />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker clearable v-model="form.endTime" type="date" value-format="timestamp" placeholder="选择结束时间" />
        </el-form-item>
        <el-form-item label="需要人数" prop="numberPeople">
          <el-input v-model="form.numberPeople" placeholder="请输入需要人数" />
        </el-form-item>
        <!--        <el-form-item label="状态" prop="status">-->
        <!--          <el-select v-model="form.status" placeholder="请选择状态">-->
        <!--            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.SYSTEM_TASK_STATUS)"-->
        <!--                       :key="dict.value" :label="dict.label" :value="dict.value" />-->
        <!--          </el-select>-->
        <!--        </el-form-item>-->
        <el-form-item label="驳回意见" prop="suggestion">
          <el-input v-model="form.suggestion" placeholder="请输入驳回意见" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="passTask">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { createTask, updateTask, deleteTask, getTask, getTaskPage, exportTaskExcel, passTask, rejectTask, getApplyList} from "@/api/system/task";
import { getDictDatas, DICT_TYPE } from '@/utils/dict';
export default {
  name: "Task",
  components: {
  },
  data() {
    return {
      // 查看人选弹窗
      showApply: false,
      // 审核提示
      showCheck: false,
      // 驳回意见
      showReview: false,
      // 任务名称
      taskName: "",
      // 遮罩层
      loading: true,
      // 导出遮罩层
      exportLoading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 任务id
      taskId: undefined,
      // 驳回意见
      rejectReason: '',
      // 任务列表
      list: [],
      // 申请人员列表
      applyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        name: null,
        deptName: null,
        amount: null,
        startTime: [],
        endTime: [],
        numberPeople: null,
        status: null,
        suggestion: null,
        createTime: [],
      },
      //
      statusTypeDictData: getDictDatas(DICT_TYPE.SYSTEM_TASK_STATUS),
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [{ required: true, message: "名字不能为空", trigger: "blur" }],
        deptName: [{ required: true, message: "部门名称不能为空", trigger: "blur" }],
        amount: [{ required: true, message: "任务报酬不能为空", trigger: "blur" }],
        startTime: [{ required: true, message: "开始时间不能为空", trigger: "blur" }],
        endTime: [{ required: true, message: "结束时间不能为空", trigger: "blur" }],
        numberPeople: [{ required: true, message: "需要人数不能为空", trigger: "blur" }],
        //status: [{ required: false, message: "状态不能为空", trigger: "change" }],
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
      getTaskPage(this.queryParams).then(response => {
        this.list = response.data.list;
        this.total = response.data.total;
        this.loading = false;
      });
    },
    /** 取消按钮 */
    cancel() {
      this.open = false;
      this.showReview = false;
    },
    /** 表单重置 */
    reset() {
      this.form = {
        id: undefined,
        name: undefined,
        deptName: undefined,
        amount: undefined,
        startTime: undefined,
        endTime: undefined,
        numberPeople: undefined,
        //status: undefined,
        suggestion: undefined,
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
      this.title = "添加任务";
    },
    handleLookTask(row) {

    },
    handleLookPeople(row) {
      // this.loading = true;
      getApplyList(id).then(response => {
        this.applyList = response.data.list;
        this.showApply = true;
        this.taskName = row.name;
        this.title = "申请人选";
        this.loading = false;
      });
    },
    handleCheck(row) {
      this.taskName = row.name;
      this.taskId = row.id;
      this.showCheck = true;
      this.title = "审核提示";
    },
    passTask() {
      // todo
      this.loading = true;
      passTask(this.taskId).then(response => {
        this.showCheck = false;
        this.loading = false;
        this.getList();
      });
    },
    handleClose(row) {
      
    },
    rejectTask() {
      this.title = "请填写驳回意见";
      this.showReview = true;
      this.showCheck = false;
    },
    confirmReject(){
      this.loading = true
      rejectTask(this.taskId, this.rejectReason).then(response => {
        this.loading = false;
        this.showReview = false;
        this.getList();
      });
    },
    /** 修改按钮操作 */
    // handleUpdate(row) {
    //   this.reset();
    //   const id = row.id;
    //   getTask(id).then(response => {
    //     this.form = response.data;
    //     this.open = true;
    //     this.title = "修改任务";
    //   });
    // },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return;
        }
        // 修改的提交
        if (this.form.id != null) {
          updateTask(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
          return;
        }
        // 添加的提交
        createTask(this.form).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.open = false;
          this.getList();
        });
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const id = row.id;
      this.$modal.confirm('是否确认删除任务编号为"' + id + '"的数据项?').then(function () {
        return deleteTask(id);
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
      this.$modal.confirm('是否确认导出所有任务数据项?').then(() => {
        this.exportLoading = true;
        return exportTaskExcel(params);
      }).then(response => {
        this.$download.excel(response, '任务.xls');
        this.exportLoading = false;
      }).catch(() => { });
    }
  }
};
</script>
