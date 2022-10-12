<template>
  <!-- Sidebar starts -->
  <div class="absolute lg:relative h-screen px-6 md:px-8 lg:px-12 bg-champagnePink hidden lg:block">
    <div>
      <NavigationUserProfile/>
    </div>
    <ul>
      <slot name="items"></slot>
    </ul>
    <!-- Sidebar ends -->
  </div>

</template>

<script>
import NavigationUserProfile from "@/Components/User/NavigationUserProfile";
import MobileNavigation from "@/Layouts/Navigation/Admin/MobileNavigation";

export default {
  name: "DashboardNavigation",
  components: {NavigationUserProfile},


}
</script>

<style scoped>

</style>