import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
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
  async create(description,orderLines,currency,payer,)
  {
   await this.save();
  }
  // Add custom methods here
}
export default OrderApiService;
