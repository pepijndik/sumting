<template>
  <!-- Sidebar starts -->
  <div class="absolute lg:relative w-80 h-auto min-h-screen bg-champagnePink hidden lg:block">
    <div>
      <NavigationUserProfile/>
    </div>
    <ul class="px-6 md:px-8 lg:px-12">
      <slot name="items"></slot>
    </ul>
    <!-- Sidebar ends -->
  </div>

</template>

<script>
import NavigationUserProfile from "@/Components/User/NavigationUserProfile";

export default {
  name: "DashboardNavigation",
  components: {NavigationUserProfile},


}
</script>

<style scoped>

</style>