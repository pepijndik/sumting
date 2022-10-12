<template>
  <router-link :to="to">
    <div class="flex items-center justify-between cursor-pointer my-6 text-yInMnBlue hover:text-candyPink font-bold">
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