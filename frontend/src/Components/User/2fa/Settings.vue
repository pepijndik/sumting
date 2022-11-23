<template>
  <div class="row justify-content-md-center">
    <div class="col-md-8">
      <div class="card">
        <div class="card-header"><strong>Two Factor Authentication</strong></div>
        <div class="card-body">
          <p>Tweefactorauthenticatie (2FA) versterkt de toegangsbeveiliging door twee methoden (ook wel factoren genoemd) te vereisen om uw identiteit te verifiëren. Tweefactorauthenticatie beschermt tegen phishing, social engineering en brute force-aanvallen met wachtwoorden en beveiligt uw aanmeldingen tegen aanvallers die misbruik maken van zwakke of gestolen inloggegevens.</p>
          <div v-if="user.user_secret_code == null">
            <div class="form-horizontal">
              <div class="form-group">
<!--                generate2faSecret-->
                <button type="submit" class="btn btn-primary">
                  Genereer geheime sleutel, Om twee staps verificatie te activeren
                </button>
              </div>
            </div>
          </div>

          1. Scan deze QR-code met uw Google Authenticator-app. Als alternatief kunt u de code gebruiken: <code v-text="user.secret"></code><br/>
          <img :src="two2fa.qr" alt="Qr code">
          <br/><br/>
          2. Voer de pincode van de Google Authenticator-app in:<br/><br/>
          <div class="form-horizontal">
<!--            enable2fa-->
            <div class="form-group">
              <label for="secret" class="control-label">Authenticator Code</label>
              <input id="secret" type="password" class="form-control col-md-4" name="secret" required>
            </div>
            <button type="submit" class="btn btn-primary">
              Zet tweestaps verificatie aan
            </button>
          </div>
          <div v-if="user.twofactor_enabled">
            <div class="alert alert-success">
              tweestaps verificatie is momenteel <strong>enabled</strong> op je account
            </div>
            <p>Als u Two Factor Authentication wilt uitschakelen. Bevestig uw wachtwoord en klik op de 2FA-knop uitschakelen.</p>
            <div>
<!--              disable2fa-->

              <div class="form-group">
                <label for="change-password" class="control-label">Current Password</label>
                <input id="current-password" type="password" class="form-control col-md-4" name="current-password" required>
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
export default {
  name: "Settings",
  data() {
    return {
      user: {
        twofactor_enabled: false,
        secret: null,
      },
      two2fa:{
        qr: ""
      }
    };
  },
}
</script>

<style scoped>

</style>