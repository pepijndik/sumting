<template>
  <OrderSubItem v-bind:key="project.name" v-for="(project, index) in selectedProjects" :name="project.name"
                :price_per="project.price_per" :target_item="project.target_item" :amount="0" :index="index" @update="updateTotal"/>
  <div class="w-full h-12">
    <div class="focus:outline-none focus:ring-2 focus:ring-offset-2 h-12
            focus:ring-candyPink dark:border-gray-700 bg-white font-normal w-1/3 flex text-sm float-right
            border-gray-300 rounded border shadow font-inter justify-between text-left items-center h-10 pl-4 overflow-hidden ">
      <p class="text-yInMnBlue font-bold">Total: {{totalCost}} </p>
    </div>
  </div>
</template>

<script>
import OrderSubItem from "@/Components/Form/SubItems/OrderSubItem";

export default {
  name: "OrderTotalCostSubItem",
  components: {OrderSubItem},
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
      selectedProjects: [
          {name: "New project", price_per: 100, target_item: "item"},
          {name: "New project 2", price_per: 200, target_item: "item 2"},
          {name: "New project 3", price_per: 300, target_item: "item 3"}
      ],
      costArray: [],
      totalCost: 0
    }
  }
}
</script>

<style scoped>

</style>