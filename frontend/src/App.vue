<template class="">
  <div>
    <div v-if="isLoading" class="absolute z-40 top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">
      <semipolar-spinner
          :animation-duration="2000"
          :size="65"
          color="#E56B6F"
      />
    </div>
    <component :is="layout" />
  </div>
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
import AuthHeader from "@/Services/AuthHeader";
import BaseApi from "@/Services/BaseApi";
import BatchApiService from "@/Services/Batch/BatchApiService";
import {SemipolarSpinner} from "epic-spinners";
export default {
  name: "App",
  components: {
    AuthLayout,
    AdminLayout,
    SemipolarSpinner
  },
  inject: ['axios'],
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
      BatchApi: new BatchApiService(),
    };
  },

  data() {
    return {
      layout: null,
      isLoading: false,
    };
  },
  mounted() {
    this.enableInterceptor();
  },
  unmounted() {
    //this.disableInterceptor();
  },
  methods:{
    enableInterceptor() {
      this.axiosInterceptor = this.axios.interceptors.request.use((config) => {
        //Intercept request and add token
        this.isLoading = true
        config.headers['Authorization'] = `${AuthHeader()?.Authorization}`
        return config
      }, (error) => {
        this.isLoading = false;
        console.log(error)

        return Promise.reject(error)
      })

      this.axios.interceptors.response.use((response) => {
        this.isLoading = false
        return response
      }, function(error) {
        this.isLoading = false;
        if(error.response.status === 401) {
          console.log("User was not logged in, redirect to login")
          this.Auth.logout();
        }
        Event.$emit('error', 500, error.response.data.message)
        return Promise.reject(error)
      })
    },

    disableInterceptor() {
      this.axios.interceptors.request.eject(this.axiosInterceptor)
    },
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
