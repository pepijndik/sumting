
import BaseApi from "@/Services/BaseApi";
import User from "@/Models/User";

class AuthenticationService
{
    constructor() {

    }
    static isLoggedIn() {
        if(process.env?.VUE_APP_ENV === 'development') {
            return true;
        }
        return localStorage.getItem('token') !== null;
    }
    async login(email, password, remember = false) {
        if(!email || !password) return false;

        return await BaseApi.post("auth", {email, password}).then(
            response => {
                const BearToken = response.headers.authorization.slice(7);
                const data = response.data.me;
                localStorage.setItem('token', BearToken);
                BaseApi.defaults.headers['Authorization'] = 'Bearer ' + BearToken;
                return new User(data.id, data.name, data.email, data.avatar, data.user_type);
            }
        ).catch(error => {
            console.log(error);
           return false;
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