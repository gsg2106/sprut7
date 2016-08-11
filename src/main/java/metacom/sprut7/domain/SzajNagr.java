package metacom.sprut7.domain;

import metacom.sprut7.domain.LimitKeyPower;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 02.08.16
 * Time: 21:11
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "SZAJNAGR")
@XmlRootElement
@IdClass(LimitKeyPower.class)
public class SzajNagr implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_POTR")
    private Integer idPotr;
    @Id
    @Column(name = "YEARS")
    private Integer years;
    @Id
    @Column(name = "MONTHS")
    private Integer months;
    @Id
    @Column(name = "STORNO")
    private Character storno;
    @Id
    @Column(name = "DATE_DOK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDoc;
    @Column(name = "DATE_PROV")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateProv;
    @Column(name = "ZAJ_VAL_U")
    private Double zajValU;
    @Column(name = "ZAJ_VAL_V")
    private Double zajValV;
    @Column(name = "HAND")
    private Integer hand;

    @Transient
    private String nameMonth;

    public Integer getIdPotr() {
        return idPotr;
    }

    public void setIdPotr(Integer idPotr) {
        this.idPotr = idPotr;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getMonths() {
        return months;
    }

    public void setMonths(Integer months) {
        this.months = months;
    }

    public Character getStorno() {
        return storno;
    }

    public void setStorno(Character storno) {
        this.storno = storno;
    }

    public Date getDateDoc() {
        return dateDoc;
    }

    public void setDateDoc(Date dateDoc) {
        this.dateDoc = dateDoc;
    }

    public Double getZajValU() {
        return zajValU;
    }

    public void setZajValU(Double zajValU) {
        this.zajValU = zajValU;
    }

    public Double getZajValV() {
        return zajValV;
    }

    public void setZajValV(Double zajValV) {
        this.zajValV = zajValV;
    }
    public String getNameMonth() {
        if (months != null){
            Month mon = Month.of(months);
            Locale loc = Locale.forLanguageTag("ru");
            return mon.getDisplayName(TextStyle.FULL_STANDALONE, loc);
        } else return  null;
    }

    public void setNameMonth(String nameMonth) {}

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public Date getDateProv() {
        return dateProv;
    }

    public void setDateProv(Date dateProv) {
        this.dateProv = dateProv;
    }
}
