<template>
  <BarGraph></BarGraph>
  <LineGraph></LineGraph>
</template>

<script>
import BarGraph from "@/Components/Chart/BarGraph";
import LineGraph from "@/Components/Chart/OrderlineGraph";

export default {
  name: "Dashboard",
  components: {
    BarGraph,
    LineGraph,
  },
};
</script>

<style scoped></style>
