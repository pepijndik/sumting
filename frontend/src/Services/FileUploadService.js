import BaseApi from "@/Services/BaseApi";

export default class FileUploadService {
  constructor(resource) {
    this.resource = resource;
  }
  setHeader(Header = {}) {
    BaseApi.defaults.headers = Header;
  }

  async uploadIMG(id, img) {
    this.setHeader({
      "Content-Type": "multipart/form-data",
    });
    let formData = new FormData();

    formData.append("file", img);

    return BaseApi.post(`users/${id}/profile-picture`, formData)
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
}
