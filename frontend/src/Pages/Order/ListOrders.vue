<template>
  <Table :items="[1,2]">
    <template v-slot:rowHeader>
      <th class="pl-8 text-gray-600 dark:text-gray-400 font-normal pr-6 text-left text-sm tracking-normal leading-4">
        <input placeholder="check box" type="checkbox"
               class="cursor-pointer relative w-5 h-5 border rounded border-gray-400 bg-white dark:bg-gray-800 focus:outline-none focus:outline-none focus:ring-2  focus:ring-gray-400"
               onclick="checkAll(this)"/>
      </th>
      <th class="text-gray-600 dark:text-gray-400 font-normal pr-6 text-left text-sm tracking-normal leading-4">
        <div class="text-gray-600 dark:text-gray-400 opacity-0 cursor-default relative w-10">
          <div
              class="absolute top-0 right-0 w-5 h-5 mr-2 -mt-1 rounded-full bg-indigo-700 text-white flex justify-center items-center text-xs">
            3
          </div>
          <img class="dark:hidden"
               src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg8.svg"
               alt="icon-tabler-file">
          <img class="dark:block hidden"
               src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg8dark.svg"
               alt="icon-tabler-file">
        </div>
      </th>
      <th role="columnheader"
          class="text-gray-600 dark:text-gray-400 font-normal pr-6 text-left text-sm tracking-normal leading-4">
        Invoice Number
      </th>
      <th role="columnheader"
          class="text-gray-600 dark:text-gray-400 font-normal pr-6 text-left text-sm tracking-normal leading-4">
        Client
      </th>
      <th role="columnheader"
          class="text-gray-600 dark:text-gray-400 font-normal pr-6 text-left text-sm tracking-normal leading-4">
        Company Contact
      </th>
      <th role="columnheader"
          class="text-gray-600 dark:text-gray-400 font-normal pr-6 text-left text-sm tracking-normal leading-4">
        Amount
      </th>
      <th role="columnheader"
          class="text-gray-600 dark:text-gray-400 font-normal pr-6 text-left text-sm tracking-normal leading-4">Date
      </th>
      <th class="text-gray-600 dark:text-gray-400 font-normal pr-6 text-left text-sm tracking-normal leading-4">
        <div class="opacity-0 w-2 h-2 rounded-full bg-indigo-400"></div>
      </th>
    </template>
  </Table>
</template>

<script>
import Table from "@/Components/Table";
export default {
  components: {
    Table
  },
  name: "ListOrders"
}
</script>

<style scoped>

</style>