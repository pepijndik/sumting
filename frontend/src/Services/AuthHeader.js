export default function AuthHeader() {
    let token = localStorage.getItem('token');
    if (token) {
        return { Authorization: 'Bearer ' + token };
    } else {
        return {error: 'No token found'};
    }
}