/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
//import org.eclipse.persistence.annotations.ReadOnly;

/**
 *
 * @author Сергей
 */
//@ReadOnly
@Entity
@Table(name = "GSG_WEB_DOLGI")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GsgWebDolgi.findAll", query = "SELECT g FROM GsgWebDolgi g"),
    @NamedQuery(name = "GsgWebDolgi.findByIdPlat", query = "SELECT g FROM GsgWebDolgi g WHERE g.idPlat = :idPlat"),
    @NamedQuery(name = "GsgWebDolgi.findByIdTipm", query = "SELECT g FROM GsgWebDolgi g WHERE g.idTipm = :idTipm"),
    @NamedQuery(name = "GsgWebDolgi.findByTxt", query = "SELECT g FROM GsgWebDolgi g WHERE g.txt = :txt"),
    @NamedQuery(name = "GsgWebDolgi.findBySaldoD", query = "SELECT g FROM GsgWebDolgi g WHERE g.saldoD = :saldoD"),
    @NamedQuery(name = "GsgWebDolgi.findBySaldoK", query = "SELECT g FROM GsgWebDolgi g WHERE g.saldoK = :saldoK"),
    @NamedQuery(name = "GsgWebDolgi.findBySaldoDNds", query = "SELECT g FROM GsgWebDolgi g WHERE g.saldoDNds = :saldoDNds"),
    @NamedQuery(name = "GsgWebDolgi.findBySaldoKNds", query = "SELECT g FROM GsgWebDolgi g WHERE g.saldoKNds = :saldoKNds"),
    @NamedQuery(name = "GsgWebDolgi.findBySaldoDFull", query = "SELECT g FROM GsgWebDolgi g WHERE g.saldoDFull = :saldoDFull"),
    @NamedQuery(name = "GsgWebDolgi.findBySaldoKFull", query = "SELECT g FROM GsgWebDolgi g WHERE g.saldoKFull = :saldoKFull")})
public class GsgWebDolgi implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    @Size(max = 50)
    private String id;
    @Column(name = "ID_PLAT")
    private Integer idPlat;
    @Column(name = "ID_TIPM")
    private Integer idTipm;
    @Size(max = 50)
    @Column(name = "TXT")
    private String txt;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "SALDO_D")
    private Double saldoD;
    @Column(name = "SALDO_K")
    private Double saldoK;
    @Column(name = "SALDO_D_NDS")
    private Double saldoDNds;
    @Column(name = "SALDO_K_NDS")
    private Double saldoKNds;
    @Column(name = "SALDO_D_FULL")
    private Double saldoDFull;
    @Column(name = "SALDO_K_FULL")
    private Double saldoKFull;

    public GsgWebDolgi() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.txt = id;
    }
    
    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public Integer getIdTipm() {
        return idTipm;
    }

    public void setIdTipm(Integer idTipm) {
        this.idTipm = idTipm;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public Double getSaldoD() {
        return saldoD;
    }

    public void setSaldoD(Double saldoD) {
        this.saldoD = saldoD;
    }

    public Double getSaldoK() {
        return saldoK;
    }

    public void setSaldoK(Double saldoK) {
        this.saldoK = saldoK;
    }

    public Double getSaldoDNds() {
        return saldoDNds;
    }

    public void setSaldoDNds(Double saldoDNds) {
        this.saldoDNds = saldoDNds;
    }

    public Double getSaldoKNds() {
        return saldoKNds;
    }

    public void setSaldoKNds(Double saldoKNds) {
        this.saldoKNds = saldoKNds;
    }

    public Double getSaldoDFull() {
        return saldoDFull;
    }

    public void setSaldoDFull(Double saldoDFull) {
        this.saldoDFull = saldoDFull;
    }

    public Double getSaldoKFull() {
        return saldoKFull;
    }

    public void setSaldoKFull(Double saldoKFull) {
        this.saldoKFull = saldoKFull;
    }
    
}
