<template>
  <div>
    <NavigationItem>
      <slot name="titel"></slot>
      <ul class="hidden py-2 space-y-2">
        <slot>

        </slot>
      </ul>
    </NavigationItem>
  </div>
</template>

<script>
import NavigationItem from "@/Layouts/Navigation/Admin/NavigationItem";
export default {
  name: "NavigationDropdownItem",
  components: {NavigationItem}
}
</script>

<style scoped>

</style>