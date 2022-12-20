<template class="">
  <component :is="layout" />
</template>

<script>
import AuthLayout from "@/Layouts/AuthLayout";
import AdminLayout from "@/Layouts/AdminLayout";
import OrderApiService from "@/Services/Order/OrderApiService";
import ProjectApiService from "@/Services/Projects/ProjectApiService";
import AuthenticationService from "@/Services/AuthenticationService";
import UserApiService from "@/Services/User/UserApiService";
import ProductApiService from "@/Services/Products/ProductApiService";
import CountryApiService from "@/Services/Country/CountryApiService";
import DashboardApiService from "./Services/Dashboard/DashboardApiService";
import Currency from "@/Services/Currency";
import FileUploadApiService from "@/Services/FileUploadService";
import BatchApiService from "@/Services/Batch/BatchApiService";
export default {
  name: "App",
  components: {
    AuthLayout,
    AdminLayout,
  },
  provide() {
    return {
      OrderApi: new OrderApiService(),
      Auth: new AuthenticationService(),
      ProjectApi: new ProjectApiService(),
      UserApi: new UserApiService(),
      Curreny: new Currency(),
      ProductApi: new ProductApiService(),
      CountryApi: new CountryApiService(),
      DashboardApi: new DashboardApiService(),
      FileUploadApi: new FileUploadApiService(),
      BatchApi: new BatchApiService()
    };
  },

  data() {
    return {
      layout: null,
    };
  },
  watch: {
    $route(to) {
      // set layout by route meta
      if (to.meta.layout !== undefined) {
        this.layout = to.meta.layout;
      } else {
        this.layout = "AdminLayout"; // this is default layout if route meta is not set
      }
    },
  },
};
</script>

<style></style>
