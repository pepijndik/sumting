<template>
  <router-link :to="to" class="focus:outline-none cursor-pointer text-gray-600 dark:text-gray-400 text-sm leading-3 tracking-normal py-3 focus:bg-gray-200 hover:bg-gray-100 px-3 flex items-center" >
    <slot/>
  </router-link>
</template>

<script>


export default {
  name: "DropdownItem",
  props: {
    to: String,
  }
};
</script>
<style scoped>

</style>
