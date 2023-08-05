<template>
  <div class="app-container">

    <!-- 搜索工作栏 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学校名称" prop="schoolName">
        <el-input v-model="queryParams.schoolId" placeholder="请输入学校名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
      <el-form-item label="实践名称" prop="practiceName">
        <el-input v-model="queryParams.schoolId" placeholder="请输入实践名称" clearable @keyup.enter.native="handleQuery"/>
      </el-form-item>
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

    <!-- 列表 -->
    <el-table v-loading="loading" :data="list">
      <el-table-column label="编号" align="center" prop="id" />
      <el-table-column label="实践名称" align="center" prop="practiceName" />
      <el-table-column label="学校名称" align="center" prop="schoolName" />
      <el-table-column label="状态" align="center" prop="status" />
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
                     v-hasPermi="['system:practice-school:company:review']">通过申请</el-button>
          <span v-show="scope.row.status === 1">已通过</span> 
          <!-- <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)"
                     v-hasPermi="['system:practice-school:delete']">删 除</el-button> -->
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页组件 -->
    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNo" :limit.sync="queryParams.pageSize"
                @pagination="getList"/>

     
  </div>
</template>

<script>
import { companyGetPracticeSchoolPage, passPracticeSchool } from "@/api/system/practiceApply";
export default {
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
      // 学校申请实践列表
      list: [],
      // 查询参数
      queryParams: {
        pageNo: 1,
        pageSize: 10,
        schoolId: null,
        schoolName: null,
        practiceName: null,
        practiceId: null,
        status: null,
        createTime: [],
      },
      // 表单参数
      form: {},   
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
      console.log("12312313", this.queryParams.status);
      companyGetPracticeSchoolPage(this.queryParams).then(response => {
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
    // /** 新增按钮操作 */
    // handleAdd() {
    //   this.reset();
    //   this.open = true;
    //   this.title = "添加学校申请实践";
    // },
    /** 审核 */
    handleReview(row) {
      this.$modal.confirm("是否确认通过"+ row.schoolName + "的申请").then(()=>{
        return passPracticeSchool(row.id);
      }).then(()=>{
        this.$modal.msgSuccess('成功通过申请'); 
      });
    },
  }
};
</script>
