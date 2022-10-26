<template>
  <div class="mb-4">
    <h4 class="text-candyPink text-base font-inter font-bold uppercase">View</h4>
    <h1 class="text-yInMnBlue text-3xl xl:text-4xl font-Alatsi">Client order</h1>
  </div>
  <div class="flex flex-col sm:flex-row sm:justify-between xl:justify-start">
    <div>
      <p class="font-inter text-yInMnBlue">Client</p>
      <SearchableDropDown
          class="mt-1"
          placeholder="Choose a client">
      </SearchableDropDown>
    </div>
    <div class="mt-3 sm:mt-0">
      <p class="font-inter text-yInMnBlue">Project(s)</p>
      <SearchableDropDown
          class="mt-1"
          placeholder="Select project(s)">
      </SearchableDropDown>
    </div>
  </div>
</template>

<script>
import SearchableDropDown from "@/Components/Form/SearchableDropDown";

export default {
  name: "OrderView",
  components: {SearchableDropDown}
}
</script>

<style scoped>

</style>