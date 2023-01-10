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
  inject: ["DashboardApi", "ProjectApi"],
  data() {
    return {
      chartData: {
        labels: [],
        data: this.currentMonth,
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
    let data = await this.ProjectApi.findAll(0,false,0);
    let projectDescriptions = [];
    this.chartData.labels = projectDescriptions;

    data.forEach((item) => {
      projectDescriptions.push(item.description_long)
    });


  },
};
</script>

<style scoped></style>
