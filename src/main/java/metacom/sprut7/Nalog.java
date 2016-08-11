/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

//import com.lowagie.text.DocumentException;
//import com.mycompany.maven_sprut.domain.WebSchets;
import com.vaadin.data.Property;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
//import com.vaadin.terminal.FileResource;
//import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.domain.WebSchets;

/**
 *
 * @author Сергей
 */
public class Nalog extends VerticalLayout implements IComponentContainer {
    private InlineDateField datetime;
    private String linkRes = null;
    private Integer idplat;
    private Integer year;
    private Panel pan;
//    private final SprutVaadinApplication application = SprutVaadinApplication.getInstance();
    private String puthReady; // = application.getPuthToReadyLS();
    private static final ThemeResource ICON = new ThemeResource(
            "img/16/action_save.gif");
    private String keystore;
    private String nameKeystore;
    private String keystorePassword;
    static Config config = null;
    private StatePlat statePlat;
    public void Nalog(){
    };
    public void init() {
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();

        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        this.idplat = statePlat.getIdPlat();
        
        puthReady = config.getPathToReadyLS();
        try {
            nameKeystore = (String) VaadinSession.getCurrent().getAttribute("nameKeystore");
        } catch (Exception ex) {
        //    Logger.getLogger(LispReadySchets.class.getName()).log(Level.SEVERE, null, ex);
            Notification.show("Не известно название контейнера сертификата энергоснабжающей организации",Notification.Type.ERROR_MESSAGE);
        }
       keystore = config.getPathKeystore()+"\\"+nameKeystore;
       keystorePassword = config.getLispKeystorePasswords().get(nameKeystore);       
        setMargin(true);
        setStyleName("DolgiWindow");
        BuildMain();
        setImmediate(true);

    }
    private void BuildMain(){ 
       Statistika stat = new Statistika();
       stat.init(idplat, Statistika.st_doc_nn );
        
        datetime = new InlineDateField("Укажите год, который вас интересует.");
        // текущая дата
        Date dt = new java.util.Date();
        final java.util.Calendar calendar = GregorianCalendar.getInstance();
	calendar.setTime(dt);
        year = calendar.get(java.util.Calendar.YEAR);
        datetime.setValue(dt);
        datetime.setResolution(InlineDateField.RESOLUTION_YEAR);
        datetime.setImmediate(true);
        datetime.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Object value = event.getProperty().getValue();
                if (value == null || !(value instanceof Date)) {
                    Notification.show("Введена неверная дата");
                } else {
                   calendar.setTime((Date)value);
                   year = calendar.get(java.util.Calendar.YEAR);
                   refresh(year);
               }
            }
        });
        addComponent(datetime);
        
        pan = new Panel("Список опубликованных налоговых накладных");
        pan.setWidth("100%");
