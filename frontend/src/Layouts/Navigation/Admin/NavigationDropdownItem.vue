<template>
  <router-link :to="to">
    <div class="flex items-center justify-center cursor-pointer my-4 text-base font-inter font-bold text-yInMnBlue hover:text-candyPink">
      <svg class="fill-yInMnBlue" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" width="24px"
           height="24px">
        <path
            d="M 12 2.0996094 L 1 12 L 4 12 L 4 21 L 10 21 L 10 15 L 14 15 L 14 21 L 20 21 L 20 12 L 23 12 L 12 2.0996094 z"/>
      </svg>
      <span v-text="name"></span>
      <slot name="icon"></slot>
    </div>
  </router-link>
</template>

<script>
export default {
  name: "NavigationDropdownItem",
  props: {
    to: String,
    name: {
      type: String,
      default: "NavDropdownItem"
    }
  }
}
</script>

<style scoped>

</style>