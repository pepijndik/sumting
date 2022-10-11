<template>
  <transition name="dropdownContent">
    <div v-if="active" class="p-2 w-full border-r bg-white absolute rounded left-0 shadow mt-12 sm:mt-16">
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
