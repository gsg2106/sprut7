/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.beans;

//import com.mycompany.maven_sprut.SprutVaadinApplication;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import metacom.sprut7.ReportsUI;
import metacom.sprut7.domain.GsgWebViewPlat;

/**
 *
 * @author Сергей
 */
public class StatePlat {
    private Integer idPlat;
    private String  namePlat;
    private String  adres;
    private Integer raportEnabled;
    private String  mess;
    private Boolean goest;
    private String  state;
    private Integer yearRaport;
    private Integer monthRaport;
    private String  numdog;
    private Integer yeaNew;
    private Integer monNew;
    private Date    dtLock;
    private Integer pause;
    private String tested;
    private String linkRes;
    private String linkPool;
    
    public Integer getIdPlat(){
        return idPlat;
    }
    public void setIdPlat(Integer idPlat){
        this.idPlat = idPlat;
    }
    
    public String getNamePlat(){
        return namePlat;
    }
    public void setNamePlat(String namePlat){
        this.namePlat = namePlat;
    }
    
    public String getAdres(){
        return adres;
    }
    public void setAdres(String adres){
        this.adres = adres;
    }
    
    public Integer getRaportEnabled(){
        return raportEnabled;
    }
    public void setRaportEnabled(Integer raportEnabled){
        this.raportEnabled = raportEnabled;
    }
    
    public String getMess(){
        return mess;
    }
    public void setMess(String mess){
        this.mess = mess;
    }
    
    public Boolean getGoest(){
        return goest;
    }
    public void setGoest(Boolean goest){
        this.goest = goest;
    }
    
    public String getState(){
        return state;
    }
    public void setState(String state){
        this.state = state;
    }

    public Integer getYearRaport(){
        if (yearRaport == null){
            return 0;
        } else {
            return yearRaport;
        }
    }
    public void setYearRaport(Integer yearRaport){
        this.yearRaport = yearRaport;
    }

    public Integer getMonthRaport(){
        if (monthRaport == null){
            return 0;
        } else {
            return monthRaport;
        }
    }
    public void setMonthRaport(Integer monthRaport){
        this.monthRaport = monthRaport;
    }
    
    public String getNumdog(){
        return numdog;
    }
    public void setNumdog(String numDog){
        this.numdog = numDog;
    }

    public Integer getYeaNew(){
        return yeaNew;
    }
    public void setYeaNew(Integer yeaNew){
        this.yeaNew = yeaNew;
    }

    public Integer getMonNew(){
        return monNew;
    }
    public void setMonNew(Integer monNew){
        this.monNew = monNew;
    }

    public Date getDtLock(){
        return dtLock;
    }
    public void setDtLock(Date dtLock){
        this.dtLock = dtLock;
    }

    public Integer getPause(){
        return pause;
    }
    public void setPause(Integer pause){
        this.pause = pause;
    }
    
    public String getTested(){
        return tested;
    }
    public void setTested(String tested){
        this.tested = tested;
    }
    
    public String getLinkRes(){
        return linkRes;
    }
    public void setLinkRes(String linkRes){
        this.linkRes = linkRes;
    }
    public String getLinkPool(){
        return linkPool;
    }
    public void setLinkPool(String linkPool){
        this.linkPool = linkPool;
    }
    
    public void refreshState(){
        //linkRes = SprutVaadinApplication.getInstance().getLinkRes();
        //linkRes = ((ReportsUI) UI.getCurrent()).getLink();
        //linkRes =(String) VaadinSession.getCurrent().getAttribute("linkRes");
        //StatePlat statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        //linkRes = statePlat.getLinkRes();

        
        EntityManager em = Persistence
 	.createEntityManagerFactory(this.linkRes)
	.createEntityManager();
        Collection emps = em.createQuery("SELECT g FROM GsgWebViewPlat g WHERE g.idPlat = :idplat ").setParameter("idplat", this.idPlat).setMaxResults(1).getResultList();
        Boolean platexists = !emps.isEmpty();  
        if (platexists) {
            GsgWebViewPlat e = (GsgWebViewPlat) emps.iterator().next();
            setYearRaport(getYearRaport(e.getDatelastraport()));
            setMonthRaport(getMonthRaport(e.getDatelastraport()));
            setPause(e.getPause());
            setYeaNew(e.getYea());
            setMonNew(e.getMon());
            setRaportEnabled(e.getRaportEnabled());
            setMess(e.getMess());
            setDtLock(e.getDtLock());
        };
        em.close();          
    }
    
    private Integer getYearRaport(Date datelastraport){
        if (datelastraport == null) {
            return null;
        }
        Calendar calendar = GregorianCalendar.getInstance();
	calendar.setTime(datelastraport);
//	int currentYear = calendar.get(Calendar.YEAR);
//      return datelastraport. getYear();
      return calendar.get(Calendar.YEAR);
  }
  private Integer getMonthRaport(Date datelastraport){
        if (datelastraport == null) {
            return null;
        }
        Calendar calendar = GregorianCalendar.getInstance();
	calendar.setTime(datelastraport);
//      return datelastraport.getMonth();
      return calendar.get(Calendar.MONTH)+1;
  }
    
}
