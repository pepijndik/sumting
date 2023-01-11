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
    // let projectSize = [];
    let projectSize = [2,5,12,10,16,5,2,1,4,2];

    data.forEach((item) => {
      projectDescriptions.push(item.description_long)
    });

    // projectDescriptions.forEach((async item => {
    //   projectPerNote.push(await this.OrderApi.getAllOrderlinesByNotes(item));
    // }))

    // projectPerNote.forEach((item => {
    //   projectSize.push(item.length)
    // }))

    // console.log(this.OrderApi.getAllOrderlinesByNotes("Restoring kelp forest in Portugal"))

    this.chartData.labels = projectDescriptions;
    this.chartData.datasets[0].data = projectSize;
  },
};
</script>

<style scoped></style>
