package metacom.sprut7.domain;

import com.sun.org.apache.xpath.internal.operations.Equals;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 25.07.16
 * Time: 23:54
 * To change this template use File | Settings | File Templates.
 */

//@EqualsAndHashCode
//@ToStringpublic
class LimitKey implements Serializable{
    private static final long serialVersionUID = 1L;
    public LimitKey(){}
    private Integer idPotr;
    private Integer years;
    private Integer month;
    //@SuppressWarnings("UnusedDeclaration")
    public boolean equals(LimitKey limitKey){
        return (limitKey.idPotr.intValue() == this.idPotr.intValue())
               && (limitKey.years.intValue() == this.years.intValue())
               && (limitKey.month.intValue() == this.month.intValue());
    }

    @Override
    public int hashCode(){
        int hash = 0;
        hash += idPotr.hashCode();
        hash += years.hashCode();
        hash += month.hashCode();
        return hash;
    }

    @Override
    public String toString(){
        return "com.mycompany.maven_sprut.domain.LimitKey[ years=" + years.toString() + ", month=" + month.toString() + ", idPotr=" + idPotr.toString() + " ]";

    }

    //@SuppressWarnings("UnusedDeclaration")
    public Integer getIdPotr() {
        return idPotr;
    }

    //@SuppressWarnings("UnusedDeclaration")
    public void setIdPotr(Integer idPotr) {
        this.idPotr = idPotr;
    }

    //@SuppressWarnings("UnusedDeclaration")
    public Integer getYears() {
        return years;
    }

    //@SuppressWarnings("UnusedDeclaration")
    public void setYears(Integer years) {
        this.years = years;
    }

    //@SuppressWarnings("UnusedDeclaration")
    public Integer getMonth() {
        return month;
    }

    //@SuppressWarnings("UnusedDeclaration")
    public void setMonth(Integer month) {
        this.month = month;
    }
}
