import moment from "moment";
export default class Order
{
    id; // number
    orderDate; // Datetime
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
    static copyConstructor(order){
        if(order == null) return null;
        let c = Object.assign(new order(),order);
        c.orderDate = moment(order.orderDate);
        c.createdAt = moment(order.createdAt);
        return c;
    }
    getFormattedOrderDate() {
        return moment(this.orderDate).fromNow();
    }

}