<template>
  <Modal :open="modal" source="warning.svg" :callback="test">
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
        You are about to delete batch
        <span class="text-candyPink"> {{ batch.id }} </span>,<br/>
        this action is not reversible.
      </p>
    </template>

    <template #footer>
      <button
          @click="delBatch"
          class="transition duration-150 ease-in-out hover:bg-candyPink/50 bg-candyPink rounded text-white px-4 sm:px-8 py-2 text-xs sm:text-sm"
      >
        Yes, delete it!
      </button>
    </template>
  </Modal>
  <div
      class="dark:border-gray-700 w-full lg:h-10 md:h-20 flex text-sm border-gray-300 border-b font-Alatsi items-center"
  >
    <div class="m-2 flex lg:flex-row flex-wrap border-r-2">
      <div
          class="float-left items-left flex w-[80px] md:w-[100px] lg:w-[200px] items-center md:border-r-2 pr-1 mr-1"
      >
        <img
            :src="require(`@/Assets/img/icons/file.svg`)"
            alt="File icon"
            width="24"
        />
        <p class="bId text-yInMnBlue">ID {{ batch.id }}</p>
      </div>

      <div class="flex flex-row">
        <div
            class="h-5 lg:h-10 items-center flex lg:w-[200px] md:w-[180px] w-[95px] text-xs md:text-sm border-r-2 mr-1"
        >
          <p class="text-yInMnBlue">Batch size:&nbsp;</p>
          <p class="bSize text-candyPink">{{ batch.batchSize }}</p>
        </div>

        <div
            class="h-5 lg:h-10 items-center flex lg:w-[150px] md:w-[120px] w-[95px] font-Alatsi text-xs md:text-sm"
        >
          <p class="text-yInMnBlue">Date:&nbsp;</p>
          <p class="bDate text-candyPink">{{ batchDate }}</p>
        </div>
      </div>
    </div>

    <button
        @click="redirectToEdit"
        class="bEdit items-center flex bg-yInMnBlue hover:bg-champagnePink rounded-md lg:w-[150px] md:w-[100px] w-[50px] justify-center h-[32px] mr-2 md:mr-4"
    >
      <img
          :src="require(`@/Assets/img/icons/edit.svg`)"
          alt="Edit icon"
          width="24"
      />
    </button>
    <button
        @click="confirmDelete"
        class="bDelete items-center flex bg-candyPink hover:bg-champagnePink rounded-md lg:w-[150px] md:w-[100px] w-[50px] justify-center h-[32px]"
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
import moment from "moment";
import Modal from "../Modal/index.vue";

let batchDate;
let mobile;
export default {
  name: "BatchRow",
  props: ["batch"],
  components: {Modal},
  emits: ["deleteBatchEvent"],
  data() {
    return {
      modal: false,
    };
  },
  watch: {
    mobile(newMobile, oldMobile) {
      this.mobile = newMobile;
    },
  },
  methods: {
    /**
     * Creates a delete event for the batch and closes the modal
     */
    delBatch() {
      this.$emit("deleteBatchEvent", this.batch.id);
      this.modal = false;
      //console.log("delete" + this.order.id);
    },
    /**
     * Opens the modal
     *
     * @param ding
     */
    test(ding) {
      this.modal = ding;
    },
    /**
     * Asks for confirmation before deleting the batch by opening the modal
     */
    confirmDelete() {
      this.modal = true;
      console.log(this.modal, this.batch.id);
      console.log("deleted");
    },
    /**
     * Formats the date of the batch
     * @param inputDate
     * @returns {string}
     */
    dateFormat(inputDate) {
      return moment(inputDate).format("DD/MM/YY");
    },
    redirectToEdit() {
      this.$router.push({ path: '/batch/edit/' + this.batch.id });
    }
  },
  beforeMount() {
    let createOrUpdateDate = this.batch.updatedAt == null ? this.batch.createdAt : this.batch.updatedAt;
    this.batchDate = this.dateFormat(createOrUpdateDate);
    this.mobile = window.innerWidth < 640;
  },
};
</script>

<style scoped>
</style>
