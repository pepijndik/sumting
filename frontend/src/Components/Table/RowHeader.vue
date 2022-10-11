<template>
  <tr class="w-full h-16 border-gray-300 border-b py-8">
    <slot name="rowHeader">

    </slot>
    <td role="columnheader"
        class="text-gray-600 dark:text-gray-400 font-normal pr-8 text-left text-sm tracking-normal leading-4">Actions
    </td>
  </tr>
</template>

<script>
export default {
  name: "RowHeader",

}
</script>

<style scoped>

</style>