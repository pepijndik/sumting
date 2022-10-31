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
        :options="projects">
    </SearchableDropDown>
    <OrderSubItem v-bind:key="project.name" v-for="(project, index) in selectedProjects" :name="project.name" :price_per="project.price_per" :target_item="project.target_item" :amount="0" :index="index"/>
    <OrderTotalCostSubItem/>
    <button class="my-2 w-full sm:w-80 bg-candyPink transition duration-150 ease-in-out hover:bg-indigo-600 rounded
    text-white font-inter px-8 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600">
      Create order</button>
  </div>
</template>

<script>
import SearchableDropDown from "@/Components/Form/SearchableDropDown";
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import OrderSubItem from "@/Components/Form/SubItems/OrderSubItem";
import OrderTotalCostSubItem from "@/Components/Form/SubItems/OrderTotalCostSubItem";
export default {
  name: "OrderCreate",
  components: {OrderSubItem, OrderTotalCostSubItem, SearchableDropDown},
  inject: ['ProjectApi'],
  data() {
    return {
      editor: ClassicEditor,
      editorData: '<p>Sumting project description</p>',
      editorConfig: {
        toolbar: [ 'heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote' ]
      },
      projects: [],
      selectedProjects: [{name: "New project", price_per: 100, target_item: "item"}, {name: "New project 2", price_per: 200, target_item: "item 2"}, {name: "New project 3", price_per: 300, target_item: "item 3"}]
    }
  },
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
  },
}
</script>

<style scoped>

</style>