<template>
  <div v-if="mobileOpen" class="absolute w-full h-full z-24 overflow-visible xl:hidden">
    <div class="bg-gray-800 opacity-50 absolute h-screen w-screen" @click="toggle"></div>
    <div class="relative overflow-visible z-40 sm:relative w-64 md:w-96 shadow pb-4 bg-gray-100  transition duration-150 ease-in-out h-screen" >
      <div class="flex flex-col justify-between h-full w-full">
        <ul class="py-6">
          <slot></slot>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "MobileNavigation",
  data() {
    return {
      mobileOpen: false,
    };
  },

  methods: {
    toggle() {
      this.mobileOpen = !this.mobileOpen;
    },

  }
}
</script>

<style scoped>

</style>