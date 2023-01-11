import BaseApi from "@/Services/BaseApi";
import User from "@/Models/User";
import AuthHeader from "@/Services/AuthHeader";
import Twofactor from "@/Models/Twofactor";
import router from "@/Router/router";

class AuthenticationService {
    constructor() {

    }

    /**
     * Get whether the user is logged in or not
     * @returns {boolean}
     */
    static isLoggedIn() {
        if (process.env?.VUE_APP_ENV === 'development') {
            return true;
        }
        return localStorage.getItem('token') !== null && localStorage.getItem('user') !== null && localStorage.getItem('token') !== undefined && localStorage.getItem('user') !== undefined;
    }

    /**
     * Login the user
     * @param email
     * @param password
     * @param remember
     * @returns {Promise<AxiosResponse<any>|boolean>}
     */
    async login(email, password, remember = false) {
        if (!email || !password) return false;

        return await BaseApi.post("auth", {email, password}).then(
            response => {
                if (response.headers?.authorization !== undefined) {
                    const BearToken = response.headers.authorization.slice(7);
                    localStorage.setItem('token', BearToken);
                    BaseApi.defaults.headers['Authorization'] = 'Bearer ' + BearToken;
                }
                if (response.data.need_twofactor) {
                    return {success: true, need_twofactor: true};
                }
                if (response.data?.me !== undefined) {
                    const data = response.data.me;
                    const user = new User(data.id, data.name, data.email, data.country, data.user_type)
                    user.profileImage = data.profileImage;
                    user.profileText = data.profileText;
                    user.twofactor = new Twofactor(data.twoFactorEnabled);
                    localStorage.setItem('user', JSON.stringify(user));
                    return user;
                }
                return false;
            }
        ).catch(error => {
            console.log(error);
            return false;
        });
    }

    /**
     * Logs out the user by clearing the local storage
     */
    logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('user');
        router.push({name: 'auth:login'});
    }

    /**
     * Verify the user's 2FA code
     * @param code
     * @param user
     * @returns {Promise<AxiosResponse<any>|boolean>}
     */
    async verify2Fa(code, user) {
        return await BaseApi.post("auth/2fa/verify", {
            code: code,
        }, {headers: AuthHeader()}).then(
            response => {
                if (response.data.success) {
                    user.twofactor.setEnabled(true);
                    user.twofactor.setVerified(true);
                } else {
                    user.twofactor.setVerified(false);
                }
                return user;
            }
        ).catch(error => {
            console.log(error);
            return false;
        });
    }

    /**
     * Get the logged user from the api
     * @returns {Promise<User|{}>}
     */
    async getMe() {
        BaseApi.defaults.headers = AuthHeader();
        return await BaseApi.get("auth/me")
            .then(
                response => {
                    const data = response.data;
                    return new User(data.id, data.name, data.email, data.createdAt, data.TwoFactorEnabled, data.profileImage, data.profileText, data.user_type);
                }
            ).catch(error => {
                console.log("User was not logged in, redirect to login")
                router.push({name: 'auth:login'});
                return {}
            });
    }

    /**
     * Enable 2FA for the user
     * @param {User} user
     * @returns {Promise<AxiosResponse<any>|boolean>}
     */
    async enable2FA(user) {
        console.log(user);
        BaseApi.defaults.headers = AuthHeader();
        return await BaseApi.post("auth/2fa/setup")
            .then(
                response => {
                    const data = response.data;
                    user.twofactor.setQr(data.qr_code);
                    user.twofactor.setEnabled(true);
                }
            ).catch(error => {
                console.log(error);
                return false;
            });
    }

    /**
     * Register a new user
     * @param user
     * @returns {Promise<AxiosResponse<any>>}
     */
    async register(user = {}) {
        return BaseApi.post('users', {
            username: user?.username ?? '',
            email: user?.email ?? '',
            password: user?.password ?? '',
            role: user?.role ?? 'user',
        });
    }

    /**
     * Refreshes the users token
     * @returns {Promise<AxiosResponse<any>|boolean>}
     */
    async refreshToken() {
        return await BaseApi.post("auth/refresh-token",).then(
            response => {
                if (response.headers?.authorization !== undefined) {
                    const BearToken = response.headers.authorization.slice(7);
                    localStorage.setItem('token', BearToken);
                    BaseApi.defaults.headers['Authorization'] = 'Bearer ' + BearToken;
                    return true;
                }
            }
        ).catch(error => {
            console.log(error);
            return false;
        });
    }

}

export default AuthenticationService;