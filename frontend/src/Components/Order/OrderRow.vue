<template>
  <div
    class="text-gray-300 dark:border-gray-700 bg-white w-full h-10 flex text-sm border-gray-300 border-b mt-4"
  >
    <div class="float-left items-left flex pb-3 w-[250px]">
      <img
        :src="require(`@/Assets/img/icons/file.svg`)"
        alt="File icon"
        width="24"
        class="ml-2 mr-2 rounded-md pb-2"
      />
      <p class="font-inter text-yInMnBlue font-bold pr-4">ID {{ order.id }}</p>
    </div>

    <div class="divider" />

    <div class="h-10 pb-3 items-center flex w-[200px]">
      <p class="font-inter text-yInMnBlue font-bold">Total amount:&nbsp;</p>
      <p class="font-inter text-candyPink font-bold">
        € {{ order.transactionTotal }}
      </p>
    </div>

    <div class="divider" />

    <div class="h-10 pb-3 items-center flex w-[150px]">
      <p class="font-inter text-yInMnBlue font-bold">Date:&nbsp;</p>
      <p class="font-inter text-candyPink font-bold">{{ orderDate }}</p>
    </div>

    <div class="divider" />

    <button class="float-center items-center justify-between flex pb-2">
      <div
        class="flex bg-yInMnBlue rounded-md m-4 lg:w-[150px] md:w-[100px] justify-center h-[32px]"
      >
        <img
          :src="require(`@/Assets/img/icons/edit.svg`)"
          alt="Edit icon"
          width="24"
          class="ml-2 mr-2"
        />
      </div>
    </button>
    <button
      @click="delOrder()"
      class="float-center items-center justify-between flex pb-2"
    >
      <div
        class="flex bg-candyPink rounded-md m-4 lg:w-[150px] md:w-[100px] justify-center h-[32px]"
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
let orderDate;

export default {
  name: "OrderSubItem",
  props: ["order"],
  methods: {
    delOrder() {
      this.$emit("deleteOrderEvent", this.order.id);
      //console.log("delete" + this.order.id);
    },

    dateFormat(inputDate, format) {
      //parse the input date
      const date = new Date(inputDate);

      //extract the parts of the date
      const day = date.getDate();
      const month = date.getMonth() + 1;
      const year = date.getFullYear();

      //replace the month
      format = format.replace("MM", month.toString().padStart(2, "0"));
      //replace the year
      format = format.replace("yy", year.toString().substr(2, 2));
      //replace the day
      format = format.replace("dd", day.toString().padStart(2, "0"));
      //replaces the leading 0's
      format = format.replace(/(^|\/)0+/g, "$1");

      return format;
    },
  },
  beforeMount() {
    this.orderDate = this.dateFormat(this.order.order_date, "dd/MM/yy");
  },
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
