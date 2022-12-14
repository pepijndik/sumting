<template>
  <div class="flex items-center">
    <input :id="id" :name="name" type="checkbox"  v-model="inputData" @keyup="$emit('update',inputData);"
           class="h-4 w-4 rounded border-gray-300 text-yInMnBlue focus:ring-yInMnBlue"
    >
    <label :for="id" class="ml-2 block text-sm text-gray-900" v-text="placeholder"></label>
  </div>
</template>

<script>
export default {
  name: "Checkbox",
  props: {
    id: {
      type: String,
      required: true
    },
    inputData:{
      type: Boolean,
      default: false,
      required: false
    },
    name: {
      type: String,
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