export default function AuthHeader() {
    let token = localStorage.getItem('token');
    if (token) {
        return { Authorization: 'Bearer ' + token };
    } else {
        console.log({error: 'No token found'})
    }
}