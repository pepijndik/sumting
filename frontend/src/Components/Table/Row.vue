<template>
  <tr class="h-24 border-gray-300 border-b">
    <td class="pl-8 pr-6 text-left whitespace-no-wrap text-sm text-gray-800 dark:text-gray-100 tracking-normal leading-4">
      <input placeholder="check box" type="checkbox"
             class="cursor-pointer relative w-5 h-5 border rounded border-gray-400 bg-white dark:bg-gray-800 focus:outline-none focus:outline-none focus:ring-2  focus:ring-gray-400"
             onclick="tableInteract(this)"/>
    </td>
    <td class="text-sm pr-6 whitespace-no-wrap text-gray-800 dark:text-gray-100 tracking-normal leading-4">
      <div aria-label="documents" role="contentinfo" class="relative w-10 text-gray-600 dark:text-gray-400">
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
    </td>
    <td class="text-sm pr-6 whitespace-no-wrap text-gray-800 dark:text-gray-100 tracking-normal leading-4">
      #MC10023
    </td>
    <td class="text-sm pr-6 whitespace-no-wrap text-gray-800 dark:text-gray-100 tracking-normal leading-4">Toyota
      Motors
    </td>
    <td class="pr-6 whitespace-no-wrap">
      <div class="flex items-center">
        <div class="h-8 w-8">
          <img src="https://tuk-cdn.s3.amazonaws.com/assets/components/advance_tables/at_1.png"
               alt="Display Avatar of Carrie Anthony" role="img"
               class="h-full w-full rounded-full overflow-hidden shadow"/>
        </div>
        <p class="ml-2 text-gray-800 dark:text-gray-100 tracking-normal leading-4 text-sm">Carrie Anthony</p>
      </div>
    </td>
    <td class="text-sm pr-6 whitespace-no-wrap text-gray-800 dark:text-gray-100 tracking-normal leading-4">
      $2,500
    </td>
    <td class="text-sm pr-6 whitespace-no-wrap text-gray-800 dark:text-gray-100 tracking-normal leading-4">
      02.03.20
    </td>
    <td class="pr-6">
      <div class="w-2 h-2 rounded-full bg-indigo-400"></div>
    </td>
    <td class="pr-8 relative">
      <button onclick="dropdownFunction(this)" aria-label="dropdown" role="button"
              class="dropbtn text-gray-500 rounded cursor-pointer border border-transparent  focus:outline-none focus:ring-2 focus:ring-offset-2  focus:ring-gray-400">
        <img src="https://tuk-cdn.s3.amazonaws.com/can-uploader/compact_table_with_actions_and_select-svg9.svg"
             alt="dropdown">
      </button>
      <div class="dropdown-content mt-1 absolute left-0 -ml-12 shadow-md z-10 hidden w-32">
        <ul class="bg-white dark:bg-gray-800 shadow rounded py-1">
          <li class="cursor-pointer text-gray-600 dark:text-gray-400 text-sm leading-3 tracking-normal py-3 hover:bg-indigo-700 hover:text-white px-3 font-normal">
            Edit
          </li>
          <li class="cursor-pointer text-gray-600 dark:text-gray-400 text-sm leading-3 tracking-normal py-3 hover:bg-indigo-700 hover:text-white px-3 font-normal">
            Delete
          </li>
          <li class="cursor-pointer text-gray-600 dark:text-gray-400 text-sm leading-3 tracking-normal py-3 hover:bg-indigo-700 hover:text-white px-3 font-normal">
            Duplicate
          </li>
        </ul>
      </div>
    </td>
  </tr>
</template>

<script>
export default {
  name: "Row",
  props:{
    row: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>

</style>