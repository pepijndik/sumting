<template>
  <h1>Dashboard</h1>
  <Bar
       :chart-options="chartOptions"
       :chart-data="chartData"
       :chart-id="chartId"
       :dataset-id-key="datasetIdKey"
       :width="250"
       :height="80"
  />
</template>

<script>
import SearchableDropDown from "@/Components/Form/SearchableDropDown";
import {Bar} from 'vue-chartjs'
import {Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale} from 'chart.js'

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

export default {
  name: "Dashboard",
  components: {
    Bar
  },
  data() {
    return {
      chartData: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June'],
        datasets: [
          {
            label: 'Data Bar',
            data: [40, 20, 12, 39, 10, 40],
            backgroundColor: '#E56B6F',
            pointBackgroundColor: 'blue',
          }
        ]
      },
      chartOptions: {
        responsive: true
      }
    }
  },
  props: {
    chartId: {
      type: String,
      default: 'bar-chart'
    },
    datasetIdKey: {
      type: String,
      default: 'label'
    },
  },
  metaInfo: {
    title: 'Dashboard',
  }
}
</script>

<style scoped>

</style>