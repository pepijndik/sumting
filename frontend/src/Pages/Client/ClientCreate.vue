<template>
  <div>
    <div class="mb-20">
      <!--Input fields name and email-->
      <div class="flex flex-row mb-10">
        <div class="mx-5">
          <p class="font-inter text-yInMnBlue">Name</p>
          <InputComponent
            class="text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-80 h-10 flex focus:border-candyPink focus:border-2 focus:border-l-2 focus:border-r-2 focus:border-b-1 items-center pl-10 text-sm border-gray-300 rounded-md border shadow font-inter"
            :name="name"
            placeholder="Name"
          />
        </div>
        <div>
          <p class="font-inter text-yInMnBlue">Email</p>
          <InputComponent
            class="text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-80 h-10 flex focus:border-candyPink focus:border-2 focus:border-l-2 focus:border-r-2 focus:border-b-1 items-center pl-10 text-sm border-gray-300 rounded-md border shadow font-inter"
            :name="email"
            placeholder="Email"
          />
        </div>
      </div>
      <!--Dropdown Type and Search Dropdown Location-->
      <div>
        <p class="font-inter text-yInMnBlue">Client Information</p>

        <div class="flex flex-row">
          <div class="mx-5">
            <p class="font-inter text-yInMnBlue">Type</p>
            <button
              @click="openTab"
              v-on:click="clicked = !clicked"
              class="text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-40 h-10 flex focus:border-candyPink focus:border-2 focus:border-l-2 focus:border-r-2 focus:border-b-1 items-center justify-end text-sm border-gray-300 rounded-md border shadow font-inter px-2"
            >
              <svg
                viewBox="0 0 20 20"
                width="20"
                height="20"
                xmlns="http://www.w3.org/2000/svg"
              >
                <g><path d="M7 10l5 5 5-5" /></g>
              </svg>
            </button>

            <!-- Dropdown menu -->
            <div
              v-if="dropdownOpen"
              class="z-10 w-30 bg-white rounded divide-y divide-gray-100 shadow dark:bg-gray-700 dark:divide-gray-600"
            >
              <div><p>Organisation</p></div>
              <div><p>Fund</p></div>
              <div><p>Company</p></div>
            </div>
          </div>
          <div>
            <p class="font-inter text-yInMnBlue">Location</p>
            <SearchableDropDown />
          </div>
        </div>
      </div>
      <!--IMG upload-->
    </div>

    <button
      class="my-2 w-full sm:w-80 bg-candyPink transition duration-150 ease-in-out hover:bg-indigo-600 rounded text-white font-inter px-8 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600"
    >
      Create order
    </button>
  </div>
</template>

<script>
import InputComponent from "@/Components/Form/InputComponent.vue";
import SearchableDropDown from "@/Components/Form/SearchableDropDown.vue";

export default {
  name: "clientCreate",
  components: { InputComponent, SearchableDropDown },
  inject: ["UserApi"],
  data() {
    return {
      dropdownOpen: false,
      clicked: false,
      transformValue: "rotate(0deg)",
    };
  },
  methods: {
    openTab() {
      if (this.transformValue === "rotate(0deg)") {
        this.transformValue = "rotate(180deg)";
        this.dropdownOpen = true;
      } else {
        this.transformValue = "rotate(0deg)";
        this.dropdownOpen = false;
      }
    },
  },
  async created() {},
};
</script>

<style scoped></style>
