/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.beans;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import metacom.sprut7.domain.GsgWebGoest;
import metacom.sprut7.domain.GsgWebViewPlat;
import metacom.sprut7.domain.WebPlat;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author Сергей
 */
@Component
@Scope("prototype")
public class LoadPlatImpl implements LoadPlat{
  private Boolean platexists;
  private Integer idplat;
  private String nameplat;
  private String adres;
  private String state;
  private Date datelastraport;
  private String login_;
  private String password;
  private String numdog;
  private Integer pause;
//  private Date datebegin;
//  private Date dateend;
  private Integer raportenabled;
  private String puthnsschets;
  private Integer yea;
  private Integer mon;
  private Date dtLock;
  private String mess;
  private Boolean goest;
  
  public LoadPlatImpl(){
  }
  
  @Override  
  public void init(String login, Boolean goest, String tested, String linkRes ){
     login_ = login;
     this.goest = goest;
     if (!goest) {
        // рабочий режим
        EntityManager em = Persistence
 	   .createEntityManagerFactory(linkRes)
      	   .createEntityManager();
        Collection emps = em.createQuery("SELECT g FROM GsgWebViewPlat g WHERE g.login = :login ").setParameter("login", login).setMaxResults(1).getResultList();
        platexists = !emps.isEmpty();  
        if (platexists) {
            GsgWebViewPlat e = (GsgWebViewPlat) emps.iterator().next();
            idplat= e.getIdPlat();
            nameplat = e.getNampotr();
            adres = e.getAdrline();
            state = e.getName();
            datelastraport = e.getDatelastraport();
            password = e.getPw();
            numdog = e.getNumDog();
            pause = e.getPause();
            raportenabled = e.getRaportEnabled();
            yea = e.getYea();
            mon = e.getMon();
            dtLock = e.getDtLock();
            mess = e.getMess();
            puthnsschets = e.getPuthnsschets();
        } else {
            idplat=null;
            nameplat=null;
            adres=null;
            state=null;
            datelastraport = null;
            password = null;
            numdog = null;
            pause = null;
            raportenabled = null;
            puthnsschets = null;
        }
        em.close();      
     } else {
         // гостевой режим
        EntityManager em = Persistence
 	   .createEntityManagerFactory(linkRes)
      	   .createEntityManager();
        Collection emps = em.createQuery("SELECT g FROM GsgWebGoest g WHERE g.login = :login ").setParameter("login", login).setMaxResults(1).getResultList();
        platexists = !emps.isEmpty();  
        if (platexists) {
            GsgWebGoest e = (GsgWebGoest) emps.iterator().next();
            idplat= e.getIdPlat();
            nameplat = e.getNampotr();
            adres = e.getAdrline();
            state = e.getName();
            datelastraport = GregorianCalendar.getInstance().getTime();
            Double pw = e.getPw();
            Long p = null;
            if (pw !=null) {
               p = (Math.round(e.getPw()*100))/100;
               password = p.toString();
            }   
            //Integer p = e.getPw().intValue();
            numdog = e.getNumDog();
            pause = 1;
            raportenabled = null;
            puthnsschets = null;
            yea = null;
            mon = null;
            dtLock = null;
            mess = null;
            em.close();      
        } else {
            idplat=null;
            nameplat=null;
            adres=null;
            state=null;
            datelastraport = null;
            password = null;
            numdog = null;
            pause = null;
            raportenabled = null;
            puthnsschets = null;
            em.close();      
            return;
        }
        
        if (tested.equals("1")){
            EntityManager em2 = Persistence
              	  .createEntityManagerFactory(linkRes)
                  .createEntityManager();
            Collection emps2 = em2.createQuery("SELECT g FROM WebPlat g WHERE g.idPlat = :idplat").setParameter("idplat", idplat).setMaxResults(1).getResultList();
            Boolean platexists = !emps2.isEmpty();  
            if (!platexists) {
                //WebPlat e2 = (WebPlat) emps2.iterator().next();
                Date dt = new java.util.Date();
                final Calendar calendar = GregorianCalendar.getInstance();
          	calendar.setTime(dt);
                em2.getTransaction().begin();
                WebPlat e2 = new WebPlat();
                e2.setIdPlat(idplat);
                e2.setYeaStart(calendar.get(java.util.Calendar.YEAR));
                e2.setMonStart(calendar.get(java.util.Calendar.MONTH)+1);
                e2.setLogin(login);
                e2.setDtAdd(dt);
                e2.setPause(new Integer(0)); // потому что работает только в тестовор режиме
                //e2.setPw(password);
                emps2.add(e2);
                em2.persist(e2);
                em2.getTransaction().commit();
            }
            em2.close();
//дубликат -----------------------------------------
           EntityManager em3 = Persistence
    	      .createEntityManagerFactory(linkRes)
      	      .createEntityManager();
//           Collection emps3 = em3.createQuery("SELECT g FROM GsgWebViewPlat g WHERE g.login = :login ").setParameter("login", login).setMaxResults(1).getResultList();
           Collection emps3 = em3.createQuery("SELECT g FROM GsgWebViewPlat g WHERE g.idPlat = :idplat ").setParameter("idplat", idplat).setMaxResults(1).getResultList();
           platexists = !emps3.isEmpty();  
           if (platexists) {
               GsgWebViewPlat e3 = (GsgWebViewPlat) emps3.iterator().next();
               idplat= e3.getIdPlat();
               nameplat = e3.getNampotr();
               adres = e3.getAdrline();
               state = e3.getName();
               datelastraport = e3.getDatelastraport();
               password = e3.getPw();
               numdog = e3.getNumDog();
               pause = e3.getPause();
               raportenabled = e3.getRaportEnabled();
               yea = e3.getYea();
               mon = e3.getMon();
               dtLock = e3.getDtLock();
               mess = e3.getMess();
               puthnsschets = e3.getPuthnsschets();
           } else {
               idplat=null;
               nameplat=null;
               adres=null;
               state=null;
               datelastraport = null;
               password = null;
               numdog = null;
               pause = null;
               raportenabled = null;
               puthnsschets = null;
           }
           em3.close();      
//--------------------------------------------------            
        }
        
     }
  } 

