<template>
  <div class="row justify-content-md-center">
    <div class="col-md-8">
      <div class="card">
        <div class="card-header"><strong>Two Factor Authentication</strong></div>
        <div class="card-body" v-if="user.twofactor">
          <p>Tweefactorauthenticatie (2FA) versterkt de toegangsbeveiliging door twee methoden (ook wel factoren
            genoemd) te vereisen om uw identiteit te verifiëren. Tweefactorauthenticatie beschermt tegen phishing,
            social engineering en brute-force aanvallen met wachtwoorden en beveiligt uw aanmeldingen tegen aanvallers
            die misbruik maken van zwakke of gestolen inloggegevens.</p>
          <div>
            <div v-if="!user.twofactor.secret">
              <Button v-on:click="enable2fa" v-if="!user.twofactor.secret && !user.twofactor.enabled">
                Zet tweestapsverificatie aan
              </Button>
              <div v-if="user.twofactor.enabled">
                1. Scan deze QR-code met uw Google Authenticator-app. Als alternatief kunt u de code gebruiken: <code
                  v-text="user.twofactor.secret"></code><br/>
                <img :src="user.twofactor.qr" alt="Qr code">
                <br/><br/>
                2. Voer de pincode van de Google Authenticator-app in:<br/><br/>
                <div class="">
                  <!--            enable2fa-->
                  <div class="">
                    <label for="secret" class="control-label">Authenticator Code</label>
                    <DigitInput @update="$event = otp"/>
                  </div>
                  <Button v-on:click="verifyCode">
                    Verifieer authenticator code
                  </Button>
                </div>
              </div>

            </div>
          </div>
          <div v-if="user.twofactor.enabled && user.twofactor.secret">
            <div class="alert alert-success">
              tweestapsverificatie is momenteel <strong>enabled</strong> op je account
            </div>
            <p>Als u Two Factor Authentication wilt uitschakelen. Bevestig uw wachtwoord en klik op de 2FA-knop
              uitschakelen.</p>
            <div>
              <!--              disable2fa-->

              <div class="form-group">
                <label for="change-password" class="control-label">Current Password</label>
                <input id="current-password" type="password" class="form-control col-md-4" name="current-password"
                       required>
              </div>
              <button type="submit" class="btn btn-primary ">Zet tweestaps verificatie uit</button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import User from "@/Models/User";
import Button from "@/Components/Form/Button";
import DigitInput from "@/Components/Auth/Twofactor/otp/DigitInput";

export default {
  name: "Settings",
  components: {Button, DigitInput},
  inject: ["Auth"],
  data() {
    return {
      user: User,
      otp: ""
    }
  },
  async created() {
    this.user = await this.Auth.getMe();
  },
  methods: {
    /**
     * Verify 2fa
     * @returns {Promise<void>}
     */
    async verifyCode() {
      await this.Auth.verify2Fa(this.otp);
    },
    /**
     * Enable 2fa
     * @returns {Promise<void>}
     */
    async enable2fa() {
      console.log("enable2fa");
      await this.Auth.enable2FA(this.user);
    },
    /**
     * Disable 2fa
     * @returns {Promise<void>}
     */
    async disable2fa() {
      await this.Auth.disable2FA();
    },
  },
}
</script>

<style scoped>

</style>