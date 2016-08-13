package metacom.sprut7.beans;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.Table;
import metacom.sprut7.dao.Orik;
import metacom.sprut7.domain.GsgWebArea;
import metacom.sprut7.domain.SzajNagr;
import metacom.sprut7.domain.SzajPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 02.08.16
 * Time: 23:30
 * To change this template use File | Settings | File Templates.
 */
@Scope("prototype")
public class OrikKvitPowerImpl  extends TableEditParent{
        @Autowired
        private Orik dao;

        public OrikKvitPowerImpl(){
            setCaption( "Потребление");
        }
        @Override
        protected Container extractData(String linkRes, Integer idPotr, Integer year) {
            return dao.getLispNagr(linkRes, idPotr, year);
        }

        @Override
        protected void setColunns(Table tableArea, GsgWebArea item, Integer year) {
            Character yesLimNagr = item.getYesLimNagr();
            Character neprZikl = item.getNeprZikl();
            String[] columns;
            String[] headers;
            columns = new String[]{"nameMonth","zajValU","zajValV","ok"};
            headers = new String[]{"Месяц","Утренний максимум кВт","Вечерний максимум кВт","Согласовано"};

            Object[] s = tableArea.getVisibleColumns();
            HashSet<Object> hs =  new HashSet<Object>(Arrays.asList(s));
            if (!hs.contains("ok")){
                tableArea.addGeneratedColumn("ok",new Table.ColumnGenerator() {
                   @Override
                    public Object generateCell(Table components, Object o, Object o2) {
                        //SzajNagr itemBin = (SzajNagr) o;
                        Object read;
                        if (((SzajNagr) o).getDateProv() != null){
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
    protected void saveData(String linkRes, Item item) {
        dao.saveNagr(linkRes, item);
    }
    @Override
    protected boolean validateItem(FieldGroup fieldGroup) {
        return true;
    }
    @Override
    protected void addValidator(Field field, String idField) {}
    @Override
    protected boolean validateField(FieldGroup fieldGroup, Property.ValueChangeEvent valueChangeEvent, String idField) {
        return true;
    }
    @Override
    protected void prepare(Integer idPotr, Integer year) {
        dao.prepareLispPower(linkRes, idPotr, year);
    }

    @Override
    protected void clear(Integer idPotr, Integer year) {
        dao.clearLispPower(linkRes, idPotr, year);
    }

    @Override
    protected String sinchrinize(Integer idPlat, Integer year) {
        return dao.sinchrinizeLispPower(linkRes, idPlat, year);
    }

}
