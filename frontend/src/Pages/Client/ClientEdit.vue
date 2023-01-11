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
                    @mousedown="selectOption('BUSINESS')"
                >
                  BUSINESS
                </div>
                <div
                    class="text-sm flex items-center justify-between text-gray-600 hover:bg-champagnePink hover:text-gray-800 p-1 hover:cursor-default z-10 border-gray-300 border-0 border-b"
                    @mousedown="selectOption('PERSON')"
                >
                  PERSON
                </div>
              </div>
            </div>
          </div>
          <div>
            <p class="font-inter text-yInMnBlue">Location</p>
            <SearchableDropdown
                :primarykey="'id'"
                :options="locations"
                :fields="['name']"
                autocomplete="off"
                placeholder="Search for a location"
                :optionHasIcon="true"
                :text="['name', 'alpha2']"
                :selectedItem="userCountry"
                :max-items="249"
                return="primarykey"
                @selected="selectLocation"
                :icon="true"
                :imgField="'imgSmall'"
            />
          </div>
        </div>
      </div>
      <!--IMG upload-->
      <ImgUpload @selectedFile="selectedImg" :previewImg="this.client.img"/>
    </div>

    <button
        class="my-2 w-full sm:w-80 bg-candyPink transition duration-150 ease-in-out hover:bg-yInMnBlue rounded text-white font-inter px-8 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600"
        @click="updateClient()"
    >
      Update client
    </button>
  </div>
</template>

<script>
import ImgUpload from "@/Components/Form/imgUpload.vue";
import SearchableDropdown from "@/Components/Form/SearchableDropdown.vue";

export default {
  name: "clientCreate",
  components: {ImgUpload, SearchableDropdown},
  inject: ["UserApi", "CountryApi", "FileUploadApi"],
  data() {
    return {
      client: {
        id: "",
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
      imgFile: null,
      user: null,
      userCountry: null,
    };
  },
  methods: {
    /**
     * Toggles the type
     */
    showType() {
      this.optionsShown = this.optionsShown === false;
    },
    /**
     * Selects the option
     * @param option
     */
    selectOption(option) {
      this.client.type = option;
    },
    /**
     * Updates the client
     */
    async updateClient() {
      let user;
      console.log(this.client);
      if (this.client.name != "" &&
          this.client.email != "" &&
          this.client.type != "" &&
          this.client.location != "") {

        user = await this.UserApi.updateUser(
            this.client.id,
            this.client.name,
            this.client.email,
            this.client.location,
            this.client.type,
            this.client.img
        );

        this.$toast.open({
          type: "success",
          message: "Client Updated",
          duration: 5000,
          dismissible: true,
          position: "top-right",
        });
      } else {
        this.$toast.open({
          type: "error",
          message: "Please fill in all fields",
          duration: 5000,
          dismissible: true,
          position: "top-right",
        });
      }

      if (this.imgFile != null) {
        try {
          await this.FileUploadApi.uploadIMG(user.me.id, this.imgFile);
        } catch (e) {
          this.$toast.open({
            type: "error",
            message: "Img upload failed",
            duration: 5000,
            dismissible: true,
            position: "top-right",
          });
        }
      }
    },
    /**
     * Selects the location
     * @param location
     */
    selectLocation(location) {
      console.log(location);
      this.client.location = location;
    },
    /**
     * Selects the img
     * @param img
     */
    selectedImg(img) {
      this.imgFile = img;
    },
  },
  /**
   * Initializes the data
   * @returns {Promise<void>}
   */
  async created() {
    this.locations = await this.CountryApi.findAll();
    this.user = await this.UserApi.findOne(this.$route.params.id);
    console.log(this.user);
    this.client.id = this.user.data.id;
    this.client.name = this.user.data.name;
    this.client.email = this.user.data.email;
    this.client.type = this.user.data.user_type;
    this.client.location = this.user.data.country.id;

    this.userCountry = this.user.data.country;
    if (this.user.data.profileImage != null) {
      this.client.img = this.user.data.profileImage;
    }
    this.selectedLocation = this.client.location;
  },
};
</script>

<style scoped></style>
