/**
 * User model
 * @author Pepijn dik
 * @since 12-09-2022
 * @constructor
 */
export default class User {
    constructor(id, name, email, type, role = 'user') {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
        this.role = role;
    }
}