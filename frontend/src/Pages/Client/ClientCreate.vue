<template>
  <div>
    <div class="mb-20">
      <!--Input fields name and email-->
      <div class="flex flex-row mb-10">
        <div class="mx-5">
          <p class="font-inter text-yInMnBlue">Name</p>
          <input
            class="text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-80 h-10 flex focus:border-candyPink focus:border-2 focus:border-l-2 focus:border-r-2 focus:border-b-1 items-center pl-5 text-sm border-gray-300 rounded-md border shadow font-inter"
            placeholder="Enter name of client"
            v-model="client.name"
          />
        </div>
        <div>
          <p class="font-inter text-yInMnBlue">Email</p>
          <input
            class="text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-80 h-10 flex focus:border-candyPink focus:border-2 focus:border-l-2 focus:border-r-2 focus:border-b-1 items-center pl-5 text-sm border-gray-300 rounded-md border shadow font-inter"
            placeholder="Enter email of client"
            v-model="client.email"
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
                  v-model="client.type"
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
                  @mousedown="selectOption('Business')"
                >
                  Business
                </div>
                <div
                  class="text-sm flex items-center justify-between text-gray-600 hover:bg-champagnePink hover:text-gray-800 p-1 hover:cursor-default z-10 border-gray-300 border-0 border-b"
                  @mousedown="selectOption('Person')"
                >
                  Person
                </div>
              </div>
            </div>
          </div>
          <div>
            <p class="font-inter text-yInMnBlue">Location</p>
            <SearchableDropDown
              @selected="selectedLocation"
              :options="locations"
              :primary-key="'id'"
              :Fields="['name']"
              :disabled="false"
              autocomplete="off"
              placeholder="Search for a location"
              :maxItem="locations.length"
              :optionHasIcon="true"
            />
          </div>
        </div>
      </div>
      <!--IMG upload-->
      <ImgUpload @selectedFile="selectedImg" />
    </div>

    <button
      class="my-2 w-full sm:w-80 bg-candyPink transition duration-150 ease-in-out hover:bg-yInMnBlue rounded text-white font-inter px-8 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600"
      @click="createClient()"
    >
      Create client
    </button>
  </div>
</template>

<script>
import ImgUpload from "@/Components/Form/imgUpload.vue";
import SearchableDropDown from "@/Components/Form/SearchableDropDown.vue";

export default {
  name: "clientCreate",
  components: { SearchableDropDown, ImgUpload },
  inject: ["UserApi", "CountryApi"],
  data() {
    return {
      client: {
        name: "",
        email: "",
        type: "",
        location: "",
        img: "",
      },
      clicked: false,
      readonly: true,
      optionsShown: false,
      locations: [],
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
      this.client.type = option;
      console.log("This type has been selected: " + this.client.type);
    },
    createClient() {
      //this.UserApi.createClient(this.client);
      console.log("Creating client: " + JSON.stringify(this.client));
    },
    selectedLocation(location) {
      this.client.location = location.id;
    },
    selectedImg(img) {
      this.client.img = img.name;
    },
  },
  async created() {
    this.locations = await this.CountryApi.findAll();
  },
};
</script>

<style scoped></style>
