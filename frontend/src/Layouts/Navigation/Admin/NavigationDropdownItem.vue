<template>
  <RouterLink :to="to">
    <div class="flex justify-between cursor-pointer pb-2 mt-6 hover:text-candyPink hover:fill-candyPink font-Alatsi
    border-b-2 border-candyPink"
         @click="openTab"
         v-on:click ="clicked = !clicked"
         v-bind:class="clicked ? 'text-candyPink fill-candyPink' : 'text-yInMnBlue fill-yInMnBlue'">
      <div class="flex">
        <img :src="require(`@/Assets/img/icons/${source}`)" alt="Dropdown icon" width="17" class="mr-2">
        <span v-text="name"></span>
      </div>
      <div v-if="this.showSub()">
        <!-- :style="{ transform: transformValue}" -->
        <svg viewBox="0 0 20 20"
             width="20" height="20"
             xmlns="http://www.w3.org/2000/svg">
          <g><path d="M7 10l5 5 5-5"/></g>
        </svg>
      </div>
    </div>
    <slot v-if="dropdownOpen"/>
  </RouterLink>

</template>

<script>
import {useSlots} from "vue";

export default {
  name: "NavigationDropdownItem",
  props: {
    to: {
      type: String,
      default: "",
    },
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
    /**
     * Open the dropdown item
     */
    openTab() {
      if (this.transformValue === "rotate(0deg)") {
        this.transformValue = "rotate(180deg)";
        this.dropdownOpen = true;
      } else {
        this.transformValue = "rotate(0deg)";
        this.dropdownOpen = false;
      }
    },
    /**
     * Check if the dropdown item has a sub item
     * @type {boolean}
     */
    showSub(){
      const slots= useSlots();
      return !!slots.default
    }
  }
}
</script>

<style scoped>
.hide-on-close {
  display: none;
}
</style>