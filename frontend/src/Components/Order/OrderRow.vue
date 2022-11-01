<template>
  <div
    class="dark:border-gray-700 w-full lg:h-10 md:h-20 flex text-sm border-gray-300 border-b mb-5 mt-5 font-Alatsi items-center pt-2 pb-2"
  >
    <div class="m-2 flex lg:flex-row flex-wrap border-r-2 pbb-2">
      <div
        class="float-left items-left flex w-[80px] md:w-[100px] lg:w-[200px] items-center md:border-r-2 pr-1 mr-1"
      >
        <img
          :src="require(`@/Assets/img/icons/file.svg`)"
          alt="File icon"
          width="24"
        />
        <p class="text-yInMnBlue">ID {{ order.id }}</p>
      </div>

      <!-- <div class="divider" /> -->
      <div class="flex flex-row">
        <div
          class="h-5 lg:h-10 items-center flex lg:w-[200px] md:w-[180px] w-[120px] text-xs md:text-sm border-r-2 pr-1 mr-1"
        >
          <p class="text-yInMnBlue">Total amount:&nbsp;</p>
          <p class="text-candyPink">€ {{ order.transactionTotal }}</p>
        </div>

        <!-- <div class="divider" /> -->

        <div
          class="h-5 lg:h-10 items-center flex lg:w-[150px] md:w-[100px] w-[85px] font-Alatsi text-xs md:text-sm"
        >
          <p class="text-yInMnBlue">Date:&nbsp;</p>
          <p class="text-candyPink">{{ orderDate }}</p>
        </div>
      </div>
    </div>

    <!-- <div class="divider" /> -->

    <button
      class="items-center flex bg-yInMnBlue rounded-md lg:w-[150px] md:w-[100px] w-[50px] justify-center h-[32px] mr-2 md:mr-4"
    >
      <img
        :src="require(`@/Assets/img/icons/edit.svg`)"
        alt="Edit icon"
        width="24"
      />
    </button>
    <button
      @click="delOrder()"
      class="items-center flex bg-candyPink rounded-md lg:w-[150px] md:w-[100px] w-[50px] justify-center h-[32px]"
    >
      <img
        :src="require(`@/Assets/img/icons/delete.svg`)"
        alt="Delete icon"
        width="24"
      />
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

    dateFormat(inputDate) {
      //parse the input date
      const date = new Date(inputDate);
      let format = "dd / MM / yy";

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
      format = format.replace(/(^|\/| )0+/g, "$1");

      return format;
    },
  },
  beforeMount() {
    this.orderDate = this.dateFormat(this.order.order_date);
  },
};
</script>

<style scoped>
.divider {
  /*border-left: 2px solid rgb(55 65 81);*/
  border-right: 1px solid rgb(209 213 219);
  height: 26px;
  margin: 0 10px;
}
</style>
