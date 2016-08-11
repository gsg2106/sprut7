/*
 * To change is template, choose Tools | Templates
 * and open the template in the editor.
 * Надо искать и раскомментировать prepareButton.setVisible
 */
package metacom.sprut7;

import com.amb.asn1.ASN1String;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.filter.And;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
//import com.vaadin.terminal.Resource;
//import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
//import de.steinwedel.vaadin.MessageBox;
//import de.steinwedel.vaadin.MessageBox.ButtonType;
//import de.steinwedel.vaadin.MessageBox.EventListener;
import java.util.Date;
import com.vaadin.ui.TabSheet;
import de.steinwedel.messagebox.ButtonId;
import de.steinwedel.messagebox.Icon;
import de.steinwedel.messagebox.MessageBox;
import de.steinwedel.messagebox.MessageBoxListener;
//import de.steinwedel.vaadin.MessageBox;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import metacom.sprut7.RaEditor.EditorSavedEvent;
import metacom.sprut7.RaEditor.EditorSavedListener;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.domain.Ra;
/**
 *
 * @author Сергей
 */
/*public class LispRaWindow extends HorizontalSplitPanel implements*/
/*public class LispRaWindow extends Panel implements*/
/*        ComponentContainer {*/

public class LispRaWindow extends VerticalLayout  implements
        IComponentContainer {
    private Integer idplat;
    private String nameplat;
    private String adres;
    private String state;
    private String numdog;
    private String linkRes = null;
    private Date dtLock;
    
    private Button prepareButton;
    private Button editButton;
    private Button calcButton;
    private ProgressBar pi2;
    private ProgressBar pi3;
    private JPAContainer<Ra> ra;
    private Table raTable;
    private boolean ok;
    private LoadRaWeb raweb = null;
    private String basepath;
    private TabSheet t;
    private UploadPDF uploadpdf1;
    private VerticalLayout vl1;
    private VerticalLayout vl2;
    private VerticalLayout vl3;
    private Worker2 worker2;
    private Worker3 worker3;
    private LispReadySchets ls;
    static Config config = null;
    private StatePlat statePlat;
    
    public LispRaWindow()
    {}
    public void init(){
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        this.idplat=statePlat.getIdPlat();  //idplat;
        this.nameplat=statePlat.getNamePlat();  //nameplat;
        this.adres= statePlat.getAdres(); //adres;
        this.state= statePlat.getState(); //state;
        this.numdog= statePlat.getNumdog(); //numdog;
        this.dtLock = statePlat.getDtLock(); //dtLock;
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        basepath = config.getPathToSignLS();

        setMargin(true);
        setHeight("100%");        
        setStyleName("LispRaWindow");
        this.setHeight("700px");
        buildMain();
        setImmediate(true);
    }
    private void buildMain() {
        //------------------- создадим панель закладок ----------------------
        // Tab 1 content
       Statistika stat = new Statistika();
       stat.init(idplat, Statistika.st_input );
        
        Resource icon1 = null;
        Resource icon2 = null;
        vl1 = new VerticalLayout();
        vl1.setMargin(true);
        String ms ="<i><p style=\"color:#0000ff\">"+
             "Сформируйте бланк рапорта за текущий отчетный период."+
             "Нажмите кнопку \"Получить счет\" чтобы сформировать комплект документов."+
             "Перейдите на закладку \"Счета на оплату\" для продолжения операции."+
             "</p> </i>";
        vl1.addComponent(new Label(ms,ContentMode.HTML)); //Label.CONTENT_XHTML));
        vl1.addComponent(new Ruler());
        
        // Tab 2 content
        vl2 = new VerticalLayout();
        vl2.setMargin(true);
        
        String ms1 ="<i><p style=\"color:#0000ff\">"+
             "Посмотрите список сформированных документов и  загрузите их в свой компьютер,  нажав на   ссылку. "+
             "Нажмите кнопку \"Подписать счет\" и перейдите на закладку \"Подписать файл счета\""+
             "</p> </i>";
        vl2.addComponent(new Label(ms1,ContentMode.HTML)); // .CONTENT_XHTML));
        vl2.addComponent(new Ruler());

        t = new TabSheet();  
        t.setHeight("100%"); //t.setHeight("200px");
        t.setWidth("100%");  //t.setWidth("400px");
        t.addTab(vl1, "Ввод показаний счетчиков.", icon1);
        t.addTab(vl2, "Счета на оплату.", icon2);
        addComponent(t);
        //-------------------------------------------------------------------
       ra = JPAContainerFactory.make(Ra.class,linkRes);
       ra.addContainerFilter(new And(new Compare.Equal("raPK.idPlat", idplat),
                             new Compare.Equal("raPK.yea", statePlat.getYearRaport().shortValue()),
                             new Compare.Equal("raPK.mon", statePlat.getMonthRaport().shortValue())
                                    )
                             );
       ra.applyFilters();
        String ss;
        if (statePlat.getMonthRaport()<=0){
            ss = "Предыдущий рапорт отсутствует.";
        } else {
            ss = "Показания по рапорту за "+getMonths(statePlat.getMonthRaport())+" месяц "+statePlat.getYearRaport()+" года.";
        }
       raTable = new Table(ss, ra);
       raTable.setSizeFull();       
       raTable.setSelectable(true);
       raTable.setImmediate(true);
       raTable.setHeight("100%");
       raTable.setVisibleColumns(new Object[] {"stateRecord","codtu","nametu","eng","zavno","nameZone","nachalo","danrap", "ktr","kvtchrazn" }); //,"puOn","offPu","readOnly" //,"razn" });
       raTable.setColumnHeaders(new String[] {"Вид учета","№ ТУ","Название ту","А/Р","Зав.№","Зона","Нач.пок","Тек.пок.","Ктр","Квт-ч начисл."});   //,"puOn","offPu","readOnly" //,"Разница"
       raTable.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {
                setModificationsEnabled(event.getProperty().getValue() != null);
            }

            private void setModificationsEnabled(Boolean b) {
                editButton.setEnabled(b);
            }
        });
       
        raTable.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent event) {
                if (event.isDoubleClick()) {
                    raTable.select(event.getItemId());
                    editPokaz();
                }
            }
        });
       
       HorizontalLayout toolbar = new HorizontalLayout();
       HorizontalLayout bar1 = new HorizontalLayout();       
       HorizontalLayout bar2 = new HorizontalLayout();       
       Boolean raExists = PrecalcExist(statePlat.getMonNew());
       prepareButton = new Button( raExists ? "Переформировать рапорт за "+getMonths(statePlat.getMonNew()) : "Сформировать рапорт за "+getMonths(statePlat.getMonNew()));
       if (statePlat.getMonNew() == null) {
         prepareButton.setEnabled(false);  
       }
       prepareButton.setId("btnPrepare");
       prepareButton.setDescription("Формирование рапорта необходимо для последующего ввода показаний и расчета счета");
       prepareButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
              //---------Начинаем диалог по пересчету бланка рапорта------------
              /* Если бланк рапорта уже есть, то переспросить факт операции
               * если бланка нет, то сразу расчет.
               * Заблокировать кнопки самое себя, editButton и calcButton
               * послать запрос на расчет бланка рапорта,
               *  показать ожидание,
               *  дождаться итога и перерисовать состав рапорта
               *  если не дождались, прекратить поцесс и дать сообщение.
               *  В любом случае по окончании процесса разблокировать все заблокированное
               */
               statePlat.refreshState(); 
               if (!((statePlat.getYearRaport().intValue()== statePlat.getYeaNew().intValue()) &
                   (statePlat.getMonthRaport().intValue()==statePlat.getMonNew().intValue()))){
                   // бланка рапорта нет
                   recalcBlankRaport();
               } else {
                   // бланк рапорта есть обновим картинку
                   Notification.show("Бланк рапорта уже сформирован",Notification.Type.WARNING_MESSAGE);        
                          raTable.setCaption("Показания по рапорту за "+getMonths(statePlat.getMonthRaport())+" месяц "+statePlat.getYearRaport()+" года.");
                           prepareButton.setCaption("Переформировать рапорт за "+getMonths(statePlat.getMonNew()));
                          //Обновить фильтр
                          ra.removeAllContainerFilters();
                          ra.addContainerFilter(new And(new Compare.Equal("raPK.idPlat", idplat),
                                                        new Compare.Equal("raPK.yea", statePlat.getYearRaport().shortValue()),
                                                        new Compare.Equal("raPK.mon", statePlat.getMonthRaport().shortValue())
                                                        )
                                               );
                          ra.applyFilters();
                          raTable.refreshRowCache();
                           
                          // приведение кнопок в порядок по окончании процесса ожидание формирования бланка
                          pi3.setVisible(false);
                          pi3.setValue(0f);
                          prepareButton.setEnabled(true);
                          editButton.setVisible(true);
                          calcButton.setVisible(true);
                          // если рапорт сформирован без ошибок и не пустой то
                          if ((ra.size()>0)) {
                             prepareButton.setVisible(false);
                          };
                          calcButton.setEnabled(true);
                          //----------------------------------
                          t.getTab(vl2).setVisible(false);
                          if (vl3 != null) {
                             t.getTab(vl3).setVisible(false);
                          }
                          //----------------------------------
                          if (ls != null){
                             ls.Refresh(statePlat.getYearRaport(), statePlat.getMonthRaport());
                             if (uploadpdf1 != null){
                                 uploadpdf1.setPeriod(statePlat.getYearRaport(), statePlat.getMonthRaport());
                             }   
                         }  
               }
               //---------------------------------------------------------------                
            }
        });
       editButton = new Button("Исправить");
       editButton.setDescription("Ввод показаний прибора учата в строку рапорта");
       editButton.addClickListener(new Button.ClickListener() {
            //-----------------начинаем ввод <fyre ytdjpvj;yj ghjfyfkbpbhjdfnmпоказаний-------------------------
            @Override
            public void buttonClick(Button.ClickEvent event) {
                editPokaz();
            }
       });
       editButton.setEnabled(false); 
       calcButton = new Button("Получить счет");
       calcButton.setDescription("Формирование платежных документов на основании рапорта");
       calcButton.setEnabled(PrecalcExist(statePlat.getMonNew()));
       calcButton.setId("btnRefresh");
       editButton.setVisible(raExists); 
       if (statePlat.getMonNew() == null){
           editButton.setVisible(false); 
       } else if (statePlat.getMonthRaport().intValue() < statePlat.getMonNew().intValue()){
           editButton.setVisible(false); 
       }
       calcButton.setVisible(raExists); 
       calcButton.addClickListener(new Button.ClickListener() {
            //------------ начинаем формирование счетов по рапорту-------------
            @Override
            public void buttonClick(Button.ClickEvent event) {
//            временно отключена проверка на наличие пустого рапорта              
              Boolean e = raIsEmty();
              if (e){
                 Notification.show("показания введены не во всех точках учета.",Notification.Type.WARNING_MESSAGE); 
                 return;
/*    Внимание! Эта часть нужна для рвботы но отключена по причине требования полной блокировки  
                 MessageBox mb = new MessageBox(getWindow(), 
                              "Вы не полностью заполнили рапорт.",
                              MessageBox.Icon.QUESTION, 
                              "В часть точек учета не введены показания."
                              +"\n Вы действительно желаете получить счет без показаний?",  
                              new MessageBox.ButtonConfig(MessageBox.ButtonType.YES, "Да"),
                              new MessageBox.ButtonConfig(MessageBox.ButtonType.NO, "Нет"));
                 mb.show(new EventListener() {
                     private static final long serialVersionUID = 1L;
                     @Override
                     public void buttonClicked(ButtonType buttonType) {
                                if (buttonType.equals(MessageBox.ButtonType.YES)){
                                    continueCalc();
                                }}
                 });    
*/                 
              } else {
                continueCalc();
              }
            }
       });
       //----------------- продолжим размещение компонент на форме--------------
       pi2 = new ProgressBar();
       pi2.setIndeterminate(true);
       pi2.setSizeUndefined();
       pi2.setValue(0f);
       pi2.setVisible(false);

       pi3 = new ProgressBar();
       pi3.setIndeterminate(true);
       pi3.setVisible(false);
       bar2.setSizeUndefined();
       
       bar1.addComponent(pi3);
       bar1.addComponent(prepareButton);
       bar1.addComponent(editButton);
       bar2.addComponent(pi2);
       bar2.addComponent(calcButton);
       toolbar.addComponent(bar1);
       toolbar.addComponent(bar2);
       toolbar.setWidth("100%");
       toolbar.setComponentAlignment(bar1, Alignment.TOP_LEFT);       
       toolbar.setComponentAlignment(bar2, Alignment.TOP_RIGHT);       
       vl1.addComponent(toolbar);
       vl1.addComponent(raTable); //на первую закладку
       vl1.setExpandRatio(raTable, 1);
       // показать панель счетов и заполнить ее
       viewchet();
       if (ls == null){
           t.getTab(vl2).setVisible(false);
       } else if (ls.isEmpty()){
           t.getTab(vl2).setVisible(false);
       } else if (statePlat.getMonNew() == null){
           t.getTab(vl2).setVisible(true);
           t.setSelectedTab(vl2);
       } else if (statePlat.getMonthRaport().intValue() < statePlat.getMonNew().intValue()){
           t.getTab(vl2).setVisible(false);
       } else {
           t.getTab(vl2).setVisible(true);
           t.setSelectedTab(vl2);
       }
       
       if (!((statePlat.getYearRaport().intValue()==statePlat.getYeaNew().intValue()) & (statePlat.getMonthRaport()).intValue()==statePlat.getMonNew().intValue())){
           // проверим внешнюю блокировку перерасчета.
           if (externalLock())
               calcButton.setEnabled(false);
           Notification.show("Бланк рапорта на "+getMonths(statePlat.getMonNew())+" отсутствует. Выполняется формирование бланка рапорта для ввода показаний.",Notification.Type.WARNING_MESSAGE);
//             Integer MilliSec = 10;
//             String script = "setTimeout(\"document.getElementById('btnPrepare').click()\","+ MilliSec + ")";
//             Page.getCurrent().getJavaScript().execute(script);
// должно работать             JavaScript.getCurrent().execute("alert('Hello')");
             prepareButton.click();
       } else {
           if ((ra.size()>0)) {
              prepareButton.setVisible(false);
              calcButton.setEnabled(true);
              calcButton.setVisible(true);
              calcButton.setEnabled(!t.getTab(vl2).isVisible());
           };
           // проверим внешнюю блокировку перерасчета.
           if (externalLock())
               calcButton.setEnabled(false);
       }
       // проверим внешнюю блокировку перерасчета.
