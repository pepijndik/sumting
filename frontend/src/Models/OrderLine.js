
import moment from "moment";
export default class OrderLine
{
    id; // number
    order; // Order
    product; // Product
    owner;
    wallet;
    proofName;
    proofDate;
    latitude;
    longitude;
    proofSmall;
    proofMedium;
    proofLarge
    proofUploadDate;
    transactionLineFee;
    transactionLineVat;
    loadedDate;
    StripeChargeId;
    batch;
    stripeChargeId;
    notes;

    constructor()
    {
    }
    static copyConstructor(line){
        if(line == null) return null;
        let c = Object.assign(new OrderLine(),line);
        c.proofDate = moment(line.proofDate);
        return c;
    }
    getFormattedProofDate() {
        return moment(this.proofDate).fromNow();
    }

}