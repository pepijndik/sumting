<template>
  <div
    class="text-gray-300 dark:border-gray-700 bg-white font-normal w-full h-10 flex text-sm border-gray-300 border-b mt-4"
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
    <button class="float-center h-10 items-center justify-between flex">
      <div
        class="flex bg-yInMnBlue rounded-md m-4 w-[200px] justify-center h-[32px]"
      >
        <img
          :src="require(`@/Assets/img/icons/edit.svg`)"
          alt="Edit icon"
          width="24"
          class="ml-2 mr-2"
        />
      </div>
    </button>
    <button class="float-center h-10 items-center justify-between flex">
      <div
        class="flex bg-candyPink rounded-md m-4 w-[200px] justify-center h-[32px]"
      >
        <img
          :src="require(`@/Assets/img/icons/delete.svg`)"
          alt="Delete icon"
          width="24"
          class="ml-2 mr-2"
        />
      </div>
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
