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
    profileImage = null,
    created_at = null,
    TwoFactorEnabled = null,
    profileText = null
  ) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.country = country;
    this.user_type = type;
    this.profileImage = profileImage;
    this.createdAt = new Date(created_at);
    this.twofactor = new Twofactor(
      TwoFactorEnabled == null ? false : TwoFactorEnabled
    );
    this.profileText = profileText;
  }

  static copyEntity(entity) {
    const user = new User(
      entity.id,
      entity.name,
      entity.email,
      entity.country,
      entity.user_type,
      entity.profileImage,
      entity.created_at
    );
    return user;
  }

  static equal(otherA, otherB) {
    return (
      otherB.id === otherA.id &&
      otherB.name === otherA.name &&
      otherB.email === otherA.email
    );
  }
}
