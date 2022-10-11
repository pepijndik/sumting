<template>
  <div>
    <button aria-label="Main Menu" class="text-gray-600 mr-8 visible lg:hidden relative focus:outline-none focus:ring-2 focus:ring-gray-600 " @click="toggle">
      <img src="https://tuk-cdn.s3.amazonaws.com/can-uploader/light_with_header_and_icons-svg7.svg" alt="toggler">
    </button>
  </div>
  <div v-if="mobileOpen" class="absolute w-full h-full z-24 overflow-visible xl:hidden">
    <div class="bg-gray-800 opacity-50 absolute h-screen w-screen" @click="toggle"></div>
    <div class="relative overflow-visible z-40 sm:relative w-64 md:w-96 shadow pb-4 bg-gray-100  transition duration-150 ease-in-out h-screen" >
      <div class="flex flex-col justify-between h-full w-full">
        <div>
          <div class="flex items-center justify-between px-8">
            <div class="h-16 w-full flex items-center">
              <img src="@/Assets/logo.png" alt="logo">

            </div>
            <button aria-label="close sidebar" id="closeSideBar"
                    class="flex items-center justify-center h-10 w-10 focus:outline-none focus:ring-2 focus:ring-gray-800 rounded"
                    @click="toggle">
              <img src="https://tuk-cdn.s3.amazonaws.com/can-uploader/light_with_header_and_icons-svg2.svg"
                   alt="cross">
            </button>
          </div>
          <ul class="py-6">
            <slot></slot>
          </ul>
        </div>
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