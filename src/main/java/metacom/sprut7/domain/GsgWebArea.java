package metacom.sprut7.domain;

import org.eclipse.persistence.annotations.ReadOnly;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 25.07.16
 * Time: 0:04
 * To change this template use File | Settings | File Templates.
 */

@ReadOnly
@Entity
@Table(name = "GSG_WEB_VIEW_AREA")
@NamedQueries({
        @NamedQuery(name = "GsgWebArea.findAll", query = "SELECT g FROM GsgWebArea g"),
        @NamedQuery(name = "GsgWebArea.findByIdPlat", query = "SELECT g FROM GsgWebArea g WHERE g.idPlat = :idPlat"),
        @NamedQuery(name = "GsgWebArea.findByIdPotr", query = "SELECT g FROM GsgWebArea g WHERE g.idPotr = :idPotr")
})
@XmlRootElement
public class GsgWebArea implements Serializable{
private static final long serialVersionUID = 1L;
@Id
@Column(name = "ID_POTR")
private Integer idPotr;
@Column(name = "NAMPOTR")
@Size(max = 40)
private String namPotr;
@Column(name = "TYPEPOTR")
private Character typePotr;
@Column(name = "NPLOS")
private Integer nplos;
@Column(name = "YES_LIMNAGR")
private Character yesLimNagr;
@Column(name = "RAZR_NAGR")
private BigDecimal razrNagr;
@Column(name = "ID_PLAT")
private Integer idPlat;
@Column(name = "NEPR_ZIKL")
private Character neprZikl;
@Column(name = "IS_DIF")
private Character isDif;

    public Integer getIdPotr() {
        return idPotr;
    }

    public void setIdPotr(Integer idPotr) {
        this.idPotr = idPotr;
    }

    public String getNamPotr() {
        return namPotr;
    }

    public void setNamPotr(String namPotr) {
        this.namPotr = namPotr;
    }

    public Character getTypePotr() {
        return typePotr;
    }

    public void setTypePotr(Character typePotr) {
        this.typePotr = typePotr;
    }

    public Integer getNplos() {
        return nplos;
    }

    public void setNplos(Integer nplos) {
        this.nplos = nplos;
    }

    public Character getYesLimNagr() {
        return yesLimNagr;
    }

    public void setYesLimNagr(Character yesLimNagr) {
        this.yesLimNagr = yesLimNagr;
    }

    public BigDecimal getRazrNagr() {
        return razrNagr;
    }

    public void setRazrNagr(BigDecimal razrNagr) {
        this.razrNagr = razrNagr;
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public Character getNeprZikl() {
        return neprZikl;
    }

    public void setNeprZikl(Character neprZikl) {
        this.neprZikl = neprZikl;
    }

    public Character getDif() {
        return isDif;
    }

    public void setDif(Character dif) {
        isDif = dif;
    }

}
