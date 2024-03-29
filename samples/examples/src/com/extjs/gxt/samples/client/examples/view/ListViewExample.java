/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.view;

import com.extjs.gxt.samples.client.ExampleServiceAsync;
import com.extjs.gxt.samples.client.Examples;
import com.extjs.gxt.ui.client.Events;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Util;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.ListView;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ListViewExample extends LayoutContainer {

  public ListViewExample() {
    setLayout(new FlowLayout(10));
  }

  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);

    final ExampleServiceAsync service = (ExampleServiceAsync) Registry.get(Examples.SERVICE);

    RpcProxy proxy = new RpcProxy() {
      @Override
      protected void load(Object loadConfig, AsyncCallback callback) {
        service.getPhotos(callback);
      }
    };

    ListLoader loader = new BaseListLoader(proxy, new BeanModelReader());
    ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
    loader.load();

    final ContentPanel panel = new ContentPanel();
    panel.setCollapsible(true);
    panel.setAnimCollapse(false);
    panel.setFrame(true);
    panel.setId("images-view");
    panel.setHeading("Simple ListView (0 items selected)");
    panel.setWidth(535);
    panel.setAutoHeight(true);
    panel.setLayout(new FitLayout());

    panel.setBodyBorder(false);

    ListView<BeanModel> view = new ListView<BeanModel>() {
      @Override
      protected BeanModel prepareData(BeanModel model) {
        String s = model.get("name");
        model.set("shortName", Util.ellipse(s, 15));
        return model;
      }

    };

    view.setTemplate(getTemplate());
    view.setStore(store);
    view.setItemSelector("div.thumb-wrap");
    view.getSelectionModel().addListener(Events.SelectionChange,
        new Listener<SelectionChangedEvent<BeanModel>>() {
          public void handleEvent(SelectionChangedEvent<BeanModel> be) {
            panel.setHeading("Simple ListView (" + be.getSelection().size() + " items selected)");
          }
        });
    panel.add(view);
    add(panel);
  }

  private native String getTemplate() /*-{
    return ['<tpl for=".">',
    '<div class="thumb-wrap" id="{name}" style="border: 1px solid white">',
    '<div class="thumb"><img src="{path}" title="{name}"></div>',
    '<span class="x-editable">{shortName}</span></div>',
    '</tpl>',
    '<div class="x-clear"></div>'].join("");
    
    }-*/;
}
