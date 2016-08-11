/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.domain.WebSchets;

/**
 *
 * @author Сергей
 */
public class Schets extends VerticalLayout  implements IComponentContainer {
    private InlineDateField datetime;
    private String linkRes = null;
    private Integer idplat;
    private Integer year;
    private Panel pan;
    private StatePlat statePlat;
    private Config config = null;
    
    //private final SprutVaadinApplication application = SprutVaadinApplication.getInstance();
    //private String puthReady = application.getPuthToReadyLS();
    private static final ThemeResource ICON = new ThemeResource(
            "img/16/action_save.gif");
    
    public Schets() {
    }
    
    public void init(){
        //linkRes =(String) VaadinSession.getCurrent().getAttribute("linkRes");
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        this.idplat = statePlat.getIdPlat();
        setMargin(true);
        //setHeight("100%");        
        setStyleName("DolgiWindow");
        //setSizeFull();
        BuildMain();
        setImmediate(true);
        
    }
    
    private void BuildMain(){
       Statistika stat = new Statistika();
       stat.init(idplat, Statistika.st_doc_sch );

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
            public void valueChange(ValueChangeEvent event) {
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
        
        pan = new Panel("Список опубликованных документов");
        pan.setWidth("100%");
//        pan.setHeight("100%");
        pan.setHeight("585px");
        addComponent(pan);
        refresh(year);
    }
    
    private void refresh(Integer year){
        Integer month = 1;
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        
        //pan.removeAllComponents();
        pan.setContent(null);

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
        Collection emps = em.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and (g.created is not null) and (g.deleted is null) and (g.ready = 1) and (g.idgroup = 0) order by g.mon").
                 setParameter("idplat", idplat).setParameter("yea", year).getResultList();
        Boolean schetExists = !emps.isEmpty();  
        if (schetExists) {
            Iterator it = emps.iterator();
            int i = 0; int count = 0;
            Integer viddoc;
            Short mon_old =0;
            while (it.hasNext()){
                WebSchets e = (WebSchets) it.next();
                viddoc = e.getViddoc();
                
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
                   new FileResource(new File(config.getPathToReadyLS()+"/"+e.getFileNameSign()));
//                   new FileResource(new File(config.getPathToReadyLS()+"/"+e.getFileNameSign()),application);
                
                Link link = new Link(e.getFileNameSign(), resource);
                link.setIcon(ICON);
                
                custom.addComponent(link,"username"+i);
                i++;
            }
            
            //pan.addComponent(custom);
            pan.setContent(custom);
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

    /*        
            final StreamResource stream = new StreamResource(
                    new StreamResource.StreamSource() {
                        public InputStream getStream() {
                            return new ByteArrayInputStream(("<html><body><h1>"
                                    + "проверка" + "</h1></body></html>").getBytes());
                        }
                    },"ppp" ,SprutVaadinApplication.getInstance());
            
           Embedded e = new Embedded("", stream);
           //InternalResource
                   
           //e.setAlternateText("Vaadin web site");
           e.setType(Embedded.TYPE_BROWSER);
           e.setWidth("100%");
           e.setHeight("380px");
           addComponent(e);
    */