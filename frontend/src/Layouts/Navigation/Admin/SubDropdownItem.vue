<template>
  <router-link :to="to">
    <div class="flex justify-between cursor-pointer my-6 text-yInMnBlue hover:text-candyPink font-bold" @click="openTab">
      <img :src="require(`@/Assets/img/icons/${source}`)" alt="" width="24" class="ml-6">
      <span v-text="name"></span>
      <slot name="icon"></slot>
      <img src="@/Assets/img/icons/dropdown.svg" alt="" class="float-right">
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