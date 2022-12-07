<template>
  <div class="px-4 my-24">
    <div class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-xl rounded-lg ">
      <div class="px-6">
        <div class="flex flex-wrap justify-center">
          <div class="w-full lg:w-3/12 px-4 lg:order-2 flex justify-center">
              <img alt="" :src="this.user.profileImage" class="shadow-xl rounded-full align-middle border-none absolute -m-16 -ml-20 lg:-ml-16 w-36 h-36">
          </div>
          <div class="w-full lg:w-4/12 px-4 lg:order-3 lg:text-right lg:self-center">
            <div class="py-6 px-3 mt-32 sm:mt-0">
              <RouterLink class="bg-candyPink hover:bg-yInMnBlue uppercase text-white font-bold hover:shadow-md shadow text-xs px-4 py-2 rounded outline-none focus:outline-none sm:mr-2 mb-1 ease-linear transition-all duration-150" to="/account/2fa/settings">  Enable Twofactor</RouterLink>
            </div>
          </div>
        </div>
        <div class="text-center mt-12">
          <h3 class="text-4xl font-semibold leading-normal mb-2 text-blueGray-700 mb-2" v-text="user.name">

          </h3>
          <div class="text-sm leading-normal mt-0 mb-2 text-blueGray-400 font-bold uppercase" v-text="user.email">

          </div>
        </div>
        <div class="mt-10 py-10 border-t border-blueGray-200 text-center">
          <div class="flex flex-wrap justify-center">
            <div class="w-full lg:w-9/12 px-4">
              <p class="mb-4 text-lg leading-relaxed text-blueGray-700" v-text="user.profileText">

              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import User from "@/Models/User";

export default {
  name: "Profile",
  inject: ["Auth"],
  data() {
    return {
      user: User
    };
  },
  async created() {
    this.user = await this.Auth.getMe();
  },



}
</script>

<style scoped>

</style>