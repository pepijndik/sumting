<template>
  <div class="flex flex-col sm:flex-row sm:justify-between xl:justify-start">
    <div>
      <p class="font-inter text-yInMnBlue">Client</p>
      <SearchableDropdown
        class="mt-1"
        :options="users"
        :fields="['name', 'email', 'user_role']"
        :primary-key="'id'"
        placeholder="Choose a client"
      >
      </SearchableDropdown>
    </div>
    <div class="mt-3 sm:mt-0">
      <p class="font-inter text-yInMnBlue">Project(s)</p>
      <SearchableDropdown
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
      </SearchableDropdown>
    </div>
  </div>
  <div
    class="h-80 pl-4 pt-4 mt-4 text-sm border-gray-300 rounded border shadow overflow-y-scroll overflow-x-hidden scrollbar-thin scrollbar-thumb-yInMnBlue"
  >
    <!--    <p v-if="this.orders" class="font-Alatsi text-gray-300 text-base">No client selected.</p>-->
    <div
      class="w-full h-10 items-center text-sm pr-4 snap-y snap-mandatory"
      v-for="item in computedObj"
      :key="item.id"
    >
      <OrderRow :order="item" @deleteOrderEvent="this.deleteOrder" />
    </div>
  </div>
</template>

<script>
import SearchableDropdown from "@/Components/Form/SearchableDropdown";
import OrderRow from "@/Components/Order/OrderRow.vue";

export default {
  name: "OrderView",
  components: { SearchableDropdown, OrderRow },
  inject: ["ProjectApi", "OrderApi", "UserApi"],
  data() {
    return {
      projects: [],
      orders: [],
      users: [],
      selectedProject: null,
      limit: 20,
    };
  },
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
    // this.orders = await this.OrderApi.findAll();
    this.users = await this.UserApi.GetAllUsers();
  },
  computed: {
    computedObj() {
      return this.limit ? this.orders.slice(0, this.limit) : this.orders;
    },
  },
  methods: {
    deleteOrder(id) {
      this.OrderApi.delete(id);
    },
  },
};
</script>

<style scoped></style>
