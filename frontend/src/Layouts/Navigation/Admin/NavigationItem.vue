<template>
  <router-link :to="to">
    <div class="flex items-center justify-between cursor-pointer mt-6 text-yInMnBlue hover:text-candyPink font-Alatsi">
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