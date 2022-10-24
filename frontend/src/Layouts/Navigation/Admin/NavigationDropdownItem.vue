<template>
  <router-link :to="to">
    <div class="flex justify-between cursor-pointer my-6 mx-2 pb-2 text-yInMnBlue hover:text-candyPink font-bold border-b-2 border-candyPink" @click="openTab">
      <img :src="require(`@/Assets/img/icons/${source}`)" alt="" width="24" class="ml-6">
      <span v-text="name"></span>
      <slot name="icon"></slot>
      <img src="@/Assets/img/icons/dropdown.svg" alt="" class="float-right" :style="{ transform: transformValue}">
    </div>
  </router-link>
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
      transformValue: "rotate(0deg)"
    }
  },
  methods: {
    openTab() {
      if (this.transformValue === "rotate(0deg)") {
        this.transformValue = "rotate(270deg)";
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