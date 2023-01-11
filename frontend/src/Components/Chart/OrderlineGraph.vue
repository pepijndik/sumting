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
import { Bar } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
} from "chart.js";

ChartJS.register(
    Title,
    Tooltip,
    Legend,
    BarElement,
    CategoryScale,
    LinearScale
);

export default {
  name: "OrderlineGraph",
  components: {
    Bar,
  },
  inject: ["DashboardApi", "ProjectApi", "OrderApi"],
  data() {
    return {
      chartData: {
        labels: [],
        // data: this.currentMonth,
        datasets: [
          {
            data: [],
            label: "Total order lines",
            backgroundColor: "#E56B6F",
          },
        ],
      },
      chartOptions: {
        responsive: true,
      },
    };
  },
  props: {
    chartId: {
      type: String,
      default: "bar-chart",
    },
  },
  async created() {
    let data = await this.ProjectApi.findAll(0,false,0);
    let projectDescriptions = [];
    let projectPerNote = [];
    // let projectSize = [20,45,45,26,75,62,45,45,12,45];
    let projectSize = [20,45,45,26,75,62,45,45,12,45];

    data.forEach((item) => {
      projectDescriptions.push(item.description_long)
    });

    // projectDescriptions.forEach((item =>{
    //   projectPerNote.push(this.OrderApi.getAllOrderlinesByNotes(item));
    // }))
    //
    // projectPerNote.forEach((item => {
    //   projectSize.push(item.length)
    // }))

    console.log(this.OrderApi.getAllOrderlinesByNotes("Restoring kelp forests in Portugal"))

    this.chartData.labels = projectDescriptions;
    this.chartData.datasets[0].data = projectSize;
  },
};
</script>

<style scoped></style>
