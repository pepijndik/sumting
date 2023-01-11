<template>
  <div class="mt-8 space-y-6">
    <input type="hidden" name="remember" :value="remember">
    <div class="-space-y-px rounded-md shadow-sm">
      <div>
        <label for="email-address" class="sr-only">Email address</label>
        <InputComponent :inputData="email" @update="email = $event;" id="email" placeholder="Email adres" required
                        autocomplete="" type="email" name="email" :class="'rounded-t-md border'" ref="loginEmail"/>
      </div>
      <div>
        <label for="password" class="sr-only">Password</label>
        <InputComponent ref="password" :inputData="password" @update="password = $event;" id="password" name="password"
                        type="password" autocomplete="current-password" required placeholder="Password"
                        :class="'rounded-b-md'"></InputComponent>
      </div>
    </div>

    <div class="flex items-center justify-between">
      <Checkbox id="remember" :inputData="remember" @update="remember = $event;" name="remember"
                placeholder="Remember me"></Checkbox>

      <div class="text-sm">
        <a href="#" class="font-medium text-candyPink hover:text-yInMnBlue">Forgot your password?</a>
      </div>
    </div>

    <div>
      <button @click="login()"
              class="group relative flex w-full justify-center rounded-md border border-transparent bg-candyPink py-2 px-4 text-sm font-medium text-white hover:bg-yInMnBlue focus:outline-none focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2">
          <span class="absolute inset-y-0 left-0 flex items-center pl-3">
            <!-- Heroicon name: mini/lock-closed -->
            <svg class="h-5 w-5 text-white group-hover:text-white" xmlns="http://www.w3.org/2000/svg"
                 viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
              <path fill-rule="evenodd"
                    d="M10 1a4.5 4.5 0 00-4.5 4.5V9H5a2 2 0 00-2 2v6a2 2 0 002 2h10a2 2 0 002-2v-6a2 2 0 00-2-2h-.5V5.5A4.5 4.5 0 0010 1zm3 8V5.5a3 3 0 10-6 0V9h6z"
                    clip-rule="evenodd"/>
            </svg>
          </span>
        Sign in
      </button>
    </div>
  </div>
</template>

<script>
import InputComponent from "@/Components/Form/InputComponent";
import Checkbox from "@/Components/Form/Checkbox";

export default {
  name: "Login",
  inject: ["Auth"],
  components: {Checkbox, InputComponent},
  data() {
    return {
      remember: false,
      email: "",
      password: "",
    }
  },
  methods: {
    /**
     * Login the user
     * @returns {Promise<void>}
     */
    async login() {
      const user = await this.Auth.login(this.email, this.password, this.remember);
      const borders = () => {
        this.$refs.loginEmail.$el.classList.add('border-red-500');
        this.$refs.password.$el.classList.add('border-red-500')
      }

      if (!user) {
        borders();
      } else if (user?.need_twofactor) {
        this.$router.push({name: 'auth:verify'});
      } else {
        this.$router.push({name: "dashboard"});
      }
    }
  }
}
</script>

<style scoped>

</style>