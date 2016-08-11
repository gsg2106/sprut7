/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "PZONE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pzone.findAll", query = "SELECT p FROM Pzone p"),
    @NamedQuery(name = "Pzone.findByCodZone", query = "SELECT p FROM Pzone p WHERE p.codZone = :codZone"),
    @NamedQuery(name = "Pzone.findByNameZone", query = "SELECT p FROM Pzone p WHERE p.nameZone = :nameZone"),
    @NamedQuery(name = "Pzone.findByChasi", query = "SELECT p FROM Pzone p WHERE p.chasi = :chasi"),
    @NamedQuery(name = "Pzone.findByKoeffP", query = "SELECT p FROM Pzone p WHERE p.koeffP = :koeffP")})
public class Pzone implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_ZONE")
    private Character codZone;
    @Size(max = 15)
    @Column(name = "NAME_ZONE")
    private String nameZone;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "CHASI")
    private Double chasi;
    @Column(name = "KOEFF_P")
    private Double koeffP;

    public Pzone() {
    }

    public Pzone(Character codZone) {
        this.codZone = codZone;
    }

    public Character getCodZone() {
        return codZone;
    }

    public void setCodZone(Character codZone) {
        this.codZone = codZone;
    }

    public String getNameZone() {
        return nameZone;
    }

    public void setNameZone(String nameZone) {
        this.nameZone = nameZone;
    }

    public Double getChasi() {
        return chasi;
    }

    public void setChasi(Double chasi) {
        this.chasi = chasi;
    }

    public Double getKoeffP() {
        return koeffP;
    }

    public void setKoeffP(Double koeffP) {
        this.koeffP = koeffP;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codZone != null ? codZone.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pzone)) {
            return false;
        }
        Pzone other = (Pzone) object;
        if ((this.codZone == null && other.codZone != null) || (this.codZone != null && !this.codZone.equals(other.codZone))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.Pzone[ codZone=" + codZone + " ]";
    }
    
}
