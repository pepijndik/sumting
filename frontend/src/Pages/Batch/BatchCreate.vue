<template>
  <div class="grid w-full grid-cols-1 lg:grid-cols-3 md:grid-cols-1 gap-1 mt-7">
    <div>
      <p class="font-inter text-yInMnBlue">Project</p>
      <SearchableDropdown
          class="mt-1"
          placeholder="Choose a project"
          :fields="['description', 'type.description']"
          :text="['description']"
          :primarykey="'id'"
          :return="'object'"
          @selected="selectedProject = $event"
          :options="projects">
      </SearchableDropdown>
    </div>
  </div>

  <div>
    <p v-if="selectedProject !== null" v-text="selectedProject.description_long"></p>
  </div>
</template>

<script>
import SearchableDropdown from "@/Components/Form/SearchableDropdown";
export default {
  name: "BatchCreate",
  components: {
    SearchableDropdown
  },
  inject: ['ProjectApi'],
  data() {
    return {
      projects: [],
      selectedProject: null
    }
  },
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
  }
}
</script>

<style scoped>

</style>