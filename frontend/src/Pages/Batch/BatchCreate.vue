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
    <div class="px-3 w-full h-15 lg:h-10 items-center text-sm snap-y snap-mandatory"
         v-for="orderline in orderLines"
         :key="orderline.id"
    >
      <p v-text="orderline.notes"></p>
    </div>
    <p v-if="isEmpty(selectedProject)" v-text="defaultListText"></p>
  </div>
</template>

<script>
import SearchableDropdown from "@/Components/Form/SearchableDropdown";
export default {
  name: "BatchCreate",
  components: {
    SearchableDropdown
  },
  inject: ["ProjectApi", "ProductApi", "OrderApi"],
  data() {
    return {
      projects: [],
      orderLines: [],
      selectedProject: null,
      selectedProjectsProduct: null,
      defaultListText: "No project selected"
    }
  },
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
    console.log(this.isEmpty(this.selectedProject));
  },
  watch: {
    async selectedProject(project) {
      //When the dropdown selection changes it adds the value obtained from this event to the list of projects selected.
      if (project !== null) {
        await this.findProductOfProject(project);
      }
    },
    async selectedProjectsProduct() {
      await this.findOrderlinesByProduct();
    }
  },
  methods: {
    async findProductOfProject(project) {
      if (project.id !== undefined) {
        const data = await this.ProductApi.findProductByProjectId(project.id);
        data.forEach((product) => {
          this.selectedProjectsProduct = product;
        });
      }
    },
    async findOrderlinesByProduct() {
      let currentOrderlines = [];
      const data = await this.OrderApi.getAllOrderlinesByProductId(this.selectedProjectsProduct.id);
      data.forEach((orderline) => {
        currentOrderlines.push(orderline);
      });

      this.orderLines = currentOrderlines;
    },
    isEmpty(obj) {
      for(var prop in obj) {
        if(Object.prototype.hasOwnProperty.call(obj, prop)) {
          return false;
        }
      }

      return JSON.stringify(obj) === JSON.stringify({});
    }
  }
}
</script>

<style scoped>

</style>