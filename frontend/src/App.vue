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
  },
  metaInfo: {
    // if no subcomponents specify a metaInfo.title, this title will be used
    title: 'Sumting Admin panel',
    // all titles will be injected into this template
    titleTemplate: '%s | Sumting admin panel',
    htmlAttrs: {
      lang: 'en',
      amp: true
    }
  }
}
</script>

<style>
</style>
