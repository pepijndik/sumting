<template>
  <div id="otp" class="flex flex-row justify-center text-center px-2 mt-5">
    <input v-for="(num,index) in maxNumbers"
           v-on:keyup="focusNext($event.target)"
           ref="digitinput"
           class="m-2 border h-10 w-10 text-center form-control rounded focus:ring-yInMnBlue text-candyPink"
           type="text" maxlength="1"
           v-model="otpData[num]"
           v-bind:key="index"
    />
  </div>
</template>

<script>
export default {
  name: "DigitInput",
  emits: ["update"],
  props: {
    otpData: {
      type: Array,
      default: () => [],
      required: false
    },
    maxNumbers: {
      type: Number,
      default: 6,
      required: false
    }
  },
  computed: {
    /**
     * Returns the otp data as a string
     * @returns {string}
     */
    otp() {
      return this.otpData.map((number) => number).join("");
    }
  },
  data() {
    return {
      numbers: [],
    };
  },
  methods: {
    /**
     * Focuses the next input field
     * @param el the sibling element of the next input field
     */
    focusNext(el) {
      if (el.value.length === 1) {
        el.nextElementSibling?.focus();
        if( el.nextElementSibling === null){
          this.$emit('update',this.otp);
        }

      }
    },
  },
}
</script>

<style scoped>
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* Firefox */
input[type=number] {
  -moz-appearance: textfield;
}
</style>