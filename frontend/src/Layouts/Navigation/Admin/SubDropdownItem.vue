<template>
  <router-link :to="to">
    <div class="flex justify-between cursor-pointer my-6 text-yInMnBlue hover:text-candyPink font-bold">
      <span v-text="name" class="ml-10"></span>
      <img :src="require(`@/Assets/img/icons/${source}`)" alt="" width="24" class="mr-6">
      <slot name="icon"></slot>
    </div>
  </router-link>
</template>

<script>
export default {
  name: "SubDropdownItem",
  props: {
    to: String,
    name: {
      type: String,
      default: "NavDropdownItem"
    },
    source: {
      type: String,
      default: "@/Assets/img/icons/dashboard.svg"
    }
  },
}
</script>

<style scoped>

</style>