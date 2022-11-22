/**
 * User model
 * @author Pepijn dik
 * @since 12-09-2022
 * @constructor
 */
export default class User {
    constructor(id, name, email, type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;

        this.TwoFactorEnabled = false;
        this.profileImage = "";
        this.profileText= "";
    }

    static copyEntity(entity) {
        return new User(entity.id, entity.name, entity.email, entity.type);
    }

    static equal(otherA, otherB) {
        return otherB.id === otherA.id &&
            otherB.name === otherA.name &&
            otherB.email === otherA.email;
    }

}