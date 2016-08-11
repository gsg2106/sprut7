package metacom.sprut7.beans;


import com.vaadin.data.Property;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import metacom.sprut7.domain.GsgWebArea;
import com.vaadin.data.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 01.08.16
 * Time: 15:56
 * Правая панель редактора ОРИК
 */
//@Component
@Scope("prototype")

public class OrikRightImpl extends VerticalLayout implements IUpdatebleArea{
    private TabSheet tabSheet;
    private InlineDateField datetime;
    private java.util.Calendar calendar = GregorianCalendar.getInstance();
    private int year;
    private GsgWebArea item;
    //private int month;

    @Autowired
    OrikKvitImpl orikKvit;

    @Autowired
    OrikKvitPowerImpl orikKvitPower;

    @Autowired
    TabSheetMainLayout tabSheetMainLayout;

    @Autowired
    TabSheetMainLayout tabSheetMainLayoutPower;

    @Override
    public void init(String linkRes, GsgWebArea item) {
        this.item = item;
        setStyleName("Layoutra");
        VerticalLayout topLayout = new VerticalLayout();
        topLayout.setWidth("100%");
        topLayout.setMargin(true);
        setMargin(false);
        //ownerBox.setSizeFull();
        Date dt = new java.util.Date();
        calendar.setTime(dt);
        year = calendar.get(java.util.Calendar.YEAR);
        //month = calendar.get(java.util.Calendar.MONTH)+1;
        datetime = getDateTime(dt);
        topLayout.addComponent(datetime);
        //addComponent(datetime);
        Resource icon1 = null;
        Resource icon2 = null;
        /** сформируем панель закладок */
        tabSheet = new TabSheet();
        tabSheet.setHeight("100%"); //t.setHeight("200px");
        tabSheet.setWidth("100%");  //t.setWidth("400px");
        orikKvit.init(linkRes, item, year);
        orikKvitPower.init(linkRes, item, year);
        tabSheetMainLayout.init(orikKvit);
        tabSheetMainLayoutPower.init(orikKvitPower);
        tabSheet.addTab(tabSheetMainLayout,"Заявки на лимиты потребления.", icon1).setStyleName("vl1");
//        tabSheet.addTab(orikKvit, "Заявки на лимиты потребления.", icon1).setStyleName("vl1");
        tabSheet.addTab(tabSheetMainLayoutPower, "Заявки на лимиты мощности.", icon1).setStyleName("vl1");
        if (item.getYesLimNagr().charValue() == '1' && item.getDif().charValue() == '1'){
            tabSheet.getTab(1).setVisible(true);
//            tabSheet.addTab(orikKvitPower, "Заявки на лимиты мощности.", icon1).setStyleName("vl1");
        } else {
            tabSheet.getTab(1).setVisible(false);
        }
        tabSheet.setStyleName(Reindeer.TABSHEET_MINIMAL);
        /*--------------------------------------------------- */
        addComponent(topLayout);
        addComponent(tabSheet);
    }

    /** сформируем выбор года - месяца */
    private InlineDateField getDateTime(Date dt) {
        InlineDateField datetime = new InlineDateField("Укажите за какой год вы желаете посмотреть документы.");
        datetime.setValue(dt);
        datetime.setResolution(Resolution.YEAR);  //InlineDateField.RESOLUTION_MONTH
        Locale ru = new Locale("ru");
        datetime.setLocale(ru);
        datetime.setImmediate(true);
        datetime.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
                Object value = valueChangeEvent.getProperty().getValue();
                if (value == null || !(value instanceof Date)) {
                    Notification.show("Введена неверная дата");
                } else {
                    calendar.setTime((Date) value);
                    year = calendar.get(java.util.Calendar.YEAR);
                    update(item);
                    //month = calendar.get(java.util.Calendar.MONTH) + 1;
                    //refresh(year, month, idTipM, sostav);
                }
            }
        });
        return datetime;
    }

    @Override
    public void update(GsgWebArea item) {
        this.item = item;
        orikKvit.update(item, year);
        if (item.getYesLimNagr().charValue() == '1' && item.getDif().charValue() == '1'){
            orikKvitPower.update(item, year);
        }
        if (item.getYesLimNagr().charValue() == '1' && item.getDif().charValue() == '1'){
            tabSheet.getTab(1).setVisible(true);
        } else {
            tabSheet.getTab(1).setVisible(false);
        }

    }
}
