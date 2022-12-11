<template>
  <transition name="dropdownContent">
    <div v-if="active" class="transition duration-300 opacity-100 p-2 w-32 border-r bg-white absolute rounded left-0 top-12 shadow">
      <slot/>
    </div>
  </transition>
</template>

<script>

export default {
  name: "DropdownContent",
  inject: ["sharedState"],
  computed: {
    active() {

      return this.sharedState.active;
    }
  }

};
</script>

<style>
</style>
