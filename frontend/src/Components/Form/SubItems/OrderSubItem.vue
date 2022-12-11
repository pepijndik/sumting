<template>
  <div class="focus:outline-none focus:ring-2 focus:ring-offset-2 md-flex-col flex-row
            focus:ring-candyPink dark:border-gray-700 bg-white font-normal w-full md-h-12 h-20 flex
            sm-text-sm text-xs border-gray-300 rounded border shadow font-inter justify-between text-left">
    <div class="w-1/3 flex items-center h-20 md-h-10 pl-4 overflow-hidden">
      <p class="text-yInMnBlue font-bold pr-4 ">{{ product.project.description }}</p>
    </div>
    <div class="items-center flex">
      <div class="divider"/>
    </div>
    <div class="w-1/3 flex items-center justify-center md-flex-row flex-col">
      <div class="flex-row flex items-center">
        <p class="text-candyPink font-bold">€{{ product.price }}</p>
        <p class="text-yInMrBlue font-bold p-1">/</p>
      </div>
      <p class="text-yInMnBlue font-bold">{{ product.type.product_type_name }}</p>
    </div>
    <div class="items-center flex">
      <div class="divider"/>
    </div>
    <div class="w-1/3 flex items-center">
      <div class="md-flex-row flex-col">
        <label for="amountToOrder" class="font-inter text-yInMnBlue font-bold p-4 flex">Amount</label>
        <InputComponentNumeric :inputData="amount" @update="amount = $event" id="amount" placeholder="Amount"
                               required autocomplete="" name="amount" type="number" @keyup="emitUpdate"
                               :class="'rounded-full border font-inter text-yInMnBlue font-bold md-w-1/2 w-full h-8 place-self-center'"
                               ref="amountToOrder"/>
      </div>
      <img :src="require(`@/Assets/img/icons/close.svg`)" alt="Close icon" width="36"
           class="ml-2 mr-2 bg-candyPink rounded-md" @click="this.$emit('deleteProduct', this.index)">
    </div>
  </div>
</template>

<script>
import InputComponentNumeric from "@/Components/Form/InputComponentNumeric";
import Product from "@/Models/Product";

export default {
  name: "OrderSubItem",
  components: {InputComponentNumeric},
  props: {
    product: {
      type: Product,
      required: true,
    },
    index: {
      type: Number,
      default: 0,
      required: true,
    }
  },
  data() {
    return {
      amount: 0,


    }
  },
  methods: {
    emitUpdate() {
      let total = Math.round(this.amount * this.product.price * 100) / 100;
      this.$emit('update', {total: total, index: this.index});
    }
  },
  watch: {
    amount: function () {
      if (!Number(this.amount)) {
        this.amount = 0;
      }
    }
  },
  emits: ['update', 'deleteProduct'],
}
</script>

<style scoped>
  .divider {
    width: 1px;
    height: 60%;
    background-color: #E2E8F0;
  }
</style>