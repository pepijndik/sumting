<template>
  <OrderSubItem v-bind:key="product.name" v-for="(product, index) in products" :name="product.name"
                :price_per="product.price_per" :target_item="product.target_item" :amount="0" :index="index"
                @update="updateTotal"/>
  <div class="w-full h-12">
    <div class="focus:outline-none focus:ring-2 focus:ring-offset-2 h-12
            focus:ring-candyPink dark:border-gray-700 bg-white font-normal w-1/3 flex text-sm float-right
            border-gray-300 rounded border shadow font-inter justify-between text-left items-center h-10 pl-4 overflow-hidden ">
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
    selectedProjects: Array,
    default: []
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
    },
    findProductsForProjects(val) {
      val.forEach((project) => {
        console.log("test")
        this.ProductApi.findProductByProjectId(project.id).then((response) => {
          this.products.push(response.data);
          console.log(this.products);
        });
      });
    }
  },
  data() {
    return {
      products: [],
      projects: [],
      costArray: [],
      totalCost: 0
    }
  },
  created() {
    this.projects = this.ProjectApi.SearchableDropDown();
    this.products = this.ProductApi.SearchableDropDown();
    // console.log(this.products);
    // for (let i = 0; i < this.selectedProjects.length; i++) {
    //   if (this.selectedProjects[i] === this.ProductApi.findProductByProjectId(19)) {
    //     this.selectedProjects.push(this.ProductApi.findProductByProjectId(19));
    //   }
    // }
    // console.log("Searched for project id=19, result is: " + this.ProductApi.findProductByProjectId(19));
  },
  watch: {
    selectedProjects: function (newVal) {
      console.log("selectedProjects changed");
      this.findProductsForProjects(newVal);
    }
  }
}
</script>

<style scoped>

</style>