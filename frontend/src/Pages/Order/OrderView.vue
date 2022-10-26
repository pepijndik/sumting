<template>
  <div class="flex flex-col sm:flex-row sm:justify-between xl:justify-start">
    <div>
      <p class="font-inter text-yInMnBlue">Client</p>
      <SearchableDropDown
          class="mt-1"
          :options="[]"
          placeholder="Choose a client">
      </SearchableDropDown>
    </div>
    <div class="mt-3 sm:mt-0">
      <p class="font-inter text-yInMnBlue">Project(s)</p>
      <SearchableDropDown
          :options="projects"
          :disabled="false"
          autocomplete="off"
          :maxItem="10"
          class="mt-1"
          placeholder="Select project(s)">
      </SearchableDropDown>
    </div>
  </div>
  <div class="flex h-80 pl-4 pt-4 mt-4 text-sm border-gray-300 rounded border shadow">
    <p class="font-Alatsi text-gray-300 text-base">No client selected.</p>
  </div>
</template>

<script>
import SearchableDropDown from "@/Components/Form/SearchableDropDown";

export default {
  name: "OrderView",
  components: {SearchableDropDown},
  inject: ['ProjectApi'],
  data() {
    return {
      projects: [],
    };
  },
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
  },

}
</script>

<style scoped>

</style>