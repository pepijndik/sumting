<template>
  <div v-if="mobileOpen" class="absolute z-10 w-full h-full overflow-visible xl:hidden">
    <div class="bg-gray-700 opacity-10 absolute h-screen w-screen" @click="toggle"></div>
    <div class="relative overflow-visible sm:relative w-80 bg-champagnePink transition duration-150 ease-in-out
    h-screen shadow-r float-right">
      <div class="flex flex-col h-full w-full">
        <div>
          <NavigationUserProfile/>
        </div>
        <ul class="px-6 md:px-8 lg:px-12">
          <slot name="items"></slot>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import NavigationUserProfile from "@/Components/User/NavigationUserProfile";

export default {
  name: "MobileNavigation",
  components: {NavigationUserProfile},
  data() {
    return {
      mobileOpen: false,
    };
  },

  methods: {
    /**
     * Toggle the mobile navigation
     */
    toggle() {
      this.mobileOpen = !this.mobileOpen;
    },

  }
}
</script>

<style scoped>

</style>