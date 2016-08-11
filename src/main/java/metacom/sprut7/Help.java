/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.TabSheet;
import metacom.sprut7.beans.IComponentContainer;

/**
 *
 * @author Сергей
 */

@SuppressWarnings("serial")
public class Help extends VerticalLayout implements IComponentContainer {
    private TabSheet t;
    private VerticalLayout vl1;
    private VerticalLayout vl2;
    private VerticalLayout vl3;
    
    Help(){
    }
    
    public void init(){
        vl1 = new VerticalLayout();
        vl1.setMargin(true);
        vl2 = new VerticalLayout();
        vl2.setMargin(true);
        vl3 = new VerticalLayout();
        vl3.setMargin(true);
        Resource icon1 = null;
        Resource icon2 = null;
        
        t = new TabSheet();  
        t.setHeight("100%"); //t.setHeight("200px");
        t.setWidth("100%");  //t.setWidth("400px");
        t.addTab(vl1, "Ввод показаний.", icon1);
        t.addTab(vl2, "Настройка браузера.", icon2);
        t.addTab(vl3, "Законодательная база.", icon2);
        addComponent(t);
        
        Embedded e = new Embedded("", new ThemeResource("help.html"));
        //e.setAlternateText("Vaadin web site");
        e.setType(Embedded. TYPE_BROWSER);
       // e.setType(Browser ("iframe") );
        e.setWidth("100%");
        e.setHeight("560px");
        vl2.addComponent(e);

        Embedded e1 = new Embedded("", new ThemeResource("help1.html"));
        e1.setType(Embedded.TYPE_BROWSER);
        e1.setWidth("100%");
        e1.setHeight("560px");
        vl1.addComponent(e1);
        
        Embedded e2 = new Embedded("", new ThemeResource("help2.html"));
        e2.setType(Embedded.TYPE_BROWSER);
        e2.setWidth("100%");
        e2.setHeight("560px");
        vl3.addComponent(e2);  
        //setComponentAlignment(e2, Alignment.TOP_CENTER);
        
    }
            
}
