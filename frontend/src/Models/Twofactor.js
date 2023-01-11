/**
 * Holds Twofactor data for a user.
 */
export default class Twofactor {
    constructor(enabled = false, qr = "", secret = "") {
        this.enabled = enabled;
        this.qr = qr;
        this.secret = secret;
        this.verified = false;
    }

    /**
     * Sets the qr code for the user
     * @param qr
     */
    setQr(qr) {
        this.qr = qr;
    }

    /**
     * Sets the two-factor authentication the user
     * @param enabled
     */
    setEnabled(enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets the verified state of the user
     * @param verified
     */
    setVerified(verified) {
        this.verified = verified;
    }
}