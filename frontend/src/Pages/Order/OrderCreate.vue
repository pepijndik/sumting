<template>
  <div>
    <p class="font-inter text-yInMnBlue">Client</p>
    <SearchableDropDown
        class="mt-1"
        placeholder="Choose a client"
        :options="projects">
    </SearchableDropDown>
    <h3 class="font-inter text-2xl text-yInMnBlue font-bold">Order info</h3>
    <p class="font-inter text-yInMnBlue">Description</p>
    <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>

    <p class="font-inter text-yInMnBlue mt-2">Project(s)</p>
    <SearchableDropDown
        class="mt-1 pb-4"
        placeholder="Select project(s)"
        :options="projects"
        :max-items="20"
        @selected="searchSelection = $event"/>
    <OrderTotalCostSubItem :products="products" @removeSelected="removeSelected"/>
    <button class="my-2 w-full sm:w-80 bg-candyPink transition duration-150 ease-in-out hover:bg-indigo-600 rounded
    text-white font-inter px-8 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600">
      Create order
    </button>
  </div>
</template>

<script>
import SearchableDropDown from "@/Components/Form/SearchableDropDown";
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import OrderTotalCostSubItem from "@/Components/Form/SubItems/OrderTotalCostSubItem";

export default {
  name: "OrderCreate",
  components: {OrderTotalCostSubItem, SearchableDropDown},
  inject: ['ProjectApi', "ProductApi"],
  data() {
    return {
      editor: ClassicEditor,
      editorData: '<p>Sumting project description</p>',
      editorConfig: {
        toolbar: ['heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote']
      },
      searchSelection: null,
      selectedProjects: [],
      projects: [],
      products: []
    }
  },
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
  },
  watch: {
    async searchSelection(val) {
      //When the dropdown selection changes it adds the value obtained from this event to the list of projects selected.
      if (val !== null) {
        await this.addProjectToSelected(val);
      }
    },
  },
  methods: {
    removeSelected(i) {
      this.searchSelection = null;
      this.selectedProjects.splice(i, 1);
    },
    async addProjectToSelected(project) {
      let found = false;
      for (let i = 0; i < this.selectedProjects.length; i++) {
        if (this.selectedProjects[i].id === project.id) {
          found = true;
          break;
        }
      }
      if (!found) {
        this.selectedProjects.push(project);
        await this.findProductsForProjects(this.selectedProjects);
      }
    },
    async findProductsForProjects(projects) {
      let tempProducts = [];
      for (const project of projects) {
        //Searches for the product corresponding to the project id.
        if (project.id !== undefined) {
          await this.ProductApi.findProductByProjectId(project.id).then((data) => {
            data.forEach((product) => {
              tempProducts.push(product);
            });
          }).catch((error) => {
            throw error;
          });
        }
      }

      this.products = tempProducts;

    }
  }
}
</script>

<style scoped>

</style>