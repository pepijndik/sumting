<template>
  <div class="container max-w-full mx-auto py-24 px-6">
    <div class="relative flex flex-wrap">
      <div class="w-full relative">
        <div class="mt-6">
          <div class="mb-5 pb-1border-b-2 text-center font-base text-gray-700">
            <span>By <a class="text-blue-500" href="https://twitter.com/framansi">@framansi</a></span>
          </div>
          <div class="text-center font-semibold text-black">
            Lorem ipsum dolor, sit amet?
          </div>

          <router-view/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "AuthLayout"
}
</script>

<style scoped>

</style>