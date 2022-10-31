<template>
  <div class="w-full h-12">
    <div class="focus:outline-none focus:ring-2 focus:ring-offset-2 h-12
            focus:ring-candyPink dark:border-gray-700 bg-white font-normal w-1/3 flex text-sm float-right
            border-gray-300 rounded border shadow font-inter justify-between text-left items-center h-10 pl-4 overflow-hidden ">
      <p class="text-yInMnBlue font-bold" @totalCostUpdate="updateTotal">Total: {{totalCost}} </p>
    </div>
  </div>
</template>

<script>
export default {
  name: "OrderTotalCostSubItem",
  methods: {
    updateTotal(n) {
      this.totalCost += n;
      console.log(this.totalCost);
    }
  },
  data() {
    return {
      totalCost: 0
    }
  }
}
</script>

<style scoped>

</style>