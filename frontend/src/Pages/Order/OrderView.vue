<template>
  <div class="flex flex-col sm:flex-row sm:justify-between xl:justify-start">
    <div>
      <p class="font-inter text-yInMnBlue">Client</p>
      <SearchableDropdown
          class="mt-1"
          @selected="selectedUser = $event"
          :selected-item="selectedUser"
          :options="users"
          :fields="['name', 'email']"
          :text="['name', 'email']"
          :primarykey="'id'"
          placeholder="Choose a client"
      >
      </SearchableDropdown>
    </div>
    <div class="mt-3 sm:mt-0">
      <p class="font-inter text-yInMnBlue">Project(s)</p>
      <SearchableDropdown
          @selected="selectedProject = $event"
          :options="projects"
          :selected-item="selectedProject"
          :fields="['description']"
          :text="['description']"
          :primarykey="'id'"
          :disabled="false"
          autocomplete="off"
          :maxItem="10"
          class="mt-1"
          placeholder="Select project(s)"
      >
      </SearchableDropdown>
    </div>
  </div>
  <div class="h-80 mt-4 text-sm border-gray-300 rounded border shadow overflow-y-scroll overflow-x-hidden
    scrollbar-thin scrollbar-thumb-yInMnBlue">
    <div class="p-3 flex gap-2 border-0 border-b sm:justify-between">
      <div class="relative sm:w-80">
        <div class="absolute text-gray-300 flex items-center pl-4 h-full cursor-pointer">
          <svg class="fill-gray-300" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 487.95 487.95" width="17"
               height="17" xml:space="preserve">
          <g>
            <g>
              <path d="M481.8,453l-140-140.1c27.6-33.1,44.2-75.4,44.2-121.6C386,85.9,299.5,0.2,193.1,0.2S0,86,0,191.4s86.5,191.1,192.9,191.1
                c45.2,0,86.8-15.5,119.8-41.4l140.5,140.5c8.2,8.2,20.4,8.2,28.6,0C490,473.4,490,461.2,481.8,453z M41,191.4
                c0-82.8,68.2-150.1,151.9-150.1s151.9,67.3,151.9,150.1s-68.2,150.1-151.9,150.1S41,274.1,41,191.4z"/>
            </g>
          </g>
          </svg>
        </div>
        <input
            class="rounded text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full
            h-10 flex focus:border-yInMnBlue focus:border items-center pl-10 text-sm border-gray-300
            border font-inter"
            v-model="searchKeyWord"
            placeholder="Search for your order"
        />
      </div>
      <div @click="changeListOrder()" class="flex px-2 font-inter text-yInMnBlue border border-gray-300 rounded
      cursor-pointer">
        <p v-if="searchOrder" class="flex m-auto">
          <span>Old</span>
          <svg
              class="fill-yInMnBlue rotate-90 m-auto"
              width="17"
              height="17"
              viewBox="0 0 36 36"
              version="1.1"
              preserveAspectRatio="xMidYMid meet"
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
          >
            <path
                d="M27.66,15.61,18,6,8.34,15.61A1,1,0,1,0,9.75,17L17,9.81V28.94a1,1,0,1,0,2,0V9.81L26.25,17a1,1,0,0,0,1.41-1.42Z"
                class="clr-i-outline clr-i-outline-path-1"
            ></path>
          </svg>
          <span>New</span>
        </p>
        <p v-if="!searchOrder" class="flex m-auto">
          <span>Old</span>
          <svg
              class="fill-yInMnBlue rotate-270 m-auto"
              width="17"
              height="17"
              viewBox="0 0 36 36"
              version="1.1"
              preserveAspectRatio="xMidYMid meet"
              xmlns="http://www.w3.org/2000/svg"
              xmlns:xlink="http://www.w3.org/1999/xlink"
          >
            <path
                d="M27.66,15.61,18,6,8.34,15.61A1,1,0,1,0,9.75,17L17,9.81V28.94a1,1,0,1,0,2,0V9.81L26.25,17a1,1,0,0,0,1.41-1.42Z"
                class="clr-i-outline clr-i-outline-path-1"
            ></path>
          </svg>
          <span>New</span>
        </p>
      </div>
    </div>
    <div class="px-3 w-full h-15 lg:h-10 items-center text-sm snap-y snap-mandatory"
         v-for="item in computedObj"
         :key="item.id">
      <OrderRow :order="item" @deleteOrderEvent="deleteOrder"/>
    </div>
  </div>
