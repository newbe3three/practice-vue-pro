<template>
    <div class="app-container">

      <!-- 搜索工作栏 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <!-- <el-form-item label="用户名称" prop="userNickName">
          <el-input v-model="queryParams.schoolId" placeholder="请输入用户名称" clearable @keyup.enter.native="handleQuery"/>
        </el-form-item>
        <el-form-item label="实践名称" prop="practiceName">
          <el-input v-model="queryParams.schoolId" placeholder="请输入实践名称" clearable @keyup.enter.native="handleQuery"/>
        </el-form-item> -->
        <el-form-item label="状态" prop="status">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable size="small">
            <el-option v-for="dict in this.getDictDatas(DICT_TYPE.PRACTICE_SCHOOL_STATUS)"
                         :key="dict.value" :label="dict.label" :value="dict.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="申请/创建时间" prop="createTime">
          <el-date-picker v-model="queryParams.createTime" style="width: 240px" value-format="yyyy-MM-dd HH:mm:ss" type="daterange"
                          range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期" :default-time="['00:00:00', '23:59:59']" />
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
                     v-hasPermi="['system:practice-school:create']">新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" :loading="exportLoading"
                     v-hasPermi="['system:practice-school:export']">导出</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row> -->

      <!-- 列表 -->
      <el-table v-loading="loading" :data="list">
        <el-table-column label="编号" align="center" prop="id" />
        <el-table-column label="用户名称" align="center" prop="userNickName" />
        <el-table-column label="实践名称" align="center" prop="practiceName" />
        <el-table-column label="简历" align="center" prop="resume" />
        <el-table-column label="附加信息" align="center" prop="message"/>
        <el-table-column label="状态" align="center" prop="status">
          <template v-slot="scope">
            <dict-tag :type="DICT_TYPE.PRACTICE_SCHOOL_STATUS" :value="scope.row.status" />
          </template>
        </el-table-column>
        <el-table-column label="申请/创建时间" align="center" prop="createTime" width="180">
          <template v-slot="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template v-slot="scope">
            <el-button v-show="scope.row.status === 0" size="mini" type="text" icon="el-icon-edit" @click="handleReview(scope.row)"
                       v-hasPermi="['system:practice-apply:comapny:review']">审 核</el-button>
                       <span v-show="scope.row.status === 1">已通过</span> 

          </template>
        </el-table-column>
      </el-table>
      <!-- 分页组件 -->
      <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                  @pagination="getList"/>

      <!-- 对话框(添加 / 修改) -->
      <!-- <el-dialog :title="title" :visible.sync="open" width="500px" v-dialogDrag append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="申请学校id" prop="schoolId">
            <el-input v-model="form.schoolId" placeholder="请输入申请学校id" />
          </el-form-item>
          <el-form-item label="申请实践id" prop="practiceId">
            <el-input v-model="form.practiceId" placeholder="请输入申请实践id" />
          </el-form-item>
          <el-form-item label="状态" prop="status">
            <el-select v-model="form.status" placeholder="请选择状态">
              <el-option v-for="dict in this.getDictDatas(DICT_TYPE.PRACTICE_SCHOOL_STATUS)"
                         :key="dict.value" :label="dict.label" :value="dict.value" />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog> -->

        <!-- 对话框（审核）-->
        <el-dialog :title="title" :visible.sync="showReview" width="500px" v-dialogDrag append-to-body center>
        <div v-show="reviewList.length != 0">
          <div v-for="(item, index) in reviewList" :key="index">
            <div>创建时间{{ item.createTime }}</div>
            <div>驳回意见{{ item.suggestion }}</div>
            <div style="height: 10px;"></div>
          </div>
        </div>
        <div v-show="reviewList.length === 0">是否通过{{ practice.id }}</div>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="reviewPass"  v-hasPermi="['system:practice-apply:company:review']">审核通过</el-button>

          <el-button @click="reject"  v-hasPermi="['system:practice-apply:company:review']">驳 回</el-button>
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

    </div>
  </template>

  <script>
  import { companyGetPracticeApply, reviewFailurePractice, reviewPassPractice, reviewPracticeApply } from "@/api/system/practiceApply";
  export default {
    data() {
      return {
        // 遮罩层
        loading: true,
        // 导出遮罩层
        exportLoading: false,
        // 显示搜索条件
        showSearch: true,
        // 显示拒绝dialog
        showReject: false,
        // 显示审核dialog
        showReview: false,
        // 总条数
        total: 0,
        // 学校申请实践列表
        list: [],
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 审核记录提示
        reviewList: [],
        // 驳回理由
        rejectReason: '',
        // 实践
        practice: {},
        // 查询参数
        queryParams: {
          pageNo: 1,
          pageSize: 10,
          status: null,
          createTime: [],
        },
        // 表单参数
        form: {},
        // 表单校验
        rules: {
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
        companyGetPracticeApply(this.queryParams).then(response => {
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
          schoolId: undefined,
          practiceId: undefined,
          status: undefined,
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
        this.title = "添加学校申请实践";
      },
      /** 审核 */
      handleReview(row) {
        this.title = '审核提示';
        this.showReview = true;
        this.practice = row;
        reviewPracticeApply(row.id).then(res => {
          this.reviewList = res.data;
        });
      },
      /** 通过审核 */
      reviewPass() {
        this.showReview = false;
        reviewPassPractice(this.practice.id).then(res => {
          this.$modal.msgSuccess("审核通过");
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
        reviewFailurePractice(this.practice.id, this.rejectReason).then(res => {
          this.$modal.msgSuccess('驳回成功');
          this.showReject = false;
        });
      },
    }
  };
  </script>
