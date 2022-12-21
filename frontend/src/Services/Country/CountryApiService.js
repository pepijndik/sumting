import ApiAdapter from "@/Services/ApiAdapter";
import AuthHeader from "@/Services/AuthHeader";
class CountryApiService extends ApiAdapter {
  constructor() {
    super("countries");
    this.headers = AuthHeader();
  }

  // Add custom methods here
}
export default CountryApiService;
