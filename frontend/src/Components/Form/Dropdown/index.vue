<template>
  <div @click="toggle" v-click-away="onClickAway" class="flex items-center relative cursor-pointer">
    <slot name="toggler">
      <button>Toggle</button>
    </slot>
    <DropdownContent>
      <slot/>
    </DropdownContent>
  </div>
</template>

<script>
import { mixin as VueClickAway } from "vue3-click-away";
import DropdownContent from "@/Components/Form/Dropdown/DropdownContent";

export default {
  name: 'Dropdown',
  components: {DropdownContent},
  mixins: [VueClickAway],
  provide () {
    return {
      sharedState: this.sharedState
    }
  },
  data () {
    return {
      sharedState: {
        active: false
      }
    }
  },
  methods: {
    toggle () {
      this.sharedState.active = !this.sharedState.active
    },
    onClickAway() {
      this.sharedState.active = false
    }
  }
}
</script>
