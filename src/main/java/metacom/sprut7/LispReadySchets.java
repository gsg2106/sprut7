/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

//import com.lowagie.text.DocumentException;
//import com.mycompany.maven_sprut.domain.TipDocs;
//import com.mycompany.maven_sprut.domain.WebSchets;
//import com.vaadin.Application;
import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.server.*;
//import com.vaadin.terminal.FileResource;
//import com.vaadin.terminal.StreamResource;
//import com.vaadin.terminal.StreamResource.StreamSource;
//import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutionException;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityTransaction;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.domain.TipDocs;
import metacom.sprut7.domain.WebSchets;

/**
 * Получить список счетов из базы данных,
 * подписаль их, при необходимости и опубликовать через таблицу на скачивание
 * @author Сергей
 */
@SuppressWarnings("serial")

public class LispReadySchets extends VerticalLayout{
//    private JPAContainer<WebSchets> schets;
    public static Map<Integer,String> tipDocs = new HashMap<Integer,String>();
    private Integer yea;
    private Integer mon;
    private Integer idplat;
    private String PuthSign;
    //private final SprutVaadinApplication application = SprutVaadinApplication.getInstance();
    private String keystore;
    private String nameKeystore;
    private String keystorePassword;
    private String PuthReady;
    private String linkRes = null;
    private Boolean allReady = false;
    static Config config = null;
    private StatePlat statePlat;
    private boolean onlyNotReadyDocs = false;
    private static final ThemeResource ICON = new ThemeResource(
            "img/16/action_save.gif");
//            "../runo/icons/icon_world.gif");
//            "../sampler/icons/icon_world.gif");
    
    Table table;
    ThemeResource okImage = new ThemeResource("img/16/Ok.gif");
    
    static {
//        linkRes = SprutVaadinApplication.getInstance().getLinkRes(); 
        EntityManager em = Persistence
    	   .createEntityManagerFactory("sprut")
	   .createEntityManager();
//круто.. получить соединение  java.sql.Connection connection = em.unwrap(java.sql.Connection.class);                
        Collection emps = em.createQuery("SELECT g FROM TipDocs g").getResultList();
        Boolean docsExists = !emps.isEmpty();  
        if (docsExists) {
            Iterator it = emps.iterator();
            while (it.hasNext()){
                TipDocs e = (TipDocs) it.next();
                tipDocs.put(e.getKindDoc() , e.getDescDoc());
            }
       }
       em.close(); 
    }
            
//    public LispReadySchets(String licShetPath) {
    public LispReadySchets() {
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        this.idplat=statePlat.getIdPlat();  //idplat; 
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        //this.nameplat=statePlat.getNamePlat();  //nameplat;
        //this.adres= statePlat.getAdres(); //adres;
        //this.state= statePlat.getState(); //state;
        //this.numdog= statePlat.getNumdog(); //numdog;
       this.yea = statePlat.getYearRaport();
       this.mon = statePlat.getMonthRaport();

//       application = SprutVaadinApplication.getInstance();
       PuthReady = config.getPathToReadyLS();
//       String PuthNoSign = application.getPuthToNoSignLS();
       PuthSign = config.getPathToSignLS();
        try {
            nameKeystore = (String) VaadinSession.getCurrent().getAttribute("nameKeystore");
        } catch (Exception ex) {
            Logger.getLogger(LispReadySchets.class.getName()).log(Level.SEVERE, null, ex);
            //Window wnd = application.getMainWindow();
            //wnd.showNotification("Не известно название контейнера сертификата энергоснабжающей организации",Window.Notification.TYPE_ERROR_MESSAGE);
            Notification.show("Не известно название контейнера сертификата энергоснабжающей организации",Notification.Type.ERROR_MESSAGE);
        }
       keystore =  config.getPathKeystore()+"\\"+nameKeystore;
       keystorePassword = config.getLispKeystorePasswords().get(nameKeystore);       
       //linkRes = (String) VaadinSession.getCurrent().getAttribute("linkRes");
       
//       table = new Table("Список выписанных счетов по рапорту",schets); 
       table = new Table("Список выписанных счетов по рапорту");
       addComponent(table);
       table.setWidth("100%");
       table.setHeight("170px");
       //table.setHeight("100%");
       //table.setSizeUndefined();
       //table.setSizeFull() ;
       table.setSelectable(true);
       table.setMultiSelect(true);
       table.setImmediate(true); // react at once when something is selected
       
        table.addContainerProperty("Подписан",Embedded.class, null);        
        table.addContainerProperty("Файл документа", Link.class,  null);
        table.addContainerProperty("Операция", Button.class,  null);
        table.addContainerProperty("Вид документа",  String.class,  null);
        table.addContainerProperty("Состояние документа",String.class, null);        
        //table.setColumnExpandRatio(selected, SIZE_UNDEFINED);        
        //table.setColumnWidth(selected, UNITS_EM);
        //table.setColumnWidth("Скачан", 60);
//        table.setColumnWidth("Подписан",120);
        table.setColumnWidth("Операция",75);
        table.setColumnWidth("Вид документа",180);
        table.setColumnWidth("Подписан",55);
        Refresh(statePlat.getYearRaport(),statePlat.getMonthRaport());
    }
    
