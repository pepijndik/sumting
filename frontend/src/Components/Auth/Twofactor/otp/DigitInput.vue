<template>
  <div id="otp" class="flex flex-row justify-center text-center px-2 mt-5">
    <input v-for="(numer,index) in maxNumers" @keyup="$emit('update',otp);" class="m-2 border h-10 w-10 text-center form-control rounded focus:ring-yInMnBlue text-candyPink" type="text" maxlength="1" v-model="numer.value" v-bind:key="index"/>
  </div>
</template>

<script>
export default {
  name: "DigitInput",
  props: {
    maxNumers: {
      type: Number,
      default: 6,
      required: false
    }
  },
  computed: {
    otp() {
      return this.maxNumers.map((numer) => numer.value).join("");
    }
  },
  data() {
    return {
      numbers: [],
    };
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