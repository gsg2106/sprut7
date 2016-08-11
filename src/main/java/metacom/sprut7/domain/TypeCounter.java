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
@Table(name = "TYPE_COUNTER")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TypeCounter.findAll", query = "SELECT t FROM TypeCounter t"),
    @NamedQuery(name = "TypeCounter.findByIdTypecCounter", query = "SELECT t FROM TypeCounter t WHERE t.idTypecCounter = :idTypecCounter"),
    @NamedQuery(name = "TypeCounter.findByNameType", query = "SELECT t FROM TypeCounter t WHERE t.nameType = :nameType"),
    @NamedQuery(name = "TypeCounter.findByIdUslug", query = "SELECT t FROM TypeCounter t WHERE t.idUslug = :idUslug")})
public class TypeCounter implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TYPEC_COUNTER")
    private Integer idTypecCounter;
    @Size(max = 20)
    @Column(name = "NAME_TYPE")
    private String nameType;
    @Column(name = "ID_USLUG")
    private Character idUslug;

    public TypeCounter() {
    }

    public TypeCounter(Integer idTypecCounter) {
        this.idTypecCounter = idTypecCounter;
    }

    public Integer getIdTypecCounter() {
        return idTypecCounter;
    }

    public void setIdTypecCounter(Integer idTypecCounter) {
        this.idTypecCounter = idTypecCounter;
    }

    public String getNameType() {
        return nameType;
    }

    public void setNameType(String nameType) {
        this.nameType = nameType;
    }

    public Character getIdUslug() {
        return idUslug;
    }

    public void setIdUslug(Character idUslug) {
        this.idUslug = idUslug;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypecCounter != null ? idTypecCounter.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TypeCounter)) {
            return false;
        }
        TypeCounter other = (TypeCounter) object;
        if ((this.idTypecCounter == null && other.idTypecCounter != null) || (this.idTypecCounter != null && !this.idTypecCounter.equals(other.idTypecCounter))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "metacom.sprut7.domain.TypeCounter[ idTypecCounter=" + idTypecCounter + " ]";
    }
    
}
