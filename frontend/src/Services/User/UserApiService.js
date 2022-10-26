import {ApiAdapter} from "@/Services/ApiAdapter";

class UserApiService extends ApiAdapter {
    constructor() {
        super('user');
    }

    /**
     * Login a User and return the user
     * @param email
     * @param password
     * @returns {Promise<*>}
     */
    async login(email, password) {
        return await this.post('login', {email, password}).then(
            response => {
                return response.data;
            }
        ).catch(error => {
            throw error;
        });
    }
    async getMe() {
        return await this.get('me').then(
            response => {
                return response.data;
            }
        ).catch(error => {
            throw error;
        });
    }
}