/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.widget.treetable;

import com.extjs.gxt.ui.client.XDOM;
import com.extjs.gxt.ui.client.Style.SortDir;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.ContainerEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreeTableEvent;
import com.extjs.gxt.ui.client.util.DelayedTask;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.util.StyleTemplate;
import com.extjs.gxt.ui.client.widget.ComponentHelper;
import com.extjs.gxt.ui.client.widget.table.BaseTable;
import com.extjs.gxt.ui.client.widget.table.TableColumn;
import com.extjs.gxt.ui.client.widget.table.TableColumnModel;
import com.extjs.gxt.ui.client.widget.table.TableHeader;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;

/**
 * A hierarchical tree widget with support for additional columns. The tree
 * contains a hierarchy of <code>TreeTableItems</code> that the user can open,
 * close, and select.
 * 
 * <dl>
 * <dt><b>Events:</b></dt>
 * 
 * <dd><b>CellClick</b> : TreeTableEvent(treeTable, item, rowIndex, cellIndex)<br>
 * <div>Fires after a cell has been clicked.</div>
 * <ul>
 * <li>treeTable : tree table</li>
 * <li>item : item represented by the cell</li>
 * <li>rowIndex : the item's depth</li>
 * <li>cellIndex : the cell index</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>CellDoubleClick</b> : TreeTableEvent(treeTable, item, rowIndex,
 * cellIndex)<br>
 * <div>Fires after a cell has been double clicked.</div>
 * <ul>
 * <li>treeTable : tree table</li>
 * <li>item : item represented by the cell</li>
 * <li>rowIndex : the item's depth</li>
 * <li>cellIndex : the cell index</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>RowClick</b> : TreeTableEvent(treeTable, item, rowIndex, cellIndex)<br>
 * <div>Fires after a cell has been clicked.</div>
 * <ul>
 * <li>treeTable : tree table</li>
 * <li>item : item that represents the row</li>
 * <li>rowIndex : the item's depth</li>
 * <li>cellIndex : the cell index</li>
 * </ul>
 * </dd>
 * 
 * <dd><b>RowDoubleClick</b> : TreeTableEvent(treeTable, item, rowIndex,
 * cellIndex)<br>
 * <div>Fires after a cell has been double clicked.</div>
 * <ul>
 * <li>treeTable : tree table</li>
 * <li>item : item that represents the row</li>
 * <li>rowIndex : the item's depth</li>
 * <li>cellIndex : the cell index</li>
 * </ul>
 * </dd>
 * </dl>
 */
public class TreeTable extends Tree implements BaseTable {

  protected StyleTemplate styleTemplate = null;
  protected TreeTableHeader header;
  protected TreeTableColumnModel cm;
  protected TreeTableView view;

  private boolean columnContextMenu;
  private boolean horizontalScroll = true;
  private int lastLeft;
  private Size lastSize;

  private DelayedTask scrollTask = new DelayedTask(new Listener() {
    public void handleEvent(BaseEvent be) {
      header.updateSplitBars();
    }
  });

  /**
   * Creates a new single select tree table. A column model must be set before
   * the table is rendered.
   */
  public TreeTable() {

  }

  /**
   * Creates a new tree table with the given column model.
   * 
   * @param cm the tree table column model
   */
  public TreeTable(TreeTableColumnModel cm) {
    this.cm = cm;
  }

  @Override
  public boolean fireEvent(int type, ComponentEvent ce) {
    // handle case where events are disabled why animating expand / collapse
    boolean d = isDisabledEvents();
    disableEvents(false);
    boolean result = super.fireEvent(type, ce);
    disableEvents(d);
    return result;
  }

  /**
   * Returns the column at the specified index.
   * 
   * @param index the column index
   * @return the column
   */
  public TableColumn getColumn(int index) {
    return cm.getColumn(index);
  }

  /**
   * Returns the column with the given id.
   * 
   * @param id the column id
   * @return the column
   */
  public TableColumn getColumn(String id) {
    return cm.getColumn(id);
  }

  /**
   * Returns the column context menu enabed state.
   * 
   * @return <code>true</code> if enabled, <code>false</code> otherwise.
   */
  public boolean getColumnContextMenu() {
    return !columnContextMenu;
  }

  /**
   * Returns the number of columns contained in the table.
   * 
   * @return the number of columns
   */
  public int getColumnCount() {
    return cm.getColumnCount();
  }

  /**
   * Returns the table's column model.
   * 
   * @return the column model
   */
  public TableColumnModel getColumnModel() {
    return cm;
  }

  /**
   * Returns true if horizontal scrolling is enabled
   * 
   * @return the horizontal scroll state
   */
  public boolean getHorizontalScroll() {
    return horizontalScroll;
  }

  /**
   * Returns the tree table's header.
   * 
   * @return the table header
   */
  public TableHeader getTableHeader() {
    if (header == null) {
      header = new TreeTableHeader(this);
    }
    return header;
  }

