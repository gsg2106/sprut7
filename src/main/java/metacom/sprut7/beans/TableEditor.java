package metacom.sprut7.beans;

import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.ui.*;
import metacom.sprut7.FormComponent;
import metacom.sprut7.domain.SzajPlan;

import java.util.Collection;
import java.util.Collections;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 03.08.16
 * Time: 11:52
 * To change this template use File | Settings | File Templates.
 */
public class TableEditor extends Window implements Button.ClickListener{
    private  Button saveButton;
    private  Button cancelButton;
    private Table table;
    private String linkRes;
    private Item item;
    private TableRecipient tableRecipient;
    private Integer year;
    private FieldGroup fieldGroup = new FieldGroup();

    public TableEditor(String linkRes,  Table table, Item item, Integer year, TableRecipient tableRecipient){
        this.table = table;
        this.linkRes = linkRes;
        this.item = item;
        this.tableRecipient = tableRecipient;
        this.year = year;

        setModal(true);
//        setWidth("220px");
        setWidth("270px");
//        setHeight("320");
        setHeightUndefined();

        //setSizeUndefined();

        VerticalLayout mainLayout = new VerticalLayout();
        //mainLayout.setSizeFull();
        mainLayout.setWidth("100%");
        mainLayout.setHeightUndefined();

        mainLayout.setMargin(true);
        mainLayout.setSpacing(true);
        //----------- прикрепим к окну форму ввода данных ---------------
        Layout body = getBody();

        mainLayout.addComponent(body);
        mainLayout.setComponentAlignment(body, Alignment.TOP_CENTER);
        HorizontalLayout footer = getFooter();
        mainLayout.addComponent(footer);
        mainLayout.setComponentAlignment(footer, Alignment.BOTTOM_RIGHT);

        mainLayout.setExpandRatio(body, 1);
        setContent(mainLayout);
    }

    private Layout getBody(){
        Layout body = new FormLayout(); //VerticalLayout();
        //FormComponent editorForm = new FormComponent();;

        //Layout body = editorForm.getCentral();

        //body.setSizeFull();
        body.setWidth("250px");
        body.setHeightUndefined();
        //body.setHeight("150px");
        String[] headers = table.getColumnHeaders();
        fieldGroup.setItemDataSource(item);
        fieldGroup.setBuffered(true);
        int i = 0;
        for (Object column: table.getVisibleColumns()){
            String itemBean = (String) column;
            if (item.getItemProperty(itemBean) != null){
            Field field = fieldGroup.buildAndBind(itemBean);
            field.setCaption(headers[i]);
            field.setId(itemBean);
            if (itemBean.equals("nameMonth")) field.setReadOnly(true);
            field.setWidth("70px");
            if (field instanceof TextField) ((TextField) field).setNullRepresentation("Поле ввода не может быть пустым.");
            //field.addValidator(new NullValidator("Поле ввода не может быть пустым.",false)); //IntegerRangeValidator());
            //if (item.getItemProperty(itemBean).getValue() instanceof Integer){
            //field.addValidator(Validator );
            tableRecipient.addValidatorToField(field, itemBean);
            field.addValueChangeListener(new Property.ValueChangeListener() {
                @Override
                public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                    // проверка валидности введенных данных
                    Field field = (Field) valueChangeEvent.getProperty();
                    String idField = field.getId();
                    tableRecipient.validateFieldAfterEdit( fieldGroup, valueChangeEvent, idField);
                }
            });
            body.addComponent(field);
            }
            i++;
        }
        return  body; //editorForm;
    }

    private HorizontalLayout getFooter(){
        HorizontalLayout footer = new HorizontalLayout();
        footer.setHeight("25px");
        //---------------- сделаем кропки выхода из окна ----------------
        saveButton = new Button("Выполнить", this);
        cancelButton = new Button("Отменить  ", this);
        //--------------- поместим кнопки выхода в подвал ---------------
        footer.addComponent(cancelButton);
        footer.setComponentAlignment(cancelButton, Alignment.TOP_LEFT);
        footer.addComponent(saveButton);
        footer.setComponentAlignment(saveButton, Alignment.TOP_RIGHT);
        return footer;
    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        if (clickEvent.getButton() == saveButton) {
//            if (usernameFld.getValue().toString().length()>40){
//                Notification.show("Длина логина более 40 символов.");
//            } if (passwordFld.getValue().toString().length()< 8){
//                Notification.show("Длина пароля менее 8 символов.");
//            } if (passwordFld.getValue().toString().length()>40){
//                Notification.show("Длина пароля более 40 символов.");
//            } else {
//                if (passwordFld.getValue().equals(confirmPasswordFld.getValue())) {
//                    if ((usernameFld.getValue().toString().equals(login)) | (recipient.testLogin(usernameFld.getValue().toString()))){
//                        // showNotification("Логин верный.");
//                        login = usernameFld.getValue().toString();
//                        password = passwordFld.getValue().toString();
//                        recipient.gotInput(login,password);
//                        //getParent().removeWindow(this);
//                        getUI().removeWindow(this);
//                    } else{
//                        usernameFld.setValue(login);
//                        Notification.show("Такой логин уже есть.");
//                    }
//                } else{
//                    Notification.show("Вы не правильно ввели пароль.");
//                }
//            }

            try {
                fieldGroup.commit();
                table.commit();
                if (tableRecipient.validateItemAfterEdit(fieldGroup)){
                    String error = tableRecipient.gotInput(item);
                    table.refreshRowCache();
                    close();
                }
            } catch (FieldGroup.CommitException ex) {
                Notification.show("Данные не сохранены.");
                Logger.getLogger(TableEditor.class.getName()).log(Level.SEVERE, null, ex);
                close();
            }


//            String error = tableRecipient.gotInput(item);
//            if (error !=null){
//                Notification.show("Ошибка записи"+error);
//            }
            //getUI().removeWindow(this);
        }
        if (clickEvent.getButton() == cancelButton) {
            //getParent().removeWindow(this);
            fieldGroup.discard();
            close();

//            getUI().removeWindow(this);
        }
    }

    public interface TableRecipient{
        public String gotInput(Item item);
        public void addValidatorToField(Field field, String idField);
        public boolean validateItemAfterEdit(FieldGroup fieldGroup);
        public boolean validateFieldAfterEdit(FieldGroup fieldGroup, Property.ValueChangeEvent valueChangeEvent, String idField);
    }
}
