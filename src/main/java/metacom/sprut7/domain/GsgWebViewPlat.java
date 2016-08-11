/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "GSG_WEB_VIEW_PLAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GsgWebViewPlat.findAll", query = "SELECT g FROM GsgWebViewPlat g"),
    @NamedQuery(name = "GsgWebViewPlat.findByIdPlat", query = "SELECT g FROM GsgWebViewPlat g WHERE g.idPlat = :idPlat"),
    @NamedQuery(name = "GsgWebViewPlat.findByNumDog", query = "SELECT g FROM GsgWebViewPlat g WHERE g.numDog = :numDog"),
    @NamedQuery(name = "GsgWebViewPlat.findByNampotr", query = "SELECT g FROM GsgWebViewPlat g WHERE g.nampotr = :nampotr"),
    @NamedQuery(name = "GsgWebViewPlat.findByLogin", query = "SELECT g FROM GsgWebViewPlat g WHERE g.login = :login"),
    @NamedQuery(name = "GsgWebViewPlat.findByPw", query = "SELECT g FROM GsgWebViewPlat g WHERE g.pw = :pw"),
    @NamedQuery(name = "GsgWebViewPlat.findByPause", query = "SELECT g FROM GsgWebViewPlat g WHERE g.pause = :pause"),
    @NamedQuery(name = "GsgWebViewPlat.findByAdrline", query = "SELECT g FROM GsgWebViewPlat g WHERE g.adrline = :adrline"),
    @NamedQuery(name = "GsgWebViewPlat.findByName", query = "SELECT g FROM GsgWebViewPlat g WHERE g.name = :name"),
    @NamedQuery(name = "GsgWebViewPlat.findByDatelastraport", query = "SELECT g FROM GsgWebViewPlat g WHERE g.datelastraport = :datelastraport"),
//    @NamedQuery(name = "GsgWebViewPlat.findByDateBeg", query = "SELECT g FROM GsgWebViewPlat g WHERE g.dateBeg = :dateBeg"),
//    @NamedQuery(name = "GsgWebViewPlat.findByDateEnd", query = "SELECT g FROM GsgWebViewPlat g WHERE g.dateEnd = :dateEnd"),
    @NamedQuery(name = "GsgWebViewPlat.findByRaportEnabled", query = "SELECT g FROM GsgWebViewPlat g WHERE g.raportEnabled = :raportEnabled"),

    @NamedQuery(name = "GsgWebViewPlat.findByYea", query = "SELECT g FROM GsgWebViewPlat g WHERE g.yea = :yea"),
    @NamedQuery(name = "GsgWebViewPlat.findByMon", query = "SELECT g FROM GsgWebViewPlat g WHERE g.mon = :mon"),
    @NamedQuery(name = "GsgWebViewPlat.findByDtLock", query = "SELECT g FROM GsgWebViewPlat g WHERE g.dtLock = :dtLock"),
    @NamedQuery(name = "GsgWebViewPlat.findByMess", query = "SELECT g FROM GsgWebViewPlat g WHERE g.mess = :mess"),
    
    @NamedQuery(name = "GsgWebViewPlat.findByPuthnsschets", query = "SELECT g FROM GsgWebViewPlat g WHERE g.puthnsschets = :puthnsschets")})
