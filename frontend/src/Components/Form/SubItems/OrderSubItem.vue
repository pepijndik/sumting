<template>
  <div class="focus:outline-none focus:ring-2 focus:ring-offset-2
            focus:ring-candyPink dark:border-gray-700 bg-white font-normal w-full h-12 flex
            text-sm border-gray-300 rounded border shadow font-inter justify-between text-left">
    <div class="w-1/3 flex items-center h-10 pl-4 overflow-hidden">
      <p class="text-yInMnBlue font-bold pr-4 ">{{ name }}</p>
    </div>
    <div class="items-center flex">
      <div class="divider"/>
    </div>
    <div class="w-1/3 flex items-center justify-center">
      <p class="text-candyPink font-bold">€ {{ price_per }}</p>
        <p class="text-yInMrBlue font-bold p-1">/</p>
        <p class="text-yInMnBlue font-bold">{{ target_item }}</p>
    </div>
    <div class="items-center flex">
      <div class="divider"/>
    </div>
    <div class="w-1/3 flex items-center">
      <label for="amountToOrder" class="font-inter text-yInMnBlue font-bold p-4 flex">Amount</label>
      <InputComponentNumeric :inputData="amount" @update="amount = $event" id="amount" placeholder="Amount"
                      required autocomplete="" name="amount" type="number" @keyup="emitUpdate"
                      :class="'rounded-md border font-inter text-yInMnBlue font-bold w-1/2 h-8'" ref="amountToOrder"/>
      <img :src="require(`@/Assets/img/icons/close.svg`)" alt="Close icon" width="36"
           class="ml-2 mr-2 bg-candyPink rounded-md">
    </div>
  </div>
</template>

<script>
import InputComponentNumeric from "@/Components/Form/InputComponentNumeric";

export default {
  name: "OrderSubItem",
  components: {InputComponentNumeric},
  props: {
    name: String,
    price_per: {
      type: Number,
      default: 0.00
    },
    target_item: {
      type: String,
      default: "Tree"
    },
    amount: {
      type: Number,
      default: 0
    },
    index: {
      type: Number,
      default: 0
    }
  },
  methods: {
    emitUpdate() {
      console.log(this.amount * this.price_per);
      this.$emit('totalCostUpdate', this.amount * this.price_per, this.index);
    }
  }
}
</script>

<style scoped>

</style>