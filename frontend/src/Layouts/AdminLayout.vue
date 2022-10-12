<template>

  <div class="w-full h-full bg-white">
    <DashboardHeaderBar></DashboardHeaderBar>
    <div class="flex flex-no-wrap">
      <Navigation>
        <template v-slot:items>
          <NavigationItem to="/" name="Dashboard">
            <template v-slot:icon>
              <svg class="fill-yInMnBlue" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24px"
                   height="24px">
                <path
                    d="M 12 2.0996094 L 1 12 L 4 12 L 4 21 L 10 21 L 10 15 L 14 15 L 14 21 L 20 21 L 20 12 L 23 12 L 12 2.0996094 z"/>
              </svg>
            </template>
          </NavigationItem>
          <NavigationDropdownItem>

          </NavigationDropdownItem>
        </template>
      </Navigation>
      <div class="w-full shadow-t-inner lg:shadow-inner">
        <div class="container mx-auto py-10 h-64 md:w-4/5 w-11/12 px-6">
          <div class="w-full h-full rounded border-gray-300">
            <router-view/>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Navigation from "@/Layouts/Navigation/Admin/Navigation";
import DashboardHeaderBar from "@/Layouts/Header/Admin/AdminHeaderBar";
import NavigationItem from "@/Layouts/Navigation/Admin/NavigationItem";
import DashboardSvg from "@/Assets/img/icons/dashboard.svg";

export default {
  name: "DashboardLayout",
  components: {NavigationItem, DashboardHeaderBar, Navigation},
  methods: {
    toggle() {
      console.log("toggle")
      this.mobileOpen = !this.mobileOpen
    },

  },
  data() {
    return {
      mobileOpen: false,
    };
  },
}
</script>

<style scoped>

</style>