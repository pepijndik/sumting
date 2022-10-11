<template>
  <router-link :to="to" class="pl-6 cursor-pointer text-sm leading-3  hover:fill-shadow tracking-normal pb-4 pt-5 text-indigo-700 focus:text-indigo-700 focus:outline-none">
    <div class="flex items-center text-yInMnBlue  hover:text-shadow font-bold">
      <span class="ml-8" v-text="name"></span>
      <div class="ml-8">
        <slot name="icon"></slot>
      </div>
    </div>
  </router-link>
</template>

<script>
export default {
  name: "NavigationItem",
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