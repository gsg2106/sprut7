/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.J2EEConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;

/**
 *
 * @author Сергей
 */
@SuppressWarnings("serial")
public class HistoryNalNak  extends VerticalLayout implements IComponentContainer{
    private String linkRes = null;
    private Integer idplat;
    private Panel pan;
    private String linkPool;
    private StatePlat statePlat;
    private Config config = null;
    //private SQLContainer container;
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    private InlineDateField datetime1;
    private InlineDateField datetime2;
    private Integer year1;
    private Integer year2;
    private String puthReady; // = application.getPuthToReadyLS();
    private String keystore;
    private String nameKeystore;
    private String keystorePassword;

HistoryNalNak(){
}

    @Override
    public void init() {
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        this.idplat = statePlat.getIdPlat();
        linkPool = statePlat.getLinkPool();

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
       stat.init(idplat, Statistika.ST_HIST_NN );
       
       datetime1 = new InlineDateField();
       datetime2 = new InlineDateField();
        // текущая дата
        Date dt = new java.util.Date();
        final java.util.Calendar calendar = GregorianCalendar.getInstance();
	calendar.setTime(dt);
        year2 = calendar.get(java.util.Calendar.YEAR);
        datetime2.setValue(dt);
        datetime2.setResolution(InlineDateField.RESOLUTION_YEAR);
        datetime2.setImmediate(true);
        datetime2.addValueChangeListener(new Property.ValueChangeListener(){

           @Override
           public void valueChange(Property.ValueChangeEvent event) {
                Object value = event.getProperty().getValue();
                if (value == null || !(value instanceof Date)) {
                    Notification.show("Введена неверная дата");
                } else {
                   calendar.setTime((Date)value);
                   year2 = calendar.get(java.util.Calendar.YEAR);
                   refresh();
               }
            }
        });
        calendar.add(Calendar.YEAR, -3);
        year1 = calendar.get(java.util.Calendar.YEAR);
        datetime1.setResolution(InlineDateField.RESOLUTION_YEAR);
        datetime1.setValue(calendar.getTime());
        datetime1.setImmediate(true);
        datetime1.addValueChangeListener(new Property.ValueChangeListener(){

           @Override
           public void valueChange(Property.ValueChangeEvent event) {
                Object value = event.getProperty().getValue();
                if (value == null || !(value instanceof Date)) {
                    Notification.show("Введена неверная дата");
                } else {
                   calendar.setTime((Date)value);
                   year1 = calendar.get(java.util.Calendar.YEAR);
                   refresh();
               }
           }
        });
        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(new Label("<font size=\"4\" ><b>Период просмотра: </b></font>",ContentMode.HTML));
        hl.addComponent(datetime1);
        hl.addComponent(new Label("<font size=\"4\"><b>  -  </b></font>",ContentMode.HTML));
        hl.addComponent(datetime2);

        
        pan = new Panel("История налоговых накладных");
        pan.setWidth("100%");
//        pan.setHeight("100%");
        pan.setHeight("585px");
        addComponent(hl);
        addComponent(pan);
        refresh();
        
    }
    public void refresh(){
        pan.setContent(null);

        StringBuffer output = new StringBuffer(110);
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        output.append(" <td>№ п.п.</td>");
        output.append(" <td>№ НН</td>");
        output.append(" <td width=\"80\">Дата выписки</td>");
        output.append(" <td >Выдана</td>");
        output.append(" <td >Всего, грн</td>");
        output.append(" <td >НДС, грн</td>");
        output.append(" <td >Всего с НДС, грн</td>");
        output.append(" <td >Корректируемая накладная</td>");
        output.append(" <td >Накладная</td>");
        output.append("</tr>");
        
       J2EEConnectionPool connectionPool = new J2EEConnectionPool(linkPool);
       FreeformQuery dlg =  new FreeformQuery("select * from GSG_WEB_HIST_NN", connectionPool,"nompp");
       dlg.setDelegate(new ViewDelegate("GSG_WEB_HIST_NN"));
       SQLContainer container = null;
        try {
            container = new SQLContainer(dlg);
        } catch (SQLException ex) {
            Logger.getLogger(LispTuWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        container.addContainerFilter(new Compare.Equal("ID_PLAT",idplat)); // чтобы не было выборки
        container.addContainerFilter(new Compare.GreaterOrEqual("YR",year1)); // чтобы не было выборки
        container.addContainerFilter(new Compare.LessOrEqual("YR",year2)); // чтобы не было выборки
        int ii=0;
        Integer oldYr=0;
        Integer yr;
        for (int i = 0; i < container.size(); i++) {
            Object id = container.getIdByIndex(i);
            Item item = container.getItem(id);
            yr = (Integer) item.getItemProperty("YR").getValue();
            if (oldYr.intValue() != yr.intValue() ) {
                   output.append("<tr align=\"center\">");
                   output.append(" <td colspan=\"9\" bgcolor=\"#ADD8E6\">"+yr.toString()+" год</td>");
                   output.append("</tr>");
                   oldYr = yr;
            }
            if (ii == 1) {
                   output.append("<tr align=\"left\"  bgcolor=\"#FFFAFA\">");
                } else {
                   output.append("<tr align=\"left\">");
            } 
            
            output.append(" <td align=\"left\">"+item.getItemProperty("NOMPP").getValue().toString()+"</td>");
            output.append(" <td align=\"center\">"+item.getItemProperty("NOMNAL").getValue().toString()+"</td>");
            if (item.getItemProperty("DATVIP").getValue() == null){output.append(" <td></td>");} else {
                  output.append(" <td align=\"center\">"+df.format(item.getItemProperty("DATVIP").getValue())  +"</td>");
            }    
            output.append(" <td align=\"left\">"+item.getItemProperty("VIDANA").getValue().toString()+"</td>");
            output.append(" <td align=\"right\">"+item.getItemProperty("SUM_BAZ").getValue().toString()+"</td>");
            output.append(" <td align=\"right\">"+item.getItemProperty("SUM_NDS").getValue().toString()+"</td>");
            output.append(" <td align=\"right\">"+item.getItemProperty("SUM_ALL").getValue().toString()+"</td>");
            output.append(" <td align=\"left\">"+item.getItemProperty("NAL_CORR").getValue().toString()+"</td>");
            output.append(" <td align=\"left\">"+item.getItemProperty("PRIM").getValue().toString()+"</td>");
            ii = ii==0 ? 1:0;
        }    
        
        output.append("</tbody></table>");        
        
        try {
            CustomLayout custom = new CustomLayout(new ByteArrayInputStream(output.toString().getBytes("UTF-8")));
            pan.setContent(custom);
        } catch (IOException ex) {
            Logger.getLogger(HistoryNalNak.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        pan.setImmediate(true);
        
    }
}
