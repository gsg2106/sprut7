/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Label;
import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author Сергей
 */
@org.springframework.stereotype.Component
@Scope("prototype")
public class CommonView   extends CustomComponent implements View{
    private StatePlat statePlat;
    private CustomLayout castomLayout;
    private Boolean initialised = false;
    private IComponentContainer consist;
    public CommonView(){
    }
    public void init(){
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        
        InitWindow();
        setCompositionRoot(castomLayout);
    }
    private void InitWindow(){
        castomLayout = new CustomLayout("sprut-login-layout");
        HeaderAreaImpl headerArea = new HeaderAreaImpl();
        headerArea.addComponent(new ToolBarImpl());
        castomLayout.addComponent((Component)headerArea,"header"); 
        Label labelCapt = new Label("Плательщик: "+statePlat.getNamePlat()+", адрес: "+statePlat.getAdres()+", состояние: "+statePlat.getState());
        labelCapt.setStyleName("labelcapt");        
        castomLayout.addComponent(labelCapt,"subheader");
        castomLayout.addComponent(new MenuBarArea(),"menuheader");
        consist.init();
        castomLayout.addComponent(consist,"mainpanel");
        
    }
    public void setComponentContainer(IComponentContainer container){
        this.consist = container;
    }
    @Override
    public void enter(ViewChangeEvent event) {
       if (!initialised) {
          initialised = true; 
          init();
       }     
    }
    
}