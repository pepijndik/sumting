/**
 * User model
 * @author Pepijn dik
 * @since 12-09-2022
 * @constructor
 */
import Twofactor from "@/Models/Twofactor";
import moment from "moment";
export default class User {
  profileImage = "";
  profileText = "";
  constructor(
    id,
    name,
    email,
    country,
    type,
    created_at = null,
    TwoFactorEnabled = null,
    profileImage = null,
    profileText = null
  ) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.country_key = country;
    this.user_type = type;
    this.createdAt = moment(created_at).format("YYYY-MM-DD HH:mm:ss");
    this.twofactor = new Twofactor(
      TwoFactorEnabled == null ? false : TwoFactorEnabled
    );
    this.profileImage = profileImage;
    this.profileText = profileText;
  }

  static copyEntity(entity) {
    return new User(entity.id, entity.name, entity.email, entity.type);
  }

  static equal(otherA, otherB) {
    return (
      otherB.id === otherA.id &&
      otherB.name === otherA.name &&
      otherB.email === otherA.email
    );
  }
}
