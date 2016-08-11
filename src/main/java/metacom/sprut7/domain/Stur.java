/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "STUR")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stur.findAll", query = "SELECT s FROM Stur s"),
    @NamedQuery(name = "Stur.findByIdTu", query = "SELECT s FROM Stur s WHERE s.idTu = :idTu"),
    @NamedQuery(name = "Stur.findByIdPlat", query = "SELECT s FROM Stur s WHERE s.idPlat = :idPlat"),
    @NamedQuery(name = "Stur.findByMethod", query = "SELECT s FROM Stur s WHERE s.method = :method"),
    @NamedQuery(name = "Stur.findByPu", query = "SELECT s FROM Stur s WHERE s.pu = :pu")
/*    
    @NamedQuery(name = "Stur.findByIdtarif", query = "SELECT s FROM Stur s WHERE s.idtarif = :idtarif"),
    @NamedQuery(name = "Stur.findByUstpotw", query = "SELECT s FROM Stur s WHERE s.ustpotw = :ustpotw"),
    @NamedQuery(name = "Stur.findByUstpots", query = "SELECT s FROM Stur s WHERE s.ustpots = :ustpots"),
    @NamedQuery(name = "Stur.findByDel", query = "SELECT s FROM Stur s WHERE s.del = :del"),
    @NamedQuery(name = "Stur.findByGez", query = "SELECT s FROM Stur s WHERE s.gez = :gez"),
    @NamedQuery(name = "Stur.findByPzpoint", query = "SELECT s FROM Stur s WHERE s.pzpoint = :pzpoint"),
    @NamedQuery(name = "Stur.findByD", query = "SELECT s FROM Stur s WHERE s.d = :d"),
    @NamedQuery(name = "Stur.findByNot100", query = "SELECT s FROM Stur s WHERE s.not100 = :not100"),
    @NamedQuery(name = "Stur.findBySbor", query = "SELECT s FROM Stur s WHERE s.sbor = :sbor"),
    @NamedQuery(name = "Stur.findByIsCosf", query = "SELECT s FROM Stur s WHERE s.isCosf = :isCosf"),
    @NamedQuery(name = "Stur.findByCodZone", query = "SELECT s FROM Stur s WHERE s.codZone = :codZone"),
    @NamedQuery(name = "Stur.findByIsConstD", query = "SELECT s FROM Stur s WHERE s.isConstD = :isConstD")
    */
    })
public class Stur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TU")
    private Integer idTu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLAT")
    private int idPlat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "METHOD")
    private char method;
    @Column(name = "PU")
    private Character pu;
    
/*    
    @Column(name = "IDTARIF")
    private Integer idtarif;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "USTPOTW")
    private Double ustpotw;
    @Column(name = "USTPOTS")
    private Double ustpots;
    @Column(name = "DEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date del;
    @Column(name = "GEZ")
    private Character gez;
    @Column(name = "PZPOINT")
    private Character pzpoint;
    @Column(name = "D")
    private Double d;
    @Column(name = "NOT100")
    private Character not100;
    @Column(name = "SBOR")
    private Character sbor;
    @Column(name = "IS_COSF")
    private Character isCosf;
    @Column(name = "COD_ZONE")
    private Character codZone;
    @Column(name = "IS_CONST_D")
    private Character isConstD;
*/    
//-----------------------------------------------------------------------------
@OneToMany(mappedBy = "stur")
private Collection<Ra> raCollection;    
//-----------------------------------------------------------------------------

    public Stur() {
    }

    public Stur(Integer idTu) {
        this.idTu = idTu;
    }

    public Stur(Integer idTu, int idPlat, char method) {
        this.idTu = idTu;
        this.idPlat = idPlat;
        this.method = method;
    }

    public Integer getIdTu() {
        return idTu;
    }

    public void setIdTu(Integer idTu) {
        this.idTu = idTu;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public char getMethod() {
        return method;
    }

    public void setMethod(char method) {
        this.method = method;
    }

    public Character getPu() {
        return pu;
    }

    public void setPu(Character pu) {
        this.pu = pu;
    }

    /*
    public Integer getIdtarif() {
        return idtarif;
    }

    public void setIdtarif(Integer idtarif) {
        this.idtarif = idtarif;
    }

    public Double getUstpotw() {
        return ustpotw;
    }

    public void setUstpotw(Double ustpotw) {
        this.ustpotw = ustpotw;
    }

    public Double getUstpots() {
        return ustpots;
    }

    public void setUstpots(Double ustpots) {
        this.ustpots = ustpots;
    }

    public Date getDel() {
        return del;
    }

    public void setDel(Date del) {
        this.del = del;
    }

    public Character getGez() {
        return gez;
    }

    public void setGez(Character gez) {
        this.gez = gez;
    }

    public Character getPzpoint() {
        return pzpoint;
    }

    public void setPzpoint(Character pzpoint) {
        this.pzpoint = pzpoint;
    }

    public Double getD() {
        return d;
    }

    public void setD(Double d) {
        this.d = d;
    }

    public Character getNot100() {
        return not100;
    }

    public void setNot100(Character not100) {
        this.not100 = not100;
    }

    public Character getSbor() {
        return sbor;
    }

    public void setSbor(Character sbor) {
        this.sbor = sbor;
    }

    public Character getIsCosf() {
        return isCosf;
    }

    public void setIsCosf(Character isCosf) {
        this.isCosf = isCosf;
    }

    public Character getCodZone() {
        return codZone;
    }

    public void setCodZone(Character codZone) {
        this.codZone = codZone;
    }

    public Character getIsConstD() {
        return isConstD;
    }

    public void setIsConstD(Character isConstD) {
        this.isConstD = isConstD;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTu != null ? idTu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stur)) {
            return false;
        }
        Stur other = (Stur) object;
        if ((this.idTu == null && other.idTu != null) || (this.idTu != null && !this.idTu.equals(other.idTu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.Stur[ idTu=" + idTu + " ]";
    }
    
}
