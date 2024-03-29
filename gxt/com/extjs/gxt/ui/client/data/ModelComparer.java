/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.data;

/**
 * Comapares the model instances for equality.
 * 
 * @param <M> the model type
 */
public interface ModelComparer<M extends ModelData> {

  public boolean equals(M m1, M m2);

}
