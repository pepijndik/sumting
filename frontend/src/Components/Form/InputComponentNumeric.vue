<template>
  <input :id="id" :name="name" :type="type"
         v-model="inputData"
         v-on:change="$emit('update', inputData);"
         :value="isNaN(inputData) ? '' : inputData"
         :min="min"
         :max="max"
         :step="step"
         :autocomplete="autocomplete"
         :required="required"
         class="relative block w-full appearance-none rounded-none border border-gray-300 px-3 py-2 text-gray-900 placeholder-gray-500 focus:z-10 focus:border-yInMnBlue focus:outline-none focus:ring-yInMnBlue sm:text-sm"
         :placeholder="placeholder">
</template>

<script>
export default {
  name: "InputComponentNumeric",
  props: {
    id: {
      type: String,
      required: true
    },
    inputData: {
      type: Number,
      required: false,
    },

    name: {
      type: String,
      required: true
    },
    max: {
      type: Number,
      default: Infinity,
    },
    disabled: Boolean,
    min: {
      type: Number,
      default: -Infinity,
    },
    step: {
      type: Number,
      default: 1,
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