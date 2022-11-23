<template>
  <div class="px-4 my-24">
    <div class="relative flex flex-col min-w-0 break-words bg-white w-full mb-6 shadow-xl rounded-lg ">
      <div class="px-6">
        <div class="flex flex-wrap justify-center">
          <div class="w-full lg:w-3/12 px-4 lg:order-2 flex justify-center">
            <div class="relative">
              <img alt="" :src="user.image" class="shadow-xl rounded-full h-auto align-middle border-none absolute -m-16 -ml-20 lg:-ml-16 max-w-150-px">
            </div>
          </div>
          <div class="w-full lg:w-4/12 px-4 lg:order-3 lg:text-right lg:self-center">
            <div class="py-6 px-3 mt-32 sm:mt-0">
              <button class="bg-candyPink hover:bg-yInMnBlue uppercase text-white font-bold hover:shadow-md shadow text-xs px-4 py-2 rounded outline-none focus:outline-none sm:mr-2 mb-1 ease-linear transition-all duration-150" type="button">
                Enable Twofactor
              </button>
            </div>
          </div>
        </div>
        <div class="text-center mt-12">
          <h3 class="text-4xl font-semibold leading-normal mb-2 text-blueGray-700 mb-2">
            Jenna Stones
          </h3>
          <div class="text-sm leading-normal mt-0 mb-2 text-blueGray-400 font-bold uppercase">
            <i class="fas fa-map-marker-alt mr-2 text-lg text-blueGray-400"></i>
            Los Angeles, California
          </div>
          <div class="mb-2 text-blueGray-600 mt-10">
            <i class="fas fa-briefcase mr-2 text-lg text-blueGray-400"></i>Solution Manager - Creative Tim Officer
          </div>
        </div>
        <div class="mt-10 py-10 border-t border-blueGray-200 text-center">
          <div class="flex flex-wrap justify-center">
            <div class="w-full lg:w-9/12 px-4">
              <p class="mb-4 text-lg leading-relaxed text-blueGray-700">
                An artist of considerable range, Jenna the name taken by
                Melbourne-raised, Brooklyn-based Nick Murphy writes,
                performs and records all of his own music, giving it a
                warm, intimate feel with a solid groove structure. An
                artist of considerable range.
              </p>
              <a href="#pablo" class="font-normal text-pink-500">Show more</a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Profile",
  include: ["Auth"],
  methods: {
   async user() {
      return await this.Auth.getMe();
    }
  },

}
</script>

<style scoped>

</style>