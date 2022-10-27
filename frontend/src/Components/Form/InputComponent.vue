<template>
  <input :id="id"  :name="name" :type="type"  v-model="inputData" @keyup="$emit('update',inputData);"  :autocomplete="autocomplete" :required="required" class="relative block w-full appearance-none rounded-none border border-gray-300 px-3 py-2 text-gray-900 placeholder-gray-500 focus:z-10 focus:border-yInMnBlue focus:outline-none focus:ring-yInMnBlue sm:text-sm" :placeholder="placeholder">
</template>

<script>
export default {
  name: "InputComponent",
  props: {
    id: {
      type: String,
      required: true
    },
    inputData:{
      type: String,
      required: false
    },

    name: {
      type: String,
      required: true
    },
    type: {
      type: String,
      required: true
    },
    autocomplete: {
      type: String,
      required: true
    },
    required: {
      type: Boolean,
      required: true
    },
    placeholder: {
      type: String,
      required: true
    }
  }
}
</script>

<style scoped>

</style>