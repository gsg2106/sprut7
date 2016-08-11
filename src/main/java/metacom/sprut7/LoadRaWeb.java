/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Notification;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.domain.RaWeb;

/**
 *
 * @author Сергей
 */
public class LoadRaWeb {
   private Boolean platexists;
   private Integer idplat;
   private Integer curridplat;
   private Integer state;
   private Character locked;
   private String messprepare;
   private String messschet;
   private Character prepared;
   private EntityManager em;
   private RaWeb e;
   private Character YesI = new Character('1');
   private Character NoI = new Character('0');
   private Character requestBlank = new Character('2');
   private Date timeLock;
   private Date timePrepare;
   private String messPrepare;
   private String messSchet;
   private Integer mon;
   private Integer yea;
   private String linkRes;
   private Character reqRecalc;
   private StatePlat statePlat;
   
public LoadRaWeb(Integer idplat){
// работает    SprutVaadinApplication.getInstance().getMainWindow().showNotification("проверка");                 
    this.idplat = idplat;
    //linkRes =(String) VaadinSession.getCurrent().getAttribute("linkRes");
    statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
    linkRes = statePlat.getLinkRes();
     em = Persistence
 	.createEntityManagerFactory(linkRes)
	.createEntityManager();
     Collection emps = em.createQuery("SELECT g FROM RaWeb g WHERE g.idPlat = :idplat ").setParameter("idplat", this.idplat).setMaxResults(1).getResultList();
     platexists = !emps.isEmpty();  
     if (platexists) {
         e = (RaWeb) emps.iterator().next();
         state = e.getStateSchet();
         locked = e.getLocked();
         messprepare = e.getMessPrepare();
         messschet = e.getMessSchet();
         prepared = e.getPrepared();
         timeLock = e.getTimeLock();
         timePrepare = e.getTimePrepare();
         messPrepare = e.getMessPrepare();
         messSchet = e.getMessSchet();
         mon = e.getMon();
         yea =e.getYea();
         curridplat = e.getIdPlat();
         reqRecalc = e.getReqRecalc();
     } else {
         state = null;
         locked = null;
         messprepare = null;
         messschet = null;
         prepared = null;
         timeLock = null;
         timePrepare = null;
         messPrepare = null;
         messSchet = null;
         yea = null;
         mon = null;
         curridplat = this.idplat;
         reqRecalc = null;
         Notification.show("Плательщик не найден");                 
         //getApplication().getMainWindow()         
         
     }
  //        System.out.println("--------Object--------");                        
}    

public void SendQueryBlank(Integer yeaNew,Integer monNew){
    //this.yea = yeaNew;
    //this.mon = monNew;
    em.refresh(e);

//  if (!(e.getReqRecalc().equals(requestBlank)) && e.getMon().equals(monNew) && e.getYea().equals(yeaNew)) {
    if ((e.getMon() == null) || (e.getYea() == null)
          || (!(e.getReqRecalc().equals(requestBlank)) && e.getMon().equals(monNew) && e.getYea().equals(yeaNew))
          || ( (yeaNew.intValue() *12) + monNew.intValue() > (e.getYea().intValue()*12) + e.getMon().intValue() )
       )  {
        em.getTransaction().begin();
        e.setReqRecalc( NoI );
        e.setYea(yeaNew);
        e.setMon(monNew);
        e.setLocked(new Character('0'));
        e.setStateSchet(0);
        em.persist(e);
        em.getTransaction().commit();
        em.getTransaction().begin();
        e.setYea(yeaNew);
        e.setMon(monNew);
        e.setReqRecalc( requestBlank );
        em.persist(e);
        em.getTransaction().commit();
    }    
//          System.out.println("--------end--------");                        
    
}

public void SendQuery(Integer yeaNew,Integer monNew){
//          System.out.println("--------Start--------");                        
    em.refresh(e);
    em.getTransaction().begin();
        e.setYea(yeaNew);
        e.setMon(monNew);
    e.setReqRecalc( NoI );
    e.setLocked(new Character('0'));
    e.setStateSchet(0);
    em.persist(e);
    em.getTransaction().commit();
    em.getTransaction().begin();
        e.setYea(yeaNew);
        e.setMon(monNew);
    e.setReqRecalc( YesI );
    em.persist(e);
    em.getTransaction().commit();
//          System.out.println("--------end--------");                        
    
}

public Boolean getCanRecalc(){
    Boolean res = false;
    if (e == null){
           return  false;
    } else {
       if (e.getStateSchet() != null) {
          if ( e.getStateSchet() <= 1){
              res = true;
          } else {
              res = false;
          }
       }
    }
    
    return res & getPrepared() & !getLocked();
}
public Boolean getOnReady(){
 //         System.out.println("--------1111--------");                            Boolean res = false;
    Boolean res = false;
    if (e.getStateSchet() == null) 
        return false;
    if (e.getStateSchet() == 1){
        res = true;
    } else {
        res = false;
    }
 //         System.out.println("--------end--------");                            return res;
    return res;
}

public Boolean getRefreshOnReady(){
    Boolean res = false;
    em.refresh(e);
         state = e.getStateSchet();
         locked = e.getLocked();
         messprepare = e.getMessPrepare();
         messschet = e.getMessSchet();
         prepared = e.getPrepared();
         timeLock = e.getTimeLock();
         timePrepare = e.getTimePrepare();
         messPrepare = e.getMessPrepare();
         messSchet = e.getMessSchet();
         mon = e.getMon();
         yea =e.getYea();
         curridplat = e.getIdPlat();
         reqRecalc = e.getReqRecalc();
 
    if (e.getStateSchet() == null)
       return  false;  
    if (e.getStateSchet() == 1){
        res = true;
    } else {
        res = false;
    }
    return res;
}


public Boolean getRefreshOnPrepared(){
    Boolean res = false;
    em.refresh(e);
         state = e.getStateSchet();
         locked = e.getLocked();
         messprepare = e.getMessPrepare();
         messschet = e.getMessSchet();
         prepared = e.getPrepared();
         timeLock = e.getTimeLock();
         timePrepare = e.getTimePrepare();
         messPrepare = e.getMessPrepare();
         messSchet = e.getMessSchet();
         mon = e.getMon();
         yea =e.getYea();
         curridplat = e.getIdPlat();
         reqRecalc  = e.getReqRecalc();
    
    if (e.getStateSchet() == null)
       return  false;  
    if (e.getStateSchet() == 0){
        res = true;
    } else {
        res = false;
    }
    return res;
}

public Boolean getLocked1(){
    return locked.equals(YesI);
}
public Boolean getPrepared(){
    // плательщик превварительно расчитан
    Boolean res = false;
    if (prepared.equals(YesI)){
        res = true;
    }
    return res;
}

public Boolean getLocked(){
//    if ((timePrepare == null) | (timeLock == null)) {
    if  (timeLock == null) {
        return false;
    } else {
       Date dt = GregorianCalendar.getInstance().getTime();
       Boolean ret;
//       if ((dt.getTime() >= timePrepare.getTime() ) && (dt.getTime() <= timeLock.getTime())){
       if  (dt.getTime() <= timeLock.getTime()){
           ret = false;
       } else
           ret = true;
       return ret; 
    }    
}

public String getMessPrepare(){
    return messPrepare;
}

public String getMessSchet(){
    return messSchet;
}

public Integer getYea(){
    return yea;
}

public Integer getMon(){
    return mon;
}

public Character getReqRecalc(){
   return reqRecalc;
}

public void Close(){
    em.close();
}
}

