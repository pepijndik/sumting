<template class="">
  <component :is="layout"/>
</template>

<script>
import AuthLayout from "@/Layouts/AuthLayout";
import AdminLayout from "@/Layouts/AdminLayout";

export default {
  name: 'App',
  components: {
    AuthLayout,
    AdminLayout,
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
        this.layout = to.meta.layout
      } else {
        this.layout = "AdminLayout" // this is default layout if route meta is not set
      }
    },
  }
}
</script>

<style>
</style>
