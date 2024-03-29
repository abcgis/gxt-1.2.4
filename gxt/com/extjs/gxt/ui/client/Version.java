/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client;

/**
 * Contains the current codes release information. Use {@link GXT#getVersion()} to get
 * an instance of this class.
 */
public final class Version {

  private static final String release = "1.2.4";
  private static final int major = 1;
  private static final int minor = 2;
  private static final int revision = 4;

  Version() {

  }

  /**
   * Returns the release name.
   * 
   * @return the release name
   */
  public String getRelease() {
    return release;
  }

  /**
   * Returns the major number.
   * 
   * @return the major number
   */
  public int getMajor() {
    return major;
  }

  /**
   * Returns the minor number.
   * 
   * @return the minor number
   */
  public int getMinor() {
    return minor;
  }

  /**
   * Returns the revision number.
   * 
   * @return the revision number
   */
  public int getRevision() {
    return revision;
  }

}
