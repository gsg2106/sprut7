/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

//import com.mycompany.maven_sprut.domain.GsgWebKs;
//import com.mycompany.maven_sprut.domain.Ra;
//import com.vaadin.addon.beanvalidation.BeanValidationForm;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.*;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
//import com.vaadin.addon.beanvalidation.BeanValidationForm;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import metacom.sprut7.domain.GsgWebKs;
import metacom.sprut7.domain.Ra;

//import de.essendi.vaadin.ui.component.numberfield.NumberField;
//import com.vaadin.ui.MaskedTextField;
//import com.vaadin.ui.NumericField;



/**
 *
 * @author locus
 */
@SuppressWarnings("serial")
    public class RaEditor  extends Window implements Button.ClickListener{
    private final Item raItem;
    private VerticalLayout mainLayout = new VerticalLayout();
    private Integer asrecalc; 
    private FormComponent editorForm = new FormComponent();;
//    private FormLayout editorForm;    private FieldGroup  fieldGroup = new FieldGroup();
    private FieldGroup  fieldGroup = new FieldGroup();

    private Button saveButton;
    private Button cancelButton;
    private Button recalcButton;
    private Integer itog;
    private Boolean ksexists=null;
    private Date dt;
    private String kontrp;
    private String linkRes;
    private Double kp = null;
    private Window editor;
//    private final Window mainWindow;
    public RaEditor(Item id, String linkRes){
        editor = this;
        setModal(true);  
        setWidth("340px");

//        mainWindow = SprutVaadinApplication.getInstance().getMainWindow();
        
        this.raItem = id;
        this.linkRes = linkRes;
        itog=0;
        asrecalc = -1;
        
        
        
//------------------------------------------------------------------------
        Integer idPutu =(Integer) raItem.getItemProperty("idPutu").getValue();
        Character eng =(Character) raItem.getItemProperty("eng").getValue();
        Integer idScale =(Integer) raItem.getItemProperty("idScale").getValue();
        
        EntityManager em = Persistence
 	   .createEntityManagerFactory(linkRes)
      	   .createEntityManager();
        Collection emps = em.createQuery("SELECT g FROM GsgWebKs g WHERE g.idPutu = :idPutu and g.eng = :eng and g.idScale = :idScale").setParameter("idPutu", idPutu).setParameter("eng", eng).setParameter("idScale", idScale).setMaxResults(1).getResultList();
        ksexists = !emps.isEmpty();  
        if (ksexists) {
            GsgWebKs e = (GsgWebKs) emps.iterator().next();
            dt= e.getDt();
            kontrp = e.getKontrp();
        } else
          kontrp = null;  
        em.close();      
        
        if (kontrp != null) {
           try {
                 kp = Double.parseDouble(kontrp);
               } catch (NumberFormatException ex) { kp = null;}
        }
//------------------------------------------------------------------------
                
//        editorForm = new BeanValidationForm<Ra>(Ra.class);
        fieldGroup.setItemDataSource(raItem);                
//        editorForm.setFormFieldFactory(this);
        //Включим буферизацию
        fieldGroup.setBuffered(true);
//        editorForm.setWriteThrough(false); 
        
        editorForm.setImmediate(true);
        Layout layout = editorForm.getCentral();
        //??editorForm.setItemDataSource(raItem, Arrays.asList("nachalo","danrap","ktr","kvtchrazn","razn"));
        //Field F = fieldGroup.getField("nachalo");
        layout.addComponent(fieldGroup.buildAndBind("nachalo"));
        layout.addComponent(fieldGroup.buildAndBind("danrap"));
        layout.addComponent(fieldGroup.buildAndBind("ktr"));
        layout.addComponent(fieldGroup.buildAndBind("kvtchrazn"));
        layout.addComponent(fieldGroup.buildAndBind("razn"));
        Field field = (fieldGroup.getField("danrap"));
        field.setCaption("Текущие");
        if (field instanceof TextField) ((TextField) field).setNullRepresentation("");
        field.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(ValueChangeEvent event) {               
               Double boofer;
               if (event.getProperty().getValue() != null){
                  String current =(String)event.getProperty().getValue();
                  try{
                     boofer = Double.parseDouble(current);
 	          } catch (NumberFormatException ex) {
                     Notification.show("Неправильно введены показания: "+current);
                     asrecalc = 0;
                     itog = -99999;
                     return;
                  }
                  Field nachalo = fieldGroup.getField("nachalo");                        
                  if (nachalo == null)
                     return;
                           
                  Field danrap = fieldGroup.getField("danrap");
                  if (danrap == null)
                     return;
//??//??
                  String  maska = GetMaska(fieldGroup.getField("nachalo").getValue().toString());
                  danrap.setValue(FormatPokaz(boofer, maska));
                  danrap.focus();
                  ((TextField)event.getProperty()).setImmediate(true);
                  //((TextField)field).setImmediate(true);
                           
                 Field razn = fieldGroup.getField("razn");
                 if (razn == null)
                     return;
                                   
                 Field potrebl = fieldGroup.getField("kvtchrazn");                        
                 if (potrebl == null)
                    return;
                           
                 Field ktr = fieldGroup.getField("ktr");                        
                 if (ktr == null)
                    return;
                 
//                 itog = recalckvt(current, nachalo.getValue().toString(), ktr.getValue().toString());
                 itog = recalckvt(current, nachalo.getValue().toString(),raItem.getItemProperty("ktr").getValue().toString());
                 
//                 potrebl.setValue(itog);
                 potrebl.setValue(itog.toString());
//                 raItem.getItemProperty("potrebl").setValue(itog);
                 
                 Double delta =boofer - Double.parseDouble(nachalo.getValue().toString());

                 //  delta = 427.4022;
                 //  delta = 427.4099;
                 //--------------- Округление-------------------
                 BigDecimal bdDelta = BigDecimal.valueOf(delta);
                 Integer sz;
                 Integer ln = maska.length();
                 Integer dot = maska.indexOf(".");
                 if (dot>=0) sz = ln-dot-1;
                 else sz = 0;
                 String srazn = bdDelta.setScale(sz, RoundingMode.HALF_UP).toString();
                 //-------------- старый вид ---------------------------
                 // String srazn = delta.toString().substring(0,(delta.toString().length()-1 > 7 ? 8 : delta.toString().length()));

                 razn.setValue(srazn);
                 //------- вычислим новые показания --------           
                 danrap = fieldGroup.getField("danrap");                        
                 String curr = danrap.getValue().toString();
                 Double newPokaz= null;
                 try{
                    newPokaz = Double.parseDouble(curr);
                 } catch (NumberFormatException ex) {
                    Notification.show("Неправильно введены показания: "+curr);
                    newPokaz = null;
                 }
                 //------------ итог отрицательный ----------
                 if (itog<0 && (asrecalc < 2)) {
                    final List<String> variantdo = Arrays.asList(new String[] {
                       "Ошибка ввода", "Переполнение счетчика", "Ранее завышенные показания"});
                       asrecalc = 0;
                       modifyform(asrecalc);
//                     ChoiceDialog dialog = new ChoiceDialog(getWindow(),mainWindow,
                       ChoiceDialog dialog = new ChoiceDialog(
                         editor,      
                         "Получено отрицательное потребление",
                         variantdo,
                         new ChoiceDialog.Recipient() {
                             public void gotInput(String input) {
                                if ("-1".equals(input)) {
                                   asrecalc = 0;
                                } else {
                                   fieldGroup.getField("danrap").focus();
                                   asrecalc = variantdo.indexOf(input)+1;
                                }  
                                //editor.setVisible(true);
                                modifyform(asrecalc);
                             }
                         });
                         editor.setVisible(false);
                         getUI().addWindow(dialog);
//                         close();
//                         if (UI.getCurrent().getWindows().contains(editor))
//                            getUI().removeWindow(editor);

                         //if (getUI().getWindows().contains(editor)){
                         //getUI().removeWindow(editor);
                    } else {
                        if (kp != null){
                            Double nach = Double.parseDouble(nachalo.getValue().toString());
//                                (int) Math.round(ii)

                            String  maska1 = GetMaska(fieldGroup.getField("nachalo").getValue().toString());
                            double maxvalue = 0;
                            try {
                                maxvalue = Double.parseDouble(maska1);
                            } catch (NumberFormatException ex) {
                                Notification.show(ex.getMessage());
                            }


                            if ((newPokaz < kp) && (kp >= nach) && ((kp - newPokaz) < (maxvalue/2))) {
                                Notification.show("Введеные показания меньше показаний последнего контрольного съема.");
                            }
                        }
                    }
               } 
            }
        });

        field = (fieldGroup.getField("nachalo"));
        field.setCaption("Предыдушие");
        field.setReadOnly(true);
        if (field instanceof TextField) ((TextField) field).setNullRepresentation("");
        
        field = (fieldGroup.getField("ktr"));
        field.setCaption("Коэфф-тр");
        //TextField field1.setConverter(Integer.class);
        field.setReadOnly(true);
        if (field instanceof TextField) ((TextField) field).setNullRepresentation("");

        field = (fieldGroup.getField("razn"));
        field.setCaption("разница показаний");
        field.setEnabled(false);;
        if (field instanceof TextField) ((TextField) field).setNullRepresentation("");
        
        field = (fieldGroup.getField("kvtchrazn"));
        field.setCaption("Потребление");
        field.setEnabled(false);;
        if (field instanceof TextField) ((TextField) field).setNullRepresentation("");

        field = (fieldGroup.getField("kvtchrazn"));
        field.setCaption("Потребление");
        field.setEnabled(false);;
        if (field instanceof TextField) ((TextField) field).setNullRepresentation("");
        
        saveButton = new Button("Сохранить", this);
        cancelButton = new Button("Отмена", this);
        recalcButton = new Button("Пересчитать", this);

        editorForm.getFooter().addComponent(saveButton);
        editorForm.getFooter().addComponent(cancelButton);
        editorForm.getFooter().addComponent(recalcButton);
        
        String  maska = GetMaska(fieldGroup.getField("nachalo").getValue().toString());
        
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String ms1;
        if (kontrp == null){
          ms1 = "<i><p style=\"color:#0000ff\">"+
             "Контрольного съема нет."+
             "</p> </i>";
        } else {
          ms1 = "<i><p style=\"color:#0000ff\">"+
             "Последний контр. съем \"<b><font color=\"color:#700000\">"+kontrp+"</font></b>\" от "+df.format(dt)+                
             "</p> </i>";
        }
        
        mainLayout.addComponent(new Label(ms1,Label.CONTENT_XHTML));