//       if (externalLock())
//           calcButton.setEnabled(false);
    }

private void continueCalc(){
              if   (raweb == null)
                  raweb = new LoadRaWeb(idplat);
              // получить состояние готовности счета              
              ok = raweb.getRefreshOnReady(); 
              if (!ok ){
                  // счет не готов
                  recalcSchet();
              } else {
                   // счет готов   
                   MessageBox mb = MessageBox.showPlain(Icon.QUESTION,
                         "Счет уже рассчитан",
                          "Пересчет счета потребует повторной загрузки и подписывания всех документов за текущий период."
                                + "\n Ранее подписанные документы будут удалены."
                                + "\n Вы действительно желаете пересчитать счет?",
                           new MessageBoxListener() {
                               
                              @Override
                              public void buttonClicked(ButtonId buttonId) {
                                if (buttonId.equals(ButtonId.OK)){
                                    raTable.commit();                                    
                                    // запросим пересчет
                                    ok = false;
                                    raweb.SendQuery(statePlat.getYeaNew(), statePlat.getMonNew());
                                    recalcSchet();
                                }    
                              }
                           },                                 
                           ButtonId.OK, ButtonId.NO);
                   mb.getButton(ButtonId.OK).setCaption("Да");
                   mb.getButton(ButtonId.NO).setCaption("Нет");                  
              }    
}    
    
