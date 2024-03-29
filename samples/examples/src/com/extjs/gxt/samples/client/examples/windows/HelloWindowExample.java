/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.windows;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;

public class HelloWindowExample extends LayoutContainer {

  public HelloWindowExample() {
    setLayout(new FlowLayout(10));

    final Window window = new Window();
    window.setSize(500, 300);
    window.setPlain(true);
    window.setHeading("Hello Window");
    window.setLayout(new FitLayout());

    TabPanel panel = new TabPanel();
    panel.setBorders(false);
    TabItem item1 = new TabItem("Hello World 1");
    item1.addText("Hello...");
    item1.addStyleName("pad-text");

    TabItem item2 = new TabItem("Hello World 2");
    item2.addText("... World!");
    item2.addStyleName("pad-text");
    panel.add(item1);
    panel.add(item2);

    window.add(panel, new FitData(4));

    Button btn = new Button("Hello World");
    btn.addSelectionListener(new SelectionListener<ButtonEvent>() {
      @Override
      public void componentSelected(ButtonEvent ce) {
        window.show();
      }
    });
    add(btn);
  }

}
