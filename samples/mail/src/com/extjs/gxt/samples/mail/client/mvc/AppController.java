/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.mail.client.mvc;

import com.extjs.gxt.samples.mail.client.AppEvents;
import com.extjs.gxt.samples.mail.client.Mail;
import com.extjs.gxt.samples.mail.client.MailServiceAsync;
import com.extjs.gxt.samples.resources.client.model.Folder;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Controller;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AppController extends Controller {

  private AppView appView;
  private MailServiceAsync service;

  public AppController() {
    registerEventTypes(AppEvents.Init);
    registerEventTypes(AppEvents.Login);
    registerEventTypes(AppEvents.Error);
  }

  public void handleEvent(AppEvent event) {
    switch (event.type) {
      case AppEvents.Init:
        onInit(event);
        break;
      case AppEvents.Login:
        onLogin(event);
        break;
      case AppEvents.Error:
        onError(event);
        break;
    }
  }

  public void initialize() {
    appView = new AppView(this);
  }

  protected void onError(AppEvent ae) {
    System.out.println("error: " + ae.data);
  }

  private void onInit(AppEvent event) {
    forwardToView(appView, event);
    service = (MailServiceAsync) Registry.get(Mail.SERVICE);
    service.getMailFolders("darrell", new AsyncCallback<Folder>() {
      public void onFailure(Throwable caught) {
        Dispatcher.forwardEvent(AppEvents.Error, caught);
      }

      public void onSuccess(Folder result) {
        Dispatcher.forwardEvent(AppEvents.NavMail, result);
      }
    });

  }

  private void onLogin(AppEvent event) {
    forwardToView(appView, event);
  }

}
