/**
 * Copyright 2013 Micro Focus. All rights reserved. 
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

import com.borland.silktest.jtf.Desktop;
import org.junit.Before;
import com.borland.silktest.jtf.MobileBaseState;
import com.microfocus.silktest.jtf.mobile.MobileObject;

public class Test {

  private Desktop desktop = new Desktop();

  @Before
  public void baseState() {
    MobileBaseState baseState = new MobileBaseState();
    baseState.execute(desktop);
  }

  @org.junit.Test
  public void login() {
    desktop.<MobileObject> find("Device.silktest insurancemobile id email").typeKeys("john.smith@gmail.com");
    desktop.<MobileObject> find("Device.silktest insurancemobile id password").typeKeys("john");
    desktop.<MobileObject> find("Device.silktest insurancemobile id log_in_button").click();
    desktop.<MobileObject> find("Device.drawer_open").click();
    desktop.<MobileObject> find("Device.Logout").click();
  }

  @org.junit.Test
  public void agentlookup() {
    desktop.<MobileObject> find("Device.silktest insurancemobile id email").typeKeys("john.smith@gmail.com");
    desktop.<MobileObject> find("Device.silktest insurancemobile id password").typeKeys("john");
    desktop.<MobileObject> find("Device.silktest insurancemobile id log_in_button").click();
    desktop.<MobileObject> find("Device.drawer_open").click();
    desktop.<MobileObject> find("Device.Agent Lookup").click();
    desktop.<MobileObject> find("Device.silktest insurancemobile id agent_search_zip").typeKeys("78731");
    desktop.<MobileObject> find("Device.silktest insurancemobile id search_by_location_button").click();
    desktop.<MobileObject> find("Device.Alice Walker").click();
    desktop.<MobileObject> find("Device.Navigate up").click();
    desktop.<MobileObject> find("Device.Navigate up").click();
    desktop.<MobileObject> find("Device.drawer_open").click();
    desktop.<MobileObject> find("Device.Logout").click();
  }

}