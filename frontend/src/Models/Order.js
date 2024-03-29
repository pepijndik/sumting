import moment from "moment";
export default class Order
{
    id; // number
    orderDate; // Datetime
    payerKey;
    typeKey;
    
    paymentMethod; // Offer.Status
    createdAt; // Date
    description; // String
    transactionTotal; // string
    orderTypeKey; // string
    transactionFee; // string
    transactionVat;
    currency = 'EUR';
    payer;
    orderlines = []; // OrderLine[]
    constructor() {
    }

    /**
     * Create a new Order from an object
     * @param order
     * @returns {any|null}
     */
    static copyConstructor(order){
        if(order == null) return null;
        let c = Object.assign(new Order(),order);
        c.orderDate = moment(order.orderDate);
        c.createdAt = moment(order.createdAt);
        return c;
    }
    getFormattedOrderDate() {
        return moment(this.orderDate).fromNow();
    }

}