<template>
  <Bar
      :chart-options="chartOptions"
      :chart-data="chartData"
      :chart-id="chartId"
      :width="150"
      :height="50"
  />
</template>

<script>
import {Bar} from 'vue-chartjs'
import {Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale} from 'chart.js'
import axios from "axios";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
  name: "BarGraph",
  components: {
    Bar
  },
  inject: ['DashboardApi'],
  data() {
    return {
      projects: [],
      currentMonths: 0,
      chartData: {
        labels: ['Past 30 Days', 'Past 60 Days', 'Past 90 Days'],
        datasets: [
          {
            label: 'Orders past 90 days',
            data: [40, 60 , 50],
            backgroundColor: '#E56B6F',
            pointBackgroundColor: 'blue',
          }
        ]
      },
      chartOptions: {
        responsive: true,
      }
    }
  },
  props: {
    chartId: {
      type: String,
      default: 'bar-chart'
    }
  },
  metaInfo: {
    title: 'Dashboard',
  },
  computed: {
    defaultEndDate() {
      const currentDate = new Date();
      currentDate.setMonth(currentDate.getMonth() - 1);
      return new Date(currentDate.toJSON().slice(0, 10));
    }
  },
  async created() {
    // this.projects = await this.DashboardApi.findByMonth(this.defaultEndDate)
    //
    // for (let i = 0; i < this.projects.length; i++) {
    //   this.currentMonths++;
    // }
    // console.log(this.chartData.datasets.data)
    // this.projects = await this.DashboardApi.findByMonth(this.defaultEndDate)
    //
    // console.log(this.projects)
  }
}
</script>

<style scoped>

</style>