    public void Refresh(Integer yearraport, Integer monthraport){
        //String suff;
       this.yea = yearraport;
       this.mon = monthraport;
        table.removeAllItems();
        
        EntityManager em = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
//        Collection emps = em.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon and (g.created is not null) and (g.deleted is null)").
//                 setParameter("idplat", idplat).setParameter("yea", yea).setParameter("mon", mon).getResultList();
        Collection emps;
        if (onlyNotReadyDocs){
            emps = em.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon and (g.created is not null) and (g.deleted is null) and (g.idgroup = 0 and g.ready is null )").
                 setParameter("idplat", idplat).setParameter("yea", yea).setParameter("mon", mon).getResultList();
        } else{
            emps = em.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon and (g.created is not null) and (g.deleted is null) and (g.idgroup = 0 )").
                    setParameter("idplat", idplat).setParameter("yea", yea).setParameter("mon", mon).getResultList();
        }
        Boolean schetExists = !emps.isEmpty();
//////        Collection emps = em.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon and (g.prepared is not null)").
        if (schetExists) {
            Iterator it = emps.iterator();
            AmbSignPDF signer = null;
            Integer i = 0;
            allReady = true;
            while (it.hasNext()){
                WebSchets e = (WebSchets) it.next();
                if (e.getReady() == null){
                   allReady = false;
                } else {if (e.getReady().intValue() == 0){
                   allReady = false;
                }}
                Short signed = e.getSigned();
                Embedded dwn = null; Embedded read = null;
                String stateLink = null;
                String puth = PuthSign;
                String fileName = e.getFileName();
                String fileNameSign = e.getFileNameSign();
                Integer viddoc = e.getViddoc();
                Boolean makeSign = false;
                if (e.getSigned()==null){makeSign = true;}
                else { if (e.getSigned().intValue() == 0)  {makeSign = true;} };
                if (makeSign){
                    // ------------- подпишем документ -------------------------
                    String FileNameIn = config.getPathToNoSignLS()+"\\"+fileName;
                    String FileNameOut ="";
                    if (e.getIdgroup().intValue() == 0){
                        FileNameOut = config.getPathToSignLS()+"\\"+fileName;
                    } else {
                        FileNameOut = config.getPathToReadyLS()+"\\"+fileNameSign;
                    }
                    // проверим наличие файла
                    File tmp = new File(FileNameIn);
                    if (!tmp.exists()){
                        /*
                        // файла нет, но он может быть среди подписанных
                        File tmpOut = new File(FileNameOut);
                        if (tmpOut.exists()){
                           // О файл уже был подписан не не помечен в базе данных
                           // исправляем
                           em.getTransaction().begin();
                           e.sets
                           em.getTransaction().commit();
                        } else{
                        */
                          // сбой файла нет  
                         // Window wnd = application.getMainWindow();
                         //wnd.showNotification("Файл документа не найден! сформируйте его повторно ("+FileNameIn+")",Window.Notification.TYPE_ERROR_MESSAGE);
                         Notification.show("Файл документа не найден! сформируйте его повторно ("+FileNameIn+")",Notification.Type.ERROR_MESSAGE);
                         
                          table.removeAllItems();
                          return;
                        //}
                    }
                    
                    if (signer == null) {
//                       getWindow().showNotification("Подождите, система криптографии готовится к работе");
                       signer = new AmbSignPDF(keystore, keystorePassword);
//                       getWindow().showNotification("подготовка завершена");
                    }
                    try {
                        // --------- ПОДПИШЕМ -----------------------------------  
//                        Window wnd = getApplication().getMainWindow(); //getWindow();
//                        wnd.showNotification("Подождите, документ подписывается");
                        signer.signPdf(FileNameIn, FileNameOut);
//                        wnd.showNotification("Документ успешно подписан");
                        //---------- ВЫТАШИМ ДАЙДЖЕСТ ---------------------------
                        String digest= signer.getDigest();
                        //----- СКОРРЕКТИРУЕМ РЕЕСТР СЧЕТОВ ---------------------
                        EntityTransaction transact =em.getTransaction();
                        //em.getTransaction().begin();
                        transact.begin();
                        e.setSigned(new Short("1"));
                        e.setSignatura(digest);
                        Integer ln = digest.length();
                        em.persist(e);
                        //em.getTransaction().commit();
                        transact.commit();
                        //----- удалить не подписаннный файл ------------------
                      //  File Fb = new File(FileNameIn); Fb.
                      //  Boolean bb = new File(FileNameIn).delete();
                      //  if (bb) 
                      //        getWindow().showNotification("Исходный Файл удален");
                      //  else  
                      //        getWindow().showNotification("Исходный Файл не удален");
                        //-------------------------------------------------------
                    } catch (IOException ex) {
Notification.show(ex.toString(),Notification.Type.ERROR_MESSAGE);
table.removeAllItems();

                        Logger.getLogger(LispRaWindow.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (DocumentException ex) {
//Notification.show(ex.toString(),Notification.Type.ERROR_MESSAGE);
//table.removeAllItems();
//                        Logger.getLogger(LispRaWindow.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
Notification.show(ex.toString(),Notification.Type.ERROR_MESSAGE);
table.removeAllItems();
//                        getWindow().showNotification(ex.toString());
                        Logger.getLogger(LispRaWindow.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                }
             
               // if (e.getDownloaded() != null){dwn = new Embedded("",okImage);};
                String fileNameLink;
                if (e.getReady() != null){
                    read = new Embedded("",okImage); puth = PuthReady;
                     fileNameLink = fileNameSign;
                    //suff = ".sign.pdf";
                    stateLink = "Выставлено к оплате";
                } else {
                    stateLink = "Требуется скачать и подписать!";
                    fileNameLink = fileName;
                    //suff = "";
                }
//-----------------------------------------------------------------------------                
// Application app = getApplication();
// Application app1 = SprutVaadinApplication.getInstance();
// Application app2 = application;
// такая фигня При первом проходе работает app1 а app = null
// при втором проходе  работает app а app1 = null;
// app2  работает всегда  (переменная типа final)
//-----------------------------------------------------------------------------                
                String fullFileName = puth+"/"+fileNameLink;
                FileResource resource =
//                   new FileResource(new File(puth+"/"+fileName+suff),SprutVaadinApplication.getInstance());
                   new FileResource(new File(fullFileName));
//                   new FileResource(new File(puth+"/"+fileName+suff),application);

                Link link = new Link(fileNameLink, resource);
 link.setTargetName("_top");                
                link.setIcon(ICON);
                final Button linkButton = new Button("Скачать",new Button.ClickListener(){
                           @Override
                                public void buttonClick(Button.ClickEvent event) {
                            String fname = (String) event.getButton().getData();
  //------------------------ работает одинаково --------------------------------- 
  FileDownloadResource resource = new FileDownloadResource(new File(fname));
// тоже должно работать  BrowserWindowOpener opener = new BrowserWindowOpener(resource);
//  opener.extend(event.getButton());
  
  
  FileDownloader fd = new FileDownloader(resource);
  fd.extend(event.getButton());
//  Page.getCurrent().open(resource, "_self");
//????  getWindow().open(resource, "_self"); //"_blank", "_self", "_top", "_parent"
//  getUI().getPage().open(resource,"_self");
//  getUI().getPage().open(resource, fname, true);
                                }
                           });
                linkButton.setData(fullFileName);
                // Add a few items in the table. 
                table.addItem(new Object[] {read,
                           link,
                           linkButton,
                           // new Embedded("",okImage),
                           tipDocs.get(viddoc),
                           stateLink},
                           new Integer(i+1));
                i++;
            }
            if (i.intValue() == 0) {
               allReady = false;
            }
        }
        setImmediate(true);
    } 
    
    public Boolean isEmpty() {
       if (table.size() == 0) { 
          return true; 
       } else {
          return false; 
       }
    }
    
    public Boolean getAllReady(){
        return allReady;
    }

    public void  setOnlyNotReadyDocs(boolean onlyNotReadyDocs){
        this.onlyNotReadyDocs = onlyNotReadyDocs;
    }
    
}
