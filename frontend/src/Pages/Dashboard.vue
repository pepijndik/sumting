<template>
  <div class="mb-5">
    <h4 class="text-candyPink text-base font-inter font-bold uppercase">Statistics</h4>
    <h1 class="text-yInMnBlue text-3xl xl:text-4xl font-Alatsi">Graphs & Charts</h1>
  </div>
  <BarGraph></BarGraph>
  <LineGraph></LineGraph>
</template>

<script>
import SearchableDropDown from "@/Components/Form/SearchableDropDown";
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