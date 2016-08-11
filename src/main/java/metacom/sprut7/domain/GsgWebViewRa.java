/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "GSG_WEB_VIEW_RA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GsgWebViewRa.findAll", query = "SELECT g FROM GsgWebViewRa g"),
    @NamedQuery(name = "GsgWebViewRa.findById", query = "SELECT g FROM GsgWebViewRa g WHERE g.id = :id"),
    @NamedQuery(name = "GsgWebViewRa.findByIdPlat", query = "SELECT g FROM GsgWebViewRa g WHERE g.idPlat = :idPlat"),
    @NamedQuery(name = "GsgWebViewRa.findByIdTu", query = "SELECT g FROM GsgWebViewRa g WHERE g.idTu = :idTu"),
    @NamedQuery(name = "GsgWebViewRa.findByCodtu", query = "SELECT g FROM GsgWebViewRa g WHERE g.codtu = :codtu"),
    @NamedQuery(name = "GsgWebViewRa.findByEng", query = "SELECT g FROM GsgWebViewRa g WHERE g.eng = :eng"),
    @NamedQuery(name = "GsgWebViewRa.findByNump", query = "SELECT g FROM GsgWebViewRa g WHERE g.nump = :nump"),
    @NamedQuery(name = "GsgWebViewRa.findByKtr", query = "SELECT g FROM GsgWebViewRa g WHERE g.ktr = :ktr"),
    @NamedQuery(name = "GsgWebViewRa.findByNachalo", query = "SELECT g FROM GsgWebViewRa g WHERE g.nachalo = :nachalo"),
    @NamedQuery(name = "GsgWebViewRa.findByCodZone", query = "SELECT g FROM GsgWebViewRa g WHERE g.codZone = :codZone"),
    @NamedQuery(name = "GsgWebViewRa.findByDanrap", query = "SELECT g FROM GsgWebViewRa g WHERE g.danrap = :danrap"),
    @NamedQuery(name = "GsgWebViewRa.findByKvtchrazn", query = "SELECT g FROM GsgWebViewRa g WHERE g.kvtchrazn = :kvtchrazn"),
    @NamedQuery(name = "GsgWebViewRa.findByRazn", query = "SELECT g FROM GsgWebViewRa g WHERE g.razn = :razn"),
    @NamedQuery(name = "GsgWebViewRa.findByPott", query = "SELECT g FROM GsgWebViewRa g WHERE g.pott = :pott"),
    @NamedQuery(name = "GsgWebViewRa.findByPotl", query = "SELECT g FROM GsgWebViewRa g WHERE g.potl = :potl"),
    @NamedQuery(name = "GsgWebViewRa.findByYea", query = "SELECT g FROM GsgWebViewRa g WHERE g.yea = :yea"),
    @NamedQuery(name = "GsgWebViewRa.findByMon", query = "SELECT g FROM GsgWebViewRa g WHERE g.mon = :mon"),
    @NamedQuery(name = "GsgWebViewRa.findByMarkZon", query = "SELECT g FROM GsgWebViewRa g WHERE g.markZon = :markZon")
//    @NamedQuery(name = "GsgWebViewRa.findByStateRecord", query = "SELECT g FROM GsgWebViewRa g WHERE g.stateRecord = :stateRecord"),
//    @NamedQuery(name = "GsgWebViewRa.findByZavno", query = "SELECT g FROM GsgWebViewRa g WHERE g.zavno = :zavno")
})
 
public class GsgWebViewRa implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 50)
    @Column(name = "ID")
    @Id
    private String id;
    @Column(name = "ID_PLAT")
    private Integer idPlat;
    @Column(name = "ID_TU")
    private Integer idTu;
    @Column(name = "CODTU")
    private Short codtu;
    @Column(name = "ENG")
    private Character eng;
    @Column(name = "NUMP")
    private Short nump;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "KTR")
    private BigDecimal ktr;
    @Size(max = 14)
    @Column(name = "NACHALO")
    private String nachalo;
    @Column(name = "COD_ZONE")
    private Character codZone;
    @Size(max = 14)
    @Column(name = "DANRAP")
    private String danrap;
    @Column(name = "KVTCHRAZN")
    private Integer kvtchrazn;
    @Size(max = 8)
    @Column(name = "RAZN")
    private String razn;
    @Column(name = "POTT")
    private Integer pott;
    @Column(name = "POTL")
    private Integer potl;
    @Column(name = "YEA")
    private Short yea;
    @Column(name = "MON")
    private Short mon;
    @Column(name = "MARK_ZON")
    private String markZon;
//    @Size(max = 15)
//    @Column(name = "STATERECORD")
//    private String stateRecord;
//    @Size(max = 20)
//    @Column(name = "ZAVNO")
//    private String zavno;

    public GsgWebViewRa() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public Integer getIdTu() {
        return idTu;
    }

    public void setIdTu(Integer idTu) {
        this.idTu = idTu;
    }

    public Short getCodtu() {
        return codtu;
    }

    public void setCodtu(Short codtu) {
        this.codtu = codtu;
    }

    public Character getEng() {
        return eng;
    }

    public void setEng(Character eng) {
        this.eng = eng;
    }

    public Short getNump() {
        return nump;
    }

    public void setNump(Short nump) {
        this.nump = nump;
    }

    public BigDecimal getKtr() {
        return ktr;
    }

    public void setKtr(BigDecimal ktr) {
        this.ktr = ktr;
    }

    public String getNachalo() {
        return nachalo;
    }

    public void setNachalo(String nachalo) {
        this.nachalo = nachalo;
    }

    public Character getCodZone() {
        return codZone;
    }

    public void setCodZone(Character codZone) {
        this.codZone = codZone;
    }

    public String getDanrap() {
        return danrap;
    }

    public void setDanrap(String danrap) {
        this.danrap = danrap;
    }

    public Integer getKvtchrazn() {
        return kvtchrazn;
    }

    public void setKvtchrazn(Integer kvtchrazn) {
        this.kvtchrazn = kvtchrazn;
    }

    public String getRazn() {
        return razn;
    }

    public void setRazn(String razn) {
        this.razn = razn;
    }

    public Integer getPott() {
        return pott;
    }

    public void setPott(Integer pott) {
        this.pott = pott;
    }

    public Integer getPotl() {
        return potl;
    }

    public void setPotl(Integer potl) {
        this.potl = potl;
    }

    public Short getYea() {
        return yea;
    }

    public void setYea(Short yea) {
        this.yea = yea;
    }

    public Short getMon() {
        return mon;
    }

    public void setMon(Short mon) {
        this.mon = mon;
    }


    public String getMarkZon() {
        return markZon;
    }

    public void setMarkZon(String markZon) {
        this.markZon = markZon;
    }

/*    
    public String getStateRecord() {
        return stateRecord;
    }

    public void setStateRecord(String stateRecord) {
        this.stateRecord = stateRecord;
    }
    
    public String getZavno() {
        return zavno;
    }

    public void setZavno(String zavno) {
        this.zavno = zavno;
    }
*/    
}
