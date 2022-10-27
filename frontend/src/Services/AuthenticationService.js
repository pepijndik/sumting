
import BaseApi from "@/Services/BaseApi";
import User from "@/Models/User";

class AuthenticationService
{
    constructor() {

    }
    async login(email, password, remember = false) {
        if(!email || !password) return false;

        return await BaseApi.post("auth", {email, password}).then(
            response => {
                const BearToken = response.headers.Authorization.slice(7);
                localStorage.setItem('token', BearToken);
                BaseApi.defaults.headers['Authorization'] = 'Bearer ' + BearToken;
                return new User(response.data.id, response.data.name, response.data.email, response.data.avatar, response.data.user_type);
            }
        ).catch(error => {
            throw error;
        });
    }
    logout() {
        localStorage.removeItem('user');
    }
    register(user = {}) {
        return BaseApi.post( 'users', {
            username: user?.username ?? '',
            email: user?.email ?? '',
            password: user?.password ?? '',
            role: user?.role?? 'user',
        });
    }

}
export default AuthenticationService;