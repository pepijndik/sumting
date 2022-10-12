<template>
  <router-link :to="to" class="cursor-pointer text-sm">
    <div class="flex items-center justify-between text-yInMnBlue font-bold">
      <span v-text="name"></span>
      <slot name="icon"></slot>
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