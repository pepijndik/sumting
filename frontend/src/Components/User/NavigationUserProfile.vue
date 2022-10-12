<template>
  <div class="flex items-center gap-6">
    <div class="relative">
      <img class="rounded-full h-12 w-12 object-cover"
           :src="user.avatar" alt="display avatar" role="img">
    </div>
    <div>
      <p class="text-yInMnBlue text-sm font-bold" v-text="user.name"></p>
      <p class="text-yInMnBlue text-sm" v-text="user.role"></p>
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
        name: "David Grey. H",
        role: "Admin"
      }
    }
  }
}
</script>

<style scoped>

</style>