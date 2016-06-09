/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile.model;

import java.util.ArrayList;
import java.util.List;

public class Agent {
  private static List<Agent> allAgents;

  static {
    allAgents = new ArrayList<>();

    allAgents.add(new Agent(0, "Alice", "Walker", "12 8th Street, Suite B", "Austin", "78731", "TX", "(512) 555-1212"));
    allAgents.add(new Agent(1, "Jim", "Taylor", "12 8th Street, Suite B", "Cupertino", "95014", "CA", "(512) 555-1212"));
    allAgents.add(new Agent(2, "George", "Wilson", "12 8th Street, Suite B", "Chicago ", "90266", "IL", "(512) 555-1212"));
    allAgents.add(new Agent(3, "Willow", "Cameron", "1212 Main Street", "Reston", "11921", "VA", "(512) 555-1212"));
    allAgents.add(new Agent(4, "Al", "Borland", "1212 Main Street", "New York", "10120", "NY", "(512) 555-1212"));
    allAgents.add(new Agent(5, "Dave", "Clayton", "1212 Main Street", "Atlanta", "30339", "GA", "(512) 555-1212"));
  }

  public static List<Agent> getAllAgents() {
    return allAgents;
  }

  public static List<Agent> getAgentsByZipCode(String zipCode) {
    List<Agent> result = new ArrayList<>();

    if ("*".equals(zipCode.trim())) {
      result.addAll(allAgents);
    }else {
      for (Agent agent : allAgents) {
        if (agent.getZipCode().equals(zipCode.trim())) {
          result.add(agent);
        }
      }
    }

    return result;
  }

  public static List<Agent> getAgentsByName(String name) {
    List<Agent> result = new ArrayList<>();

    if ("*".equals(name.trim())) {
      result.addAll(allAgents);
    }else {
      for (Agent agent : allAgents) {
        if (agent.getFirstName().toLowerCase().contains(name.trim().toLowerCase()) || agent.getLastName().toLowerCase().contains(name.trim().toLowerCase())) {
          result.add(agent);
        }
      }
    }

    return result;
  }

  public static Agent getAgentById(int id) {
    return allAgents.get(id);
  }

  private int id;
  private String firstName;
  private String lastName;
  private String address;
  private String city;
  private String zipCode;
  private String state;
  private String phone;

  public Agent(int id, String firstName, String lastName, String address, String city, String zipCode, String state, String phone) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.address = address;
    this.city = city;
    this.zipCode = zipCode;
    this.state = state;
    this.phone = phone;
  }

  public int getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getAddress() {
    return address;
  }

  public String getCity() {
    return city;
  }

  public String getZipCode() {
    return zipCode;
  }

  public String getState() {
    return state;
  }

  public String getPhone() {
    return phone;
  }

  @Override
  public String toString() {
    return String.format("%s %s", firstName, lastName);
  }
}