//        pan.setHeight("100%");
        pan.setHeight("585px");
        addComponent(pan);
        refresh(year);
        
    }   
    
    private void refresh(Integer year){
        Integer month = 1;
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        
        pan.setContent(null); //removeAllComponents();

        StringBuffer output = new StringBuffer(110);
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        output.append(" <td>Файл документа</td>");
        output.append(" <td width=\"200\">Вид документа</td>");
        output.append(" <td width=\"80\">Дата создания</td>");
        output.append("</tr>");
        
        EntityManager em = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
        Collection emps = em.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and (g.created is not null) and (g.deleted is null) and (g.idgroup = 1) order by g.mon").
                 setParameter("idplat", idplat).setParameter("yea", year).getResultList();
        Boolean schetExists = !emps.isEmpty();  
        if (schetExists) {
            Iterator it = emps.iterator();
            int i = 0; int count = 0;
            Integer viddoc;
            Short mon_old =0;
            AmbSignPDF signer = null;
            while (it.hasNext()){
                WebSchets e = (WebSchets) it.next();
                viddoc = e.getViddoc();
                //  проверим необходимость подписи
                Boolean makeSign = false;
                if (e.getSigned()==null){makeSign = true;} else {
                   if (e.getSigned().intValue() == 0)  {makeSign = true;} };
                if (makeSign){
                    
                    
                    // ------------- подпишем документ -------------------------
                    String fileName = e.getFileName();
                    String fileNameSign = e.getFileNameSign();
                    
                    String FileNameIn = config.getPathToNoSignLS()+"\\"+fileName;
                    String FileNameOut = config.getPathToReadyLS()+"\\"+fileNameSign;
                    // проверим наличие файла
                    File tmp = new File(FileNameIn);
                    if (!tmp.exists()){
                         // сбой файла нет  
//                         Window wnd = application.getMainWindow();
                         Notification.show("Файл документа не найден! сформируйте его повторно ("+FileNameIn+")",Notification.Type.ERROR_MESSAGE);
                         // table.removeAllItems();
                         return;
                        //}
                    }
                    
                    if (signer == null) {
                       signer = new AmbSignPDF(keystore, keystorePassword);
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
                        e.setReady(new Short("1"));
                        Integer ln = digest.length();
                        em.persist(e);
                        //em.getTransaction().commit();
                        transact.commit();
                    } catch (IOException ex) {
Notification.show(ex.toString(),Notification.Type.ERROR_MESSAGE);
//table.removeAllItems();
                        Logger.getLogger(LispRaWindow.class.getName()).log(Level.SEVERE, null, ex);
//                    } catch (DocumentException ex) {
//Notification.show(ex.toString(),Notification.Type.ERROR_MESSAGE);
//                        Logger.getLogger(LispRaWindow.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
Notification.show(ex.toString(),Notification.Type.ERROR_MESSAGE);
//table.removeAllItems();
//                        getWindow().showNotification(ex.toString());
                        Logger.getLogger(LispRaWindow.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                    
                    
                    
                    
                    
                }
                
                if (mon_old.intValue() != e.getMon().intValue()) {
                   output.append("<tr align=\"center\">");
                   output.append(" <td colspan=\"3\" bgcolor=\"#ADD8E6\">"+getMonths(e.getMon().intValue())+" месяц</td>");
                   output.append("</tr>");
                   mon_old = e.getMon();
                }
                if (i == 1) {
                   output.append("<tr align=\"left\"  bgcolor=\"#FFFAFA\">");
                } else {
                   output.append("<tr align=\"left\">");
                }   
                output.append(" <td>");
                
                output.append("<div location=\"username"+count+"\"></div>");
/*                
//!!!!! Это работает. Доступ к каталогу root output.append("<a href=\"../1.htm\">Относительная ссылка</a>");
Опубликованные таким образом фаллы отсчитывабтся от каталога root апача 
                   output.append("<a href=\"");
                   output.append(puthReady);
                   output.append("\\");
                   output.append(e.getFileNameSign());
                   output.append("\">");
                   output.append(e.getFileNameSign());
                   output.append("</a>");
*/
                output.append("</td>");
                output.append(" <td>"+LispReadySchets.tipDocs.get(viddoc)+"</td>");
                output.append(" <td align=\"center\">"+df.format(e.getCreated())+"</td>");
                output.append("</tr>");
                if (i == 1){i = 0;} 
                    else {i = 1;};
                count++;    
            }    
        }   
        em.close();
        output.append("</tbody></table>");        
        try {
            CustomLayout custom = new CustomLayout(new ByteArrayInputStream(output.toString().getBytes("UTF-8")));
            
            Iterator it = emps.iterator();
            int i = 0;
            while (it.hasNext()){
                WebSchets e = (WebSchets) it.next();
                FileResource resource =
                   new FileResource(new File(puthReady+"/"+e.getFileNameSign()));
//                   new FileResource(new File(puthReady+"/"+e.getFileNameSign()),application);
                
                Link link = new Link(e.getFileNameSign(), resource);
                link.setIcon(ICON);
                
                custom.addComponent(link,"username"+i);
                i++;
            }
            
            pan.setContent(custom); //addComponent(custom);
        } catch (IOException ex) {
            Logger.getLogger(Schets.class.getName()).log(Level.SEVERE, null, ex);
        }
        pan.setImmediate(true);
    }


public String getMonths(Integer mes ) {
    //String[] mesString = {"января", "февраля", "марта", "апреля", "мая", "июня",
    //            "июля", "августа", "сентября", "октября", "ноября", "декабря"};
    String[] mesString = {"январь", "февраль", "март", "апрель", "май", "июнь",
                "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
    return mesString[mes-1];
}
    
}
