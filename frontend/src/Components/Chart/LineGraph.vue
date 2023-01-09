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
import { Line } from "vue-chartjs";
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  CategoryScale,
} from "chart.js";

ChartJS.register(
  Title,
  Tooltip,
  Legend,
  LineElement,
  LinearScale,
  PointElement,
  CategoryScale
);

export default {
  name: "LineGraph",
  components: {
    Line,
  },
  inject: ["DashboardApi"],
  data() {
    return {
      projectDescription: [],
      projectsAmount: [],
      currentMonth: [0, 0, 0],
      chartData: {
        labels: ["Past 30 Days", "Past 60 Days", "Past 90 Days"],
        data: this.currentMonth,
        datasets: [
          {
            data: [],
            label: "Total orders",
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
      default: "line-chart",
    },
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
    let project = ["Restore a coral reef in Kenya", "Restore a coral reef in Turkey",
      "Restore a coral reef in Netherlands"]
    // let projectSize = [];
    this.chartData.labels = project;

    // let projects = await this.DashboardApi.findDescriptions();
    // for (let i = 0; i < project.length.toString(); i++) {
    //   projectSize = await this.DashboardApi.findOrderlineByDescription(project[i]);
    //   console.log()
    // }

    // let data = await this.DashboardApi.findDescriptions();
    // data.forEach((item) => {
    //   console.log(item);
    //   // Do stuff with every description
    // });
    console.log(this.DashboardApi.findDescriptions())
    // console.log(this.DashboardApi.findOrderlineByDescription("Restore a coral reef in Kenya"))
    // let backend = this.DashboardApi.findOrderlineByDescription("Restore a coral reef in Kenya")
  },
};
</script>

<style scoped></style>
