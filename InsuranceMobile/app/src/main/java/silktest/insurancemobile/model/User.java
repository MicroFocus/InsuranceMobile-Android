/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile.model;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class User {
  public static final SimpleDateFormat BIRTHDAY_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

  private static final Map<String, User> USERS = new HashMap<>();

  static {
    signUp(new User("john.smith@gmail.com", "John", "Smith", "01.01.1980", "john"));
  }

  public static void signUp(User user) {
    if (USERS.containsKey(user.getEmail())) {
      throw new RuntimeException("User " + user.getEmail() + " already exists");
    }

    USERS.put(user.getEmail(), user);
  }

  public static User getUserByEmail(String email) {
    return USERS.get(email);
  }

  private String email;
  private String firstname;
  private String lastname;
  private String birthday;
  private String password;

  public String getEmail() {
    return email;
  }

  public String getFirstname() {
    return firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public String getBirthday() {
    return birthday;
  }

  public String getPassword() {
    return password;
  }

  public User(String email, String firstname, String lastname, String birthday, String password) {
    this.email = email;
    this.firstname = firstname;
    this.lastname = lastname;
    this.birthday = birthday;
    this.password = password;
  }

  @Override
  public String toString() {
    return firstname + " " + lastname;
  }
}
