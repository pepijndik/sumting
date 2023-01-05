import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
import OrderTypes from "@/Models/OrderTypes";
import Order from "@/Models/Order";
import OrderLine from "@/Models/OrderLine";
import BaseApi from "@/Services/BaseApi";
import {useToast} from 'vue-toast-notification';
import moment from "moment";

class OrderApiService extends ApiAdapter {
  constructor() {
    super("orders");
    this.headers = AuthHeader();
  }

  /**
   * Create a new order
   * @param {String} description
   * @param{Array.<OrderLine>} orderLines
   * @param{String} currency,
   * @param{Client} payer
   * @param{Number} totalAmount
   * @param{Number} tax
   * @param{OrderTypes} orderType
   * @returns {Promise<*>}
   */
  async create(description,
               orderLines,
               currency,
               payer,
               totalAmount,
               tax,
               orderType
  ) {
    const order = new Order();
    order.description = description;
    order.orderLines = orderLines;
    order.currency = currency.toUpperCase();
    order.payerKey = payer;
    order.transactionTotal = totalAmount;
    order.tax = tax;
    order.typeKey = orderType;
    order.order_date = moment().format("YYYY-MM-DD");
    await this.save(order).then((response) => {
      return response.data;
    }).catch((error) => {
      const $toast = useToast();
      $toast.error({
        message: 'Cant create order ' +error.response.data.message,
        duration: 5000,
        dismissible: true,
      });
      throw error;
    });
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
  async combinedSearch(clientID, projectID) {
    return await BaseApi.get(`orders/combinedSearch?clientID=${clientID}&projectID=${projectID}`).then(response => {
      console.log(response.data)
      return response.data;
    }).catch(error => {
      throw error;
    });
  }

  async getAllOrderlinesByProductId(productId) {
    const orderlines = await BaseApi.get(this.resource + "/orderlines?productId=" + productId)
        .then((response) => {
          return response.data;
        })
        .catch((error) => {
          // You can handle the error, like show a notification to the user
          // don't forget to re-throw the error, otherwise the promise will resolve successfully
          throw error;
        })
    return orderlines.map(orderline => OrderLine.copyConstructor(orderline));
  }
}

export default OrderApiService;
