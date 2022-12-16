import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
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
  async create(description,orderLines,currency,payer,)
  {
   await this.save();
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
}
export default OrderApiService;
