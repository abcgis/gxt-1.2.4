/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.layouts;

import com.extjs.gxt.samples.resources.client.TestData;
import com.extjs.gxt.ui.client.Style.Scroll;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.layout.AccordionLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.tree.Tree;
import com.extjs.gxt.ui.client.widget.tree.TreeItem;

public class AccordionLayoutExample extends LayoutContainer {

  public AccordionLayoutExample() {
    setLayout(new FlowLayout(10));

    ContentPanel panel = new ContentPanel();
    panel.setHeading("AccordionLayout");
    panel.setBodyBorder(false);

    panel.setLayout(new AccordionLayout());
    panel.setIconStyle("icon-accordion");

    ContentPanel cp = new ContentPanel();
    cp.setHeading("Online Users");
    cp.setScrollMode(Scroll.AUTO);
    panel.add(cp);

    Tree tree = new Tree();
    TreeItem family = new TreeItem("Family");
    tree.getRootItem().add(family);
    family.add(newItem("Darrell", "user"));
    family.add(newItem("Maro", "user-girl"));
    family.add(newItem("Lia", "user-kid"));
    family.add(newItem("Alec", "user-kid"));
    family.add(newItem("Andrew", "user-kid"));
    family.setExpanded(true);

    TreeItem friends = new TreeItem("Friends");
    tree.getRootItem().add(friends);
    friends.add(newItem("Bob", "user"));
    friends.add(newItem("Mary", "user-girl"));
    friends.add(newItem("Sally", "user-girl"));
    friends.add(newItem("Jack", "user"));
    friends.setExpanded(true);

    cp.add(tree);

    cp = new ContentPanel();
    cp.setBodyStyleName("pad-text");
    cp.setHeading("Settings");
    cp.addText(TestData.DUMMY_TEXT_SHORT);
    panel.add(cp);

    cp = new ContentPanel();
    cp.setBodyStyleName("pad-text");
    cp.setHeading("Stuff");
    cp.addText(TestData.DUMMY_TEXT_SHORT);
    panel.add(cp);

    cp = new ContentPanel();
    cp.setBodyStyleName("pad-text");
    cp.setHeading("More Stuff");
    cp.addText(TestData.DUMMY_TEXT_SHORT);
    panel.add(cp);
    panel.setSize(200, 325);

    add(panel);
  }

  private TreeItem newItem(String text, String iconStyle) {
    TreeItem item = new TreeItem(text);
    item.setIconStyle(iconStyle);
    return item;
  }

}
