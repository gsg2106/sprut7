/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.util.filter.Compare;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.J2EEConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.domain.GsgWebViewPlat;
import metacom.sprut7.domain.WebInns;
import metacom.sprut7.domain.WebPlat;

/**
 *
 * @author Сергей
 */
@SuppressWarnings("serial")
public class Kabinet extends VerticalLayout implements IComponentContainer {
    private Integer idplat;
    private JPAContainer<WebInns> inns;
    private Table innsTable;
    private String login;    
    private String password;    
    private Label lb2;
    private String linkRes = null;
    private Boolean pause;
    private Boolean goestEnabled;
    private StatePlat statePlat;
    static Config config = null;
    private String linkPool;

//    private Window subwindow;
//    private TextField usernameFld;
//    private PasswordField passwordFld;
//    private PasswordField confirmPasswordFld;
    
    Kabinet(){
    }
    public void init(){
        //linkRes =(String) VaadinSession.getCurrent().getAttribute("linkRes");
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        linkPool = statePlat.getLinkPool();
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        Integer p = statePlat.getPause();
        goestEnabled = config.getGoestEnabled().equals("1");
        pause = p.intValue() == 1;
        this.idplat = statePlat.getIdPlat();
        setMargin(true);
        setHeight("100%");        
        setSizeFull();
        setStyleName("LispRaWindow");
        BuildKabinet();
        setImmediate(true);
       
    }
    private void BuildKabinet(){
       Statistika stat = new Statistika();
       stat.init(idplat, Statistika.st_lkab );
       
       Panel pan = new Panel();
       VerticalLayout panContent = new VerticalLayout();
       pan.setCaption("Реквизиты потребителя");
       pan.setStyleName("panelFields");
       pan.setWidth("600px");
       
//       FormLayout formlayout = new FormLayout();
//       formlayout.setSizeFull();
       addComponent(pan);
       setComponentAlignment(pan, Alignment.TOP_CENTER);
       
 //      panContent.addComponent(formlayout);
       //-------начинаем заполнять ------ 
       EntityManager em = Persistence
   	  .createEntityManagerFactory(linkRes)
	  .createEntityManager();
       Collection emps = em.createQuery("SELECT g FROM GsgWebViewPlat g WHERE g.idPlat = :idplat").setParameter("idplat", idplat).setMaxResults(1).getResultList();
       Boolean platexists = !emps.isEmpty();  
       if (platexists) {
         GsgWebViewPlat e = (GsgWebViewPlat) emps.iterator().next();
         login = e.getLogin();
         password = e.getPw();
         /*----------------- нарисуем поля ----------------- */
        StringBuffer output = new StringBuffer(110);
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        output.append(" <td>Реквизит</td>");
        output.append(" <td>Значение</td>");
        output.append("</tr>");
        
        
            output.append("<tr align=\"left\">");
            output.append(" <td align=\"left\">Лицевой счет №:</td>");
            output.append(" <td align=\"left\">"+e.getNumDog()+"</td>");
            output.append("</tr>");
         
            output.append("<tr align=\"left\">");
            output.append(" <td align=\"left\">Наименование</td>");
            output.append(" <td align=\"left\">"+e.getNampotr()+"</td>");
            output.append("</tr>");
         
            output.append("<tr align=\"left\">");
            output.append(" <td align=\"left\">ЕДРПОЕ</td>");
            output.append(" <td align=\"left\">"+e.getEdrpouCod()+"</td>");
            output.append("</tr>");
            
            output.append("<tr align=\"left\">");
            output.append(" <td align=\"left\">ЕДРПОЕ подразделения</td>");
            output.append(" <td align=\"left\">"+e.getParendEdrpouCod()+"</td>");
            output.append("</tr>");
            
            output.append("<tr align=\"left\">");
            output.append(" <td align=\"left\">Налоговый номер</td>");
            output.append(" <td align=\"left\">"+e.getNomspen()+"</td>");
            output.append("</tr>");
            
            output.append("<tr align=\"left\">");
            output.append(" <td align=\"left\">Юридический адрес</td>");
            output.append(" <td align=\"left\">"+e.getUridAdress()+"</td>");
            output.append("</tr>");

            
            output.append("<tr align=\"left\">");
            output.append(" <td align=\"left\">Почтовый адрес</td>");
            output.append(" <td align=\"left\">"+e.getAdrline()+"</td>");
            output.append("</tr>");
            
            output.append("<tr align=\"left\">");
            output.append(" <td align=\"left\">Электронный адрес</td>");
            output.append(" <td align=\"left\">"+e.getWwwadr()+"</td>");
            output.append("</tr>");
            
            output.append("<tr align=\"left\">");
            output.append(" <td align=\"left\">Логин</td>");
            output.append(" <td align=\"left\">"+login+"</td>");
            output.append("</tr>");
        output.append("</tbody></table>");        
        try {
            CustomLayout custom = new CustomLayout(new ByteArrayInputStream(output.toString().getBytes("UTF-8")));
            panContent.addComponent(custom);
            //pan.setContent(custom);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        //-------------------------------
/*
         Label lb3=new Label(); lb3.setCaption("Договор №:"); lb3.setValue(e.getNumDog()); formlayout.addComponent(lb3);
         Label lb =new Label(); lb.setCaption("Потребитель:"); lb.setValue(e.getNampotr()); formlayout.addComponent(lb);
         Label lb1 =new Label(); lb1.setCaption("Адрес:"); lb1.setValue(e.getAdrline()); formlayout.addComponent(lb1);
         lb2 =new Label(); lb2.setCaption("Логин:"); lb2.setValue(login); formlayout.addComponent(lb2);
         idplat= e.getIdPlat();
*/        
         /*--------------------------------------------------- */
       }
       em.close();            
       
       panContent.addComponent(new Label("Контактные телефоны"));
       J2EEConnectionPool connectionPool = new J2EEConnectionPool(linkPool);
       FreeformQuery dlg =  new FreeformQuery("select * from GSG_WEB_PHONE", connectionPool,"id");
       dlg.setDelegate(new ViewDelegate("GSG_WEB_PHONE"));
       SQLContainer phoneContainer = null;
        try {
            phoneContainer = new SQLContainer(dlg);
        } catch (SQLException ex) {
            Logger.getLogger(LispTuWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        phoneContainer.addContainerFilter(new Compare.Equal("ID_PLAT",idplat)); // чтобы не было выборки
        StringBuffer output = new StringBuffer(110);
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
//        output.append(" <td rowspan=\"2\">№ п.п</td>");
        output.append(" <td>Должность</td>");
        output.append(" <td>Фамилия имя отчество</td>");
        output.append(" <td>Номер телефона</td>");
        output.append("</tr>");
        for (int i = 0; i < phoneContainer.size(); i++) {
            Object id = phoneContainer.getIdByIndex(i);
            Item item = phoneContainer.getItem(id);
            output.append("<tr align=\"left\">");
            Object o = item.getItemProperty("NAME_JOB").getValue();
            String s =  o == null ? "": item.getItemProperty("NAME_JOB").getValue().toString();
            output.append(" <td align=\"left\">"+ s +"</td>");
            o = item.getItemProperty("NAME_PHONE").getValue();
            s = o == null ? "" : item.getItemProperty("NAME_PHONE").getValue().toString() ;
            output.append(" <td align=\"left\">"+ s +"</td>");
            o = item.getItemProperty("PHONE_NO").getValue();
            s = o == null ? "" : item.getItemProperty("PHONE_NO").getValue().toString();
            output.append(" <td align=\"left\">"+ s +"</td>");
            output.append("</tr>");
        }
        output.append("</tbody></table>");        
        try {
            CustomLayout custom = new CustomLayout(new ByteArrayInputStream(output.toString().getBytes("UTF-8")));
            panContent.addComponent(custom);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        phoneContainer.removeAllItems();

       inns = JPAContainerFactory.make(WebInns.class,linkRes);
       inns.addContainerFilter(new Compare.Equal("idPlat",idplat));
       inns.addContainerFilter(new Compare.Equal("idPlat",idplat));
       inns.applyFilters();
       innsTable = new Table("Список подписантов", inns);
       //innsTable.setSizeFull();  
       //innsTable.setSizeUndefined();
       innsTable.setWidth("100%");
       innsTable.setHeight("80px");
       innsTable.setSelectable(true);
       innsTable.setImmediate(true);
       //innsTable.setHeight("100%");
       // raTable.enableContentRefreshing;
       innsTable.setVisibleColumns(new Object[] {"fio","edrpou","inn","email","comment" });//  });      
       innsTable.setColumnHeaders(new String[] {"Подписант","ЕДРПОУ","ИНН","Почта","Комментарий"});
       panContent.addComponent(new Ruler());
       panContent.addComponent(innsTable);
       panContent.addComponent(new Ruler());
       //------------------------ добавим кнопку поменять пароль ---------------
       Button chPassword = new Button("Поменять пароль", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
               //------------ создадим модальное окно-----------------------------
               MyLoginWindow logSubWindow = new MyLoginWindow(login,password,
                       new MyLoginWindow.LoginRecipient() {

                    @Override
                    public Boolean testLogin(String input) {
                          EntityManager em1 = Persistence
   	                      .createEntityManagerFactory(linkRes)
	                      .createEntityManager();
                          Collection emps1 = em1.createQuery("SELECT g FROM WebPlat g WHERE g.login = :login").setParameter("login", input).setMaxResults(1).getResultList();
                          Boolean loginExists = !emps1.isEmpty();  
                          em1.close();
                          if (loginExists) {
                                   //throw new UnsupportedOperationException("Not supported yet.");
                              return false;
                          } else {
                              return true;
                          }        
                    }

                    @Override
                    public void gotInput(String login,String pasword) {
//                        lb2.setValue(login);
                        EntityManager em2 = Persistence
                        	  .createEntityManagerFactory(linkRes)
	                          .createEntityManager();
                        Collection emps2 = em2.createQuery("SELECT g FROM WebPlat g WHERE g.idPlat = :idplat").setParameter("idplat", idplat).setMaxResults(1).getResultList();
                        Boolean platexists = !emps2.isEmpty();  
                        if (platexists) {
                           WebPlat e2 = (WebPlat) emps2.iterator().next();
                           em2.getTransaction().begin();
                           e2.setLogin(login);
                           e2.setPw(pasword);
                           em2.persist(e2);
                           em2.getTransaction().commit();
                        }
                        em2.close();
                        //throw new UnsupportedOperationException("Not supported yet.");
                    }
                }
                       ); 
               //-------------------- активизируем окно ------------------------
               //getWindow().addWindow(logSubWindow);
               getUI().addWindow(logSubWindow);
            }
       });
       chPassword.setEnabled(!pause);
       if (!goestEnabled) {
           chPassword.setEnabled(true);
       }
       panContent.addComponent(chPassword);
       pan.setContent(panContent);
    };
}
