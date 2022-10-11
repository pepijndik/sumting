<template>
  <router-link :to="to" class="pl-6 cursor-pointer text-white text-sm leading-3 tracking-normal pb-4 pt-5 text-indigo-700 focus:text-indigo-700 focus:outline-none">
    <div class="flex items-center">
      <div class="w-6 h-6 md:w-8 md:h-8">
        <slot name="icon"></slot>
      </div>
      <span class="ml-2 xl:text-base md:text-2xl text-base" v-text="name"></span>
    </div>
  </router-link>
</template>

<script>
export default {
  name: "MobileNavigationItem",
  props: {
    to: String,
    name: {
      type: String,
      default: "Navitem"
    }
  }
}
</script>

<style scoped>

</style>