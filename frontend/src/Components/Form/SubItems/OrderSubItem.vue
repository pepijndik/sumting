<template>
  <div class="text-gray-300 focus:outline-none focus:ring-2 focus:ring-offset-2
            focus:ring-candyPink dark:border-gray-700 bg-white font-normal w-full h-10 flex
            items-center pl-10 text-sm border-gray-300 rounded border shadow font-inter justify-between">
    <p class="font-inter text-yInMnBlue font-bold pr-4">{{ name }}</p>
    <div class="divider"/>
    <div class="pl-10 pr-10 h-10 items-center justify-between flex">
      <p class="font-inter text-candyPink font-bold">€ {{ price_per }}</p>
      <p class="font-inter text-yInMrBlue font-bold">/</p>
      <p class="font-inter text-yInMnBlue font-bold">{{ target_item }}</p>

    </div>
    <div class="divider"/>
    <div class="float-right pl-4 h-10 items-center justify-between flex">
      <label for="amountToOrder" class="font-inter text-yInMnBlue font-bold pr-4">Amount</label>
      <InputComponent :inputData="amount" @update="amount = $event" id="amount" placeholder="Amount"
                      required autocomplete="" name="amount" type="number"
                      :class="'rounded-t-md border font-inter text-yInMnBlue font-bold w-64'" ref="amountToOrder"/>
      <img :src="require(`@/Assets/img/icons/close.svg`)" alt="Close icon" width="32" class="ml-2 mr-2 bg-candyPink rounded-md">
    </div>
  </div>
</template>

<script>
import InputComponent from "@/Components/Form/InputComponent";
export default {
  name: "OrderSubItem",
  components: {InputComponent},
  props: {
    name: String,
    price_per: {
      type: String,
      default: "0,00"
    },
    target_item: {
      type: String,
      default: "Tree"
    },
    amount: {
      type: String,
      default: "0"
    },
  },
}
</script>

<style scoped>
  .divider {
    /*border-left: 2px solid rgb(55 65 81);*/
    border-left: 1px solid rgb(209 213 219);
    height: 26px;
    margin: 0 10px;
  }
</style>