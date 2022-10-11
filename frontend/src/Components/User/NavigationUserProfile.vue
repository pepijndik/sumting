<template>
  <div class="grid grid-cols-1">
    <div class="relative">
      <img class="rounded-full h-10 w-10 object-cover"
           :src="user.avatar" alt="display avatar" role="img">
      <div class="w-2 h-2 rounded-full bg-green-400 border border-white absolute inset-0 mb-0 mr-0 m-auto"></div>
    </div>
    <p class="text-gray-800 text-sm mx-3" v-text="user.name"></p>
  </div>
</template>

<script>
export default {
  name: "NavigationUserProfile",
  data() {
    return {
      user: {
        name: "David Grey. H",
        avatar: "https://tuk-cdn.s3.amazonaws.com/assets/components/sidebar_layout/sl_1.png"

      }
    }
  }
}
</script>

<style scoped>

</style>