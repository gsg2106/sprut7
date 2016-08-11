/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author Сергей
 */
public class HeaderAreaImpl extends VerticalLayout{
    public HeaderAreaImpl(){
        init();
    }

    public void init() {
      Embedded meta = new Embedded(null,new ThemeResource("img/meta.png")); 
      addComponent ( meta);
      setComponentAlignment(meta, Alignment.TOP_CENTER);
    }
}
