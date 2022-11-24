<template>
  <div class="group relative flex w-full justify-center rounded-md border border-transparent bg-candyPink py-2 px-4 text-sm font-medium text-white hover:bg-yInMnBlue focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
          <span class="absolute inset-y-0 left-0 flex items-center pl-3">
            <slot name="icon"></slot>
          </span>
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "Button",

}
</script>

<style scoped>

</style>