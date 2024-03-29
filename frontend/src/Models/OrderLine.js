
import moment from "moment";
export default class OrderLine
{
    id; // number
    order; // Order
    product; // Product
    product_key; // string
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
    transaction_line_total;
    loadedDate;
    StripeChargeId;
    batch;
    stripeChargeId;
    notes;

    amount =1;
    constructor(product,notes, total){
        this.product = product;
        this.notes = notes;
        this.transactionLineFee =total;
    }

    /**
     * Creates a new Orderline from a given Object
     * @param line
     * @returns {any|null}
     */
    static copyConstructor(line){
        if(line == null) return null;
        let c = Object.assign(new OrderLine(),line);
        c.proofDate = moment(line.proofDate);
        return c;
    }

    /**
     * Gets the formatted proof date
     * @returns {string}
     */
    getFormattedProofDate() {
        return moment(this.proofDate).fromNow();
    }

}