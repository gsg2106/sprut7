package metacom.sprut7.firebird;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.J2EEConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import metacom.sprut7.LispTuWindow;
import metacom.sprut7.Statistika;
import metacom.sprut7.ViewDelegate;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 14.12.14
 * Time: 17:56
 * To change this template use File | Settings | File Templates.
 */
public class HistTarif extends VerticalLayout implements IComponentContainer {
    private String linkRes = null;
    private Integer idplat;
    private Panel pan;
    private String linkPool;
    private StatePlat statePlat;
    private Config config = null;
    private Integer year;
    private InlineDateField datetime;
    @Override
    public void init() {
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        this.idplat = statePlat.getIdPlat();
        linkPool = statePlat.getLinkPool();
        setMargin(true);
        //setHeight("100%");
        setStyleName("DolgiWindow");
        //setSizeFull();
        BuildMain();
        setImmediate(true);
    }
    private  void BuildMain(){
        Statistika stat = new Statistika();
        stat.init(idplat, Statistika.ST_HIST_NN );
        // текущая дата
        Date dt = new java.util.Date();
        final java.util.Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(dt);
        year = calendar.get(java.util.Calendar.YEAR);
        datetime = new InlineDateField();
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
                    calendar.setTime((Date) value);
                    year = calendar.get(java.util.Calendar.YEAR);
                    refresh();
                }
            }
        });

        HorizontalLayout hl = new HorizontalLayout();
        hl.addComponent(new Label("<font size=\"4\" ><b>Период просмотра истории тарифов: </b></font>", ContentMode.HTML));
        hl.addComponent(datetime);
        pan = new Panel("История тарифов");
        pan.setWidth("300px");
//        pan.setWidth("100%");
//        pan.setHeight("100%");
//        pan.setHeight("350px");
        pan.setHeightUndefined();
        addComponent(hl);
        addComponent(pan);
        setComponentAlignment(pan, Alignment.TOP_CENTER);
        refresh();

    }
    public void refresh(){
        pan.setContent(null);

        StringBuffer output = new StringBuffer(110);
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        output.append(" <td>Период</td>");
        output.append(" <td>1-й класс</td>");
        output.append(" <td>2-й класс</td>");
        output.append("</tr>");


        J2EEConnectionPool connectionPool = new J2EEConnectionPool(linkPool);
//        FreeformQuery dlg =  new FreeformQuery("select * from KI_GET_TARPROMVAL_HIST("+year+")", connectionPool,"ID");
        FreeformQuery dlg =  new FreeformQuery("select * from KI_GET_TARPROMVAL_HIST("+year+")", connectionPool,"DATETAR");

        //dlg.setDelegate(new ViewDelegate("GSG_WEB_HIST_NN"));
        SQLContainer container = null;
        try {
            container = new SQLContainer(dlg);
        } catch (SQLException ex) {
            Logger.getLogger(LispTuWindow.class.getName()).log(Level.SEVERE, null, ex);
        }

        int ii=0;
        for (int i=0; i  < container.size(); i++){
            Object id = container.getIdByIndex(i);
            Item item = container.getItem(id);
            if (ii == 1) {
                output.append("<tr align=\"left\"  bgcolor=\"#FFFAFA\">");
            } else {
                output.append("<tr align=\"left\">");
            }
            output.append(" <td align=\"left\">"+item.getItemProperty("MONFULL").getValue().toString()+"</td>");
            output.append(" <td align=\"left\">"+item.getItemProperty("TAR_1CL").getValue().toString()+"</td>");
            output.append(" <td align=\"left\">"+item.getItemProperty("TAR_2CL").getValue().toString()+"</td>");
            output.append("</tr>");
            ii = ii==0 ? 1: 0;

        }

        output.append("</tbody></table>");

        try {
            CustomLayout custom = new CustomLayout(new ByteArrayInputStream(output.toString().getBytes("UTF-8")));
            pan.setContent(custom);
        } catch (IOException ex) {
            Logger.getLogger(HistTarif.class.getName()).log(Level.SEVERE, null, ex);
        }

        pan.setImmediate(true);

    }
}
