<template>
  <div class="w-full h-full bg-white">
    <DashboardHeaderBar></DashboardHeaderBar>
    <div class="flex flex-no-wrap">
      <Navigation>
        <template v-slot:items>
          <NavigationItem to="/" name="Dashboard">
            <template v-slot:icon>
              <svg
                class="fill-yInMnBlue"
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 24 24"
                width="17"
                height="17"
              >
                <path
                  d="M 12 2.0996094 L 1 12 L 4 12 L 4 21 L 10 21 L 10 15 L 14 15 L 14 21 L 20 21 L 20 12 L 23 12 L 12
                    2.0996094 z"
                />
              </svg>
            </template>
          </NavigationItem>
          <NavigationDropdownItem name="Order" source="shopping_cart.svg">
            <SubDropdownItem
              to="/orders"
              name="View order"
              source="shopping_cart.svg"
            />
            <SubDropdownItem
              to="/orders/create"
              name="Create order"
              source="create.svg"
            />
          </NavigationDropdownItem>
          <NavigationDropdownItem name="Batch" source="file.svg">
            <SubDropdownItem
              to="/batches"
              name="View batches"
              source="file.svg"
            />
            <SubDropdownItem
              to="/batch/create"
              name="Create batch"
              source="create.svg"
            />
          </NavigationDropdownItem>
          <NavigationDropdownItem name="Client" source="user.svg">
            <SubDropdownItem to="/clients" name="Client's" source="user.svg" />
            <SubDropdownItem
              to="/clients/create"
              name="Create Client"
              source="create.svg"
            />
          </NavigationDropdownItem>
          <NavigationDropdownItem name="Project" source="file.svg">
            <SubDropdownItem
              to="/projects"
              name="View projects"
              source="file.svg"
            />
          </NavigationDropdownItem>
        </template>
      </Navigation>
      <div class="w-full shadow-t-inner lg:shadow-inner relative">
        <div class="px-6 md:px-8 lg:px-12 py-8">
          <div class="mb-4">
            <h4
              class="text-candyPink text-base font-inter font-bold uppercase"
              v-text="$route.meta.subtitle"
            ></h4>
            <h1
              class="text-yInMnBlue text-3xl xl:text-4xl font-Alatsi"
              v-text="$route.meta.title"
            ></h1>
          </div>
          <div v-if="isLoading" class="absolute z-40 ml-80 mt-24">
            <semipolar-spinner
              :animation-duration="2000"
              :size="65"
              color="#E56B6F"
            />
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
import NavigationDropdownItem from "@/Layouts/Navigation/Admin/NavigationDropdownItem";
import SubDropdownItem from "@/Layouts/Navigation/Admin/SubDropdownItem";
import { SemipolarSpinner } from "epic-spinners";

export default {
  name: "DashboardLayout",
  inject: ["Auth"],
  components: {
    NavigationItem,
    NavigationDropdownItem,
    SubDropdownItem,
    DashboardHeaderBar,
    Navigation,
    SemipolarSpinner,
  },
  data() {
    return {
      mobileOpen: false,
      isLoading: false,
      axiosInterceptor: null,
    };
  },
};
</script>

<style scoped></style>
