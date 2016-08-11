/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Notification;
import java.util.Date;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.StatePlat;

/**
 *
 * @author Сергей
 */
public class MenuBarArea extends VerticalLayout {
    private  MenuBar menubar = new MenuBar();
    private StatePlat statePlat;
    private Config config;
    MenuBarArea(){
        init();
    }
    
    private void init(){
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        menubar.setStyleName("menubararea");
        final MenuBar.MenuItem file = menubar.addItem("Потребление", null);
        Boolean enabledInputRaport = statePlat.getPause().intValue() == 0;
        if (statePlat.getGoest() & config.getTested().equals("1")) { enabledInputRaport = true;}
        file.addItem("Ввод показаний", inputRaportCommand).setEnabled(enabledInputRaport);
        file.addItem("Счета",schetsCommand); //.setEnabled(false);
        file.addItem("Документы на подпись",lispDocOnSignum); //.setEnabled(false);
        file.addItem("Налоговые накладные",nalogCommand); //.setEnabled(false);
        file.addItem("История налоговых накладных",histRaportsCommand); //.setEnabled(false);
        file.addSeparator();
        file.addItem("Точки учета", tuCommand);
        file.addItem("Счетчики", puCommand);
        file.addItem("История тарифов", tarifsCommand);
        final MenuBar.MenuItem edit = menubar.addItem("Начисления и оплаты", null);
        edit.addItem("Долги", dolgiCommand);
        edit.addItem("Лицевой счет", licSchetCommand); //.setEnabled(false);
        final MenuBar.MenuItem orik = menubar.addItem("Заявки и коректировки", null);
        orik.addItem("Заявки на объем потребления",applicationCommand);
        orik.addItem("Корректировки договорных величин",correctionCommand);


        final MenuBar.MenuItem view = menubar.addItem("Настройки", null);
        view.addItem("Личный кабинет", kabinetCommand); //.setEnabled(false);
        view.addItem("Документы и описания", helpCommand); //.setEnabled(false);

        addComponent(menubar);
        
    }
    
   private Command inputRaportCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
           if (getUI().getNavigator().getState().equals("lispRa"))
               return;
           //--------------------------------------------------
           statePlat.refreshState();
           if (statePlat.getGoest() & config.getTested().equals("1")) {
              if (statePlat.getRaportEnabled().equals(0)) 
              Notification.show("Доступ к вводу показаний закрыт ("+statePlat.getMess()+"). Но из за тестового режима доступ откроем");
              statePlat.setRaportEnabled(1); //raportenabled = 1;
           }
           //--------------------------------------------------
           if ( statePlat.getRaportEnabled().equals(1) ){
               getUI().getNavigator().navigateTo("lispRa");
           } else
                Notification.show(statePlat.getMess());   
        }
   };     
   
   private Command tuCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
           if (getUI().getNavigator().getState().equals("lispTu"))
               return;
           getUI().getNavigator().navigateTo("lispTu");
        }
   };     
   
   private Command puCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
           if (getUI().getNavigator().getState().equals("lispAllPu"))
               return;
           getUI().getNavigator().navigateTo("lispAllPu");
        }
   };     
   
   private Command dolgiCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
           if (getUI().getNavigator().getState().equals("dolgi"))
               return;
           getUI().getNavigator().navigateTo("dolgi");
        }
   };

    private Command licSchetCommand = new Command(){
        public void menuSelected(MenuItem selectedItem) {
            if (getUI().getNavigator().getState().equals("licSchet"))
                return;
            getUI().getNavigator().navigateTo("licSchet");
        }

    };

    private Command applicationCommand = new Command(){
        public void menuSelected(MenuItem selectedItem) {
            if (getUI().getNavigator().getState().equals("application"))
                return;
            getUI().getNavigator().navigateTo("application");
        }

    };


    private Command correctionCommand = new Command(){
        public void menuSelected(MenuItem selectedItem) {
            if (getUI().getNavigator().getState().equals("correction"))
                return;
            getUI().getNavigator().navigateTo("correction");
        }

    };

   private Command schetsCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
           if (getUI().getNavigator().getState().equals("schets"))
               return;
           getUI().getNavigator().navigateTo("schets");
        }
   };

    private Command lispDocOnSignum = new Command() {
        public void menuSelected(MenuItem selectedItem) {
            if (getUI().getNavigator().getState().equals("lispDocOnSignum"))
                return;
            getUI().getNavigator().navigateTo("lispDocOnSignum");
        }
    };

   private Command nalogCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
           if (getUI().getNavigator().getState().equals("nalogNaklad"))
               return;
           getUI().getNavigator().navigateTo("nalogNaklad");
        }
   };     
   
   private Command kabinetCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
           if (getUI().getNavigator().getState().equals("kabinet"))
               return;
           getUI().getNavigator().navigateTo("kabinet");
        }
   };     

   private Command helpCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
           if (getUI().getNavigator().getState().equals("help"))
               return;
           getUI().getNavigator().navigateTo("help");
        }
   };     
/*   
   private Command menuCommand = new Command() {
        public void menuSelected(MenuItem selectedItem) {
            Notification.show("Action " + selectedItem.getText());
        }
   };     
*/   
   private Command histRaportsCommand = new Command(){

        @Override
        public void menuSelected(MenuItem selectedItem) {
           if (getUI().getNavigator().getState().equals("historyRaports"))
               return;
           getUI().getNavigator().navigateTo("historyRaports");
        };
   };

   private  Command tarifsCommand = new Command() {
       @Override
       public void menuSelected(MenuItem menuItem) {
           if (getUI().getNavigator().getState().equals("historyTarif"))
               return;
           getUI().getNavigator().navigateTo("historyTarif");
       };
   };
}

