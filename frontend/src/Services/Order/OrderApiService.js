import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import OrderTypes from "@/Models/OrderTypes";
import Order from "@/Models/Order";
import BaseApi from "@/Services/BaseApi";

class OrderApiService extends ApiAdapter {
    constructor() {
        super("orders");
        this.headers = AuthHeader();
    }

    /**
     * Create a new order
     * @param {String} description
     * @param{OrderLine} orderLines
     * @param{String} currency,
     * @param{Client}payer
     * @returns {Promise<*>}
     */
    async create(description, orderLines, currency, payer,) {
        await this.save();
    }

    async findAll() {
        const orders = await super.findAll();
        return orders.map(order => Order.copyConstructor(order));
    }

    async findByMonth(orderMonths) {
        const orders = await super.findByMonth();
        return orders.map(order => Order.copyConstructor(order));
    }

    /**
     * Get all orders types
     * @returns {Promise<void>}
     * @constructor
     */
    async GetOrderTypes() {
        const Types = await BaseApi.get(this.resource + "/types").then((response) => {
            return response.data;
        })
            .catch((error) => {
                // You can handle the error, like show a notificaiton to the user

                // dont forget to re-throw the error, otherwise the promise will resolve successfully
                throw error;
            });

                return Types.map(type => OrderTypes.copyConstructor(type));
    }

    // Add custom methods here
}

export default OrderApiService;
