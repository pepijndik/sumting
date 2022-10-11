<template>
  <router-link :to="to" class="" >
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
