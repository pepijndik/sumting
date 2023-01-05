import BaseApi from "@/Services/BaseApi";
import AuthHeader from "@/Services/AuthHeader";
export default class ApiAdapter {
  constructor(resource) {
    this.resource = resource;
    this.header = {};
  }
  setHeader(Header = {}) {
    BaseApi.defaults.headers =   {
      ...AuthHeader(),
      ...Header,
    };
  }
  getHeader() {
    return {
      ...AuthHeader(),
    }
  }
  async findAll() {
    return BaseApi.get(`/${this.resource}`)
      .then((response) => {
        return response.data;

        //return response.data;
      })
      .catch((error) => {
        // You can handle the error, like show a notificaiton to the user

        // dont forget to re-throw the error, otherwise the promise will resolve successfully
        throw error;
      });
  }

   updateQueryStringParameter(uri, key, value) {
    var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
    var separator = uri.indexOf('?') !== -1 ? "&" : "?";
    if (uri.match(re)) {
      return uri.replace(re, '$1' + key + "=" + value + '$2');
    }
    else {
      return uri + separator + key + "=" + value;
    }
  }
  async findByMonth(orderMonths) {
    return BaseApi.get(`/${this.resource}/${orderMonths}`)
      .then((response) => {
        return response.data;

        //return response.data;
      })
      .catch((error) => {
        // You can handle the error, like show a notificaiton to the user

        // dont forget to re-throw the error, otherwise the promise will resolve successfully
        throw error;
      });
  }

  async findOne(id) {
    return BaseApi.get(`/${this.resource}/${id}`);
  }

  async delete(id) {
    return BaseApi.delete(`/${this.resource}/${id}`);
  }

  async save(data) {
    return BaseApi.post(`/${this.resource}`, data);
  }

  async update(id) {
    return BaseApi.post(`/${this.resource}/${id}`);
  }
}
