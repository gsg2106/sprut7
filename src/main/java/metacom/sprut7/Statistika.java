/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metacom.sprut7;

import com.vaadin.server.VaadinSession;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.domain.GsgWebViewGetkeyStatist;
import metacom.sprut7.domain.WebStatist;

/**
 *
 * @author Сергей
 */
public class Statistika {
    public static short st_login   = 1;
    public static short st_input   = 2;
    public static short st_ECP     = 3;
    public static short st_doc_sch = 4;
    public static short st_lic     = 5;
    public static short st_tu      = 6;
    public static short st_lkab    = 7;
    public static short st_dolg    = 8;
    public static short st_doc_nn  = 9;
    public static short ST_HIST_NN = 10;
    
    private String linkRes=null;
    private Date dt = new Date();
    private StatePlat statePlat;
    
    public void Statistika(){
    }
    public void init(Integer idplat,short idVidActivn){
        if (linkRes == null){
            statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
            linkRes = statePlat.getLinkRes();
            //linkRes =(String) VaadinSession.getCurrent().getAttribute("linkRes");
        }    
            //linkRes = SprutVaadinApplication.getInstance().getLinkRes(); 
        WebStatist e;
        //dt = java.util.Calendar.getInstance().getTime(); 
        
        final Calendar calendar = GregorianCalendar.getInstance();
	calendar.setTime(dt);
        int y = calendar.get(Calendar.YEAR);
        int m = calendar.get(Calendar.MONTH);
        int d = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.clear();
        calendar.set(Calendar.YEAR,y);
        calendar.set(Calendar.MONTH,m);
        calendar.set(Calendar.DAY_OF_MONTH,d);
        dt = calendar.getTime();

        Integer NewID=null;
        EntityManager em = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
        Date dt1 = new Date(); Date dt2 = new Date();
        long l = dt.getTime();
        dt1.setTime(l-2);
        dt2.setTime(l+2);
        Collection emps = em.createQuery("SELECT g FROM WebStatist g WHERE (g.idPlat = :idplat) and (g.dt between :dt1 AND :dt2) and (g.idVidActivn = :idVidActivn)").
                 setParameter("idplat", idplat).
                 setParameter("dt1", dt1).
                 setParameter("dt2", dt2).
                 setParameter("idVidActivn",idVidActivn).
                setMaxResults(1).getResultList();
        Boolean recordExists = !emps.isEmpty();  
        em.getTransaction().begin();
        if (recordExists) {
            Iterator it = emps.iterator();
            e = (WebStatist) it.next();
            e.setCount(e.getCount()+1);
        } else{
           EntityManager emKey = Persistence
        	   .createEntityManagerFactory(linkRes)
        	   .createEntityManager();
//           Collection empsKey = em.createQuery("SELECT g FROM GsgWebViewGetkeyStatist g WHERE 1=1").
           Collection empsKey = emKey.createQuery("SELECT g FROM GsgWebViewGetkeyStatist g").
                   getResultList();
//                 setMaxResults(1).getResultList();
           Boolean keyExists = !empsKey.isEmpty();  
           if (keyExists) {
              Iterator itKey = empsKey.iterator();
              GsgWebViewGetkeyStatist eKey = (GsgWebViewGetkeyStatist) itKey.next();
              NewID = eKey.getId();
           }
           emKey.close();
            e = new WebStatist();
            e.setIdPlat(idplat);
            e.setCount(1);
            e.setDt(dt);
            e.setIdVidActivn(idVidActivn);
            e.setIdStatist(NewID);
            emps.add(e);
        }
        em.persist(e);
        em.getTransaction().commit();
        em.close();
    }
    
}
