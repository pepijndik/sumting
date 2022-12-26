<template>
  <button @click="openPreview()">
    <div
        class="mt-5 rounded text-yInMnBlue focus:outline-none dark:border-gray-700 bg-white font-normal w-full
        sm:w-40 h-9 flex focus:border-yInMnBlue focus:border items-center px-3 text-sm border-gray-00 focus:rounded-none
        focus:rounded-t-md border shadow font-inter">
      <img :src="require(`@/Assets/img/icons/upload.svg`)"
           alt="Upload icon"
           width="24"/>
      <p class="pl-3">{{ text }}</p>
    </div>
  </button>
  <input
      id="fileUpload"
      type="file"
      ref="file"
      class="hidden"
      @change="onSelectFile()"
      accept="file/*"
  />
</template>
<script>
export default {
  name: "fileUpload",
  emits: ["selectedFile", "fileTypeError"],
  props: {
    allowedTypes: {
      type: Array,
      default: () => ["text/*"],
      required: false,
    },
    text: {
      type: String,
      default: "Upload File",
      required: false,
    }
  },
  data() {
    return {
      fileData: null,
    };
  },
  methods: {
    openPreview() {
      document.getElementById("fileUpload").click();
    },
    onSelectFile() {
      const input = this.$refs.file;
      const files = input.files;

      if (files && files[0]) {
        this.fileData = files[0];
        console.log(this.fileData.type);
        if (this.allowedTypes.includes(this.fileData.type)) {
          this.$emit("selectedFile", this.fileData);
        } else {
          this.$emit("fileTypeError", "File type not allowed");
        }
      }
    },
  },
};
</script>
<style lang=""></style>
