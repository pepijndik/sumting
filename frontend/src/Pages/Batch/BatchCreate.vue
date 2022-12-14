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
    <p v-text="selectedProject"></p>
  </div>
</template>

<script>
import SearchableDropdown from "@/Components/Form/SearchableDropdown";
export default {
  name: "BatchCreate",
  components: {
    SearchableDropdown
  },
  inject: ["ProjectApi", "ProductApi"],
  data() {
    return {
      projects: [],
      orderLines: [],
      selectedProject: null
    }
  },
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
  },
  watch: {
    async selectedProject(project) {
      //When the dropdown selection changes it adds the value obtained from this event to the list of projects selected.
      if (project !== null) {
        await this.findProductOfProject(project);
      }
    },
  },
  methods: {
    async findProductOfProject(project) {
      if (project.id !== undefined) {
        const data = await this.ProductApi.findProductByProjectId(project.id);
        data.forEach((product) => {
          this.selectedProjectsProduct = product;
        });
      }
    }
  }
}
</script>

<style scoped>

</style>