/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client;

import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.samples.client.examples.model.Category;
import com.extjs.gxt.samples.client.examples.model.Entry;
import com.extjs.gxt.themes.client.Slate;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.XDOM;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.ThemeManager;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.custom.ThemeSelector;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.TextToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.RootPanel;

public class Examples implements EntryPoint {

  public static final String SERVICE = "service";
  public static final String FILE_SERVICE = "fileservice";
  public static final String MODEL = "model";
  
  private Viewport viewport;

  public void onModuleLoad() {
    String name = GWT.getModuleName();
    if (!"com.extjs.gxt.samples.Examples".equals(name)) {
      return;
    }
    ExampleServiceAsync service = (ExampleServiceAsync) GWT.create(ExampleService.class);
    ServiceDefTarget endpoint = (ServiceDefTarget) service;
    String moduleRelativeURL = GWT.getModuleBaseURL() + "service";
    endpoint.setServiceEntryPoint(moduleRelativeURL);
    Registry.register(SERVICE, service);

    FileServiceAsync fileservice = (FileServiceAsync) GWT.create(FileService.class);
    endpoint = (ServiceDefTarget) fileservice;
    moduleRelativeURL = GWT.getModuleBaseURL() + "fileservice";
    endpoint.setServiceEntryPoint(moduleRelativeURL);
    Registry.register(FILE_SERVICE, fileservice);

    Map<String, Entry> examples = new HashMap<String, Entry>();

    ExamplesModel model = new ExamplesModel();
    for (int i = 0; i < model.getChildren().size(); i++) {
      Category cat = (Category) model.getChildren().get(i);
      for (int j = 0; j < cat.getChildren().size(); j++) {
        Entry entry = (Entry) cat.getChildren().get(j);
        examples.put(entry.getId(), entry);
      }
    }
    
    Registry.register(MODEL, model);

    String id = Window.Location.getParameter("id");
    if (id == null) {
      id = XDOM.getBody().getId();
    }

    Entry entry = examples.get(id);

    if (entry == null) {
      return;
    }

    viewport = new Viewport();
    viewport.setLayout(new BorderLayout());

    createNorth();

    TabPanel panel = new TabPanel();
    panel.setResizeTabs(true);
    TabItem example = new TabItem("Example");
    example.setScrollMode(Scroll.AUTO);
    if (entry.isFill()) {
      example.setLayout(new FitLayout());
      example.setScrollMode(Scroll.NONE);
    }

    TabItem source = new TabItem("View Source");
    source.setUrl(entry.getSourceUrl());

    panel.add(example);
    panel.add(source);

    ToolBar toolBar = new ToolBar();
    TextToolItem item = new TextToolItem("View Source");

    toolBar.add(new FillToolItem());
    toolBar.add(item);

    example.add(entry.getExample());

    viewport.add(panel, new BorderLayoutData(LayoutRegion.CENTER));

    RootPanel.get().add(viewport);
  }

  private void createNorth() {
    StringBuffer sb = new StringBuffer();
    sb.append("<div id='demo-header' class='x-small-editor'><div id='demo-theme'></div><div id=demo-title>Ext GWT Examples</div></div>");

    HtmlContainer northPanel = new HtmlContainer(sb.toString());
    northPanel.setEnableState(false);

    ThemeManager.register(Slate.SLATE);
    ThemeSelector selector = new ThemeSelector();
    selector.setWidth(125);
    northPanel.add(selector, "#demo-theme");

    BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 33);
    data.setMargins(new Margins());
    viewport.add(northPanel, data);
  }
}