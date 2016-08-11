/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.data.Property;
import com.vaadin.ui.*;
import java.util.List;

/**
 *
 * @author Сергей
 */
@SuppressWarnings("serial")
public class ChoiceDialog extends Window{
    Recipient r;
    Window parent;
    private Button saveButton;
   
//    ChoiceDialog(final Window parent, final Window main, String question,final List<String> variantdo, Recipient recipient){
    ChoiceDialog(Window parent_,String question,final List<String> variantdo, Recipient recipient){
        r = recipient;
          this.parent = parent_;
//        this.setWidth("400px");
//        this.setHeight("200px");
        setCaption(question);
        setModal(true);
        //-------- присвоим значение по умолчанию--------
//        r.gotInput("-1");
        // после +1 станет=0;
        
        final Window dialog = this;
//        VerticalLayout layout = (VerticalLayout) getContent();
        VerticalLayout layout = new VerticalLayout(); //(VerticalLayout) getContent();
        layout.setSizeUndefined();
        //layout.setSizeFull();
        //layout.setWidth("200px");
        //layout.setHeight("100px");
        layout.setMargin(true);
        layout.setSpacing(true);
        
        OptionGroup variantSelect = new OptionGroup("Выберите один из предлагаемых вариантов", variantdo);
        variantSelect.setNullSelectionAllowed(false); // user can not 'unselect'
        variantSelect.select("Ошибка ввода"); // select this by default
        variantSelect.setImmediate(true); // send the change to the server at once
//        variantSelect.addListener(this); // react when the user selects something
        variantSelect.addValueChangeListener(new Property.ValueChangeListener() {
                    @Override
                    public void valueChange(Property.ValueChangeEvent event) {               
                    String input = event.getProperty().getValue().toString();
//System.out.println("Choice dialog is asrecalc=" + input);
                    r.gotInput(input);
                    if (getUI().getWindows().contains(dialog)){
                      getUI().removeWindow(dialog);
                      parent.setVisible(true);

                    }  
                    //main.removeWindow(dialog);                        
                    //main.addWindow(parent);
                    }}); // react when the user selects something

        Button close = new Button("Закрыть", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (getUI().getWindows().contains(dialog)){
                    getUI().removeWindow(dialog);
                    parent.setVisible(true);
                }    
//                main.removeWindow(dialog);
//                main.addWindow(parent);
                
                //(subwindow.getParent().getParent()).removeWindow(subwindow);
 //               fireEvent(new Button.ClickEvent(saveButton));

            }
        });
        layout.addComponent(variantSelect);
        layout.addComponent(close);
        layout.setComponentAlignment(close, Alignment.TOP_RIGHT);
        this.setContent(layout);
        
 //       if (getUI().getWindows().contains(dialog)){
 //          getUI().removeWindow(dialog);
//        }
//        getUI().addWindow(dialog);
//        if (getUI().getWindows().contains(parent))
//            getUI().removeWindow(parent);
//        UI d =getUI();
//        UI.
//        getUI().addWindow(this);        
    }
    
    public interface Recipient {
        public void gotInput(String input);
    } 
}
