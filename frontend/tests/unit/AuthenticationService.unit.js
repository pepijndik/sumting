import AuthenticationService from "@/Services/AuthenticationService";
import BaseApi from "@/Services/BaseApi";
import LoginResponse from "../mockingData/Login/LoginResponse.json";
import LoginNeedTwoFactorResponse from "../mockingData/Login/LoginNeedTwoFactorResponse.json";
import LoginTwoFactorVerifyResponse from "../mockingData/Login/LoginTwoFactorVerifyResponse.json";
import GetMeResponse from "../mockingData/User/GetMeResponse.json";
let auth = new AuthenticationService();


describe('AuthenticationService', () => {
    it("Is Logout by default", () => {
        expect(localStorage.getItem('token')).toBe(undefined);
        expect(AuthenticationService.isLoggedIn()).toBe(false);
    });
    it("Can Login a user without 2fa", async () => {
       jest.spyOn(BaseApi, 'post',).mockResolvedValue(LoginResponse);
       const user = await auth.login("admin@hva.nl", "test", false);
       expect(localStorage.getItem('token')).toBe("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjEzMywiaXNzIjoiU3VtdGluZyIsImlhdCI6MTY3MjgxNzcwNSwiZXhwIjoxNjcyODE3NzY1fQ.kF8dA7FDKPe-BQ0tU9JM5tlvbCvvf12jCOxG-qViZxDKreyEcWAY0rGkxCOtWCnrXLDX3DowTHZfgxyniElNDw");
       expect(user.name).toBe("admin");
       expect(user.email).toBe("admin@hva.nl");
    });
    it("Can Login a user with 2fa", async () => {
        jest.spyOn(BaseApi, 'post',).mockResolvedValue(LoginNeedTwoFactorResponse);
        let user = await auth.login("admin@hva.nl", "test", false);
        expect(localStorage.getItem('token')).toBe("eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOjEzMywiaXNzIjoiU3VtdGluZyIsImlhdCI6MTY3MjgxNzcwNSwiZXhwIjoxNjcyODE3NzY1fQ.kF8dA7FDKPe-BQ0tU9JM5tlvbCvvf12jCOxG-qViZxDKreyEcWAY0rGkxCOtWCnrXLDX3DowTHZfgxyniElNDw");
        expect(user?.need_twofactor).toBe(true);
        jest.spyOn(BaseApi, 'get').mockResolvedValue(GetMeResponse);
        user = await auth.getMe();
        jest.spyOn(BaseApi, 'post',).mockResolvedValue(LoginTwoFactorVerifyResponse);
        await auth.verify2Fa("123456",user);
        expect(user.twofactor.verified).toBe(true);
        expect(user.twofactor.enabled).toBe(true);
    });
});