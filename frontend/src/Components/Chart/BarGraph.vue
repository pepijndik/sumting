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
      projects: [],
      current: new Date(),
      chartData: {
        // projects: [],
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        datasets: [
          {
            label: 'Data Bar',
            data: [65, 60, 40, 50],
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
  methods: {
    // sortedItems() {
    //   this.projects.sort( (a, b) => {
    //     return new Date(a.this.projects.getMonth()) === new Date(b.this.current.getMonth());
    //   });
    //   return this.projects.length;
    // }
  },
  async created() {
    this.projects = await this.DashboardApi.findAll();
    console.log(this.projects.getMonth())
  }
}
</script>

<style scoped>

</style>