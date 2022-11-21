<template>
  <BarGraph></BarGraph>
  <LineGraph></LineGraph>
</template>

<script>
import SearchableDropdown from "@/Components/Form/SearchableDropDown";
import BarGraph from "@/Components/Chart/BarGraph";
import LineGraph from "@/Components/Chart/LineGraph";

export default {
  name: "Dashboard",
  components: {
    BarGraph,
    LineGraph
  }
}
</script>

<style scoped>

</style>