  @Override  
  public Boolean PlatIsFound(){
        return platexists;
  }
  
  @Override  
  public Integer getIdplat(){
      return  idplat;
  }
  
  @Override  
  public String getNameplat(){
      return nameplat;
  }
  
  @Override  
  public String getAdres(){
      return adres;
  }
  
  @Override  
  public String getState(){
      return state;
  }
  
  @Override  
  public Integer getYearRaport(){
        if (datelastraport == null) {
            return null;
        }
        Calendar calendar = GregorianCalendar.getInstance();
	calendar.setTime(datelastraport);
//	int currentYear = calendar.get(Calendar.YEAR);
//      return datelastraport. getYear();
      return calendar.get(Calendar.YEAR);
  }
  
  @Override  
  public Integer getMonthRaport(){
        if (datelastraport == null) {
            return null;
        }
        Calendar calendar = GregorianCalendar.getInstance();
	calendar.setTime(datelastraport);
//      return datelastraport.getMonth();
      return calendar.get(Calendar.MONTH)+1;
  }
          
  @Override  
  public String getNumDog(){
      return numdog;
  }
          
  @Override  
  public String getLogin(){
      return login_;
  }
  
  @Override  
  public String getPassword(){
         return  password;
  }
  
  @Override  
  public Integer getPause(){
      return pause;
  }
  

  @Override  
  public Integer getRaportEnabled(){
      return raportenabled;
  }

  @Override  
  public Integer getYea(){
      return yea;
  }

  @Override  
  public Integer getMon(){
      return mon;
  }

  @Override  
  public Date getDtLock(){
      return dtLock;
  }

  @Override  
  public String getMess(){
      return mess;
  }
  
  @Override  
  public String getPuthNsSchets(){
      return  puthnsschets;
}
}
