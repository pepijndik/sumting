<template>
  <div class="focus:outline-none focus:ring-2 focus:ring-offset-2 flex-row flex
            focus:ring-candyPink dark:border-gray-700 bg-white font-normal w-full md:h-12 h-16
            sm:text-sm text-xs border-gray-300 rounded border shadow font-inter justify-between text-left">
    <div class="w-1/3 flex items-center md:pl-4 pl-2 overflow-hidden">
      <p class="text-yInMnBlue font-bold md:pr-4 pr-2">{{ product.project.description }}</p>
    </div>
    <div class="items-center flex">
      <div class="divider"/>
    </div>
    <div class="w-1/3 flex items-center justify-center md:flex-row flex-col pr-2 pl-2">
      <div class="flex-row flex items-center">
        <p class="text-candyPink font-bold">€{{ product.price }}</p>
        <p class="text-yInMrBlue font-bold p-1">/</p>
      </div>
      <p class="text-yInMnBlue font-bold">{{ product.type.product_type_name }}</p>
    </div>
    <div class="items-center flex">
      <div class="divider"/>
    </div>
    <div class="w-1/3 flex items-center md:flex-row flex-row">
      <div class="md:flex-row flex-col flex items-center pl-2 md:pl-0">
        <label for="amountToOrder" class="font-inter text-yInMnBlue font-bold md:p-4 flex items-center">Amount</label>
        <InputComponentNumeric :inputData="amount" @update="amount = $event" id="amount" placeholder="Amount"
                               required autocomplete="" name="amount" type="number" @keyup="emitUpdate"
                               :class="'rounded-md border font-inter text-yInMnBlue font-bold md:w-1/2 w-full h-8'"
                               ref="amountToOrder"/>
      </div>
      <img :src="require(`@/Assets/img/icons/close.svg`)" alt="Close icon" width="36"
           class="ml-2 mr-2 bg-candyPink rounded-md md:w-8 md:h-8 h-6 w-6" @click="this.$emit('deleteProduct', this.index)">
    </div>
  </div>
</template>

<script>
import InputComponentNumeric from "@/Components/Form/InputComponentNumeric";

export default {
  name: "OrderSubItem",
  components: {InputComponentNumeric},
  props: {
    product: {
      type: Object,
      default: () => ({
        project: {
          name: "Tree"
        },
        type: {
          product_type_name: "Tree"
        },
        price: 0.00,
      }),
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