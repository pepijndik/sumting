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
        <div class="container px-6 md:px-8 lg:px-12 py-8">
          <div class="mb-4">
            <h4 class="text-candyPink text-base font-inter font-bold uppercase">{{ this.subTitle }}</h4>
            <h1 class="text-yInMnBlue text-3xl xl:text-4xl font-Alatsi">{{ this.title }}</h1>
          </div>
          <router-view />
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Navigation from "@/Layouts/Navigation/Admin/Navigation";
import DashboardHeaderBar from "@/Layouts/Header/Admin/AdminHeaderBar";
import NavigationItem from "@/Layouts/Navigation/Admin/NavigationItem";

export default {
  name: "DashboardLayout",
  components: {NavigationItem, DashboardHeaderBar, Navigation},
  data() {
    return {
      mobileOpen: false,
      title: "Graphs & Charts",
      subTitle: "Statistics"
    };
  }
}
</script>

<style scoped>

</style>