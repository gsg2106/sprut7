/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "GSG_WEB_VIEW_PU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GsgWebViewPu.findAll", query = "SELECT g FROM GsgWebViewPu g"),
    @NamedQuery(name = "GsgWebViewPu.findById", query = "SELECT g FROM GsgWebViewPu g WHERE g.id = :id"),
    @NamedQuery(name = "GsgWebViewPu.findByTypecnt", query = "SELECT g FROM GsgWebViewPu g WHERE g.typecnt = :typecnt"),
    @NamedQuery(name = "GsgWebViewPu.findByZavno", query = "SELECT g FROM GsgWebViewPu g WHERE g.zavno = :zavno"),
    @NamedQuery(name = "GsgWebViewPu.findByNTyps", query = "SELECT g FROM GsgWebViewPu g WHERE g.nTyps = :nTyps"),
    @NamedQuery(name = "GsgWebViewPu.findByNamescale", query = "SELECT g FROM GsgWebViewPu g WHERE g.namescale = :namescale"),
    @NamedQuery(name = "GsgWebViewPu.findByKtr", query = "SELECT g FROM GsgWebViewPu g WHERE g.ktr = :ktr"),
    @NamedQuery(name = "GsgWebViewPu.findByZnachn", query = "SELECT g FROM GsgWebViewPu g WHERE g.znachn = :znachn"),
    @NamedQuery(name = "GsgWebViewPu.findByDateGos", query = "SELECT g FROM GsgWebViewPu g WHERE g.dateGos = :dateGos"),
    @NamedQuery(name = "GsgWebViewPu.findByIdPutu", query = "SELECT g FROM GsgWebViewPu g WHERE g.idPutu = :idPutu"),
    @NamedQuery(name = "GsgWebViewPu.findByIdPlat", query = "SELECT g FROM GsgWebViewPu g WHERE g.idPlat = :idPlat"),
    @NamedQuery(name = "GsgWebViewPu.findByIdTu", query = "SELECT g FROM GsgWebViewPu g WHERE g.idTu = :idTu"),
    @NamedQuery(name = "GsgWebViewPu.findByEng", query = "SELECT g FROM GsgWebViewPu g WHERE g.eng = :eng"),
    @NamedQuery(name = "GsgWebViewPu.findByIdScale", query = "SELECT g FROM GsgWebViewPu g WHERE g.idScale = :idScale"),
    @NamedQuery(name = "GsgWebViewPu.findByKontrp", query = "SELECT g FROM GsgWebViewPu g WHERE g.kontrp = :kontrp"),
    @NamedQuery(name = "GsgWebViewPu.findByKontrpDt", query = "SELECT g FROM GsgWebViewPu g WHERE g.kontrpDt = :kontrpDt")})
public class GsgWebViewPu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 30)
    @Column(name = "ID")
    @Id
    private String id;
    @Column(name = "TYPECNT")
    private Short typecnt;
    @Size(max = 20)
    @Column(name = "ZAVNO")
    private String zavno;
    @Size(max = 34)
    @Column(name = "N_TYPS")
    private String nTyps;
    @Size(max = 20)
    @Column(name = "NAMESCALE")
    private String namescale;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "KTR")
    private BigDecimal ktr;
    @Size(max = 14)
    @Column(name = "ZNACHN")
    private String znachn;
    @Column(name = "DATE_GOS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateGos;
    @Column(name = "ID_PUTU")
    private Integer idPutu;
    @Column(name = "ID_PLAT")
    private Integer idPlat;
    @Column(name = "ID_TU")
    private Integer idTu;
    @Column(name = "ENG")
    private Character eng;
    @Column(name = "ID_SCALE")
    private Integer idScale;
    @Size(max = 14)
    @Column(name = "KONTRP")
    private String kontrp;
    @Column(name = "KONTRP_DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date kontrpDt;
    @Transient
    private String kontrpDtFomat;
    @Transient
    private String dateGosFomat;
            
    public GsgWebViewPu() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Short getTypecnt() {
        return typecnt;
    }

    public void setTypecnt(Short typecnt) {
        this.typecnt = typecnt;
    }

    public String getZavno() {
        return zavno;
    }

    public void setZavno(String zavno) {
        this.zavno = zavno;
    }

    public String getNTyps() {
        return nTyps;
    }

    public void setNTyps(String nTyps) {
        this.nTyps = nTyps;
    }

    public String getNamescale() {
        return namescale;
    }

    public void setNamescale(String namescale) {
        this.namescale = namescale;
    }

    public BigDecimal getKtr() {
        return ktr;
    }

    public void setKtr(BigDecimal ktr) {
        this.ktr = ktr;
    }

    public String getZnachn() {
        return znachn;
    }

    public void setZnachn(String znachn) {
        this.znachn = znachn;
    }

    public Date getDateGos() {
        return dateGos;
    }

    public void setDateGos(Date dateGos) {
        this.dateGos = dateGos;
    }

    public Integer getIdPutu() {
        return idPutu;
    }

    public void setIdPutu(Integer idPutu) {
        this.idPutu = idPutu;
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

    public String getKontrp() {
        return kontrp;
    }

    public void setKontrp(String kontrp) {
        this.kontrp = kontrp;
    }

    public Date getKontrpDt() {
        return  kontrpDt ;
    }

    public void setKontrpDt(Date kontrpDt) {
        this.kontrpDt = kontrpDt;
    }

    public String getKontrpDtFomat() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String s;
        if (kontrpDt != null){
            s = df.format(kontrpDt);
        } else s="";    
        return s;  //df.format(kontrpDt)  ;
    }

    public void setKontrpDtFormat(String kontrpDtFomat) {
        this.kontrpDtFomat = kontrpDtFomat; 
    }

    public String getDateGosFomat() {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String s;
        if (dateGos != null){
            s = df.format(dateGos);
        } else s="";    
        return s;  //df.format(kontrpDt)  ;
    }

    public void setDateGosFomat(String dateGosFomat) {
        this.dateGosFomat = dateGosFomat; 
    }
    
}