  public void onBrowserEvent(Event event) {
    super.onBrowserEvent(event);
    int type = DOM.eventGetType(event);

    if (type == Event.ONSCROLL) {
      int left = view.getScrollEl().getScrollLeft();
      if (left == lastLeft) {
        return;
      }
      lastLeft = left;
      header.el().setLeft(-left);
      scrollTask.delay(400);
    }
  }

  @Override
  public void recalculate() {
    onResize(getOffsetWidth(), getOffsetHeight());
  }

  /**
   * Scrolls the item into view.
   * 
   * @param item the item
   */
  public void scrollIntoView(TreeTableItem item) {
    item.el().scrollIntoView(view.getScrollEl().dom, false);
  }

  /**
   * Sets whether the table header context menu is displayed (defaults to true).
   * 
   * @param columnContextMenu the column context menu sate
   */
  public void setColumnContextMenu(boolean columnContextMenu) {
    this.columnContextMenu = columnContextMenu;
  }

  /**
   * True to display a horizonatal scroll bar when needed (defaults to true).
   * 
   * @param horizontalScroll the horizontal scroll state
   */
  public void setHorizontalScroll(boolean horizontalScroll) {
    this.horizontalScroll = horizontalScroll;
  }

  /**
   * Sets the tree table's header. Should only be called when providing a custom
   * tree table header. Has no effect if called after the table has been
   * rendered.
   * 
   * @param header the table header
   */
  public void setTableHeader(TreeTableHeader header) {
    if (!isRendered()) {
      this.header = header;
    }
  }

  /**
   * Sets the tree table's view. Provides a way to provide specialized views.
   * table views.
   * 
   * @param view the view
   */
  public void setView(TreeTableView view) {
    this.view = view;
  }

  /**
   * Sorts the tree table using the specified column index.
   * 
   * @param index the column index
   * @param direction the direction to sort (NONE, ASC, DESC)
   */
  public void sort(int index, SortDir direction) {

  }

  @Override
  protected ComponentEvent createComponentEvent(Event event) {
    Element target = null;
    TreeItem item = null;
    if (event != null) {
      target = event.getTarget().cast();
      item = (TreeItem) findItem(target);
    }
    TreeTableEvent te = new TreeTableEvent(this, item);
    if (view != null && event != null && item != null) {
      te.cellIndex = view.findCellIndex(target);
      te.rowIndex = item.getDepth();
    }
    return te;
  }

  @Override
  protected ContainerEvent createContainerEvent(TreeItem item) {
    return new TreeTableEvent(this, item);
  }

  @Override
  protected void createRootItem() {
    root = new RootTreeTableItem(this);
  }

  protected void doAttachChildren() {
    ComponentHelper.doAttach(header);
  }

  protected void doDetachChildren() {
    ComponentHelper.doDetach(header);
  }

  protected String getRenderedValue(TreeTableItem item, int column, Object value) {
    TreeTableColumn col = (TreeTableColumn) cm.getColumn(column);
    if (col.getRenderer() != null) {
      return col.getRenderer().render(item, col.getId(), value);
    } else {
      if (value != null) {
        return value.toString();
      }
      return null;
    }
  }

  /**
   * Returns the tree table's view.
   * 
   * @return the view
   */
  protected TreeTableView getView() {
    if (view == null) {
      view = new TreeTableView();
    }
    return view;
  }

  @Override
  protected void onRender(Element target, int index) {
    setElement(DOM.createDiv());
    setStyleName("my-treetbl");
    el().insertInto(target, index);

    DOM.appendChild(getElement(), root.getElement());
    ((RootTreeTableItem) root).renderChildren();

    cm.setTable(this);
    ((TreeTableItem) root).setValues(new String[getColumnCount()]);

    el().removeChildren();

    header = (TreeTableHeader) getTableHeader();
    header.render(el().dom);
    header.init(this);

    DOM.appendChild(getElement(), header.getElement());

    if (styleTemplate == null) {
      Element style = DOM.createElement("style");
      DOM.setElementProperty(style, "id", getId() + "-cols-style");
      DOM.appendChild(XDOM.getHead(), style);
      styleTemplate = new StyleTemplate(style);
    }

    for (int i = 0, n = cm.getColumnCount(); i < n; i++) {
      TreeTableColumn c = (TreeTableColumn) cm.getColumn(i);
      int w = cm.getWidthInPixels(c.getIndex());
      styleTemplate.set("." + getId() + "-col-" + i, "width:" + w + "px;");
    }

    view = getView();
    view.init(this);
    view.render();

    disableTextSelection(true);

    sinkEvents(Event.ONCLICK | Event.ONDBLCLICK | Event.MOUSEEVENTS | Event.KEYEVENTS
        | Event.ONSCROLL);
  }

  protected void onResize(int width, int height) {
    int h = width;
    int w = height;
    if (lastSize != null) {
      if (lastSize.width == w && lastSize.height == h) {
        return;
      }
    }
    lastSize = new Size(w, h);
    header.resizeColumns(false, true);
  }

}
