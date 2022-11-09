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
      let firstMonth = "";
      const currentDate = new Date();
      currentDate.setMonth(currentDate.getMonth() - 1);
      return currentDate.toLocaleString().split(',');
    },
    secondMonth() {
      const currentDate = new Date();
      currentDate.setMonth(currentDate.getMonth() - 2);
      return currentDate.toLocaleString().split(',')
    },
    thirdMonth() {
      const currentDate = new Date();
      currentDate.setMonth(currentDate.getMonth() - 3);
      return currentDate.toLocaleString().split(',')
    },
  },
  async created() {

    this.projects[0] = await this.DashboardApi.findByMonth("2022-10-01")
    console.log(this.projects[0])
    for (let i = 0; i < this.projects[0].length; i++) {
      this.currentMonth[0]++
    }

    this.projects[1] = await this.DashboardApi.findByMonth("2022-09-03")
    for (let i = 0; i < this.projects[1].length; i++) {
      this.currentMonth[1]++
    }

    this.projects[2] = await this.DashboardApi.findByMonth("2022-08-03")
    for (let i = 0; i < this.projects[2].length; i++) {
      this.currentMonth[2]++
    }

    // console.log(this.currentMonth[0])
    // console.log(this.currentMonth[1])
    // console.log(this.currentMonth[2])
    this.chartData.datasets[0].data = this.currentMonth
    // console.log(this.chartData.datasets[0].data)
  }
}
</script>

<style scoped>

</style>