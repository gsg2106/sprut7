package metacom.sprut7.beans;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import metacom.sprut7.MyLoginWindow;
import metacom.sprut7.dao.Orik;
import metacom.sprut7.domain.GsgWebArea;
import metacom.sprut7.domain.SzajPlan;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 02.08.16
 * Time: 22:01
 * To change this template use File | Settings | File Templates.
 */
public abstract class TableEditParent extends VerticalLayout implements IUpdatableAreaSeazon{
    String linkRes;
    Container biPotr;
    Table tableArea;
    String caption;
    Integer year;
    GsgWebArea itemBin;
    ThemeResource okImage = new ThemeResource("img/16/Ok.gif");

    @Autowired
    private Orik dao;

    @Override
    public void init(final String linkRes, GsgWebArea itemBin, Integer yearL) {
        this.linkRes = linkRes;
        this.year = yearL;
        this.itemBin = itemBin;
        setHeight("100%");
        setStyleName("Layoutra");
        setMargin(true);

        /** формируем и заполняем бинарный контейнер для списка заявок */
        biPotr = extractData(linkRes, itemBin.getIdPotr(), year);
        tableArea = getTablePotr();
        tableArea.addItemClickListener(new ItemClickEvent.ItemClickListener() {
            @Override
            public void itemClick(ItemClickEvent itemClickEvent) {
                if (itemClickEvent.isDoubleClick()){
                    if (isReadOnly()){
                        Notification.show("Редактирование запрещено", Notification.Type.WARNING_MESSAGE);
                    } else {
                        edit(itemClickEvent.getItem(), year);
                    }
                }
            }
        });
//        tableArea.setTableFieldFactory(new TableFieldFactory() {
//            @Override
//            public Field<?> createField(Container container, Object itemId, Object propertyId, Component uiContext) {
//                if (!propertyId.equals("nameMonth")) {
//                    return DefaultFieldFactory.get().createField(container, itemId, propertyId, uiContext);
//                }
//                return null;
//            }
//        });
//        tableArea.setEditable(true);
        setColunns(tableArea, itemBin, year);
        addComponent(tableArea);
    }

    /** формируем таблицу потребления */
    private Table getTablePotr(){
        // TODO: убрать код объединив его в единый вызов
        Table tableArea = new TreeTable(caption,biPotr);
        tableArea.setSizeFull();
        tableArea.setImmediate(true);
        tableArea.setHeight("100%");
        tableArea.setImmediate(true);
        tableArea.setSelectable(true);
        return tableArea;
    }

    protected abstract Container extractData(String linkRes, Integer idPotr, Integer year);
    protected abstract void setColunns(Table tableArea,GsgWebArea item, Integer year);
    protected abstract void saveData(String linkRes, Item item);
    protected abstract boolean validateItem(FieldGroup fieldGroup);
    protected abstract void addValidator(Field field, String idField);
    protected abstract boolean validateField(FieldGroup fieldGroup, Property.ValueChangeEvent valueChangeEvent, String idField);
    protected abstract void prepare(Integer idPotr, Integer year);
    protected abstract void clear(Integer idPotr, Integer year);
    protected abstract String sinchrinize(Integer idPlat, Integer year);

    public void setCaption(String caption){
        this.caption = caption;
    }

    @Override
    public void update(GsgWebArea itemBin, Integer yearL) {
        this.itemBin = itemBin;
        this.year = yearL;
        biPotr.removeAllItems();
        biPotr = extractData(linkRes, itemBin.getIdPotr(), year);
        tableArea.setContainerDataSource(biPotr);
        setColunns(tableArea, itemBin, year);
    }

    public void edit(){
        Item item = tableArea.getItem(tableArea.getValue());
        if (item != null){
            edit(item, year);
        } else {
            Notification.show("Не выбрана строка таблицы.", Notification.Type.WARNING_MESSAGE);
        }
    }

    public void prepareTable(){
        Integer idPotr = itemBin.getIdPotr();
        prepare(idPotr, year);
        update(itemBin,year);
    }
    public void clearTable(){
        Integer idPotr = itemBin.getIdPotr();
        clear(idPotr, year);
        update(itemBin,year);
    }

    public void sinchrinizeTable(){
        //Integer idPotr = itemBin.getIdPotr();
        Integer idPlat = itemBin.getIdPlat();
        String err = sinchrinize(idPlat, year);
        if (!err.trim().isEmpty()){
            Notification.show(err, Notification.Type.ERROR_MESSAGE);
        }
        update(itemBin,year);
    }

    public void edit(Item item, Integer year){
        // Item d = itemClickEvent.getItem(); Object o =itemClickEvent.getItemId();
        TableEditor subWindow = new TableEditor(linkRes, tableArea, item, year, new TableEditor.TableRecipient() {
            @Override
            public String gotInput(Item item) {
                saveData(linkRes, item);
                tableArea.refreshRowCache();
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void addValidatorToField(Field field, String idField) {
                addValidator(field,idField);
            }

            @Override
            public boolean validateItemAfterEdit(FieldGroup fieldGroup) {
                return validateItem(fieldGroup);
            }

            @Override
            public boolean validateFieldAfterEdit(FieldGroup fieldGroup, Property.ValueChangeEvent valueChangeEvent, String idField) {
                return validateField(fieldGroup, valueChangeEvent, idField);
            }
        });
        getUI().addWindow(subWindow);
    }
}
