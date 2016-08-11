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
@Table(name = "GSG_WEB_OBOROT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GsgWebOborot.findAll", query = "SELECT g FROM GsgWebOborot g"),
    @NamedQuery(name = "GsgWebOborot.findById", query = "SELECT g FROM GsgWebOborot g WHERE g.id = :id"),
    @NamedQuery(name = "GsgWebOborot.findByIdPlat", query = "SELECT g FROM GsgWebOborot g WHERE g.idPlat = :idPlat"),
    @NamedQuery(name = "GsgWebOborot.findByYea", query = "SELECT g FROM GsgWebOborot g WHERE g.yea = :yea"),
    @NamedQuery(name = "GsgWebOborot.findByMon", query = "SELECT g FROM GsgWebOborot g WHERE g.mon = :mon"),
    @NamedQuery(name = "GsgWebOborot.findByHh", query = "SELECT g FROM GsgWebOborot g WHERE g.hh = :hh"),
    @NamedQuery(name = "GsgWebOborot.findByNumDoc", query = "SELECT g FROM GsgWebOborot g WHERE g.numDoc = :numDoc"),
    @NamedQuery(name = "GsgWebOborot.findByNumSchet", query = "SELECT g FROM GsgWebOborot g WHERE g.numSchet = :numSchet"),
    @NamedQuery(name = "GsgWebOborot.findByDateDoc", query = "SELECT g FROM GsgWebOborot g WHERE g.dateDoc = :dateDoc"),
    @NamedQuery(name = "GsgWebOborot.findByDateOpl", query = "SELECT g FROM GsgWebOborot g WHERE g.dateOpl = :dateOpl"),
    @NamedQuery(name = "GsgWebOborot.findByDateCalc", query = "SELECT g FROM GsgWebOborot g WHERE g.dateCalc = :dateCalc"),
    @NamedQuery(name = "GsgWebOborot.findByYr", query = "SELECT g FROM GsgWebOborot g WHERE g.yr = :yr"),
    @NamedQuery(name = "GsgWebOborot.findByMnth", query = "SELECT g FROM GsgWebOborot g WHERE g.mnth = :mnth"),
    @NamedQuery(name = "GsgWebOborot.findByIdTipm", query = "SELECT g FROM GsgWebOborot g WHERE g.idTipm = :idTipm"),
    @NamedQuery(name = "GsgWebOborot.findByIdTip", query = "SELECT g FROM GsgWebOborot g WHERE g.idTip = :idTip"),
    @NamedQuery(name = "GsgWebOborot.findByIdGra", query = "SELECT g FROM GsgWebOborot g WHERE g.idGra = :idGra"),
    @NamedQuery(name = "GsgWebOborot.findByIdPyMet", query = "SELECT g FROM GsgWebOborot g WHERE g.idPyMet = :idPyMet"),
    @NamedQuery(name = "GsgWebOborot.findByNameIdTipM", query = "SELECT g FROM GsgWebOborot g WHERE g.nameIdTipM = :nameIdTipM"),
    @NamedQuery(name = "GsgWebOborot.findByNameIdTip", query = "SELECT g FROM GsgWebOborot g WHERE g.nameIdTip = :nameIdTip"),
    @NamedQuery(name = "GsgWebOborot.findByNameMet", query = "SELECT g FROM GsgWebOborot g WHERE g.nameMet = :nameMet"),
    @NamedQuery(name = "GsgWebOborot.findByName4", query = "SELECT g FROM GsgWebOborot g WHERE g.name4 = :name4"),
    @NamedQuery(name = "GsgWebOborot.findBySaldoD", query = "SELECT g FROM GsgWebOborot g WHERE g.saldoD = :saldoD"),
    @NamedQuery(name = "GsgWebOborot.findBySaldoDNds", query = "SELECT g FROM GsgWebOborot g WHERE g.saldoDNds = :saldoDNds"),
    @NamedQuery(name = "GsgWebOborot.findBySaldoDFull", query = "SELECT g FROM GsgWebOborot g WHERE g.saldoDFull = :saldoDFull"),
    @NamedQuery(name = "GsgWebOborot.findBySaldoK", query = "SELECT g FROM GsgWebOborot g WHERE g.saldoK = :saldoK"),
    @NamedQuery(name = "GsgWebOborot.findBySaldoKNds", query = "SELECT g FROM GsgWebOborot g WHERE g.saldoKNds = :saldoKNds"),
    @NamedQuery(name = "GsgWebOborot.findBySaldoKFull", query = "SELECT g FROM GsgWebOborot g WHERE g.saldoKFull = :saldoKFull"),
    @NamedQuery(name = "GsgWebOborot.findByKvt", query = "SELECT g FROM GsgWebOborot g WHERE g.kvt = :kvt"),
    @NamedQuery(name = "GsgWebOborot.findByKvarch", query = "SELECT g FROM GsgWebOborot g WHERE g.kvarch = :kvarch"),
    @NamedQuery(name = "GsgWebOborot.findByTar", query = "SELECT g FROM GsgWebOborot g WHERE g.tar = :tar"),
    @NamedQuery(name = "GsgWebOborot.findByNonActual", query = "SELECT g FROM GsgWebOborot g WHERE g.nonActual = :nonActual")})
