<template>
  <!-- Navigation starts -->
  <nav class="flex items-center justify-between px-6 md:px-8 lg:px-12 py-3 lg:py-5 bg-champagnePink z-20 relative">
    <div>
      <img src="@/Assets/logo.png" alt="logo" class="h-auto w-36 lg:w-48">
    </div>
    <div class="flex gap-2 md:gap-6">
      <div class="w-full flex">
        <div class="w-full flex items-center">
          <Dropdown>
            <template v-slot:toggler>
              <div class="relative">
                <img class="rounded-full h-10 w-10 object-cover"
                     src="https://tuk-cdn.s3.amazonaws.com/assets/components/sidebar_layout/sl_1.png"
                     alt="display avatar" role="img">
              </div>
              <p class="text-yInMnBlue font-bold text-sm ml-3 hidden md:block">David Grey. H</p>
              <button role="button"
                      class="cursor-pointer text-gray-600 ml-2">
                <img src="@/Assets/img/icons/arrow-down.svg"
                     alt="down">
              </button>
            </template>
            <DropdownItem to="/">
              <div class="flex w-full justify-between text-gray-600 hover:text-indigo-700 cursor-pointer items-center">
                <div class="flex items-center">
                  <svg class="h-4 w-4 fill-shadow" xmlns="http://www.w3.org/2000/svg"
                       xmlns:xlink="http://www.w3.org/1999/xlink" version="1.1" viewBox="0 0 256 256"
                       xml:space="preserve">
                    <defs>
                    </defs>
                    <g style="stroke: none; stroke-width: 0; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: none; fill-rule: nonzero; opacity: 1;"
                       transform="translate(1.4065934065934016 1.4065934065934016) scale(2.81 2.81)">
                      <path
                          d="M 85.347 90 c 0 -22.283 -18.064 -40.347 -40.347 -40.347 S 4.653 67.717 4.653 90 H 85.347 z"
                          style="stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 1;"
                          transform=" matrix(1 0 0 1 0 0) " stroke-linecap="round"/>
                      <circle cx="45.003" cy="21.413" r="21.413"
                              style="stroke: none; stroke-width: 1; stroke-dasharray: none; stroke-linecap: butt; stroke-linejoin: miter; stroke-miterlimit: 10; fill: rgb(0,0,0); fill-rule: nonzero; opacity: 1;"
                              transform="  matrix(1 0 0 1 0 0) "/>
                    </g>
                    </svg>
                  My Profile
                </div>
              </div>
            </DropdownItem>
            <DropdownItem to="/">
              <div class="flex w-full justify-between text-gray-600 hover:text-indigo-700 cursor-pointer items-center">
                <div class="flex items-center">
                  <svg class="h-6 w-6 fill-shadow" viewBox="-6 -6 24 24" xmlns="http://www.w3.org/2000/svg"
                       preserveAspectRatio="xMinYMin">
                    <path
                        d='M7.314 5.9l3.535-3.536A1 1 0 1 0 9.435.95L5.899 4.485 2.364.95A1 1 0 1 0 .95 2.364l3.535 3.535L.95 9.435a1 1 0 1 0 1.414 1.414l3.535-3.535 3.536 3.535a1 1 0 1 0 1.414-1.414L7.314 5.899z'/>
                  </svg>
                  Sign out
                </div>
              </div>
            </DropdownItem>
          </Dropdown>
        </div>
      </div>
      <button aria-label="Main Menu" class="visible lg:hidden relative" @click="openMobile">
        <img src="@/Assets/img/icons/hamburger.svg" class="w-12 md:w-10 h-12 md:h-10" alt="toggler">
      </button>
    </div>
  </nav>
  <!-- Navigation ends -->
  <!--Mobile responsive sidebar-->
  <MobileNavigation ref="mNav">
    <template v-slot:items>
      <NavigationItem to="/" name="Dashboard">
        <template v-slot:icon>
          <svg class="fill-yInMnBlue" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24"
               width="17" height="17">
            <path
                d="M 12 2.0996094 L 1 12 L 4 12 L 4 21 L 10 21 L 10 15 L 14 15 L 14 21 L 20 21 L 20 12 L 23 12 L 12
                    2.0996094 z"/>
          </svg>
        </template>
      </NavigationItem>
      <NavigationDropdownItem name="Order" source="shopping_cart.svg">
        <SubDropdownItem to="/orders" name="View order" source="shopping_cart.svg"/>
        <SubDropdownItem to="/orders/create" name="Create order" source="create.svg"/>
      </NavigationDropdownItem>
      <!--          <NavigationDropdownItem to="/" name="Batch" source="file.svg">-->
      <!--            <SubDropdownItem to="/" name="Create batch" source="create.svg"/>-->
      <!--            <SubDropdownItem to="/" name="Edit batch" source="edit.svg"/>-->
      <!--            <SubDropdownItem to="/" name="Upload proof" source="upload.svg"/>-->
      <!--          </NavigationDropdownItem>-->
      <!--          <NavigationDropdownItem to="/" name="Project" source="file.svg">-->
      <!--            <SubDropdownItem to="/" name="Create project" source="create.svg"/>-->
      <!--            <SubDropdownItem to="/" name="Edit project" source="edit.svg"/>-->
      <!--          </NavigationDropdownItem>-->
      <!--          <NavigationDropdownItem to="/" name="Client" source="user.svg">-->
      <!--            <SubDropdownItem to="/" name="Create client" source="create.svg"/>-->
      <!--            <SubDropdownItem to="/" name="Edit client" source="edit.svg"/>-->
      <!--          </NavigationDropdownItem>-->
    </template>
  </MobileNavigation>
  <!--Mobile responsive sidebar-->
</template>

<script>
import Dropdown from "@/Components/Form/Dropdown";
import DropdownItem from "@/Components/Form/Dropdown/DropdownItem";
import MobileNavigation from "@/Layouts/Navigation/Admin/MobileNavigation";
import NavigationItem from "@/Layouts/Navigation/Admin/NavigationItem";
import NavigationDropdownItem from "@/Layouts/Navigation/Admin/NavigationDropdownItem";
import SubDropdownItem from "@/Layouts/Navigation/Admin/SubDropdownItem";

export default {
  name: "DashboardHeaderBar",
  components: {MobileNavigation, NavigationItem, NavigationDropdownItem, SubDropdownItem, DropdownItem, Dropdown},
  methods: {
    openMobile() {
      this.$refs.mNav.toggle();
    },
  },
}
</script>

<style scoped>

</style>