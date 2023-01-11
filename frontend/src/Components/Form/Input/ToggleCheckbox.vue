<template>
  <div class="flex flex-col items-center inline">
    <!-- Code block starts -->
    <div class="cursor-pointer rounded-full bg-gray-200 relative shadow-sm">
      <label :for="id"
             class="toggle-label dark:bg-gray-700 block w-12 h-4 overflow-hidden rounded-full bg-gray-300 cursor-pointer"/>
      <input :aria-label="id" type="checkbox" :name="name" :id="id"
             v-on:click="$emit('update', $event.target.checked)"
             v-model="value"
             class="focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-700 focus:bg-indigo-500
             checkbox w-6 h-6 rounded-full bg-white
             absolute shadow-sm appearance-none cursor-pointer border border-transparent top-0 bottom-0 m-auto"/>
    </div>

    <!-- Code block ends -->

  </div>

</template>

<script>
export default {
  name: "Checkbox",
  props: {
    id: {
      type: String,
      default: String.fromCharCode(Math.floor(Math.random() * 26) + 97),
      required: false,
    },
    value: {
      type: Boolean,
      default: false,
      required: false,
    },
    name: {
      type: String,
      required: true,
    },
    placeholder: {
      type: String,
      default: "Checkbox",
      required: false,
    },
  },
}
</script>

<style scoped>
.checkbox:checked {
  /* Apply class right-0 and border-indigo-700*/
  @apply right-0 border-candyPink;
}

.checkbox:checked + .toggle-label {
  /* Apply class bg-indigo-700 */
  @apply bg-candyPink;
}
</style>