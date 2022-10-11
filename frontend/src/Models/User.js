/**
 * User model
 * @author Pepijn dik
 * @since 12-09-2022
 * @constructor
 */
export default class User {
    constructor(id, title, description, price, avatar, role = 'user') {
        this.id = id;
        this.title = title;
        this.description = description;
        this.avatar = avatar;
        this.role = role;
    }
}