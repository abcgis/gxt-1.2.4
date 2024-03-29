package com.extjs.gxt.samples.client.examples.dnd;

import com.extjs.gxt.samples.client.Examples;
import com.extjs.gxt.samples.client.ExamplesModel;
import com.extjs.gxt.samples.client.examples.model.Category;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binder.TreeBinder;
import com.extjs.gxt.ui.client.data.BaseTreeModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.TreeModel;
import com.extjs.gxt.ui.client.dnd.TreeDragSource;
import com.extjs.gxt.ui.client.dnd.TreeDropTarget;
import com.extjs.gxt.ui.client.event.DNDEvent;
import com.extjs.gxt.ui.client.event.DNDListener;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreSorter;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.HorizontalPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.TableData;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;
import com.google.gwt.user.client.Element;

public class TreeToTreeExample extends LayoutContainer {

  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);

    StoreSorter sorter = new StoreSorter() {

      @Override
      public int compare(Store store, ModelData m1, ModelData m2, String property) {
        boolean m1Folder = m1 instanceof Category;
        boolean m2Folder = m2 instanceof Category;
        if (m1Folder && !m2Folder) {
          return -1;
        } else if (!m1Folder && m2Folder) {
          return 1;
        }
        String s1 = m1.get("name");
        String s2 = m2.get("name");
        return s1.compareTo(s2);
      }
    };

    TreeStore store = new TreeStore();
    store.setStoreSorter(sorter);
    TreeModel root = (ExamplesModel) Registry.get(Examples.MODEL);
    root.set("name", "Ext GWT");

    store.add(root, true);

    final Tree tree = new Tree();
    tree.getStyle().setLeafIconStyle("icon-list");

    TreeBinder binder = new TreeBinder(tree, store);
    binder.setAutoLoad(true);
    binder.setDisplayProperty("name");
    binder.init();
    
    tree.getRootItem().getItem(0).setExpanded(true);

    root = new BaseTreeModel();
    root.set("name", "My Files");

    store = new TreeStore();
    store.setStoreSorter(sorter);
    store.add(root, false);

    Tree tree2 = new Tree();
    tree2.getStyle().setLeafIconStyle("icon-list");

    TreeBinder binder2 = new TreeBinder(tree2, store) {

      @Override
      public boolean hasChildren(ModelData parent) {
        if ("My Files".equals(parent.get("name")) || parent instanceof Category) {
          return true;
        }
        return super.hasChildren(parent);
      }

    };
    binder2.setAutoLoad(true);
    binder2.setDisplayProperty("name");
    binder2.init();

    HorizontalPanel hp = new HorizontalPanel();
    hp.setSpacing(10);

    hp.add(tree, new TableData("250px", "100%"));
    hp.add(tree2);

    DNDListener listener = new DNDListener() {
      @Override
      public void dragStart(DNDEvent e) {
        TreeItem item = tree.findItem(e.getTarget());
        if (item != null && item == tree.getRootItem().getItem(0)) {
          e.doit = false;
          e.status.setStatus(false);
          return;
        }
        super.dragStart(e);
      }
    };

    TreeDragSource source = new TreeDragSource(binder);
    source.addDNDListener(listener);
    TreeDragSource source2 = new TreeDragSource(binder2);
    source2.addDNDListener(listener);

    new TreeDropTarget(binder);
    new TreeDropTarget(binder2);

    add(hp);
  }

}
