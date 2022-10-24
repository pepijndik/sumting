import Api from "@/Services/BaseApi";


export class AuthenticationService
{

    async login(credentials){
        return await Api.post('/login', credentials).then(
            response => {
                if (response.data.token) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            }
        );
    }
    logout() {
        localStorage.removeItem('user');
    }
    register(user = {}) {
        return Api.post( 'signup', {
            username: user?.username ?? '',
            email: user?.email ?? '',
            password: user?.password ?? '',
            role: user?.role?? 'user',
        });
    }

}
export default new AuthenticationService();