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
@Table(name = "TIP_DOCS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TipDocs.findAll", query = "SELECT t FROM TipDocs t"),
    @NamedQuery(name = "TipDocs.findByKindDoc", query = "SELECT t FROM TipDocs t WHERE t.kindDoc = :kindDoc"),
    @NamedQuery(name = "TipDocs.findByDescDoc", query = "SELECT t FROM TipDocs t WHERE t.descDoc = :descDoc"),
    @NamedQuery(name = "TipDocs.findByKodDoc", query = "SELECT t FROM TipDocs t WHERE t.kodDoc = :kodDoc")})
public class TipDocs implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "KIND_DOC")
    private Integer kindDoc;
    @Size(max = 50)
    @Column(name = "DESC_DOC")
    private String descDoc;
    @Size(max = 4)
    @Column(name = "KOD_DOC")
    private String kodDoc;

    public TipDocs() {
    }

    public TipDocs(Integer kindDoc) {
        this.kindDoc = kindDoc;
    }

    public Integer getKindDoc() {
        return kindDoc;
    }

    public void setKindDoc(Integer kindDoc) {
        this.kindDoc = kindDoc;
    }

    public String getDescDoc() {
        return descDoc;
    }

    public void setDescDoc(String descDoc) {
        this.descDoc = descDoc;
    }

    public String getKodDoc() {
        return kodDoc;
    }

    public void setKodDoc(String kodDoc) {
        this.kodDoc = kodDoc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kindDoc != null ? kindDoc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipDocs)) {
            return false;
        }
        TipDocs other = (TipDocs) object;
        if ((this.kindDoc == null && other.kindDoc != null) || (this.kindDoc != null && !this.kindDoc.equals(other.kindDoc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.TipDocs[ kindDoc=" + kindDoc + " ]";
    }
    
}
