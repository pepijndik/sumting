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
    <OrderTotalCostSubItem v-if="selectedProjects.length > 0" :products="products"/>
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
    searchSelection(val) {
      //When the dropdown selection changes it adds the value obtained from this event to the list of projects selected.
      this.addProjectToSelected(val);
      console.log(val);
    },
    // selectedProjects(newVal, oldVal) {
    //   console.log("old: " + oldVal);
    //   console.log("new: " + newVal);
    //   this.selectedProjects = newVal;
    //   // this.findProductsForProjects(newVal);
    // }
  },
  methods: {
    addProjectToSelected(project) {
      let found = false;
      for (let i = 0; i < this.selectedProjects.length; i++) {
        if (this.selectedProjects[i].id === project.id) {
          found = true;
          console.log("!!!!!!" + this.selectedProjects[i].id);
        }
      }
      if (!found) {
        this.selectedProjects.push(project);
        this.findProductsForProjects(this.selectedProjects);
      }
      console.log("SELECTED PROJECTS")
      console.log(this.selectedProjects);
    },
    findProductsForProjects(projects) {
      projects.forEach(async (project) => {
        console.log("test")
        //Searches for the product corresponding to the project id.
        await this.ProductApi.findProductByProjectId(project.id).then((response) => {
          this.products.push(response.data);
          console.log(this.products);
        }).catch((error) => {
          console.log(error);
        });
      });
    }
  }
}
</script>

<style scoped>

</style>