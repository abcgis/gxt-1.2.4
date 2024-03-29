/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client;

import java.util.List;

import com.extjs.gxt.samples.client.examples.model.BeanPost;
import com.extjs.gxt.samples.client.examples.model.Photo;
import com.extjs.gxt.samples.client.examples.model.Post;
import com.extjs.gxt.samples.resources.client.model.Customer;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ExampleServiceAsync {
  public void getPosts(PagingLoadConfig config, AsyncCallback<PagingLoadResult<Post>> callback);

  public void getBeanPosts(PagingLoadConfig config,
      AsyncCallback<PagingLoadResult<BeanPost>> callback);

  public void getCustomers(AsyncCallback<List<Customer>> callback);

  public void getPhotos(AsyncCallback<List<Photo>> callback);
}
