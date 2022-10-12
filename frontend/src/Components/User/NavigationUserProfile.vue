<template>
  <div class="flex items-center gap-6 border-y border-white py-3 px-6 md:px-8 lg:px-12">
    <div class="relative">
      <img class="rounded-full h-12 w-12 object-cover"
           :src="user.avatar" alt="display avatar" role="img">
    </div>
    <div class="w-24">
      <p class="text-yInMnBlue text-sm font-bold truncate" v-text="user.name"></p>
      <p class="text-yInMnBlue text-sm truncate" v-text="user.role"></p>
    </div>
  </div>
</template>

<script>
export default {
  name: "NavigationUserProfile",
  data() {
    return {
      user: {
        avatar: "https://tuk-cdn.s3.amazonaws.com/assets/components/sidebar_layout/sl_1.png",
        name: "David Grey. H David Grey. H David Grey. H David Grey. H David Grey. H",
        role: "Admin"
      }
    }
  }
}
</script>

<style scoped>

</style>