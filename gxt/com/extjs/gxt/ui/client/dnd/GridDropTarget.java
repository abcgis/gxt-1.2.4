/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.ui.client.dnd;

import java.util.List;

import com.extjs.gxt.ui.client.core.El;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.dnd.DND.Feedback;
import com.extjs.gxt.ui.client.event.DNDEvent;
import com.extjs.gxt.ui.client.util.Rectangle;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.user.client.Element;

/**
 * A <code>DropTarget</code> implementation for Grids. Supports both inserts and
 * appends, specified using
 * {@link #setOperation(com.extjs.gxt.ui.client.dnd.DND.Operation)}.
 */
public class GridDropTarget extends DropTarget {

  protected Grid grid;
  protected int insertIndex;

  /**
   * Creates a new drop target instance.
   * 
   * @param grid the target grid
   */
  public GridDropTarget(Grid grid) {
    super(grid);
    this.grid = grid;
  }

  /**
   * Returns the target grid component.
   * 
   * @return the grid
   */
  public Grid getGrid() {
    return grid;
  }

  @Override
  protected void onDragDrop(DNDEvent e) {
    super.onDragDrop(e);
    Object data = e.data;
    if (feedback == Feedback.APPEND) {
      if (data instanceof ModelData) {
        grid.getStore().add((ModelData) data);
      } else if (data instanceof List) {
        grid.getStore().add((List) data);
      }
    } else {
      if (data instanceof ModelData) {
        grid.getStore().insert((ModelData) data, insertIndex);
      } else if (data instanceof List) {
        grid.getStore().insert((List) data, insertIndex);
      }
    }
  }

  @Override
  protected void onDragEnter(DNDEvent e) {
    super.onDragEnter(e);
    e.doit = true;
    e.status.setStatus(true);
  }

  @Override
  protected void onDragLeave(DNDEvent e) {
    super.onDragLeave(e);
  }

  @Override
  protected void onDragMove(DNDEvent event) {
    if (!event.within(grid.getView().getBody().dom)) {
      event.doit = false;
      event.status.setStatus(false);
      return;
    }

    if (feedback == Feedback.APPEND) {
      event.doit = true;
    } else {
      event.doit = true;
    }
  }

  @Override
  protected void showFeedback(DNDEvent event) {
    if (feedback == Feedback.INSERT) {
      event.status.setStatus(true);
      Element row = grid.getView().findRow(event.getTarget()).cast();

      if (row == null && grid.getStore().getCount() > 0) {
        row = grid.getView().getRow(grid.getStore().getCount() - 1).cast();
      }

      if (row != null) {
        int height = row.getOffsetHeight();
        int mid = height / 2;
        mid += row.getAbsoluteTop();
        int y = event.getClientY();
        boolean before = y < mid;
        int idx = grid.getView().findRowIndex(row);
        insertIndex = before ? idx : (event.source.component == grid) ? idx : idx + 1;
        if (before) {
          showInsert(event, row, true);
        } else {
          showInsert(event, row, false);
        }
      }
    }
  }

  private void showInsert(DNDEvent event, Element row, boolean before) {
    Insert insert = Insert.get();
    insert.setVisible(true);
    Rectangle rect = El.fly(row).getBounds();
    int y = !before ? (rect.y + rect.height - 4) : rect.y - 2;
    insert.el().setBounds(rect.x, y, rect.width, 6);
  }

}
