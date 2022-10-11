<template>
  <!-- Sidebar starts -->
  <div  @mobileNav="toggle" style="min-height: 700px" class="absolute lg:relative w-64 h-screen shadow bg-champagnePink hidden lg:block">
    <div class="h-16 w-full flex items-center px-8">
      <img src="@/Assets/logo.png" alt="logo">
    </div>
    <div class="m-2">
      <NavigationUserProfile/>
    </div>
    <ul class="py-6">
      <slot name="items"></slot>
    </ul>
  </div>
  <!--Mobile responsive sidebar-->
  <MobileNavigation :open="open"/>
  <!--Mobile responsive sidebar-->
  <!-- Sidebar ends -->
</template>

<script>
import NavigationUserProfile from "@/Components/User/NavigationUserProfile";
import MobileNavigation from "@/Layouts/Navigation/Admin/MobileNavigation";
export default {
  name: "DashboardNavigation",
  components: {MobileNavigation, NavigationUserProfile},
  data() {
    return {
      open: true,
    }
  },
  methods: {
    oke() {
      this.toggle();
    },
    toggle() {
      console.log("Received");
      this.open = !this.open
    },

  }
}
</script>

<style scoped>

</style>