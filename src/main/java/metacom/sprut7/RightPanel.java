/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author 1
 */
public class RightPanel extends VerticalLayout{
    private Panel panel=new Panel();
    private FormLayout formBody = new FormLayout();
    public RightPanel(){
        setSizeFull();
        setMargin(true);
        panel.setSizeFull();
        panel.setWidth("100%");
        //panel.setHeight("400px");
        panel.setCaption("Характеристики счетчика");
        panel.setContent(formBody);
        formBody.setMargin(true);
        //formBody.addComponent(new Label("Наименование"));
        addComponent(panel);
        
        
        
    }
    public FormLayout getBody(){
      return formBody;  
    } 
}
