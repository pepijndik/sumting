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
      })
      .catch((error) => {
        throw error;
      });
  }
}
