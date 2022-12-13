import BaseApi from "@/Services/BaseApi";
export default class ApiAdapter {
  constructor(resource) {
    this.resource = resource;
  }
  setHeader(Header = {}) {
    BaseApi.defaults.headers = Header;
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

  async findMe() {
    return BaseApi.get(`/auth/me`);
  }
}
