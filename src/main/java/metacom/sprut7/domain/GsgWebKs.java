/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "GSG_WEB_KS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GsgWebKs.findAll", query = "SELECT g FROM GsgWebKs g"),
//    @NamedQuery(name = "GsgWebKs.findAll", query = "SELECT g FROM GsgWebKs g order by g.dt DESC"),
    @NamedQuery(name = "GsgWebKs.findById", query = "SELECT g FROM GsgWebKs g WHERE g.id = :id"),
    @NamedQuery(name = "GsgWebKs.findByIdPutu", query = "SELECT g FROM GsgWebKs g WHERE g.idPutu = :idPutu"),
    @NamedQuery(name = "GsgWebKs.findByEng", query = "SELECT g FROM GsgWebKs g WHERE g.eng = :eng"),
    @NamedQuery(name = "GsgWebKs.findByIdScale", query = "SELECT g FROM GsgWebKs g WHERE g.idScale = :idScale"),
    @NamedQuery(name = "GsgWebKs.findByCodZone", query = "SELECT g FROM GsgWebKs g WHERE g.codZone = :codZone"),
    @NamedQuery(name = "GsgWebKs.findByDt", query = "SELECT g FROM GsgWebKs g WHERE g.dt = :dt"),
    @NamedQuery(name = "GsgWebKs.findByKontrp", query = "SELECT g FROM GsgWebKs g WHERE g.kontrp = :kontrp"),
    @NamedQuery(name = "GsgWebKs.findByNamevid", query = "SELECT g FROM GsgWebKs g WHERE g.namevid = :namevid"),
    @NamedQuery(name = "GsgWebKs.findByNdoc", query = "SELECT g FROM GsgWebKs g WHERE g.ndoc = :ndoc"),
    @NamedQuery(name = "GsgWebKs.findByIdPlat", query = "SELECT g FROM GsgWebKs g WHERE g.idPlat = :idPlat"),
    @NamedQuery(name = "GsgWebKs.findByIdTu", query = "SELECT g FROM GsgWebKs g WHERE g.idTu = :idTu")})
public class GsgWebKs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Size(max = 50)
    @Column(name = "ID")
    private String id;
    @Column(name = "ID_PUTU")
    private Integer idPutu;
    @Column(name = "ENG")
    private Character eng;
    @Column(name = "ID_SCALE")
    private Integer idScale;
    @Column(name = "COD_ZONE")
    private Character codZone;
    @Column(name = "DT")
    @Temporal(TemporalType.TIMESTAMP)
//@OrderBy ("desc")
    private Date dt;
    @Size(max = 14)
    @Column(name = "KONTRP")
    private String kontrp;
    @Size(max = 30)
    @Column(name = "NAMEVID")
    private String namevid;
    @Size(max = 20)
    @Column(name = "NDOC")
    private String ndoc;
    @Column(name = "ID_PLAT")
    private Integer idPlat;
    @Column(name = "ID_TU")
    private Integer idTu;
    @Transient
    private String dtFormat;
    
    public GsgWebKs() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getIdPutu() {
        return idPutu;
    }

    public void setIdPutu(Integer idPutu) {
        this.idPutu = idPutu;
    }

    public Character getEng() {
        return eng;
    }

    public void setEng(Character eng) {
        this.eng = eng;
    }

    public Integer getIdScale() {
        return idScale;
    }

    public void setIdScale(Integer idScale) {
        this.idScale = idScale;
    }

    public Character getCodZone() {
        return codZone;
    }

    public void setCodZone(Character codZone) {
        this.codZone = codZone;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public String getKontrp() {
        return kontrp;
    }

    public void setKontrp(String kontrp) {
        this.kontrp = kontrp;
    }

    public String getNamevid() {
        return namevid;
    }

    public void setNamevid(String namevid) {
        this.namevid = namevid;
    }

    public String getNdoc() {
        return ndoc;
    }

    public void setNdoc(String ndoc) {
        this.ndoc = ndoc;
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPpat) {
        this.idPlat = idPlat;
    }

    public Integer getIdTu() {
        return idTu;
    }

    public void setIdTu(Integer idTu) {
        this.idTu = idTu;
    }
    

    public String getDtFormat() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String s;
        if (dt != null){
            s = df.format(dt);
        } else s="";    
        return s;  
    }

    public void setDtFormat(String dtFormat) {
        this.dtFormat = dtFormat;
    }
}
