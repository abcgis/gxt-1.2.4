/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This is a marker interface for template factories.
 * 
 * All methods of subinterfaces will be generated from template source files.
 * 
 * All methods must return {@link com.extjs.gxt.ui.client.core.Template
 * Template} or String
 */
public interface Templates {

  /**
   * <p/>Used on {@link com.extjs.gxt.ui.client.core.Templates Templates}
   * subinterface methods to override the template resource.
   * 
   * <p/>If not specified, the template resource will be generated by
   * concatenating the fully qualified interface name, the # character, the
   * method name, and the extension ".html"
   * 
   * <p/>For Example <p/>
   * 
   * <pre>interface com.project.client.MyTemplates extends Templates {
   *    public Template button();
   *    public Template header();
   * }
   *</pre>
   * 
   * and the crorespinding template files are :
   * 
   * <pre>
   * com/project/client/MyTemplates#button.html
   * com/project/client/MyTemplates#header.html
   * </pre>
   * 
   * 
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.CLASS)
  public @interface Resource {

    /**
     * <p/>The name of the resource that contains the template.
     * 
     * <p/>It is resolved relative to the fully qualified name of the interface.
     */
    public String value() default "";
  }

  /**
   * <p/>Used on {@link com.extjs.gxt.ui.client.core.Templates Templates}
   * subinterface methods identify that the Template should be cached
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.CLASS)
  public @interface Cache {
  }

  /**
   * <p/>Used on {@link com.extjs.gxt.ui.client.core.Templates} subinterface
   * methods identify that the markup should be compressed
   * 
   * <li>remove white space between tags <li>remove html comments
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.CLASS)
  public @interface Compress {
  }

  /**
   * <p/>Used on {@link Selector Selectors} subinterface methods to specity the
   * css selector to use
   */
  @Target(ElementType.METHOD)
  @Retention(RetentionPolicy.CLASS)
  public @interface Selector {
    String value();
  }
}
