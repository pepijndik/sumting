<template>
  <div
    class="h-80 mt-4 text-sm border-gray-300 rounded border shadow overflow-y-scroll overflow-x-hidden scrollbar-thin scrollbar-thumb-yInMnBlue"
  >
    <div class="p-3 flex gap-2 border-0 border-b sm:justify-between">
      <div class="relative sm:w-80">
        <div
          class="absolute text-gray-300 flex items-center pl-4 h-full cursor-pointer"
        >
          <svg
            class="fill-gray-300"
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 487.95 487.95"
            width="17"
            height="17"
            xml:space="preserve"
          >
            <g>
              <g>
                <path
                  d="M481.8,453l-140-140.1c27.6-33.1,44.2-75.4,44.2-121.6C386,85.9,299.5,0.2,193.1,0.2S0,86,0,191.4s86.5,191.1,192.9,191.1
                  c45.2,0,86.8-15.5,119.8-41.4l140.5,140.5c8.2,8.2,20.4,8.2,28.6,0C490,473.4,490,461.2,481.8,453z M41,191.4
                  c0-82.8,68.2-150.1,151.9-150.1s151.9,67.3,151.9,150.1s-68.2,150.1-151.9,150.1S41,274.1,41,191.4z"
                />
              </g>
            </g>
          </svg>
        </div>
        <input
          class="rounded text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full h-10 flex focus:border-yInMnBlue focus:border items-center pl-10 text-sm border-gray-300 border font-inter"
          v-model="searchKeyWord"
          placeholder="Search for client"
        />
      </div>
    </div>
    <div
      class="px-3 w-full h-15 lg:h-10 items-center text-sm snap-y snap-mandatory"
      v-for="user in this.users"
      :key="user.id"
    >
      <ClientRow :client="user" />
    </div>
  </div>
</template>

<script>
import ClientRow from "@/Components/Client/ClientRow.vue";

export default {
  name: "ClientView",
  components: { ClientRow },
  inject: ["UserApi"],
  data() {
    return {
      users: [],
      searchKeyWord: "",
    };
  },
  async created() {
    this.users = await this.UserApi.findAll();
  },
  computed: {},
  methods: {
    deleteOrder(id) {
      //this.OrderApi.delete(id);
      console.log("Deleted: " + id);
    },
  },
};
</script>

<style scoped></style>
