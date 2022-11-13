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
        @selected="searchSelection = $event">
    </SearchableDropDown>
    <OrderTotalCostSubItem v-if="selectedProjects.length > 0" :selectedProjects="selectedProjects"/>
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
  inject: ['ProjectApi'],
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
    }
  },
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
  },
  watch: {
    searchSelection: function (val) {
      this.addProjectToSelected(val);
      console.log("Selected project: " + val);
    }
  },
  methods: {
    addProjectToSelected(project) {
      if (project === Array(1) || project === Object(Array(0, 1))) return;
      let found = false;
      for (let i = 0; i < this.selectedProjects.length; i++) {
        if (this.selectedProjects[i].id === project.id) {
          found = true;
          console.log("!!!!!!" + this.selectedProjects[i].id);
        }
      }
      if (!found) {
        this.selectedProjects.push(project);
      }
      console.log(this.selectedProjects);
    }
  }
}
</script>

<style scoped>

</style>