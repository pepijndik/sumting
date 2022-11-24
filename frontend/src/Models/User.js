/**
 * User model
 * @author Pepijn dik
 * @since 12-09-2022
 * @constructor
 */
import Twofactor from "@/Models/Twofactor";
export default class User {
    profileImage ="";
    profileText = "";
    constructor(id, name, email, created_at,TwoFactorEnabled, profileImage,profileText,type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdAt = new Date(created_at);
        this.twofactor = new Twofactor(TwoFactorEnabled == null ? false : TwoFactorEnabled);
        this.profileImage = profileImage;
        this.profileText= profileText;
        this.type = type;
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