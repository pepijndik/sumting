<template>
  <div>
    <div class="mb-20">
      <!--Input fields name and email-->
      <div class="flex flex-row mb-10">
        <div class="mx-5">
          <p class="font-inter text-yInMnBlue">Name</p>
          <InputComponent
            class="text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-80 h-10 flex focus:border-candyPink focus:border-2 focus:border-l-2 focus:border-r-2 focus:border-b-1 items-center pl-5 text-sm border-gray-300 rounded-md border shadow font-inter"
            :name="'name'"
            placeholder="Enter name of client"
          />
        </div>
        <div>
          <p class="font-inter text-yInMnBlue">Email</p>
          <InputComponent
            class="text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-80 h-10 flex focus:border-candyPink focus:border-2 focus:border-l-2 focus:border-r-2 focus:border-b-1 items-center pl-5 text-sm border-gray-300 rounded-md border shadow font-inter"
            :name="'email'"
            placeholder="Enter email of client"
          />
        </div>
      </div>
      <!--Dropdown Type and Search Dropdown Location-->
      <div>
        <p class="font-Alatsi text-xl text-yInMnBlue">Client Information</p>

        <div class="flex flex-row mt-5">
          <div class="mx-5">
            <p class="font-inter text-yInMnBlue">Type</p>
            <div class="flex flex-col lg:mr-16">
              <div class="relative">
                <input
                  class="rounded text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-40 h-10 flex focus:border-yInMnBlue focus:border items-center px-3 text-sm border-gray-300 focus:rounded-none focus:rounded-t-md border shadow font-inter cursor-pointer"
                  v-model="type"
                  :readonly="readonly"
                  :placeholder="'Type of Client'"
                  @focus="showType()"
                  @blur="showType()"
                />
              </div>
            </div>
            <div
              class="dropdown-one w-full sm:w-40 rounded-b-md outline-none bg-white relative mt-0 shadow-md"
            >
              <!-- Dropdown content -->
              <div
                class="w-full px-3 py-2 absolute rounded-b top-0 right-0 bg-white shadow-lg z-10 max-h-50 border-yInMnBlue border-0 border-b border-l border-r"
                v-show="optionsShown"
              >
                <div
                  class="text-sm flex items-center justify-between text-gray-600 hover:bg-champagnePink hover:text-gray-800 p-1 hover:cursor-default z-10 border-gray-300 border-0 border-b"
                  @mousedown="selectOption('Organization')"
                >
                  Organisation
                </div>
                <div
                  class="text-sm flex items-center justify-between text-gray-600 hover:bg-champagnePink hover:text-gray-800 p-1 hover:cursor-default z-10 border-gray-300 border-0 border-b"
                  @mousedown="selectOption('Fund')"
                >
                  Fund
                </div>
                <div
                  class="text-sm flex items-center justify-between text-gray-600 hover:bg-champagnePink hover:text-gray-800 px-1 pt-1 hover:cursor-default z-10"
                  @mousedown="selectOption('Company')"
                >
                  Company
                </div>
              </div>
            </div>
          </div>
          <div>
            <p class="font-inter text-yInMnBlue">Location</p>
            <SearchableDropDown
              @selected="selectedLocation = $event"
              :options="locations"
              :fields="['name']"
              :primary-key="'id'"
              :disabled="false"
              autocomplete="off"
              placeholder="Search for a location"
            />
          </div>
        </div>
      </div>
      <!--IMG upload-->
      <button @click="click()">
        <div
          v-show="!imagePreview"
          class="ml-5 mt-5 rounded text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-40 h-9 flex focus:border-yInMnBlue focus:border items-center px-3 text-sm border-gray-300 focus:rounded-none focus:rounded-t-md border shadow font-inter"
        >
          <img
            :src="require(`@/Assets/img/icons/upload.svg`)"
            alt="Upload icon"
            width="24"
          />
          <p class="pl-3">Upload Image</p>
        </div>
        <div
          v-show="imagePreview"
          class="ml-5 mt-5 rounded-t text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-40 h-9 flex focus:border-yInMnBlue focus:border items-center px-3 text-sm border-gray-300 focus:rounded-none focus:rounded-t-md border-x border-t shadow font-inter"
        >
          <img
            :src="require(`@/Assets/img/icons/upload.svg`)"
            alt="Upload icon"
            width="24"
          />
          <p class="pl-3">Upload Image</p>
        </div>
      </button>
      <input
        id="fileUpload"
        type="file"
        ref="file"
        class="hidden"
        @change="onSelectFile()"
        accept="image/*"
      />
      <!--IMG preview-->
      <div
        v-show="imagePreview"
        :style="{ 'background-image': `url(${imageData})` }"
        class="w-40 h-40 bg-cover bg-center rounded-b mx-5 border border-gray-300 shadow"
      />
    </div>

    <button
      class="my-2 w-full sm:w-80 bg-candyPink transition duration-150 ease-in-out hover:bg-yInMnBlue rounded text-white font-inter px-8 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600"
    >
      Create client
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
      clicked: false,
      readonly: true,
      type: "",
      optionsShown: false,
      locations: [],
      selectedLocation: null,
      imageData: null,
      imagePreview: false,
      file: null,
    };
  },
  methods: {
    showType() {
      if (this.optionsShown == false) {
        this.optionsShown = true;
      } else {
        this.optionsShown = false;
      }
    },
    selectOption(option) {
      this.type = option;
      console.log("This type has been selected: " + this.type); //Set the search filter to the first field
      this.$emit("selected", this.type);
    },
    click() {
      document.getElementById("fileUpload").click();
    },
    onSelectFile() {
      const input = this.$refs.file;
      const files = input.files;
      if (files && files[0]) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.imageData = e.target.result;
        };
        reader.readAsDataURL(files[0]);
        this.imagePreview = true;
        this.file = files[0];
      }
    },
    createClient() {
      this.UserApi.createClient(
        this.name,
        this.type,
        this.selectedLocation,
        this.file
      );
    },
  },
  async created() {},
};
</script>

<style scoped></style>
