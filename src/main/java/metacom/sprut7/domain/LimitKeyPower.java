package metacom.sprut7.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 02.08.16
 * Time: 21:42
 * To change this template use File | Settings | File Templates.
 */
public class LimitKeyPower implements Serializable{
    private static final long serialVersionUID = 1L;
    public LimitKeyPower(){};
    private Integer idPotr;
    private Integer years;
    private Integer months;
    private Character storno;
    private Date dateDoc;

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

    public boolean Equals(LimitKeyPower limitKeyPower){
        return (limitKeyPower.idPotr.intValue() == this.idPotr.intValue())
                && (limitKeyPower.years.intValue() == this.years.intValue())
                && (limitKeyPower.months.intValue() == this.months.intValue()
                && (limitKeyPower.storno.charValue() == this.storno.charValue())
                && (limitKeyPower.dateDoc.equals(this.dateDoc)));
    }
    @Override
    public int hashCode(){
        int hash = 0;
        hash += idPotr.hashCode();
        hash += years.hashCode();
        hash += months.hashCode();
        hash += storno.hashCode();
        hash += dateDoc.hashCode();
        return hash;
    }
    @Override
    public String toString(){
        return "com.mycompany.maven_sprut.domain.LimitKeyPower[ years=" + years.toString() + ", month=" + months.toString() + ", idPotr=" + idPotr.toString() + " ]";

    }

}
