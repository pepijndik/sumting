<template>
  <div class="flex flex-col sm:flex-row sm:justify-between xl:justify-start">
    <div>
      <p class="font-inter text-yInMnBlue">Project</p>
      <SearchableDropdown
          class="mt-1"
          placeholder="Choose a project"
          :fields="['description', 'type.description']"
          :text="['description']"
          :primarykey="'id'"
          :return="'object'"
          :options="projects"
          @selected="selectedProject = $event"
      >
      </SearchableDropdown>
    </div>
    <div class="mt-3 sm:mt-0">
      <p class="font-inter text-yInMnBlue">Amount of contributions</p>
      <div class="flex">
        <input type="number"
               class="w-9/12 sm:w-80 h-10 text-sm text-yInMnBlue font-normal font-inter bg-white
             focus:outline-none rounded focus:border-yInMnBlue focus:border border-gray-300 shadow"
               :disabled="isEmpty(selectedProject)"
               v-bind:class="{'bg-gray-200': isEmpty(selectedProject)}"
               :value="isEmpty(selectedProject) ? '' : defaultContributionAmount">
        <p class="w-3/12 ml-3 my-auto">of <span v-text="isEmpty(selectedProject) ? '0' : orderLines.length" /></p>
      </div>
    </div>
  </div>

  <div>
    <p v-if="isEmpty(selectedProject)" v-text="defaultListText"></p>
    <div v-else class="px-3 w-full h-15 lg:h-10 items-center text-sm snap-y snap-mandatory"
         v-for="orderline in orderLines"
         :key="orderline.id"
    >
      <p v-text="orderline.notes"></p>
    </div>
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
      defaultListText: "No project selected",
      defaultContributionAmount: 5
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