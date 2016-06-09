/**
 * Copyright 2013 Micro Focus. All rights reserved. 
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

import org.junit.Assert;

import com.borland.silk.keyworddriven.annotations.Keyword;
import com.borland.silktest.jtf.Desktop;
import com.borland.silktest.jtf.MobileBaseState;
import com.borland.silktest.jtf.common.types.Point;
import com.microfocus.silktest.jtf.mobile.MobileDevice;
import com.microfocus.silktest.jtf.mobile.MobileObject;
import com.microfocus.silktest.jtf.mobile.common.types.DisplayOrientation;

public class Keywords {

  private Desktop desktop = new Desktop();

  @Keyword("Start application")
  public void baseState() {
    MobileBaseState baseState = new MobileBaseState();
    baseState.execute(desktop);
  }

  @Keyword("Set display orientation")
  public void setDisplayOrientation(DisplayOrientation displayOrientation) {
    desktop.<MobileDevice> find("//MobileDevice").rotate(displayOrientation);
  }

  @Keyword("Reset login form")
  public void resetLoginForm() {
    MobileObject email = desktop.<MobileObject> find("Device.silktest insurancemobile id email");
    if (email.getText().length() > 0) {
      email.longClick();
      desktop.<MobileObject> find("Device.silktest insurancemobile id email").typeKeys("<Backspace>");
    }

    MobileObject password = desktop.<MobileObject> find("Device.silktest insurancemobile id password");
    if (password.getText().length() > 0) {
      password.longClick();
      desktop.<MobileObject> find("Device.silktest insurancemobile id password").typeKeys(
          "<Backspace><Backspace><Backspace><Backspace><Backspace><Backspace><Backspace><Backspace><Backspace><Backspace>");
    }
  }

  @Keyword("Login")
  public void login(String email, String password, String expectedName) {
    desktop.<MobileObject> find("Device.silktest insurancemobile id email").typeKeys(email);
    desktop.<MobileObject> find("Device.silktest insurancemobile id password").typeKeys(password);
    desktop.<MobileObject> find("Device.silktest insurancemobile id log_in_button").click();

    
    
    MobileObject silktestInsurancemobileIdLogged_in_as = desktop
        .<MobileObject> find("Device.silktest insurancemobile id logged_in_as");
    Assert.assertEquals("Logged in as " + expectedName, silktestInsurancemobileIdLogged_in_as.getText());

    // desktop.verifyAsset("ProtectYourFuture");

  }

  @Keyword("Login with invalid credentials")
  public void invalidLogin(String email, String password) {
    desktop.<MobileObject> find("Device.silktest insurancemobile id email").typeKeys(email);
    desktop.<MobileObject> find("Device.silktest insurancemobile id password").typeKeys(password);
    desktop.<MobileObject> find("Device.silktest insurancemobile id log_in_button").click();

    // TODO: We can't find the error message at the moment
    desktop.<MobileObject> find("Device.silktest insurancemobile id log_in_button");
  }

  @Keyword("Auto Quote")
  public void autoQuote() {
    desktop.<MobileObject> find("Device.drawer_open").click();
    desktop.<MobileObject> find("Device.Auto Quote").click();
  }

  @Keyword("Fill in Auto Quote form")
  public void fillInAutoQuoteForm() {
    desktop.<MobileObject> find("Device.silktest insurancemobile id autoquote_zip").typeKeys("4020");
    desktop.<MobileObject> find("Device.silktest insurancemobile id rating_driving_record").click(new Point(337, 47));
    desktop.<MobileObject> find("Device.silktest insurancemobile id radio_truck").click();

    desktop.<MobileDevice> find("//MobileDevice").swipeUp();

    desktop.<MobileObject> find("Device.silktest insurancemobile id autoquote_car_year").typeKeys("2010");

    desktop.<MobileObject> find("Device.Chrysler").click();
    desktop.<MobileObject> find("Device.Toyota").click();
    desktop.<MobileObject> find("Device.-").click();
    desktop.<MobileObject> find("Device.Hilux").click();
    desktop.<MobileObject> find("Device.Own").click();
    desktop.<MobileObject> find("Device.Financed").click();
    desktop.<MobileObject> find("Device.silktest insurancemobile id submit").click();
    MobileObject silktestInsurancemobileIdZipCode = desktop
        .<MobileObject> find("Device.silktest insurancemobile id zipCode");
    Assert.assertEquals("Zip Code: 4020", silktestInsurancemobileIdZipCode.getText());
    MobileObject silktestInsurancemobileIdAge = desktop.<MobileObject> find("Device.silktest insurancemobile id age");
    Assert.assertEquals("Age: 36", silktestInsurancemobileIdAge.getText());
    MobileObject silktestInsurancemobileIdGender = desktop
        .<MobileObject> find("Device.silktest insurancemobile id gender");
    Assert.assertEquals("Gender: Male", silktestInsurancemobileIdGender.getText());

    desktop.<MobileDevice> find("//MobileDevice").swipeUp();

    MobileObject silktestInsurancemobileIdResult = desktop
        .<MobileObject> find("Device.silktest insurancemobile id result");
    Assert.assertEquals("Your Instant Quote is USD $1,776.00 every twelve months.",
        silktestInsurancemobileIdResult.getText());
  }

  @Keyword("Agent Lookup")
  public void agentLookup() {
    desktop.<MobileObject> find("Device.drawer_open").click();
    desktop.<MobileObject> find("Device.Agent Lookup").click();
  }

  @Keyword("About")
  public void about() {
    desktop.<MobileObject> find("Device.drawer_open").click();
    desktop.<MobileObject> find("Device.About").click();
    desktop.<MobileObject> find("Device.Navigate up").click();
  }

  @Keyword("Back via toolbar")
  public void back() {
    desktop.<MobileObject> find("Device.Navigate up").click();
  }

  @Keyword("Logout")
  public void logout() {
    desktop.<MobileObject> find("Device.drawer_open").click();
    desktop.<MobileObject> find("Device.Logout").click();
  }

  @Keyword("Sign up")
  public void signUp(String firstName, String lastName, String email, String password) {
    desktop.<MobileObject> find("Device.silktest insurancemobile id sign_up_fab").click();

    desktop.<MobileObject> find("Device.silktest insurancemobile id firstName").typeKeys(firstName);
    desktop.<MobileObject> find("Device.silktest insurancemobile id lastName").typeKeys(lastName);

    desktop.<MobileObject> find("Device.silktest insurancemobile id email").typeKeys(email);
    desktop.<MobileObject> find("Device.silktest insurancemobile id password").typeKeys(password);
    desktop.<MobileObject> find("Device.silktest insurancemobile id sign_up_button").click();

    MobileObject snackBar = desktop.<MobileObject> find("Device.silktest insurancemobile id snackbar_text");
    Assert.assertEquals("User " + firstName + " " + lastName + " signed up", snackBar.getText());
  }

  @Keyword("Look up all agents")
  public void lookupAllAgents(int expectedNumberOfAgents) {

    desktop.<MobileObject> find("Device.silktest insurancemobile id show_all_agents_button").click();
    MobileObject searchStatus = desktop.<MobileObject> find("Device.silktest insurancemobile id agent_search_status");
    Assert.assertEquals(expectedNumberOfAgents + " agents found", searchStatus.getText());

    desktop.<MobileObject> find("Device.Navigate up").click();
  }

  @Keyword("Look up agent by name")
  public void lookupAgentByName(String name, int expectedNumberOfAgents) {
    desktop.<MobileObject> find("Device.silktest insurancemobile id agent_search_name").typeKeys(name);
    desktop.<MobileObject> find("Device.silktest insurancemobile id search_by_name_button").click();

    MobileObject searchStatus = desktop.<MobileObject> find("Device.silktest insurancemobile id agent_search_status");
    String expected = expectedNumberOfAgents + " agent" + (expectedNumberOfAgents == 1 ? "" : "s") + " found";
    Assert.assertEquals(expected, searchStatus.getText());

    desktop.<MobileObject> find("Device.Navigate up").click();
    
    
  }

}