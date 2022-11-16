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
      projects: [0, 0, 0],
      currentMonth: [0, 0, 0],
      chartData: {
        labels: ['Past 30 Days', 'Past 60 Days', 'Past 90 Days'],
        data: this.currentMonth,
        datasets: [
          {
            data: [],
            label: 'Total orders',
            backgroundColor: '#E56B6F',
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
  computed: {
    firstMonth() {
      const currentDate = new Date();
      const firstDay = currentDate.getDay();
      const firstMonth = currentDate.getMonth() - 1;
      const firstYear = currentDate.getFullYear();
      return firstYear + "-" + firstMonth + "-" + firstDay;
    },
    secondMonth() {
      const currentDate = new Date();
      const secondDay = currentDate.getDay();
      const secondMonth = currentDate.getMonth() - 2;
      const secondYear = currentDate.getFullYear();
      return secondYear + "-" + secondMonth + "-" + secondDay;
    },
    thirdMonth() {
      const currentDate = new Date();
      const thirdDay = currentDate.getDay();
      const thirdMonth = currentDate.getMonth() - 3;
      const thirdYear = currentDate.getFullYear();
      return thirdYear + "-" + thirdMonth + "-" + thirdDay;
    },
  },
  async created() {

    this.projects[0] = await this.DashboardApi.findByMonth(this.firstMonth)
    for (let i = 0; i < this.projects[0].length; i++) {
      this.currentMonth[0]++
    }

    this.projects[1] = await this.DashboardApi.findByMonth(this.secondMonth)
    for (let i = 0; i < this.projects[1].length; i++) {
      this.currentMonth[1]++
    }

    this.projects[2] = await this.DashboardApi.findByMonth(this.thirdMonth)
    for (let i = 0; i < this.projects[2].length; i++) {
      this.currentMonth[2]++
    }

    this.chartData.datasets[0].data = this.currentMonth
  }
}
</script>

<style scoped>

</style>