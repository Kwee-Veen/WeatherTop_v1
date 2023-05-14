package controllers;
import play.Logger;
import play.mvc.Controller;
import models.*;

public class Accounts extends Controller {
  public static void signup() {
    Logger.info("Rendering signup");
    render("signup.html");
  }
  public static void login() {
    Logger.info("Rendering login");
    render("login.html");
  }

  public static void register(String firstname, String surname, String email, String password) {
    Logger.info("Registering new account, " + email);
    Member member = new Member(firstname, surname, email, password);
    member.save();
    redirect("/login");
  }
  public static void authenticate(String email, String password) {
    Logger.info("Attempting to log in email " + email + " with password " + password);
    Member member = Member.findByEmail(email);
    if ((member != null) && (member.checkPassword(password))) {
      Logger.info("Authentication successful");
      session.put("logged_in_Memberid", member.id);
      redirect("/dashboard");
    } else {
      Logger.error("Authentication failed");
      redirect("/login");
    }
  }
  public static void logout() {
    Logger.info("Logging out");
    session.clear();
    redirect("/login");
  }
  public static Member getLoggedInMember() {
    Member member = null;
    if(session.contains("logged_in_Memberid")) {
      String memberId = session.get("logged_in_Memberid");
      member = Member.findById(Long.parseLong(memberId));
    }
    return member;
  }
}
