<template>
  <button @click="openPreview()">
    <div
        v-show="!imagePreview"
        class="ml-5 mt-5 rounded text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full sm:w-40 h-9 flex focus:border-yInMnBlue focus:border items-center px-3 text-sm border-gray-00 focus:rounded-none focus:rounded-t-md border shadow font-inter"
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
      class="w-40 h-40 bg-cover bg-center rounded-b mx-5 border border-x border-b border-gray-300 shadow"
  />
</template>
<script>
export default {
  name: "imgUpload",
  emits: ["selectedFile"],
  props: {
    previewImg: {
      type: String,
      default: "",
      required: false,
    },
  },
  watch: {
    previewImg(newVal, oldVal) {
      this.imageData = newVal;
      this.imagePreview = true;
    },
  },
  data() {
    return {
      imageData: null,
      imagePreview: false,
    };
  },
  methods: {
    /**
     * Opens the file upload dialog
     */
    openPreview() {
      document.getElementById("fileUpload").click();
    },
    /**
     * If the user selects a file, we emit the file to the parent component and set the image preview
     * @type {File} file - The file that the user selected
     */
    onSelectFile() {
      this.imagePreview = false;
      const input = this.$refs.file;
      const files = input.files;

      if (files && files[0]) {
        const reader = new FileReader();
        reader.onload = (e) => {
          this.imageData = e.target.result;
        };
        reader.readAsDataURL(files[0]);
        this.imagePreview = true;
        this.$emit("selectedFile", files[0]);
      }
    },
  },
  mounted() {
    /**
     * If the user has already uploaded an image, we set the image preview
     */
    if (this.previewImg != "" && this.previewImg != null && !this.previewImg) {
      this.imageData = this.previewImg;
      this.imagePreview = true;
    }
  },
};
</script>
<style lang=""></style>
