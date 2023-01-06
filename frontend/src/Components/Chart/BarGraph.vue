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
  name: "BarGraph",
  components: {
    Bar,
  },
  inject: ["DashboardApi"],
  data() {
    return {
      projects: [0, 0, 0],
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
      default: "bar-chart",
    },
  },
  computed: {
    firstMonth() {
      let currentDate = new Date();
      let firstDay = currentDate.getDay();
      let firstMonth = currentDate.getMonth();
      let firstYear = currentDate.getFullYear();
      if (firstMonth < 1){
        firstMonth = 12;
        firstYear -= 1;
      }
      return firstYear + "-" + firstMonth + "-" + firstDay;
    },
    secondMonth() {
      let currentDate = new Date();
      let secondDay = currentDate.getDay();
      let secondMonth = currentDate.getMonth() - 1;
      let secondYear = currentDate.getFullYear();
      if (secondMonth === -1){
        secondMonth = 11;
        secondYear -= 1;
      }
      if (secondMonth === 0){
        secondMonth = 12;
      }
      return secondYear + "-" + secondMonth + "-" + secondDay;
    },
    thirdMonth() {
      let currentDate = new Date();
      let thirdDay = currentDate.getDay();
      let thirdMonth = currentDate.getMonth() - 2;
      let thirdYear = currentDate.getFullYear();
      if (thirdMonth === -2){
        thirdMonth = 10;
        thirdYear -= 1;
      }
      if (thirdMonth === -1){
        thirdMonth = 11;
        thirdYear -= 1;
      }
      if (thirdMonth === 0){
        thirdMonth = 12;
        thirdYear -= 1;
      }
      return thirdYear + "-" + thirdMonth + "-" + thirdDay;
    },
  },
  async created() {
    this.projects[0] = await this.DashboardApi.ordersByMonth(this.firstMonth);
    for (let i = 0; i < this.projects[0].length; i++) {
      this.currentMonth[0]++;
    }

    this.projects[1] = await this.DashboardApi.ordersByMonth(this.secondMonth);
    for (let i = 0; i < this.projects[1].length; i++) {
      this.currentMonth[1]++;
    }

    this.projects[2] = await this.DashboardApi.ordersByMonth(this.thirdMonth);
    for (let i = 0; i < this.projects[2].length; i++) {
      this.currentMonth[2]++;
    }

    this.chartData.datasets[0].data = this.currentMonth;
  },
};
</script>

<style scoped></style>
