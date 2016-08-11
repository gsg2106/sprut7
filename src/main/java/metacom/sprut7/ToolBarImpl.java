/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import java.util.Date;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.beans.StatePlat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
//import com.vaadin.server.VaadinSession;
/**
 *
 * @author Сергей
 */
//@org.springframework.stereotype.Component
//@Scope("prototype")
//@Scope (value = "session")
//@com.vaadin.server.VaadinSession.Scope("prototype")
//@Scope("session")
//@Scope(ScopeType.APPLICATION)
public class ToolBarImpl extends HorizontalLayout implements Button.ClickListener{
    private Button buttonReboot;
    private Button buttonPokaz;
    private Button buttonLicSchet;
    private Button buttonSchets;
    private Button buttonNn;
    private StatePlat statePlat;    
    private Config config;
    private static final ThemeResource ICON =  new ThemeResource("img/arr_left.gif");

    public ToolBarImpl(){
        init();
    };
    
//    @Autowired
    public void init(){
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        buttonReboot = new Button();
        buttonReboot.setIcon(ICON);
        buttonReboot.setDescription("Перегрузить программу в исходное состояние");
        buttonReboot.addClickListener(this);
        
        buttonPokaz = new Button("Ввод показаний");
        buttonPokaz.setDescription("Расчеты за потребленную электроэнергию и получение документов");
        if ( (statePlat.getPause().intValue() == 1) ) {buttonPokaz.setEnabled(false);}
        if (statePlat.getGoest() & config.getTested().equals("1")) {buttonPokaz.setEnabled(true);}
        buttonLicSchet = new Button("Лицевой счет");
        buttonSchets = new Button("Счета");
        buttonSchets.setId("btnSchets");        
        buttonNn = new Button("Налоговые накладные");

        addComponent(buttonReboot);
        addComponent(buttonPokaz);
        addComponent(buttonLicSchet);
        addComponent(buttonSchets);
        addComponent(buttonNn);
        
        buttonPokaz.addClickListener(this);
        buttonLicSchet.addClickListener(this);
        buttonReboot.addClickListener(this);
        buttonSchets.addClickListener(this);
        buttonNn.addClickListener(this);
        
    };
    
    @Override
    public void buttonClick(ClickEvent event) {
        if (event.getButton().equals(buttonReboot)){
            //  перенаправим на другую страницу. После закрытия сессии ничего уже не сделать
            String ss3  = getUI().getPage().getLocation().getPath();
//            getUI().getPage().setLocation("/sprut7/");
            getUI().getPage().setLocation(ss3);
            // закрываем сессию
            getSession().close();
        }
        if (event.getButton().equals(buttonPokaz)){
            if (getUI().getNavigator().getState().equals("lispRaWindow")) return;
           statePlat.refreshState();
           if (statePlat.getGoest() & config.getTested().equals("1")) {
              if (statePlat.getRaportEnabled().equals(0)) 
              Notification.show("Доступ к вводу показаний закрыт ("+statePlat.getMess()+"). Но из за тестового режима доступ откроем");
              statePlat.setRaportEnabled(1); //raportenabled = 1;
           }
           if ( statePlat.getRaportEnabled().equals(1) ){
               getUI().getNavigator().navigateTo("lispRa");
           } else {
                  Notification.show(statePlat.getMess());   
           }
        }

        if (event.getButton().equals(buttonLicSchet)){
           if (getUI().getNavigator().getState().equals("licSchet"))
               return;
           getUI().getNavigator().navigateTo("licSchet");
        } 

        if (event.getButton().equals(buttonSchets)){
           if (getUI().getNavigator().getState().equals("schets"))
               return;
           getUI().getNavigator().navigateTo("schets");
           
        } 

        if (event.getButton().equals(buttonNn)){
           if (getUI().getNavigator().getState().equals("nalogNaklad"))
               return;
           getUI().getNavigator().navigateTo("nalogNaklad");
        } 
           
           
           
                
    }
}
