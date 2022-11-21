<template>
  <div class="flex flex-col sm:flex-row sm:justify-between xl:justify-start">
    <div>
      <p class="font-inter text-yInMnBlue">Client</p>
      <SearchableDropDown
          class="mt-1"
          :options="users"
          :fields="['email','user_name']"
          :primary-key="'id'"
          placeholder="Choose a client">
      </SearchableDropDown>
    </div>
    <div class="mt-3 sm:mt-0">
      <p class="font-inter text-yInMnBlue">Project(s)</p>
      <SearchableDropDown
          @selected="selectedProject= $event"
          :options="projects"
          :fields="['name']"
          :primary-key="'id'"
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
  inject: ['ProjectApi','OrderApi','UserApi'],
  data() {
    return {
      projects: [],
      orders: [],
      users:[],
      selectedProject: null,
    };
  },
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
    this.orders = await this.OrderApi.findAll();
    this.users = await  this.UserApi.findAll();
  },

}
</script>

<style scoped>

</style>