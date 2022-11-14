<template>
  <OrderSubItem :key="index" v-for="(product,index) in productList" :name="product.name" :product="product"  @update="updateTotal"/>
  <div class="w-full h-12" v-if="productList.length > 0">
    <div class="focus:outline-none focus:ring-2 focus:ring-offset-2 h-12 w-1/3 h-10 pl-4
            focus:ring-candyPink dark:border-gray-700 bg-white font-normal flex text-sm float-right
            border-gray-300 rounded border shadow font-inter justify-between text-left items-center overflow-hidden">
      <p class="text-yInMnBlue font-bold">Total: {{ totalCost }} </p>
    </div>
  </div>
</template>

<script>
import OrderSubItem from "@/Components/Form/SubItems/OrderSubItem";


export default {
  name: "OrderTotalCostSubItem",
  components: {OrderSubItem},
  inject: ['ProductApi', "ProjectApi"],
  props: {
    products: {
      type: Array,
      default: () => [],
      required: true,
    },
  },
  created() {
    this.products.forEach(product => {
      console.log(product);
    });
  },

  computed: {
    productList() {
      return this.products;
    },
  },
  methods: {
    updateTotal(event) {
      this.costArrayForger(event);
      this.totalCost = 0;
      this.recursiveCostCalculator(0)
    },
    costArrayForger(event) {
      if (this.costArray[event.index] === undefined) {
        this.costArray.push(event.total);
      } else {
        this.costArray[event.index] = event.total;
      }
    },
    recursiveCostCalculator(i) {
      if (i < this.costArray.length) {
        this.totalCost += this.costArray[i];
        this.recursiveCostCalculator(i + 1);
      }
    }
  },
  data() {
    return {
      costArray: [],
      totalCost: 0,
    }
  },
  // created() {
    // //NOTE: This was a test for the findProductsForProjects method. It returned an `object Promise`
    // console.log(this.ProductApi.SearchableDropDown());
    // console.log("Searched for project id=19, result is: " + this.ProductApi.findProductByProjectId(19));
  // },
  watch: {
    productList: {
      handler: function (val) {
        console.log("=={ Products have changed }==");
        console.log(val);
        /*
        TODO Items are now not being updated, but somehow do show up after a hotreload....
         (add a character or comment and then go back to the browser without refreshing)
        */
      },
    }
  }
}
</script>

<style scoped>

</style>