private void callEditor(Item item){
                //----------------- вызов редактора --------------------------
calcButton.setEnabled(true);
                RaEditor raEditor = new RaEditor(item, linkRes);
                raEditor.addListener(new EditorSavedListener() {
                    @Override
                    public void editorSaved(EditorSavedEvent event) {
                        raTable.refreshRowCache();
           t.getTab(vl2).setVisible(false);
           if (vl3 != null) {
              t.getTab(vl3).setVisible(false);
           }
                    }
                });
                 getUI().addWindow(raEditor);
}

private void recalcSchet(){
     // запросим пересчет
     raweb.SendQuery(statePlat.getYeaNew(), statePlat.getMonNew());
     // Ждем результата
     pi2.setVisible(true);
     worker3 = new Worker3();
     worker3.start();
     UI.getCurrent().setPollInterval(5500);
}
    
    public class Worker3 extends Thread {
        private Boolean err = false;
        private String mess = null;
        private boolean ok1 = false;
        // ожидание окончания расчета счета в отдельном потоке
        @Override
        public void run() {
            try {
                Integer iCount = 0;
                do {
                   Thread.sleep(5000);
                   err = false;
                   UI.getCurrent().access(new Runnable(){

                       @Override
                       public void run() {
                           ok1 = raweb.getRefreshOnReady();
                           String mess = raweb.getMessPrepare(); 
                           if (ok1 ){
                              if ( mess == null) {
                                  // перерисовать список счетов если он пересчитан.
                                  Notification.show("Список счетов сформирован.",Notification.Type.WARNING_MESSAGE);        
                             } else {
                                  // сформируем сообщение об ошибке
                                  Notification.show(mess,Notification.Type.ERROR_MESSAGE);                 
                             }    
                           } else if (raweb.getReqRecalc().charValue() == '0' && ( mess != null)) {
                              Notification.show(mess,Notification.Type.ERROR_MESSAGE);                 
                              err = true;
                              //break;
                           }
                       }
                       
                   });
                   if (err) break;
                   iCount++;
                }  while ((!ok1) && (iCount < 20));
                ok = ok1;
                if (!ok ){
                    mess ="Истек срок ожидания;";
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                mess ="Поток остановлен";
                ok = false;
            } catch (Exception e){
                ok = false;
                mess ="Ошибка "+ (String)((e == null) ? "не опознана": e.getMessage());
            }
                // synchronize changes over application
            UI.getCurrent().access(new Runnable(){
                  @Override
                  public void run() {
                    prosessed3(mess);
                  }
            });
        }
    }

    public void prosessed3(String mess) {
        // приведение кнопок в порядок по окончании процесса ожидание формирования бланка
        pi2.setVisible(false);
        if (mess ==null){
            mess = raweb.getMessPrepare(); 
        }
            if (mess == null) {
                editButton.setVisible(true);
                calcButton.setVisible(true);
                calcButton.setEnabled(false);
                t.getTab(vl2).setVisible(true);
            } else
                Notification.show(mess,Notification.Type.ERROR_MESSAGE);                 
                           ra.removeAllContainerFilters();
                          ra.addContainerFilter(new Compare.Equal("raPK.idPlat",idplat));
                          ra.addContainerFilter(new Compare.Equal("raPK.yea", statePlat.getYearRaport()));
                          ra.addContainerFilter(new Compare.Equal("raPK.mon", statePlat.getMonthRaport()));
                          ra.applyFilters();
                          raTable.refreshRowCache();
        if (ls != null){
          ls.Refresh(statePlat.getYearRaport(), statePlat.getMonthRaport());
          if (uploadpdf1 != null){
             uploadpdf1.setPeriod(statePlat.getYearRaport(), statePlat.getMonthRaport());
          }   
          if (vl2 != null) {
             t.setSelectedTab(vl2);
          }
        }  
        UI.getCurrent().setPollInterval(-1);
    }

private void recalcBlankRaport() {
       // запрос на пересчет бланка рапорта 
       prepareButton.setEnabled(false);
       editButton.setVisible(false);
       calcButton.setVisible(false);
       prepareButton.setVisible(true);
       //pi3.setEnabled(true);
       
       if (raweb == null)
          raweb = new LoadRaWeb(idplat);
       // запросим пересчет бланка рапорта
       raweb.SendQueryBlank(statePlat.getYeaNew(), statePlat.getMonNew());
       // Ждем результата
       worker2 = new Worker2();
       worker2.start();
       UI.getCurrent().setPollInterval(5500);
       pi3.setVisible(true);
       pi3.setEnabled(true);
};
    
    public void prosessed2(String mess) {
                  if ( mess == null) {
                          // перерисовать бланк рапорта если он пересчитан.
                          Notification.show("Рапорт сформирован",Notification.Type.WARNING_MESSAGE);        
                          statePlat.setMonthRaport(raweb.getMon());
                          statePlat.setYearRaport(raweb.getYea());
                          raTable.setCaption("Показания по рапорту за "+getMonths(statePlat.getMonthRaport())+" месяц "+statePlat.getYearRaport()+" года.");
                           prepareButton.setCaption("Переформировать рапорт за "+getMonths(statePlat.getMonNew()));
                          //Обновить фильтр
                          ra.removeAllContainerFilters();
                          ra.addContainerFilter(new Compare.Equal("raPK.idPlat",idplat));
                          ra.addContainerFilter(new Compare.Equal("raPK.yea", statePlat.getYearRaport()));
                          ra.addContainerFilter(new Compare.Equal("raPK.mon", statePlat.getMonthRaport()));
                          ra.applyFilters();
                          raTable.refreshRowCache();
                          //-------------------------------
//"Это перенестив prosessed2()"                          
                      } else {
                          // сформируем сообщение об ошибке
                          Notification.show(mess,Notification.Type.ERROR_MESSAGE);                 
                      }    
        
        // приведение кнопок в порядок по окончании процесса ожидание формирования бланка
        pi3.setIndeterminate(false);
        pi3.setVisible(false);
        pi3.setValue(0f);
        UI.getCurrent().setPollInterval(-1);
        prepareButton.setEnabled(true);
        if (mess == null) {
           editButton.setVisible(true);
           calcButton.setVisible(true);
           // если рапорт сформирован без ошибок и не пустой то
           if ((ra.size()>0)) {
              prepareButton.setVisible(false);
           };
           calcButton.setEnabled(true);
           
        } 
        //----------------------------------
        t.getTab(vl2).setVisible(false);
        if (vl3 != null) {
           t.getTab(vl3).setVisible(false);
        }
        //----------------------------------
        if (ls != null){
          ls.Refresh(statePlat.getYearRaport(), statePlat.getMonthRaport());
          if (uploadpdf1 != null){
             uploadpdf1.setPeriod(statePlat.getYearRaport(), statePlat.getMonthRaport());
          }   
        }
    }
    
    public class Worker2 extends Thread {
        // ожидание окончания расчета бланка рапорта в отдельном потоке
        private String mess = null;
        private boolean ok1 = false;
        @Override
        public void run() {
        try {
                int iCount = 0;
                do {
                   Thread.sleep(5000);
                   //Thread.sleep(10000);
                   UI.getCurrent().access(new Runnable(){
                       @Override
                       public void run() {
                           ok1 = raweb.getRefreshOnPrepared();  //getRefreshOnReady();
                           mess = raweb.getMessPrepare();
                       }
                   }
                   );
                   iCount++;
                }  while ((!ok1) && (iCount < 20) && mess == null);
                ok = ok1;
                if (!ok ){
                    mess ="Истек срок ожидания;";
                }
        } catch (InterruptedException e) {
                e.printStackTrace();
                mess ="Поток остановлен";
                ok = false;
        } catch (Exception e){
                ok = false;
                mess ="Ошибка "+ (String)((e == null) ? "не опознана": e.getMessage());
        }
            // synchronize changes over application
//            synchronized (getUI()) {
        UI.getCurrent().access(new Runnable(){
                  @Override
                  public void run() {
                    prosessed2(mess);
                  }
            });
        }
    }

    private Boolean PrecalcExist(Integer mon){
        // плательщик превварительно расчитан и в состоянии=1 и не блокирован интервалом времени
        // пока используется долько для установки состояния кнопки "расчет рапорта и формирования счетов"
        LoadRaWeb raweb1 = new LoadRaWeb(idplat);
        if ((statePlat.getMonNew() ==null) || (raweb1.getMon() == null)){
            return raweb1.getCanRecalc(); 
        } else if (raweb1.getMon().intValue() == mon.intValue() ){
           return  raweb1.getCanRecalc(); 
        } else {
           return false; 
        }   
    }

private void viewchet(){
       /*----------------- нарисуем ссылки ----------------- */
       //----- добавить список выписанных счетов---------------
       //------ СДЕСЬ ЖЕ ПОПУТНО ПОДПИСЫВАЮТСЯ СЧЕТА ----------
       /**
       * Получить список счетов из базы данных,
       * подписаль их, при необходимости и опубликовать через таблицу на скачивание
       */    
       if (ls == null){
           ls = new LispReadySchets();
       }    
       vl2.addComponent(ls);
       vl2.setComponentAlignment(ls, Alignment.TOP_CENTER);
       //------------------------------------------------------        
       HorizontalLayout operationbar = new HorizontalLayout();
       uploadpdf1 = new UploadPDF(idplat,statePlat.getYearRaport(),statePlat.getMonthRaport(),
               new UploadPDF.RefreshRecipient(){
                   @Override
                   public void refresh(Integer yea, Integer mon) {
                       ls.Refresh(yea, mon);
                       if (ls.getAllReady()){
                           LockRa("Все документы подписаны и получены энергоснабжающей организацией. Работа завершена.");
                       }
                   }
               }
       );
       //----------------- html интеграция ----------------------------------------
       Button htmlButton = new Button("Подписать счет");
       htmlButton.setDescription("Подписывание загруженного файла документа электронной подписью пользователя");
       htmlButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (vl3 == null) {
                   // Tab 3 content
                   Resource icon3 = null;
                   vl3 = new VerticalLayout();
                   vl3.setMargin(true);
                   
                   
        String ms2 ="<i><p style=\"color:#0000ff\">"+
             "Подпишите загруженные файлы документов и отправьте их в энергоснабжающую организацию. Учтите, имя подписанного файла отличается от исходного, наличием фразы '.sign' в конце названия файла."+
             "</p> </i>";
        vl3.addComponent(new Label(ms2,ContentMode.HTML));
        vl3.addComponent(new Ruler());
                   t.addTab(vl3, "Подписать файл счета.", icon3);
                   WebEmbedApplet();
                   vl3.addComponent(new Ruler());
                   vl3.addComponent(uploadpdf1)   ;
                   vl3.addComponent(new Ruler());
                } else {
                  t.getTab(vl3).setVisible(true);
                }
                t.setSelectedTab(vl3);  // setTabIndex(3);
            }
       });
       vl2.addComponent(new Ruler());
       operationbar.addComponent(new Label("После скачивания файла счета его необходимо подписать"));
       operationbar.addComponent(htmlButton)   ;
       vl2.addComponent(operationbar);
       vl2.addComponent(new Ruler());

    LispReadySchetsKvit kvit = new LispReadySchetsKvit();
    if (kvit.getKvitExists()){
        vl2.addComponent(kvit);
    }
    }