public class GsgWebOborot implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Size(max = 20)
    @Column(name = "ID")
    private String id;
    @Column(name = "ID_PLAT")
    private Integer idPlat;
    @Column(name = "YEA")
    private Short yea;
    @Column(name = "MON")
    private Short mon;
    @Size(max = 2)
    @Column(name = "HH")
    private String hh;
    @Size(max = 20)
    @Column(name = "NUM_DOC")
    private String numDoc;
    @Size(max = 20)
    @Column(name = "NUM_SCHET")
    private String numSchet;
    @Column(name = "DATE_DOC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDoc;
    @Column(name = "DATE_OPL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOpl;
    @Column(name = "DATE_calc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCalc;
    @Column(name = "YR")
    private Integer yr;
    @Column(name = "MNTH")
    private Integer mnth;
    @Column(name = "ID_TIPM")
    private Integer idTipm;
    @Column(name = "ID_TIP")
    private Integer idTip;
    @Column(name = "ID_GRA")
    private Integer idGra;
    @Column(name = "ID_PY_MET")
    private Integer idPyMet;
    @Size(max = 22)
    @Column(name = "NAME_ID_TIP_M")
    private String nameIdTipM;
    @Size(max = 22)
    @Column(name = "NAME_ID_TIP")
    private String nameIdTip;
    @Size(max = 50)
    @Column(name = "NAME_MET")
    private String nameMet;
    @Size(max = 4)
    @Column(name = "NAME4")
    private String name4;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALDO_D")
    private Double saldoD;
    @Column(name = "SALDO_D_NDS")
    private Double saldoDNds;
    @Column(name = "SALDO_D_FULL")
    private Double saldoDFull;
    @Column(name = "SALDO_K")
    private Double saldoK;
    @Column(name = "SALDO_K_NDS")
    private Double saldoKNds;
    @Column(name = "SALDO_K_FULL")
    private Double saldoKFull;
    @Column(name = "KVT")
    private Integer kvt;
    @Column(name = "KVARCH")
    private Integer kvarch;
    @Column(name = "TAR")
    private Double tar;
    @Column(name = "NON_ACTUAL")
    private Integer nonActual;

    public GsgWebOborot() {
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

    public String getHh() {
        return hh;
    }

    public void setHh(String hh) {
        this.hh = hh;
    }

    public String getNumDoc() {
        return numDoc;
    }

    public void setNumDoc(String numDoc) {
        this.numDoc = numDoc;
    }

    public String getNumSchet() {
        return numSchet;
    }

    public void setNumSchet(String numDoc) {
        this.numSchet = numSchet;
    }

    public Date getDateDoc() {
        return dateDoc;
    }

    public void setDateDoc(Date dateDoc) {
        this.dateDoc = dateDoc;
    }

    public Date getDateOpl() {
        return dateOpl;
    }

    public void setDateOpl(Date dateOpl) {
        this.dateOpl = dateOpl;
    }


    public Date getDateCalc() {
        return dateCalc;
    }

    public void setDateCalc(Date dateCalc) {
        this.dateOpl = dateCalc;
    }
    
    public Integer getYr() {
        return yr;
    }

    public void setYr(Integer yr) {
        this.yr = yr;
    }

    public Integer getMnth() {
        return mnth;
    }

    public void setMnth(Integer mnth) {
        this.mnth = mnth;
    }

    public Integer getIdTipm() {
        return idTipm;
    }

    public void setIdTipm(Integer idTipm) {
        this.idTipm = idTipm;
    }

    public Integer getIdTip() {
        return idTip;
    }

    public void setIdTip(Integer idTip) {
        this.idTip = idTip;
    }

    public Integer getIdGra() {
        return idGra;
    }

    public void setIdGra(Integer idGra) {
        this.idGra = idGra;
    }

    public Integer getIdPyMet() {
        return idPyMet;
    }

    public void setIdPyMet(Integer idPyMet) {
        this.idPyMet = idPyMet;
    }

    public String getNameIdTipM() {
        String ss = "";
        if (getYr() != null){
            ss = " "+getYr().toString()+" год";
        };
        if (getMnth() != null){
            ss = ss + " "+ getMnth().toString()+" мес.";
        }
        if (ss.equals("")){
            return nameIdTipM;
        } else {
            return nameIdTipM + " ( за "+ss+") ";
        }
    }

    public String getNameBrefIdTipM() {
        return nameIdTipM;
    }

    public void setNameIdTipM(String nameIdTipM) {
        this.nameIdTipM = nameIdTipM;
    }

    public String getNameIdTip() {
        return nameIdTip;
    }

    public void setNameIdTip(String nameIdTip) {
        this.nameIdTip = nameIdTip;
    }

    public String getNameMet() {
        return nameMet;
    }

    public void setNameMet(String nameMet) {
        this.nameMet = nameMet;
    }

    public String getName4() {
        return name4;
    }

    public void setName4(String name4) {
        this.name4 = name4;
    }

    public Double getSaldoD() {
        return saldoD;
    }

    public void setSaldoD(Double saldoD) {
        this.saldoD = saldoD;
    }

    public Double getSaldoDNds() {
        return saldoDNds;
    }

    public void setSaldoDNds(Double saldoDNds) {
        this.saldoDNds = saldoDNds;
    }

    public Double getSaldoDFull() {
        return saldoDFull;
    }

    public void setSaldoDFull(Double saldoDFull) {
        this.saldoDFull = saldoDFull;
    }

    public Double getSaldoK() {
        return saldoK;
    }

    public void setSaldoK(Double saldoK) {
        this.saldoK = saldoK;
    }

    public Double getSaldoKNds() {
        return saldoKNds;
    }

    public void setSaldoKNds(Double saldoKNds) {
        this.saldoKNds = saldoKNds;
    }

    public Double getSaldoKFull() {
        return saldoKFull;
    }

    public void setSaldoKFull(Double saldoKFull) {
        this.saldoKFull = saldoKFull;
    }

    public Integer getKvt() {
        return kvt;
    }

    public void setKvt(Integer kvt) {
        this.kvt = kvt;
    }

    public Integer getKvarch() {
        return kvarch;
    }

    public void setKvarch(Integer kvarch) {
        this.kvarch = kvarch;
    }

    public Double getTar() {
        return tar;
    }

    public void setTar(Double tar) {
        this.tar = tar;
    }

    public Integer getNonActual() {
        return nonActual;
    }

    public void setNonActual(Integer nonActual) {
        this.nonActual = nonActual;
    }
    
}
