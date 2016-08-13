package metacom.sprut7.beans;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import metacom.sprut7.dao.Orik;
import metacom.sprut7.domain.GsgWebArea;
import metacom.sprut7.domain.SzajPlan;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;

import java.util.*;
import java.util.function.Consumer;


/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 01.08.16
 * Time: 16:50
 * To change this template use File | Settings | File Templates.
 */
//@UIScope
//@org.springframework.stereotype.Component
@Scope("prototype")
public class OrikKvitImpl extends TableEditParent{
    @Autowired
    private Orik dao;

    public OrikKvitImpl(){
        setCaption( "Потребление");
    }
    @Override
    protected Container extractData(String linkRes, Integer idPotr, Integer year) {
        return dao.getLispPotr(linkRes, idPotr, year);
    }

    @Override
    protected void setColunns(Table tableArea, GsgWebArea item, Integer year) {
        Character yesLimNagr = item.getYesLimNagr();
        Character neprZikl = item.getNeprZikl();
        String[] columns;
        String[] headers;
        if (item.getYesLimNagr().charValue() == '0' && item.getNeprZikl().charValue() == '0') {
            columns = new String[]{"nameMonth","zajVal","chas","dayOffWeek","ok"};
            headers = new String[]{"Месяц","Величина в т.квт/ч","рабочих часов в день","Рабочих дней в неделю","Согласовано"};
        } else if (item.getYesLimNagr().charValue() == '1' && item.getNeprZikl().charValue() == '0'){
            columns = new String[]{"nameMonth","zajVal","holiday","potrHoli","chas","dayOffWeek","ok"};
            headers = new String[]{"Месяц","Величина в т.квт/ч","Нерабочих дней","Потребление в нерабочий день","рабочих часов в день","Рабочих дней в неделю","Согласовано"};
        } else if (item.getYesLimNagr().charValue() == '1' && item.getNeprZikl().charValue() == '1'){
            columns = new String[]{"nameMonth","zajVal","ok"};
            headers = new String[]{"Месяц","Величина в т.квт/ч","Согласовано"};
        } else {
//            columns = null;
//            headers = null;
            columns = new String[]{"nameMonth","zajVal","chas","dayOffWeek","ok"};
            headers = new String[]{"Месяц","Величина в т.квт/ч","рабочих часов в день","Рабочих дней в неделю","Согласовано"};
        }

//        ArrayList<String> arr = new ArrayList<String>(Arrays.asList(ss));
//        int i = arr.indexOf("ok");


        //if (tableArea.getColumnHeaders()) == null){

        Object[] s = tableArea.getVisibleColumns();
        HashSet<Object> hs =  new HashSet<Object>(Arrays.asList(s));
        if (!hs.contains("ok")){
            tableArea.addGeneratedColumn("ok",new Table.ColumnGenerator() {
                @Override
                public Object generateCell(Table components, Object o, Object o2) {
                    //SzajPlan itemBin = (SzajPlan) o;
                    Object read;
                    if (((SzajPlan) o).getWebVerify() == '1'){
                            read = new Embedded("",okImage);
                    } else read =  "";
                    return read;  //To change body of implemented methods use File | Settings | File Templates.
                }
            });
        }

        tableArea.setVisibleColumns(columns);
        tableArea.setColumnHeaders(headers);
    }

    @Override
    protected void saveData(String linkRes,Item item) {
        dao.savePlan(linkRes, item);
    }

    @Override
    protected boolean validateItem(FieldGroup fieldGroup) {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void addValidator(Field field, String idField) {
        if (!field.isReadOnly()){
            if (idField.equals("chas")){
                field.addValidator(new IntegerRangeValidator("Значение выходит за пределы допустимого интервала",1,24));
            }
            if (idField.equals("dayOffWeek")){
                field.addValidator(new IntegerRangeValidator("Значение выходит за пределы допустимого интервала",1,7));
            }
            if (idField.equals("holiday")){
                field.addValidator(new IntegerRangeValidator("Значение выходит за пределы допустимого интервала",0,20));
            }
        }
    }

    @Override
    protected boolean validateField(FieldGroup fieldGroup, Property.ValueChangeEvent valueChangeEvent, String idField) {
        return true;
    }

    @Override
    protected void prepare(Integer idPotr, Integer year) {
        dao.prepareLispPotr(linkRes, idPotr, year);
    }

    @Override
    protected void clear(Integer idPotr, Integer year) {
        dao.clearLispPotr(linkRes, idPotr, year);
    }

    @Override
    protected String sinchrinize(Integer idPlat, Integer year) {
        return dao.sinchrinizeLispPotr(linkRes, idPlat, year);
    }
}