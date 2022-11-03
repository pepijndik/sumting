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

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
  name: "BarGraph",
  components: {
    Bar
  },
  inject: ['DashboardApi'],
  data() {
    return {
      projects: Number,
      current: new Date(),
      chartData: {
        // projects: [],
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        datasets: [
          {
            label: 'Orders past 90 days',
            data: [this.projects, 55, 66, 12 ,70],
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
    },
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
    this.projects = await this.DashboardApi.findByMonth(this.defaultEndDate)
    console.log(this.projects.length)
    // console.log(this.projects.length)
  }
}
</script>

<style scoped>

</style>