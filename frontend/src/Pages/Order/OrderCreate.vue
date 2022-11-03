<template>
  <div>
    <p class="font-inter text-yInMnBlue">Client</p>
    <SearchableDropdown
        class="mt-1"
        placeholder="Choose a client"
        :options="users"
        :fields="['name', 'email', 'user_role']"
        :primary-key="'id'"
        :disabled="false"
        :max-item="20">
    </SearchableDropdown>
    <h3 class="font-inter text-2xl text-yInMnBlue font-bold">Order info</h3>
    <p class="font-inter text-yInMnBlue">Description</p>
    <ckeditor :editor="editor" v-model="editorData" :config="editorConfig"></ckeditor>

    <p class="font-inter text-yInMnBlue mt-2">Project(s)</p>
<!--    <SearchableDropDown-->
<!--        class="mt-1"-->
<!--        placeholder="Select project(s)"-->
<!--        :options="projects">-->
<!--    </SearchableDropDown>-->
    <OrderSubItem name="Plant Trees in Africa" price_per="0,00" target_item="Tree" amount="0"/>

    <button class="my-2 w-full sm:w-80 bg-candyPink transition duration-150 ease-in-out hover:bg-indigo-600 rounded
    text-white font-inter px-8 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600">
      Create order</button>
  </div>
</template>

<script>
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';
import OrderSubItem from "@/Components/Form/SubItems/OrderSubItem";
import SearchableDropdown from "@/Components/Form/SearchableDropdown";
export default {
  name: "OrderCreate",
  components: {SearchableDropdown, OrderSubItem},
  inject: ['ProjectApi', 'UserApi'],
  data() {
    return {
      editor: ClassicEditor,
      editorData: '<p>Sumting project description</p>',
      editorConfig: {
        toolbar: [ 'heading', '|', 'bold', 'italic', 'link', 'bulletedList', 'numberedList', 'blockQuote' ]
      },
      users: []
    }
  },
  async created() {
    this.users = await this.UserApi.GetAllUsers();
    console.log(this.users)
  },
}
</script>

<style scoped>

</style>