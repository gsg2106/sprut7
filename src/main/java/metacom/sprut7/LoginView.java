/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

//import com.vaadin.annotations.PreserveOnRefresh;
//import com.vaadin.annotations.Theme;
//import com.vaadin.annotations.VaadinServletConfiguration;

import com.google.common.cache.LoadingCache;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
//import com.vaadin.ui.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import javax.annotation.PostConstruct;
import metacom.sprut7.Ruler;
import metacom.sprut7.beans.*;
//import metacom.sprut7.dao.Orik;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author Сергей
 */
//import java.lang.annotation.Annotation;
@Component
@Scope("prototype")
public class LoginView extends CustomComponent implements View{
    private Panel pan = new Panel();
    private VerticalLayout contentPan = new VerticalLayout();
    private Button btnGoest  = new Button("Гостевой режим");
    private Button btnLogin = new Button("Вход");
    private NativeSelect reses;
    private TextField login = new TextField ( "Логин");
    private PasswordField password = new PasswordField ( "Пароль");
    private TextField summaLast = new TextField ( "Сумма");
    static Config config = null;
    private Boolean goest;
    private HorizontalLayout regim = new HorizontalLayout();
    private StatePlat statePlat;
    
    @Autowired
    private LoadPlat loadPlat; //TestOnplat;

//    @Autowired
//    private Orik dao;

//    private String tested = null;
    
//    private ComboBox login;
//    private PasswordField password = new PasswordField("Пароль");
//    private Button btnLogin = new Button("Вход");
//    private String logPw;
//    private String selectUser;
    //private String linkPool;
    private String linkRes;
    
    LoginView(){
    }

    @Autowired
    // @PostConstruct работает в данном случае аналогично @Autowired
    public void init(){
        //linkPool =(String) VaadinSession.getCurrent().getAttribute("link");
        //linkRes =(String) VaadinSession.getCurrent().getAttribute("linkRes");
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
//        linkPool =(String) getSession().getAttribute("link");
//        linkRes =(String) getSession().getAttribute("linkRes");
//        config = (Config) getSession().getAttribute("config");
//        statePlat = (StatePlat) getSession().getAttribute("statePlat");     
        btnLogin.setDescription("Войти в как пользователь");
        btnGoest.setDescription("Войти в как гость (с ораниченными функциями).");
        goest = false;
        regim.setWidth("100%");
        regim.setVisible(false);
//        dao.setIdplat(1);

        reses = new NativeSelect("Ваш участок.");
        reses.setWidth("330px");
        reses.setNullSelectionAllowed(false);
        Iterator it = config.getLispReses().entrySet().iterator();
        while (it.hasNext()) {
          Map.Entry pairs = (Map.Entry)it.next();
          reses.addItem(pairs.getKey());
        }
        
        reses.setImmediate(true);
        reses.addValueChangeListener(new Property.ValueChangeListener(){
            @Override
            public void valueChange(ValueChangeEvent event) {
                if (event.getProperty().getValue() != null){
                   String currentRes =(String)event.getProperty().getValue();
                   linkRes = config.getLispReses().get(currentRes);
                   
                   login.setEnabled(true);
                   password.setEnabled(true);
                   summaLast.setEnabled(true);
                   summaLast.setVisible(false);
                   btnLogin.setEnabled(true);
                   if (config.getGoestEnabled().equals("1")) {
                      btnGoest.setEnabled(true);
                   }   
                }   
            }
        });
       // пока непонятно как  password.setSecret ( true );
        summaLast.setVisible(false);
        Label regimLabel = new Label ("<i><p style=\"color:#0000ff\">Гостевой вход</p> </i>",Label.CONTENT_XHTML);
        regimLabel.setWidth("100px");
        regim.addComponent (regimLabel);
        regim.setComponentAlignment(regimLabel, Alignment.TOP_RIGHT);
        
        CustomLayout castomLayout = new CustomLayout("sprut-login-layout");
        castomLayout.addComponent(new HeaderAreaImpl(),"header");
//        users.setLinkPool(linkPool);
//        Container container =  users.getContainer();
        password.setWidth("250px");
        summaLast.setWidth("250px");

        //password.setSecret ( true );
        
        pan = new Panel("Введите ваш логин и пароль");
        pan.setWidth("410px");
//        pan.setWidth("310px");
        pan.setHeight("320px");
//        contentPan.setSizeFull();
        contentPan.setMargin(true);
        contentPan.addComponent(regim);
        contentPan.addComponent ( new Label ("Пожалуйста выберите участок, затем введите логин и пароль для индентификации"));
        contentPan.addComponent(reses);        
        contentPan.addComponent(new Ruler());        
        contentPan.addComponent ( new Label ());
        contentPan.addComponent ( login );
        contentPan.addComponent ( password );
        contentPan.addComponent ( summaLast );
        contentPan.addComponent(new Ruler());        
         
        //----------------- ГОСТЕВОЙ ВХОД -------------------
        //loginpanel.addComponent ( btnLogin );
        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.setWidth("100%");
        contentPan.addComponent (toolbar);
        toolbar.addComponent ( btnLogin );
        toolbar.addComponent ( btnGoest );
        toolbar.setComponentAlignment(btnGoest, Alignment.TOP_RIGHT);
        
        if (reses.getValue() == null){
               login.setEnabled(false);
               password.setEnabled(false);
               summaLast.setEnabled(false);
               summaLast.setVisible(false);
               btnLogin.setEnabled(false);
               btnGoest.setEnabled(false);
        }
        btnLogin.addClickListener( new Button.ClickListener()
        {
            @Override
            public void buttonClick ( Button.ClickEvent event )
            {
                try
                {
                    String pw;
                    String log;
                    if (goest) {
//                      pw = ((String)password.getValue()).replaceAll(",", ".");
                      //if (password.isVisible())  
                      //    pw = ((String)password.getValue()).split("\\.")[0].split(",")[0];
                      //if (summaLast.isVisible())  
                      pw = ((String)summaLast.getValue()).split("\\.")[0].split(",")[0];
                      
                      log = "      " + ((String)login.getValue()).trim();
                      int i = log.length()-6;
                      log = log.substring(i, i+6);
                    } else {
                       pw = (String)password.getValue();
                       log = ((String)login.getValue()).trim();
                    }
//                    ((ReportsUI) UI.getCurrent()).authenticate(log, pw , linkRes, goest, config.getTested());
                    authenticate(log, pw , linkRes, goest, config.getTested());
                    // сюда не попадет если не опознан
                    //loginpanel.setEnabled(false);
                    getSession().setAttribute("idplat",statePlat.getIdPlat());
                    Integer idplat = statePlat.getIdPlat();
                    
                    Statistika stat = new Statistika();
                    stat.init(idplat , Statistika.st_login );
                    
                    getUI().getNavigator().navigateTo("dolgi");
                }
                catch ( Exception e )
                {
                    Notification.show( e.toString ());
                }
            }
        });        
        
        btnGoest.addClickListener( new Button.ClickListener()
        {
            @Override
            public void buttonClick ( Button.ClickEvent event )
            {
                try
                {
                    
                   //btnGoest.setEnabled(false);
                   if (!goest){
                      login.setCaption("Номер договора");
                      password.setVisible(false);
                      summaLast.setVisible(true);
                      summaLast.setCaption("Сумма последнео платежа за акт.энергию.");
                      summaLast.setDescription("Сумма последнего платежа за активную энергию без копеек");
                      goest = true;
                      btnGoest.setCaption("Штатный режим");
//??                      password.setSecret ( false );
                      regim.setVisible(true);
                      if (config.getTested().equals("1")) {
                          summaLast.setEnabled(false);
                          password.setEnabled(false);
                      }
                   } else {
                      login.setCaption("Логин");
                      summaLast.setVisible(false);
                      password.setVisible(true);
                      password.setCaption("Пароль");
                      password.setDescription("");
                      goest = false;
                      regim.setVisible(false);
//??                      password.setSecret ( true );
                      btnGoest.setCaption("Гостевой режим");
                      password.setEnabled(true);
                  }   
                }
                catch ( Exception e )
                {
                      Notification.show( e.toString ());
                }
            }
        });        
        
        pan.setContent(contentPan);
        pan.setStyleName("loginpanel");
        
        VerticalLayout rootLayout = new VerticalLayout();
        rootLayout.addStyleName("login");
        rootLayout.addComponent(pan);
        rootLayout.setComponentAlignment(pan, Alignment.TOP_CENTER);                       
        
        rootLayout.setSizeFull();
        castomLayout.addComponent(rootLayout,"mainpanel");
        setImmediate(true);
        setCompositionRoot(castomLayout);
    }
 