public class GsgWebViewPlat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "ID_PLAT")
    @Id
    private Integer idPlat;
    @Size(max = 6)
    @Column(name = "NUM_DOG")
    private String numDog;
    @Size(max = 40)
    @Column(name = "NAMPOTR")
    private String nampotr;
    @Size(max = 40)
    @Column(name = "LOGIN")
    private String login;
    @Size(max = 40)
    @Column(name = "PW")
    private String pw;
    @Column(name = "PAUSE")
    private Integer pause;
    @Size(max = 200)
    @Column(name = "ADRLINE")
    private String adrline;
    @Size(max = 30)
    @Column(name = "NAME")
    private String name;
    @Column(name = "DATELASTRAPORT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datelastraport;
//    @Column(name = "DATE_BEG")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date dateBeg;
//    @Column(name = "DATE_END")
//    @Temporal(TemporalType.TIMESTAMP)
//    private Date dateEnd;
    @Column(name = "RAPORT_ENABLED")
    private Integer raportEnabled;
    
    @Column(name = "YEA")
    private Integer yea;
    
    @Column(name = "MON")
    private Integer mon;
    
    @Column(name = "DT_LOCK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtLock;
    
    @Size(max = 50)
    @Column(name = "MESS")
    private String mess;
    
    @Size(max = 50)
    @Column(name = "PUTH_NS_SCHETS")
    private String puthnsschets;
    
    @Size(max = 10)
    @Column(name = "PARENT_EDRPOU_COD")
    private String parendedrpoucod;
    
    @Size(max = 10)
    @Column(name = "EDRPOU_COD")
    private String edrpoucod;

    @Size(max = 100)
    @Column(name = "URID_ADRESS")
    private String uridadress;
    
    @Size(max = 12)
    @Column(name = "NOMSPEN")
    private String nomspen;
        
    @Size(max = 70)
    @Column(name = "WWWADR")
    private String wwwadr;
        
    public GsgWebViewPlat() {
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public String getNumDog() {
        return numDog;
    }

    public void setNumDog(String numDog) {
        this.numDog = numDog;
    }

    public String getNampotr() {
        return nampotr;
    }

    public void setNampotr(String nampotr) {
        this.nampotr = nampotr;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public Integer getPause() {
        return pause;
    }

    public void setPause(Integer pause) {
        this.pause = pause;
    }

    public String getAdrline() {
        return adrline;
    }

    public void setAdrline(String adrline) {
        this.adrline = adrline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDatelastraport() {
        return datelastraport;
    }

    public void setDatelastraport(Date datelastraport) {
        this.datelastraport = datelastraport;
    }

//    public Date getDateBeg() {
//        return dateBeg;
//    }

//    public void setDateBeg(Date dateBeg) {
//        this.dateBeg = dateBeg;
//    }

//    public Date getDateEnd() {
//        return dateEnd;
//    }

//    public void setDateEnd(Date dateEnd) {
//        this.dateEnd = dateEnd;
//    }

    public Integer getRaportEnabled() {
        return raportEnabled;
    }
    
    public void setRaportEnabled(Integer raportEnabled) {
        this.raportEnabled = raportEnabled;
    }

    public Integer getYea() {
        return yea;
    }
    
    public void setYea(Integer yea) {
        this.yea = yea;
    }
    
    public Integer getMon() {
        return mon;
    }
    
    public void setMon(Integer mon) {
        this.mon = mon;
    }
    
    public Date getDtLock() {
        return dtLock;
    }
    
    public void setDtLock(Date dtLock) {
        this.dtLock = dtLock;
    }
    
    public String getMess() {
        return mess;
    }
    
    public void setMess(String mess) {
        this.mess = mess;
    }

    public String getPuthnsschets() {
        return puthnsschets;
    }

    public void setPuthnsschets(String puthnsschets) {
        this.puthnsschets = puthnsschets;
    }

    public String getParendEdrpouCod(){
        return parendedrpoucod;
    }
    public void setParendEdrpouCod(String parendedrpoucod){
        this.parendedrpoucod = parendedrpoucod;
    }
    
    public String getEdrpouCod(){
        return edrpoucod;
    }
    public void setEdrpouCod(String edrpoucod){
        this.edrpoucod = edrpoucod;
    }
    
    public String getUridAdress(){
        return uridadress;
    }
    public void setUridAdress(String uridadress){
        this.uridadress = uridadress;
    }
    
    public String getNomspen(){
        return nomspen;
    };
    public void setNomspen(String nomspen){
        this.nomspen = nomspen;
    }

    public String getWwwadr(){
        return wwwadr;
    }
    public void setWwwadr(String wwwadr){
        this.wwwadr = wwwadr;
    }
}
