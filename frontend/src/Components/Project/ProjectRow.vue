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
        You are about to delete Project
        <span class="text-candyPink"> {{ project.id }} </span>,<br/>
        this action is not reversible.
      </p>
    </template>

    <template #footer>
      <button
          @click="delProj"
          class="transition duration-150 ease-in-out hover:bg-candyPink/50 bg-candyPink rounded text-white px-4 sm:px-8 py-2 text-xs sm:text-sm"
      >
        Yes, delete it!
      </button>
    </template>
  </Modal>
  <tr tabindex="0" class="focus:outline-none h-16 border border-gray-100 dark:border-gray-600  rounded font-Alatsi ">
    <td class="pl-5">
      <img
          :src="require(`@/Assets/img/icons/file.svg`)"
          alt="File icon"
          width="24"
      />
      <p class="text-yInMnBlue">ID {{ project.id }}</p>
    </td>
    <td>
      <div class="h-5 lg:h-10 items-center flex text-xs md:text-sm border-r-2 pr-2">
        <p v-if="mobile" class="text-yInMnBlue">Name:&nbsp;</p>
        <p v-else class="text-yInMnBlue">Name:&nbsp;</p>
        <p class="text-candyPink">{{ project.description }}</p>
      </div>
    </td>
    <td>
      <div class="h-5 lg:h-10 items-center flex font-Alatsi text-xs md:text-sm">
        <p class="text-yInMnBlue">Created at:&nbsp;</p>
        <p class="text-candyPink">{{ projectDate }}</p>
      </div>
    </td>
    <td>
      <div class="inline-flex w-full pr-4">
        <button
            class="items-center flex bg-yInMnBlue hover:bg-champagnePink rounded-md  justify-center h-[32px] mr-2 md:mr-4 w-6/12"
        >
          <img
              :src="require(`@/Assets/img/icons/edit.svg`)"
              alt="Edit icon"
              width="24"
          />
        </button>
        <button
            @click="confirmDelete"
            class="items-center flex bg-candyPink hover:bg-champagnePink rounded-md  justify-center h-[32px] w-6/12"
        >
          <img
              :src="require(`@/Assets/img/icons/delete.svg`)"
              alt="Delete icon"
              width="24"
          />
        </button>
      </div>

    </td>
  </tr>
</template>

<script>
import Modal from "../Modal/index.vue";
import moment from "moment";
import Project from "@/Models/Project";

let mobile;
export default {
  name: "ProductRow",
  components: {Modal},
  props: {
    project: {
      type: Project,
      required: true,
    },
  },
  emits: ["deleteProjectEvent"],
  data() {
    return {
      modal: false,
      projectDate: ""
    };
  },
  watch: {
    mobile(newMobile, oldMobile) {
      this.mobile = newMobile;
    },
  },
  methods: {
    /**
     * Emits a deleteProjectEvent for the given project id
     */
    delProj() {
      this.$emit("deleteProjectEvent", this.project.id);
      //console.log("delete" + this.order.id);
    },
    /**
     * Opens or closes the modal
     * @param ding Whether or not to open or close the modal
     */
    test(ding) {
      this.modal = ding;
    },
    /**
     * Asks the user to confirm the deletion of the project
     */
    confirmDelete() {
      this.modal = true;
      console.log(this.modal, this.project.id);
      console.log("deleted");
    },
    /**
     * Formats the date to a more readable format
     * @param inputDate
     * @returns {string}
     */
    dateFormat(inputDate) {
      //parse the input date
      const date = new Date(inputDate);
      return moment(date).format("DD-MM-YYYY");
    },
  },
  beforeMount() {
    this.projectDate = this.dateFormat(this.project.createdAt);
    this.mobile = window.innerWidth < 640;
  },
};
</script>

<style scoped>
</style>
