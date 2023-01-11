<template>
  <div class="mt-8 space-y-6">
    <p class="ml-40 font-medium text-yInMnBlue">Tweestapsverificatie</p>
    <div class="w-50 m-5">
      <div class="mb-4 text-sm text-gray-600 " v-show="! recovery">
        Bevestig de toegang tot uw account door de authenticatiecode in te voeren die door uw authenticatietoepassing is
        verstrekt.
      </div>

      <div class="mb-4 text-sm text-gray-600" v-show="recovery">
        Bevestig de toegang tot uw account door een van uw noodherstelcodes in te voeren.
      </div>
      <!--        When sumbitting the form, the code is sent to the backend-->
      <div>
        <div class="mt-4" v-show="! recovery">
          <label for="one-time-code" class="text-gray font-bold text-center">Code</label>
          <DigitInput @update="code = $event"/>

        </div>

        <div class="mt-4" v-show="recovery">
          <label for="recovery_code">
            Herstel Code
          </label>
          <DigitInput @update="code = $event"/>
          <!--        <input-component id="recovery_code" class="block mt-1 w-full" type="text"-->
          <!--                         name="recovery_code"-->
          <!--                         ref="recovery_code"-->
          <!--                         autocomplete="one-time-code"-->
          <!--                         placeholder=Herstel-->
          <!--                         v-model="code" />-->
        </div>
        <div v-if="error">
          <div>
            <p class="mt-2 text-sm text-red-600 dark:text-red-500"><span class="font-medium">Oops!</span>Token invalid!
            </p>
          </div>
        </div>
        <div class="flex items-center justify-end mt-4">
          <Button type="submit" class="ml-4 w-full" v-on:click="login">
            Inloggen
          </Button>
        </div>
        <div class="flex items-center justify-center mt-4">
          <div>
            <button type="button" class="text-sm text-gray-600 hover:text-gray-900 underline cursor-pointer"
                    v-show="! recovery"
                    v-on:click="recovery = true;$nextTick(() => { $refs.recovery_code.focus() })">Gebruik herstel code
            </button>
            <button type="button" class="text-sm text-gray-600 hover:text-gray-900 underline cursor-pointer"
                    v-show="recovery"
                    v-on:click="recovery = false; $nextTick(() => { $refs.code.focus() }) ">
              Gebruik een authenticatiecode
            </button>

          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import DigitInput from "@/Components/Auth/Twofactor/otp/DigitInput";
import Button from "@/Components/Form/Button";

export default {
  name: "VerifyCode",
  components: {Button, DigitInput},
  inject: ["Auth"],
  data() {
    return {
      recovery: false,
      code: '',
      error: false,
    }
  },
  methods: {
    async login() {
      /**
       * Sends the code to the backend to verify it
       *
       * @type {User|{}}
       */
      this.user = await this.Auth.getMe();
      await this.Auth.verify2Fa(this.code, this.user);
      if (this.user.twofactor.verified) {
        this.$router.push({name: 'dashboard'})
      } else {
        this.error = true;
      }
    }
  }
}
</script>

<style scoped>

</style>