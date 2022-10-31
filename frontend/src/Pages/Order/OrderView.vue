<template>
  <div class="flex flex-col sm:flex-row sm:justify-between xl:justify-start">
    <div>
      <p class="font-inter text-yInMnBlue">Client</p>
      <SearchableDropDown
        class="mt-1"
        :options="users"
        :fields="['email', 'user_name']"
        :primary-key="'id'"
        placeholder="Choose a client"
      >
      </SearchableDropDown>
    </div>
    <div class="mt-3 sm:mt-0">
      <p class="font-inter text-yInMnBlue">Project(s)</p>
      <SearchableDropDown
        @selected="selectedProject = $event"
        :options="projects"
        :fields="['name']"
        :primary-key="'id'"
        :disabled="false"
        autocomplete="off"
        :maxItem="10"
        class="mt-1"
        placeholder="Select project(s)"
      >
      </SearchableDropDown>
    </div>
  </div>
  <div
    class="h-80 pl-4 pt-4 mt-4 text-sm border-gray-300 rounded border shadow items-center justify-center"
  >
    <!--    <p v-if="this.orders" class="font-Alatsi text-gray-300 text-base">No client selected.</p>-->
    <div
      class="w-full h-10 items-center pl-10 text-sm justify-between"
      v-for="item in computedObj"
      :key="item.id"
    >
      <OrderRow :order="item" />
    </div>
  </div>
</template>

<script>
import SearchableDropDown from "@/Components/Form/SearchableDropDown";
import OrderRow from "@/Components/Order/OrderRow.vue";

export default {
  name: "OrderView",
  components: { SearchableDropDown, OrderRow },
  inject: ["ProjectApi", "OrderApi", "UserApi"],
  data() {
    return {
      projects: [],
      orders: [],
      users: [],
      selectedProject: null,
      limit: 5,
    };
  },
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
    this.orders = await this.OrderApi.findAll();
    //this.users = await  this.UserApi.findAll();
  },
  computed: {
    computedObj() {
      return this.limit ? this.orders.slice(0, this.limit) : this.orders;
    },
  },
  methods() {},
};
</script>

<style scoped></style>
