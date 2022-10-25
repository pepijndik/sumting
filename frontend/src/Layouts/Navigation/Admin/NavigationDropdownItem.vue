<template>
  <div class="flex justify-between cursor-pointer pb-2 mt-6 hover:text-candyPink hover:fill-candyPink font-Alatsi
    border-b-2 border-candyPink"
       @click="openTab"
       v-on:click ="clicked = !clicked"
       v-bind:class="clicked ? 'text-candyPink fill-candyPink' : 'text-yInMnBlue fill-yInMnBlue'">
    <div class="flex">
      <img :src="require(`@/Assets/img/icons/${source}`)" alt="Dropdown icon" width="17" class="mr-2">
      <span v-text="name"></span>
    </div>
    <slot name="icon"></slot>
    <svg :style="{ transform: transformValue}" width="24px" height="24px" viewBox="0 0 24 24"
         xmlns="http://www.w3.org/2000/svg"><rect x="0" fill="none" width="17" height="17"/>
      <g><path d="M7 10l5 5 5-5"/></g>
    </svg>
  </div>
  <slot v-if="dropdownOpen"/>
</template>

<script>
export default {
  name: "NavigationDropdownItem",
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
  data() {
    return {
      dropdownOpen: false,
      clicked: false,
      transformValue: "rotate(0deg)"
    }
  },
  methods: {
    openTab() {
      if (this.transformValue === "rotate(0deg)") {
        this.transformValue = "rotate(180deg)";
        this.dropdownOpen = true;
      } else {
        this.transformValue = "rotate(0deg)";
        this.dropdownOpen = false;
      }
    }
  }
}
</script>

<style scoped>
.hide-on-close {
  display: none;
}
</style>