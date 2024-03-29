<template>
  <Index :open="modal" source="warning.svg" :callback="closeModal">
    <template #body>
      <h1
        tabindex="0"
        class="focus:outline-none text-center text-yInMnBlue dark:text-gray-100 font-lg font-bold tracking-normal leading-tight mb-4"
      >
        Are you sure?
      </h1>
      <p
        tabindex="0"
        class="focus:outline-none mb-5 text-sm text-yInMnBlue dark:text-gray-400 text-center font-normal"
      >
        You are about to delete client
        <span class="text-candyPink"> {{ client.name }} </span>,<br />
        this action is not reversible.
      </p>
    </template>

    <template #footer>
      <button
        @click="delClient"
        class="transition duration-150 ease-in-out hover:bg-candyPink/50 bg-candyPink rounded text-white px-4 sm:px-8 py-2 text-xs sm:text-sm"
      >
        Yes, delete it!
      </button>
    </template>
  </Index>
  <div
    class="dark:border-gray-700 w-full h-20 flex text-sm border-gray-300 border-b font-Alatsi items-center my-10"
  >
    <div class="m-2 flex lg:flex-row flex-wrap border-r-2">
      <div
        class="float-left items-left flex w-[50px] items-center md:border-r-2 pr-1 mr-1"
      >
        <img
          :src="require(`@/Assets/img/icons/user.svg`)"
          alt="File icon"
          width="24"
        />
      </div>

      <div class="flex flex-row">
        <div
          class="h-20 items-center flex lg:w-[200px] md:w-[180px] w-[95px] text-xs md:text-sm border-r-2 mr-1"
        >
          <p class="text-yInMnBlue">Name:&nbsp;</p>
          <p class="text-candyPink">{{ client.name }}</p>
        </div>

        <div
          class="h-20 items-center flex lg:w-[300px] font-Alatsi text-xs md:text-sm border-r-2"
        >
          <p class="text-yInMnBlue">Email:&nbsp;</p>
          <p class="text-candyPink">{{ client.email }}</p>
        </div>
        <div
          class="h-20 items-center flex lg:w-[300px] font-Alatsi text-xs md:text-sm border-r-2"
        >
          <p class="text-yInMnBlue">Client since:&nbsp;</p>
          <p class="text-candyPink">{{ dateFormat(client.createdAt) }}</p>
        </div>
        <div
          class="h-20 items-center flex lg:w-[100px] font-Alatsi text-xs md:text-sm mx-3"
        >
          <p class="text-yInMnBlue">Type:&nbsp;</p>
          <p class="text-candyPink">{{ client.user_type }}</p>
        </div>
      </div>
    </div>

    <button
      @click="editClient"
      class="items-center flex bg-yInMnBlue hover:bg-champagnePink rounded-md lg:w-[75px] md:w-[50px] w-[25px] justify-center h-[32px] mr-2 md:mr-4"
    >
      <img
        :src="require(`@/Assets/img/icons/edit.svg`)"
        alt="Edit icon"
        width="24"
      />
    </button>
    <button
      @click="confirmDelete"
      class="items-center flex bg-candyPink hover:bg-champagnePink rounded-md lg:w-[75px] md:w-[50px] w-[25px] justify-center h-[32px]"
    >
      <img
        :src="require(`@/Assets/img/icons/delete.svg`)"
        alt="Delete icon"
        width="24"
      />
    </button>
  </div>
</template>

<script>
import Index from "../Modal/index.vue";
import moment from "moment";

export default {
  name: "ClientRow",
  props: ["client"],
  components: { Index },
  emits: ["deleteOrderEvent"],
  data() {
    return {
      modal: false,
    };
  },

  methods: {
    /**
     * Closes the modal and emits the delete event for the client
     */
    delClient() {
      this.$emit("deleteOrderEvent", this.client);
      this.modal = false;
    },
    /**
     * Sends the user to the edit page for the corresponding client
     */
    editClient() {
      this.$router.push("clients/edit/" + this.client.id);
    },
    /**
     * Opens the modal where the user can confirm the deletion of the client
     */
    confirmDelete() {
      this.modal = true;
    },
    /**
     * Closes the modal
     */
    closeModal() {
      this.modal = false;
    },
    /**
     * Formats the date to a more readable format
     * @param inputDate
     * @returns {string}
     */
    dateFormat(inputDate) {
      //parse the input date
      return moment(inputDate).format("DD/MM/YY");
    },
  },
};
</script>

<style scoped>
</style>
