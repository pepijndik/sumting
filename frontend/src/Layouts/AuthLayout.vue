<template>
  <div class="flex min-h-full items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
    <div class="w-full max-w-md space-y-8">
      <div>
        <img class="mx-auto h-12 w-auto" src="@/Assets/logo.png" alt="Your Company">
        <h2 class="mt-6 text-center text-3xl font-bold tracking-tight text-candyPink">Sign in to your account</h2>
        <p class="mt-2 text-center text-sm text-gray-600">

          <a href="#" class="font-medium text-yInMnBlue hover:text-candyPink">Sumting panel</a>
        </p>
      </div>
      <router-view></router-view>
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