<template>
  <OrderSubItem :key="index" v-for="(product, index) in productList"
                :name="product.name" :product="product"
                @update="updateOrderLine($event)"
                :index="index"
                @deleteProduct="deleteProduct"/>
  <div class="w-full h-12" v-if="productList.length > 0">
    <div class="focus:outline-none focus:ring-2 focus:ring-offset-2 h-12 w-1/3 pl-4
            focus:ring-candyPink dark:border-gray-700 bg-white font-normal flex text-sm float-right
            border-gray-300 rounded border shadow font-inter justify-between text-left items-center overflow-hidden">
      <p class="text-yInMnBlue font-bold">Total: {{ totalCost.toFixed(2) }} </p>
    </div>
  </div>
</template>

<script>
import OrderSubItem from "@/Components/Form/SubItems/OrderSubItem";

export default {
  name: "OrderTotalCostSubItem",
  components: {OrderSubItem},
  props: {
    products: {
      type: Array,
      default: Array,
      required: true,
    },
  },


  computed: {
    productList() {
      return this.products;
    },
    lines() {
      return this.orderLines;
    }
  },
  methods: {
    updateOrderLine(event) {
      const {index, OrderLine} = event;

      if (this.orderLines[index] === undefined) {
        console.log("OrderLine added");
        this.orderLines.push(OrderLine);
      } else {
        this.orderLines[index] = OrderLine;
      }
      this.updateTotal(index);
      this.$emit('update', this.orderLines);
    },
    updateTotal() {
      this.totalCost = 0;
      this.recursiveCostCalculator(0)
      this.$emit('updatedTotalCost', this.totalCost);
    },
    recursiveCostCalculator(i) {
      if (i < this.orderLines.length) {
        this.totalCost += this.orderLines[i].transaction_line_total;
        this.recursiveCostCalculator(i + 1);
      }
    },
    deleteProduct(i) {
      this.orderLines.splice(i, 1);
      this.productList.splice(i, 1);
      this.$emit('removeSelected', i);
      this.totalCost = 0;
      this.recursiveCostCalculator(0);
    }
  },
  data() {
    return {
      orderLines: [],
      costArray: [],
      totalCost: 0,
    }
  },

  emits: ['removeSelected', 'updatedTotalCost', 'update']
}
</script>

<style scoped>

</style>