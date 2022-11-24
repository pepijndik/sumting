/**
 * Holds Twofactor data for a user.
 */
export default  class Twofactor
{
    constructor(enabled = false,qr = "",secret = "") {
        this.enabled = enabled;
        this.qr = qr;
        this.secret = secret;
        this.verified = false;
    }
    setQr(qr) {
        this.qr = qr;
    }
    setEnabled(enabled) {
        this.enabled = enabled;
    }
    setVerified(verified) {
        this.verified = verified;
    }
}