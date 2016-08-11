/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.ui.*;

/**
 *
 * @author Сергей
 */
public class FormComponent extends CustomComponent {
  private Layout mainLayout;

  protected Layout header;
  protected Layout central;
  protected Layout footer;

  public FormComponent() {
    init(new HorizontalLayout(), new FormLayout(), new HorizontalLayout());
  }

  protected void init(Layout header, Layout central, Layout footer) {
    this.footer = footer;
    this.header = header;
    this.central = central;

    mainLayout = new VerticalLayout();
    mainLayout.addComponent(header);
    mainLayout.addComponent(central);
    mainLayout.addComponent(footer);

    setCompositionRoot(mainLayout);
    setSizeUndefined();
  }

  public Layout getHeader() {
    return header;
  }

  public Layout getCentral() {
    return central;
  }

  public Layout getFooter() {
    return footer;
  }
}

