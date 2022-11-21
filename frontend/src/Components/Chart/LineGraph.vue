<template>
  <Line
      :chart-options="chartOptions"
      :chart-data="chartData"
      :chart-id="chartId"
      :width="150"
      :height="50"
  />
</template>

<script>
import {Line} from 'vue-chartjs'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  CategoryScale,
} from 'chart.js'

ChartJS.register(
    Title,
    Tooltip,
    Legend,
    LineElement,
    LinearScale,
    PointElement,
    CategoryScale
)

export default {
  name: "LineGraph",
  components: {
    Line
  },
  data() {
    return {
      chartData: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June'],
        datasets: [
          {
            label: 'Data Line',
            data: [40, 20, 12, 39, 10, 40],
            borderColor: '#E56B6F',
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
      default: 'line-chart'
    }
  },
  metaInfo: {
    title: 'Dashboard',
  }
}
</script>

<style scoped>

</style>