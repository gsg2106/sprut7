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
import com.vaadin.ui.*;
import com.vaadin.ui.ComponentContainer;

import com.vaadin.ui.VerticalLayout;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;

/**
 *
 * @author Сергей
 */
public class AllPu  extends VerticalLayout implements IComponentContainer {
    private Integer idplat;
    private String linkRes;
    private StatePlat statePlat;
    private String linkPool;
    public AllPu(){}
    private Panel pan;
    private SQLContainer puContainer;
    private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
    
    public void init(){
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        linkPool = statePlat.getLinkPool();
        this.idplat=statePlat.getIdPlat(); 

        setMargin(true);
        setHeight("100%");        
        setStyleName("DolgiWindow");
        setSizeFull();
        
        pan = new Panel("Наличие приборов учета на текущий момент");
        pan.setWidth("100%");
        pan.setHeight("585px");
        
        BuildMain();
        addComponent(pan);
        setImmediate(true);
    }
    private void BuildMain(){ 
       J2EEConnectionPool connectionPool = new J2EEConnectionPool(linkPool);
       FreeformQuery dlg =  new FreeformQuery("select * from GSG_WEB_VIEW_ALL_PU", connectionPool,"id");
       dlg.setDelegate(new ViewDelegate("GSG_WEB_VIEW_ALL_PU"));
        try {
            puContainer = new SQLContainer(dlg);
        } catch (SQLException ex) {
            Logger.getLogger(LispTuWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        puContainer.addContainerFilter(new Compare.Equal("ID_PLAT",idplat)); // чтобы не было выборки
        
        pan.setContent(null);
        StringBuffer output = new StringBuffer(110);
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
//        output.append(" <td rowspan=\"2\">№ п.п</td>");
        output.append(" <td>№ п.п</td>");
        output.append(" <td>№ прибора учета</td>");
        output.append(" <td>Наименование точки учета</td>");
        output.append(" <td>Тип прибора учета</td>");
        output.append(" <td>Расчетный коэф.</td>");
        output.append(" <td>Дата гос. поверки.</td>");
        output.append(" <td>Дата истечения гос. поверки.</td>");
        output.append(" <td>Дата установки</td>");
        output.append(" <td>Интервал госповерки</td>");
        output.append(" <td>Значность</td>");
        output.append("</tr>");
        String ldate1;
        String lldate1;
        String ldate2;
        String lldate2;
        for (int i = 0; i < puContainer.size(); i++) {
            Object id = puContainer.getIdByIndex(i);
            Item item = puContainer.getItem(id);
   //         if (item.getItemProperty("ID") != null){;}
            
            if (i == 1) {
                   output.append("<tr align=\"left\"  bgcolor=\"#FFFAFA\">");
            } else {
                   output.append("<tr align=\"left\">");
            } 
            output.append(" <td align=\"left\">"+item.getItemProperty("ID").getValue().toString()+"</td>");
            output.append(" <td align=\"left\">"+item.getItemProperty("ZAVNO").getValue().toString()+"</td>");
            output.append(" <td align=\"left\">"+item.getItemProperty("NAMETU").getValue().toString()+"</td>");
            output.append(" <td align=\"left\">"+item.getItemProperty("N_TYPS").getValue().toString()+"</td>");
            output.append(" <td align=\"left\">"+item.getItemProperty("KTR").getValue().toString()+"</td>");
            ldate1 = (String) item.getItemProperty("DATE_GOS_STR").getValue();
            lldate1 = ldate1 == null ? "" : ldate1;
            output.append(" <td align=\"left\">"+lldate1+"</td>");
            ldate2 = (String) item.getItemProperty("DATE_END_GOS_STR").getValue();
            lldate2 = ldate2 == null ? "" : ldate2;
            output.append(" <td align=\"left\">"+lldate2 +"</td>");
            if (item.getItemProperty("DATE_UST").getValue() == null){output.append(" <td></td>");} else {
                  output.append(" <td align=\"center\">"+df.format(item.getItemProperty("DATE_UST").getValue())  +"</td>");
            }    
//            output.append(" <td align=\"left\">"+item.getItemProperty("DATE_UST").getValue().toString()+"</td>");
            output.append(" <td align=\"left\">"+item.getItemProperty("PERIOD_TEST_STR").getValue().toString()+"</td>");
            output.append(" <td align=\"left\">"+item.getItemProperty("ZNACHN").getValue().toString()+"</td>");
//            GenDebet(output, e);
//            GenKredit(output, e);
            output.append("</tr>");
            
        }
        puContainer.removeAllItems();
        
        output.append("</tbody></table>");        
        try {
            CustomLayout custom = new CustomLayout(new ByteArrayInputStream(output.toString().getBytes("UTF-8")));
            pan.setContent(custom);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        pan.setImmediate(true);
        
    }
    
}
