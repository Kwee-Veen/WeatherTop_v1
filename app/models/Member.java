package models;

import play.db.jpa.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member extends Model {
  public String firstname;
  public String surname;
  public String email;
  public String password;
  @OneToMany(cascade = CascadeType.ALL)
  public List<Station> stations = new ArrayList<Station>();

  public Member(String firstname, String surname, String email, String password) {
    this.firstname = firstname;
    this.surname = surname;
    this.email = email;
    this.password = password;
  }
  public static Member findByEmail(String email) {
    return find("email", email).first();
  }
  public boolean checkPassword (String password) {
    return this.password.equals(password);}
}