//        addComponent(new Label("Последний контр. съем "+kontrp+" от "+dt,Label.CONTENT_XHTML));
        mainLayout.setMargin(true);
        mainLayout.addComponent(new Label("Формат ввода показаний: "+maska.replace("9", "X")));
        mainLayout.addComponent(editorForm);
        fieldGroup.getField("danrap").focus();

        setCaption(buildCaption());
        mainLayout.setSizeUndefined();
        this.setContent(mainLayout);
    }

    @Override
    public void buttonClick(ClickEvent event) {
       if (event.getButton() == saveButton) {
            //------- вычислим новые показания --------           
            Field danrap = fieldGroup.getField("danrap");                        
            String curr = danrap.getValue().toString();
            Double newPokaz= null;
            try{
               newPokaz = Double.parseDouble(curr);
 	        } catch (NumberFormatException ex) {
               Notification.show("Неправильно введены показания: "+curr);
               newPokaz = null;
            }
            //------------------            
            if (itog >= 0) {
               if (kp != null){
                   // проверим нп переход через 0
                   String  maska = GetMaska(fieldGroup.getField("nachalo").getValue().toString());
                   double maxvalue = 0;
                   try {
                       maxvalue = Double.parseDouble(maska);
                   } catch (NumberFormatException ex) {
                       Notification.show(ex.getMessage());
                   }

                   if ((newPokaz < kp) && ( (kp  - newPokaz)<(maxvalue/2) ) ) {
                       Notification.show("Введеные показания меньше показаний последнего контрольного съема.");
                   } else {
                       try {
                           fieldGroup.commit();
                       } catch (CommitException ex) {
                           Notification.show("Данные не сохранены.");
                           Logger.getLogger(RaEditor.class.getName()).log(Level.SEVERE, null, ex);
                       }
                       fireEvent(new EditorSavedEvent(this, raItem));
                       close();
                   }
               } else {
                   try {
                       fieldGroup.commit();
                   } catch (CommitException ex) {
                       Notification.show("Данные не сохранены.");
                       Logger.getLogger(RaEditor.class.getName()).log(Level.SEVERE, null, ex);
                   }
                   fireEvent(new EditorSavedEvent(this, raItem));
                   close();
               }
            } else {
//System.out.println("asrecalc=" + asrecalc);
                if (asrecalc.equals(0)) {
                  //ошибка ввода но выбо не сделан 
                } if (asrecalc<0) {
                  Notification.show("Отрицательный результат недопустим");
               } else if (asrecalc.equals(1)) {
                  //ошибка ввода выбор сделан
                  fieldGroup.discard();
                  Notification.show("Отрицательный результат недопустим");
               } else if (asrecalc.equals(2)) {
                    try {
                        // переполнение счетчика 
                        fieldGroup.commit();
                    } catch (CommitException ex) {
                        Notification.show("Данные не сохранены.");
                        Logger.getLogger(RaEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    fireEvent(new EditorSavedEvent(this, raItem));
                    close();
               } else if (asrecalc.equals(3)) {
                    try {
                        // ранее завышенный результат
                        fieldGroup.commit();
                    } catch (CommitException ex) {
                        Notification.show("Данные не сохранены.");
                        Logger.getLogger(RaEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                  fireEvent(new EditorSavedEvent(this, raItem));
                  close();        
               }
            }
        } else if (event.getButton() == cancelButton) {
            fieldGroup.discard();
            close();        
        }
      // close();        
      // asrecalc = -1;
    }

    
    //---------- конструктор листнера --------------
    public void addListener(EditorSavedListener listener) {
        try {
            Method method = EditorSavedListener.class.getDeclaredMethod(
                    "editorSaved", new Class[] { EditorSavedEvent.class });
            addListener(EditorSavedEvent.class, listener, method);
        } catch (final java.lang.NoSuchMethodException e) {
            // This should never happen
            throw new java.lang.RuntimeException(
                    "Internal error, editor saved method not found");
        }
    }

    public void removeListener(EditorSavedListener listener) {
        removeListener(EditorSavedEvent.class, listener);
    }

    public static class EditorSavedEvent extends Component.Event {
        
        private Item savedItem;

        public EditorSavedEvent(Component source, Item savedItem) {
            super(source);
            this.savedItem = savedItem;
        }

        public Item getSavedItem() {
            return savedItem;
        }
    }

    public interface EditorSavedListener extends Serializable {
        public void editorSaved(EditorSavedEvent event);
    }
    
    /**
     * @return the caption of the editor window
     */
    private String buildCaption() {
//        return String.format("%s %s", personItem.getItemProperty("firstName")
//                .getValue(), personItem.getItemProperty("lastName").getValue());
        return "Введите показания счетчика";
    }
    
    //--------- пересчет потребления в случае переполнения---------------
    private Integer recalcOnOverload(String s_nachalo,String s_danrap,String s_ktr) {
                  //itog = Integer.parseInt(current) -  Integer.parseInt((String)nachalo.getValue());
/*        
                  String maska = "";
                  for(int i = 0; i< s_nachalo.length(); i++)
                  {
                      if (s_nachalo.substring(i,i+1) == ".") {
                      maska = maska + ".";}
                      else {maska = maska + "9";}   
                  }                     
*/
                  String  maska = GetMaska(fieldGroup.getField("nachalo").getValue().toString());
                  double maxvalue = 0;
                  try {
                        maxvalue = Double.parseDouble(maska);
   	          } catch (NumberFormatException ex) {
                      Notification.show(ex.getMessage());
                  }
                  double ii = (maxvalue - Double.parseDouble(s_nachalo)+ Double.parseDouble(s_danrap)) * Double.parseDouble(s_ktr);
        return (int) Math.round(ii); 
    }
    
    //--------- расчет потребления в отсутствие переполнения---------
    private Integer recalckvt(String s_current,String s_nachalo,String s_ktr){
       Double curr = Double.parseDouble(s_current); 
       Double nach = Double.parseDouble(s_nachalo); 
       Double ktr = Double.parseDouble(s_ktr); 
       Double it  = (curr - nach) * ktr;
               //Integer.parseInt(current) -  Integer.parseInt((String)nachalo.getValue());
       return (int) Math.round(it); 
    };
    
    private void modifyform(Integer asrecalc){
          if (asrecalc == 2) {
                  // переполнение счетчика 
                  Field nachalo = fieldGroup.getField("nachalo");
                  Field danrap = fieldGroup.getField("danrap");                        
                  Field potrebl = fieldGroup.getField("kvtchrazn");                        
                  Field ktr = fieldGroup.getField("ktr");
                  potrebl.setValue(recalcOnOverload(nachalo.getValue().toString(),
                                                    danrap.getValue().toString(),
                                                    ktr.getValue().toString()
                                                    ).toString());
          }        
          if (asrecalc == 3) {
                  // ранее завышенный результат
                  Field potrebl = fieldGroup.getField("kvtchrazn");                        
                  potrebl.setValue(0);
          }          
   };
    
   private String FormatPokaz(Double pokaz,String maska) {
       Integer sz;
       Integer ln = maska.length();
       Integer dot = maska.indexOf(".");
       if (dot>=0) sz = ln-dot-1;
       else sz = 0;
       String format = "%0"+ln.toString()+"."+sz.toString()+"f";
//       String format = "%012.3f";
       String current = String.format(format, pokaz).replace(",", ".")  ;
       if (current.length()>ln) {
          Notification.show("Введенное число слишком длинное и будет урезано.");
          current = current.substring(current.length()-ln);
       }
        return current;
   }

    private String GetMaska(String toString) {
        String maskaloc = "";
        for(int i = 0; i< toString.length(); i++){
           if (".".equals(toString.substring(i,i+1))) {
              maskaloc = maskaloc + ".";}
           else {maskaloc = maskaloc + "9";}   
        }          
       return maskaloc;
    }
   
    
}
