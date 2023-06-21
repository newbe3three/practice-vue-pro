<template>
  <div class="dashboard-editor-container" v-loading="loading">

    <panel-group @handleSetLineChartData="handleSetLineChartData" />

    <el-row style="background:#fff;padding:16px 16px 0;margin-bottom:32px;">
      <line-chart :chart-data="lineChartData" />
    </el-row>

    <el-row type="flex" style="padding:16px 16px 0;margin-bottom:32px;" justify="space-between"
      :gutter="20">
      <el-col :span="6">
        <div class="grid-container" @click="gotoPage(0)">
          组织管理
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-container" @click="gotoPage(1)">
          实践管理
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-container" @click="gotoPage(2)">
          资源管理
        </div>
      </el-col>
      <el-col :span="6">
        <div class="grid-container" @click="gotoPage(3)">
          任务管理
        </div>
      </el-col>
    </el-row>

    <!-- <el-row :gutter="32">
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <raddar-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <pie-chart />
        </div>
      </el-col>
      <el-col :xs="24" :sm="24" :lg="8">
        <div class="chart-wrapper">
          <bar-chart />
        </div>
      </el-col>
    </el-row> -->


  </div>
</template>

<script>
import { getHomeDetail } from "@/api/home";
import PanelGroup from './dashboard/PanelGroup'
import LineChart from './dashboard/LineChart'
import RaddarChart from './dashboard/RaddarChart'
import PieChart from './dashboard/PieChart'
import BarChart from './dashboard/BarChart'

const lineChartData = {
  company: {
    number: [100, 120, 161, 134, 105, 160, 165]
    // actualData: [120, 82, 91, 154, 162, 140, 145]
  },
  school: {
    number: [200, 192, 120, 144, 160, 130, 340]
    // actualData: [180, 160, 151, 106, 145, 150, 530]
  },
  student: {
    number: [80, 100, 121, 104, 105, 90, 100]
    // actualData: [120, 90, 100, 138, 142, 130, 130]
  },
}

export default {
  name: 'Index',
  components: {
    PanelGroup,
    LineChart,
    RaddarChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      loading: false,
      homeDetail: {},
      lineChartData: lineChartData.company
    }
  },

  created(){
    this.getHomeDetail();
  },
  methods: {
    handleSetLineChartData(type) {
      this.lineChartData = lineChartData[type]
    },
    getHomeDetail() {
      // this.loading = true;
      getHomeDetail().then(response => {
          this.homeDetail = response.data.homeDetail;
          this.loading = false;
        }
      );
      
    },
    gotoPage(id){
      switch(id){
        case 0:
          this.$router.push('/system/task/task');
          break;
        case 1:
          this.$router.push('/system/task/task');
          break;
        case 2:
          this.$router.push('/system/task/task');
          break;
        case 3:
          this.$router.push('/system/task/task');
          break;
      }
      
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 32px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .chart-wrapper {
    background: #fff;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

.grid-container {
  background: #fff;
  line-height: 80px;
  padding: 20px;
  text-align:center;
  font-weight: 700;
  font-size: 20px;
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }
}</style>