    public void authenticate( String login, String password, String linkRes, Boolean goest,String tested) throws Exception
    {
       statePlat.setGoest(goest);
       String linkPool = "java:comp/env/jdbc/"+linkRes;
       statePlat.setLinkPool(linkPool);
       statePlat.setLinkRes(linkRes);
       LoadingCache<String, String> lispINNs = (LoadingCache<String, String>) VaadinSession.getCurrent().getAttribute("lispINNs");
       VaadinSession.getCurrent().setAttribute("nameKeystore",config.getLispKeystores().get(lispINNs.get(linkRes) ));
       
       loadPlat.init(login, goest, tested, linkRes);
       if (loadPlat.PlatIsFound()){
           
           Boolean tstpw =false;
           if (loadPlat.getPassword() !=null) {
              tstpw = loadPlat.getPassword().equals( password );
           }
           
           if ((tested.equals("1") & goest) | ( tstpw ))
           {
                  Integer yea = loadPlat.getYearRaport();
                  Integer mon = loadPlat.getMonthRaport();
                  Integer yeaNew = loadPlat.getYea();
                  Integer monNew = loadPlat.getMon();
                  Date dtLock = loadPlat.getDtLock();
                  Integer pause  = loadPlat.getPause();
                  
                  statePlat.setIdPlat(loadPlat.getIdplat());
                  statePlat.setNamePlat(loadPlat.getNameplat());
                  statePlat.setAdres(loadPlat.getAdres());
                  statePlat.setState(loadPlat.getState());
                  statePlat.setNumdog(loadPlat.getNumDog());
                  statePlat.setYearRaport(yea);
                  statePlat.setMonthRaport(mon);
                  statePlat.setPause(pause);
                  statePlat.setYeaNew(yeaNew);
                  statePlat.setMonNew(monNew);
                  statePlat.setRaportEnabled(loadPlat.getRaportEnabled());
                  statePlat.setMess(loadPlat.getMess());
                  statePlat.setDtLock(dtLock);
                  // создание защищенного ресурса но не его вызов
                  // делаться будет по другому
                  //loadProtectedResources(TestOnplat.getIdplat(),TestOnplat.getNameplat(),TestOnplat.getAdres(),TestOnplat.getState(),yea,mon,TestOnplat.getNumDog());
           } else {
              throw new Exception("Неверный пароль");
              }
       } else
//                window.showNotification("Неверный индентификатор пользователя");
           throw new Exception("Неверный индентификатор пользователя");
    }
    
    
    
    @Override
    public void enter(ViewChangeEvent event) {
        if (password.isVisible())
            password.focus();
        if (summaLast.isVisible())
            summaLast.focus();
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}