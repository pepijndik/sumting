import AuthenticationService from "@/Services/AuthenticationService";
import BaseApi from "@/Services/BaseApi";
import LoginResponse from "../mockingData/Login/LoginResponse.json";
let auth = new AuthenticationService();

jest.spyOn(BaseApi, 'post',).mockResolvedValue(LoginResponse);
describe('AuthenticationService', () => {
    it("Is Logout by default", () => {
        expect(window.localStorage.getItem('token')).toBe(undefined);
        expect(AuthenticationService.isLoggedIn()).toBe(false);
    });
    it("Can Login a user without 2fa", async () => {
       const user = await auth.login("admin@hva.nl", "test", false);
       expect(user.name).toBe("admin");
        expect(user.email).toBe("admin@hva.nl");
    });
});