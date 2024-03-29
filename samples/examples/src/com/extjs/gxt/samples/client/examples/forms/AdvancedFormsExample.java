/*
 * Ext GWT - Ext for GWT
 * Copyright(c) 2007, 2008, Ext JS, LLC.
 * licensing@extjs.com
 * 
 * http://extjs.com/license
 */
package com.extjs.gxt.samples.client.examples.forms;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.Radio;
import com.extjs.gxt.ui.client.widget.form.RadioGroup;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Element;

public class AdvancedFormsExample extends LayoutContainer {

  private VerticalPanel vp;

  public AdvancedFormsExample() {
    vp = new VerticalPanel();
    vp.setSpacing(10);
  }

  @Override
  protected void onRender(Element parent, int index) {
    super.onRender(parent, index);
    createColumnForm();
    createTabForm();

    add(vp);
  }

  private void createTabForm() {
    FormData formData = new FormData("100%");
    FormPanel panel = new FormPanel();
    panel.setFrame(false);
    panel.setHeaderVisible(false);
    panel.setBodyBorder(false);
    panel.setButtonAlign(HorizontalAlignment.CENTER);
    panel.setLayout(new FitLayout());

    TabPanel tabs = new TabPanel();
    tabs.setDeferredRender(false);
    
    TabItem personal = new TabItem();
    personal.setText("Personal Details");
    personal.setLayout(new FormLayout());

    TextField<String> name = new TextField<String>();
    name.setFieldLabel("First Name");
    name.setValue("Darrell");
    personal.add(name, formData);

    TextField<String> last = new TextField<String>();
    last.setFieldLabel("Last Name");
    last.setValue("Meyer");
    personal.add(last, formData);

    TextField<String> company = new TextField<String>();
    company.setFieldLabel("Company");
    personal.add(company, formData);

    TextField<String> email = new TextField<String>();
    email.setFieldLabel("Email");
    personal.add(email, formData);

    tabs.add(personal);

    TabItem numbers = new TabItem();
    numbers.setText("Phone Numbers");
    numbers.setLayout(new FormLayout());

    TextField<String> home = new TextField<String>();
    home.setFieldLabel("Home");
    home.setValue("800-555-1212");
    numbers.add(home, formData);

    TextField<String> business = new TextField<String>();
    business.setFieldLabel("Business");
    numbers.add(business, formData);

    TextField<String> mobile = new TextField<String>();
    mobile.setFieldLabel("Mobile");
    numbers.add(mobile, formData);

    TextField<String> fax = new TextField<String>();
    fax.setFieldLabel("Fax");
    numbers.add(fax, formData);

    tabs.add(numbers);

    panel.add(tabs);
    panel.addButton(new Button("Cancel"));
    panel.addButton(new Button("Submit"));

    panel.setSize(340, 180);

    vp.add(panel);
  }

  private void createColumnForm() {
    FormData formData = new FormData("100%");
    FormPanel panel = new FormPanel();
    panel.setFrame(true);
    panel.setIconStyle("icon-form");
    panel.setCollapsible(true);
    panel.setHeading("FormPanel");
    panel.setSize(470, -1);
    panel.setButtonAlign(HorizontalAlignment.CENTER);
    panel.setLayout(new FlowLayout());

    LayoutContainer main = new LayoutContainer();
    main.setLayout(new ColumnLayout());

    LayoutContainer left = new LayoutContainer();

    FormLayout layout = new FormLayout();
    layout.setLabelAlign(LabelAlign.TOP);
    left.setLayout(layout);

    TextField<String> first = new TextField<String>();
    first.setFieldLabel("First Name");
    left.add(first, formData);

    TextField<String> company = new TextField<String>();
    company.setFieldLabel("Company");
    left.add(company, formData);

    DateField birthday = new DateField();
    birthday.setFieldLabel("Birthday");
    left.add(birthday, formData);

    LayoutContainer right = new LayoutContainer();

    layout = new FormLayout();
    layout.setLabelAlign(LabelAlign.TOP);
    right.setLayout(layout);

    TextField<String> last = new TextField<String>();
    last.setFieldLabel("Last");
    right.add(last, formData);

    TextField<String> email = new TextField<String>();
    email.setFieldLabel("Email");
    right.add(email, formData);

    Radio radio1 = new Radio();
    radio1.setBoxLabel("Yes");

    Radio radio2 = new Radio();
    radio2.setBoxLabel("No");

    RadioGroup group = new RadioGroup();
    group.setFieldLabel("Ext GWT User");
    group.add(radio1);
    group.add(radio2);
    right.add(group);

    main.add(left, new ColumnData(.5));
    main.add(right, new ColumnData(.5));

    panel.add(main);

    LayoutContainer area = new LayoutContainer();
    area.setStyleAttribute("padding", "0 10px 5px 10px");

    layout = new FormLayout();
    layout.setLabelAlign(LabelAlign.TOP);
    layout.setPadding(0);
    area.setLayout(layout);

    TextArea a = new TextArea();
    a.setFieldLabel("Comment");

    area.add(a, formData);

    panel.add(area);

    panel.addButton(new Button("Cancel"));
    panel.addButton(new Button("Submit"));

    vp.add(panel);
  }

}
