<template>
  <div
    class="text-gray-300 dark:border-gray-700 bg-white font-normal w-full h-10 flex items-center pl-10 text-sm border-gray-300 rounded border shadow font-inter"
  >
    <img
      :src="require(`@/Assets/img/icons/file.svg`)"
      alt="File icon"
      width="24"
      class="ml-2 mr-2 rounded-md"
    />
    <p class="font-inter text-yInMnBlue font-bold pr-4">ID {{ order.id }}</p>
    <div class="divider" />
    <div class="pl-10 pr-10 h-10 items-center justify-between flex">
      <p class="font-inter text-yInMnBlue font-bold pr-4">Total amount:</p>
      <p class="font-inter text-candyPink font-bold">
        € {{ order.transactionTotal }}
      </p>
    </div>
    <div class="divider" />
    <div class="float-right pl-4 h-10 items-center justify-between flex">
      <p class="font-inter text-yInMnBlue font-bold pr-4">Date:</p>
      <p class="font-inter text-candyPink font-bold">{{ order.order_date }}</p>
    </div>
    <div class="divider" />
    <button class="float-center pl-4 h-10 items-center justify-between flex">
      <img
        :src="require(`@/Assets/img/icons/delete.svg`)"
        alt="Close icon"
        width="32"
        class="ml-2 mr-2 bg-candyPink rounded-md"
      />
    </button>
  </div>
</template>

<script>
export default {
  name: "OrderSubItem",
  props: ["order"],
};
</script>

<style scoped>
.divider {
  /*border-left: 2px solid rgb(55 65 81);*/
  border-left: 1px solid rgb(209 213 219);
  height: 26px;
  margin: 0 10px;
}
</style>
