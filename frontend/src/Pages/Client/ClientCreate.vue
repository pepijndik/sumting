<template>
  <div>
    <p class="font-inter text-yInMnBlue">Client</p>

    <button
      class="my-2 w-full sm:w-80 bg-candyPink transition duration-150 ease-in-out hover:bg-indigo-600 rounded text-white font-inter px-8 py-3 text-sm focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-600"
    >
      Create order
    </button>
  </div>
</template>

<script>
export default {
  name: "clientCreate",
  components: {},
  inject: ["UserApi"],
  data() {
    return {
      projects: [],
    };
  },
  async created() {},
};
</script>

<style scoped></style>
