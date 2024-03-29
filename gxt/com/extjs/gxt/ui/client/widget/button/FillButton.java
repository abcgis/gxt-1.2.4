/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget.button;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;

/**
 * Fills the button bar width, aligning any new buttons to the right.
 */
public class FillButton extends Button {

  @Override
  protected void onRender(Element target, int index) {
    setElement(DOM.createDiv(), target, index);
    setWidth("100%");
  }

  @Override
  protected void onDisable() {

  }

  @Override
  protected void onEnable() {

  }

  @Override
  protected void doAttachChildren() {
    // do nothing
  }

  @Override
  protected void doDetachChildren() {
    // do nothing
  }

}