</template>

<script>
import SearchableDropdown from "@/Components/Form/SearchableDropdown";
import OrderRow from "@/Components/Order/OrderRow.vue";

export default {
  name: "OrderView",
  components: {SearchableDropdown, OrderRow},
  inject: ["ProjectApi", "OrderApi", "UserApi"],
  data() {
    return {
      projects: [],
      orders: [],
      users: [],
      selectedUser: null,
      selectedProject: null,
      searchKeyWord: '',
      searchOrder: true,
      limit: 10,
    };
  },
  watch: {
    /**
     * Watch for changes in the selected user and project and search for the orders
     * @param val
     * @param old_val
     */
    selectedProject: function (val, old_val) {
      if (val !== old_val && val !== null && val !== undefined) {
        if (val.id !== null && val.id !== undefined) {
          if (this.selectedUser.id !== undefined) {
            this.getOrdersCombinedSearch(val.id, this.selectedUser.id);
          } else {
            this.getOrdersCombinedSearch(val.id, undefined);
          }
        }
      }
    },
    /**
     * Watch for changes in the selected user and project and search for the orders
     * @param val
     * @param old_val
     */
    selectedUser: function (val, old_val) {
      if (val !== old_val && val !== null && val !== undefined) {
        if (val.id !== null && val.id !== undefined) {
          if (this.selectedProject.id !== undefined) {
            this.getOrdersCombinedSearch(this.selectedProject.id, val.id);
          } else {
            this.getOrdersCombinedSearch(undefined, val.id);
          }
        }
      }
    },
  },
  /**
   * Data initialization and sorting of the orders
   * @returns {Promise<void>}
   */
  async created() {
    this.projects = await this.ProjectApi.SearchableDropDown();
    this.orders = await this.OrderApi.findAll();
    this.users = await this.UserApi.findAll();

    this.orders.sort((a, b) => {
      return this.compareDate(a,b);
    });
  },
  computed: {
    /**
     * Computed object for the search function
     * @returns {*[]|[]|*[]}
     */
    computedObj() {
      const results = [];
      const regKeyWord = new RegExp(this.searchKeyWord, 'ig');

      if (this.searchKeyWord === "") {
        return this.limit ? this.orders.slice(0, this.limit) : this.orders;
      }

      for (const order of this.orders) {
        const string = order.id.toString();
        if (string.match(regKeyWord)) {
          results.push(order);
        }
      }

      return this.limit ? results.slice(0, this.limit) : results;
    },
  },
  methods: {
    /**
     * Function to compare dates
     * @param a
     * @param b
     * @returns {number|undefined|number}
     */
    compareDate(a,b){
      if(a?.order_date != null && b?.order_date != null){
        return a?.order_date?.localeCompare(b?.order_date);
      }
      return 0;
    },
    /**
     * Function to change the list order from old -> new and vice versa
     */
    changeListOrder() {
      if (!this.searchOrder) {
        // From Old -> New
        this.orders.sort((a, b) => {
          return this.compareDate(a,b);
        });
      } else if (this.searchOrder) {
        // From New -> Old
        this.orders.sort((a, b) => {
          return this.compareDate(b,a);
        });
      }

      this.searchOrder = !this.searchOrder;
    },
    /**
     * Function to delete an order with the given id
     * @param id
     */
    deleteOrder(id) {
      this.OrderApi.delete(id);
      console.log("Deleted: " + id);
    },
    /**
     * Does a combined search for the orders depending on the given data
     * @param project
     * @param client
     * @returns {Promise<void>}
     */
    async getOrdersCombinedSearch(project, client) {
      if (project === undefined && client === undefined) {
        this.orders = await this.OrderApi.findAll();
      } else if (project !== undefined && client === undefined) {
        this.orders = await this.OrderApi.combinedSearch(null, project);
      } else if (project === undefined && client !== undefined) {
        this.orders = await this.OrderApi.combinedSearch(client, null);
      } else if (project !== undefined && client !== undefined) {
        this.orders = await this.OrderApi.combinedSearch(project, client);
      }
    },
  },
};
</script>

<style scoped></style>
