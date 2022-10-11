<template>
  <div class="flex flex-col lg:flex-row p-4 lg:p-8 justify-between items-start lg:items-stretch w-full">
    <div class="w-full lg:w-1/3 flex flex-col lg:flex-row items-start lg:items-center">
      <div class="flex items-center">
        <slot name="tableHeaderActions">
          <button
              class="text-gray-600 dark:text-gray-400 p-2 border-transparent border bg-gray-100 dark:hover:bg-gray-600 dark:bg-gray-700 hover:bg-gray-200 cursor-pointer rounded focus:outline-none focus:border-gray-800 focus:shadow-outline-gray"
              aria-label="Edit Table" role="button">
            <img class="dark:hidden"
                 src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg1.svg"
                 alt="Edit">
            <img class="dark:block hidden"
                 src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg1dark.svg"
                 alt="Edit">
          </button>
          <button
              class="text-gray-600 dark:text-gray-400 mx-2 p-2 border-transparent border bg-gray-100 dark:hover:bg-gray-600 dark:bg-gray-700 hover:bg-gray-200 cursor-pointer rounded focus:outline-none focus:border-gray-800 focus:shadow-outline-gray"
              aria-label="table settings" role="button">
            <img class="dark:hidden"
                 src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg2.svg"
                 alt="settings">
            <img class="dark:block hidden"
                 src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg2dark.svg"
                 alt="settings">
          </button>
          <button
              class="text-gray-600 dark:text-gray-400 mr-2 p-2 border-transparent border bg-gray-100 dark:hover:bg-gray-600 dark:bg-gray-700 hover:bg-gray-200 cursor-pointer rounded focus:outline-none focus:border-gray-800 focus:shadow-outline-gray"
              aria-label="Bookmark" role="button">
            <img class="dark:hidden"
                 src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg3.svg"
                 alt="Bookmark">
            <img class="dark:block hidden"
                 src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg3dark.svg"
                 alt="Bookmark">
          </button>
          <button
              class="text-gray-600 dark:text-gray-400 mr-2 p-2 border-transparent border bg-gray-100 dark:hover:bg-gray-600 dark:bg-gray-700 hover:bg-gray-200 cursor-pointer rounded focus:outline-none focus:border-gray-800 focus:shadow-outline-gray"
              aria-label="copy table" role="button">
            <img class="dark:hidden"
                 src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg4.svg"
                 alt="">
            <img class="dark:block hidden"
                 src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg4dark.svg"
                 alt="">
          </button>
          <button
              class="text-red-500 p-2 border-transparent border bg-gray-100 dark:bg-gray-700 dark:hover:bg-gray-600 hover:bg-gray-200 cursor-pointer rounded focus:outline-none focus:border-gray-800 focus:shadow-outline-gray"
              aria-label="delete table" role="button">
            <img src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg5.svg"
                 alt="delete">
          </button>
        </slot>
      </div>
    </div>
    <div class="w-full lg:w-2/3 flex flex-col lg:flex-row items-start lg:items-center justify-end">
      <div class="flex items-center lg:border-l lg:border-r border-gray-300 py-3 lg:py-0 lg:px-6">
        <slot name="pagination">
          <p class="text-base text-gray-600 dark:text-gray-400">Viewing 1 - 20 of 60</p>
          <button
              class="text-gray-600 dark:text-gray-400 ml-2 border-transparent border cursor-pointer rounded focus:outline-none focus:ring-2 focus:ring-gray-500 "
              onclick="pageView(false)" aria-label="Goto previous page " role="button">
            <img class="dark:hidden"
                 src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg6.svg"
                 alt="">
            <img class="dark:block hidden" src="../svgs/svg6dark.svg" alt="">
          </button>
          <button
              class="text-gray-600 dark:text-gray-400 border-transparent border rounded focus:outline-none focus:ring-2 focus:ring-gray-500 cursor-pointer"
              aria-label="goto next page" role="button" onclick="pageView(true)">
            <img class="transform rotate-180 dark:hidden"
                 src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg6.svg"
                 alt="">
            <img class="transform rotate-180 dark:block hidden" src="../svgs/svg6dark.svg" alt="">
          </button>
        </slot>
      </div>
      <div class="lg:ml-6 flex items-center">
        <slot name="headerButtonRight">
          <button class="bg-gray-200 transition duration-150 ease-in-out focus:outline-none border border-transparent focus:ring-2 focus:ring-offset-2 focus:ring-indigo-700 hover:bg-gray-300 rounded text-indigo-700 px-5 h-8 flex items-center text-sm">
            Download All
          </button>
        </slot>
        <router-link :to="to"  class="text-white ml-4 cursor-pointer focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-700 border border-transparent bg-indigo-700 transition duration-150 ease-in-out hover:bg-indigo-600 w-8 h-8 rounded flex items-center justify-center">
          <img src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg7.svg"
               alt="plus">
        </router-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Header",
  props:{
    model:Object,
    to:String
  },

}
</script>

<style scoped>

</style>