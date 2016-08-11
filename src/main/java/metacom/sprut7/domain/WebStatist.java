/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metacom.sprut7.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "WEB_STATIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WebStatist.findAll", query = "SELECT w FROM WebStatist w"),
    @NamedQuery(name = "WebStatist.findByIdStatist", query = "SELECT w FROM WebStatist w WHERE w.idStatist = :idStatist"),
    @NamedQuery(name = "WebStatist.findByIdPlat", query = "SELECT w FROM WebStatist w WHERE w.idPlat = :idPlat"),
    @NamedQuery(name = "WebStatist.findByDt", query = "SELECT w FROM WebStatist w WHERE w.dt = :dt"),
    @NamedQuery(name = "WebStatist.findByIdVidActivn", query = "SELECT w FROM WebStatist w WHERE w.idVidActivn = :idVidActivn"),
    @NamedQuery(name = "WebStatist.findByCount", query = "SELECT w FROM WebStatist w WHERE w.count = :count")})
public class WebStatist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_STATIST")
    private Integer idStatist;
    @Column(name = "ID_PLAT")
    private Integer idPlat;
    @Column(name = "DT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dt;
    @Column(name = "ID_VID_ACTIVN")
    private Short idVidActivn;
    @Column(name = "COUNT_")
    private Integer count;

    public WebStatist() {
    }

    public WebStatist(Integer idStatist) {
        this.idStatist = idStatist;
    }

    public Integer getIdStatist() {
        return idStatist;
    }

    public void setIdStatist(Integer idStatist) {
        this.idStatist = idStatist;
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public Date getDt() {
        return dt;
    }

    public void setDt(Date dt) {
        this.dt = dt;
    }

    public Short getIdVidActivn() {
        return idVidActivn;
    }

    public void setIdVidActivn(Short idVidActivn) {
        this.idVidActivn = idVidActivn;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStatist != null ? idStatist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebStatist)) {
            return false;
        }
        WebStatist other = (WebStatist) object;
        if ((this.idStatist == null && other.idStatist != null) || (this.idStatist != null && !this.idStatist.equals(other.idStatist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.WebStatist[ idStatist=" + idStatist + " ]";
    }
    
}