private void WebEmbedApplet() {
       //--------------- разместить апплет на панели ------------------------
//       Embedded e = new Embedded("", new ThemeResource("signPdf.html"));
//       Embedded e = new Embedded("", new ThemeResource("signPdf_utf8.html"));
//    Embedded e = new Embedded("", new ThemeResource("signPdfutf8.html"));
    Embedded e = new Embedded("", new ThemeResource("signPdfPfx_utf8.html"));

       //e.setAlternateText("Vaadin web site");
       e.setType(Embedded.TYPE_BROWSER);
       e.setWidth("100%");
       e.setHeight("380px");
       vl3.addComponent(e);
}

public String getMonths(Integer mes ) {
     if (mes == null)     {
         return "?";
     }
    String[] mesString = {"январь", "февраль", "март", "апрель", "май", "июнь",
                "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
    return mesString[mes-1];
}

private void editPokaz(){
                //-------- проверим на разрешение редактировать данные ----
                if (statePlat.getMonNew() != null){
                   if ((statePlat.getYearRaport().intValue()==statePlat.getYeaNew().intValue()) & (statePlat.getMonthRaport().intValue() < statePlat.getMonNew().intValue())){
                      Notification.show("Рапорт за "+getMonths(statePlat.getMonthRaport())+" месяц недоступен для корректировки.",Notification.Type.WARNING_MESSAGE);        
                      return;
                   } else {
                       if ((statePlat.getYearRaport().intValue()<statePlat.getYeaNew().intValue())){
                          Notification.show("Рапорт за "+getMonths(statePlat.getMonthRaport())+" месяц недоступен для корректировки.",Notification.Type.WARNING_MESSAGE);        
                          return;
                       }   
                   }
                }
                final Item item = raTable.getItem(raTable.getValue());
                Integer readOnly = (Integer) item.getItemProperty("readOnly").getValue();
                if (readOnly.equals(1)) {
                   Character offpu = (Character) item.getItemProperty("offPu").getValue();
                   Character puOn = (Character) item.getItemProperty("puOn").getValue();
                   Character snat = (Character) item.getItemProperty("snat").getValue();
                   String mess;
                   if (snat == '1'){mess = "Эта строка показаний снятого счетчика!";}
                   else if (offpu == '1') {mess ="Точка учета отключена.Ввод показаний недопустим";}
                   else if (!(puOn == '1')) {mess ="Без учетная точка.Ввод показаний недопустим";}
                   else {mess = "Эта строка только для чтения!";}
                   Notification.show(mess);        
                   return; 
                }
                
                // проверим внешнюю блокировку перерасчета.
                if (externalLock()){
                   Notification.show("Расчет уже выполнен. Изменение показаний недопустимо",Notification.Type.WARNING_MESSAGE); 
                   return;
                }
                
                if (ls != null){
                    if ( !ls.isEmpty() ){
                       MessageBox mb = MessageBox.showPlain(Icon.QUESTION,
                                "Счет уже рассчитан",
                                "Изменение показаний потребует повторного пересчета счета, загрузки и подписывания всех документов за текущий период."
                                + "\n Ранее подписанные документы будут удалены."
                                + "\n Вы действительно желаете пересчитать счет?",  
                           new MessageBoxListener() {
                               
                              @Override
                              public void buttonClicked(ButtonId buttonId) {
                                if (buttonId.equals(ButtonId.OK)){
                                      callEditor(item);                                    
                                } else {
                                    return;
                                }
                              }
                           },                                 
                           ButtonId.OK, ButtonId.NO);
                        mb.getButton(ButtonId.OK).setCaption("Да");
                        mb.getButton(ButtonId.NO).setCaption("Нет");                  
                    } else {
                      callEditor(item);
                    }
                } else {
                  callEditor(item);
                }    
}
public void LockRa(String mm){
    statePlat.setRaportEnabled(0);
    statePlat.setMess(mm);
    String script = "document.getElementById('btnSchets').click();";
    Page.getCurrent().getJavaScript().execute(script);
}

// проверит наличие показаний в рапорте
private Boolean raIsEmty(){
   Boolean rt = false;
   Boolean kk = false;
   Boolean ll = false;
   Object itemId = ra.firstItemId();
   while (itemId !=null) {
          ll = true;
          Item item = ra.getItem(itemId);
          Integer readOnly = (Integer) item.getItemProperty("readOnly").getValue();
          if (!readOnly.equals(1)) {
              kk=true;
              String danrap = (String) item.getItemProperty("danrap").getValue();
            if (danrap == null){
                 rt=true; 
                return rt;  
            }
            if (danrap.trim().equals("")){
                rt=true; 
                return rt;  
            }
          }
          itemId = ra.nextItemId(itemId);
   }
   if (kk){
      return rt; 
   } else {
       return !ll;
       
   }
}

private Boolean externalLock(){
    if (raweb == null) raweb = new LoadRaWeb(idplat);
       raweb.getRefreshOnReady(); 
    return raweb.getLocked1();   
}

}
    //--------------------------------------------------------------------------
    //  подпись через bouncicastle работает но отключено за ненадобностью
    //--------------------------------------------------------------------------
    /*    
    SignPDF signer = new SignPDF(keystore, keystorePassword);
        try {
            signer.signPdf(FileNameIn, FileNameOut);
        } catch (IOException ex) {
            Logger.getLogger(LispRaWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(LispRaWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(LispRaWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    //---------------------------------------------------------------------------    
//System.out.println("----------- Ok ------------");
        
}  
    */