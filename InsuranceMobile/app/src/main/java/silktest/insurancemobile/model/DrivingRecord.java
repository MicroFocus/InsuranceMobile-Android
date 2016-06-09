/**
 * Copyright 2013 Micro Focus. All rights reserved.
 * Portions Copyright 1992-2009 Borland Software Corporation (a Micro Focus company).
 */

package silktest.insurancemobile.model;

public enum DrivingRecord {
  POOR("Poor"), FAIR("Fair"), GOOD("Good"), EXCELLENT("Excellent");

  private String displayName;

  private DrivingRecord(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}
