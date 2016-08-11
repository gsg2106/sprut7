/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "WEB_INNS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WebInns.findAll", query = "SELECT w FROM WebInns w"),
    @NamedQuery(name = "WebInns.findByIdInns", query = "SELECT w FROM WebInns w WHERE w.idInns = :idInns"),
    @NamedQuery(name = "WebInns.findByIdPlat", query = "SELECT w FROM WebInns w WHERE w.idPlat = :idPlat"),
    @NamedQuery(name = "WebInns.findByEdrpou", query = "SELECT w FROM WebInns w WHERE w.edrpou = :edrpou"),
    @NamedQuery(name = "WebInns.findByInn", query = "SELECT w FROM WebInns w WHERE w.inn = :inn"),
    @NamedQuery(name = "WebInns.findByFio", query = "SELECT w FROM WebInns w WHERE w.fio = :fio"),
    @NamedQuery(name = "WebInns.findByComment", query = "SELECT w FROM WebInns w WHERE w.comment = :comment"),
    @NamedQuery(name = "WebInns.findByIdUser", query = "SELECT w FROM WebInns w WHERE w.idUser = :idUser"),
    @NamedQuery(name = "WebInns.findByDateAdd", query = "SELECT w FROM WebInns w WHERE w.dateAdd = :dateAdd"),
    @NamedQuery(name = "WebInns.findByDateDeleted", query = "SELECT w FROM WebInns w WHERE w.dateDeleted = :dateDeleted"),
    @NamedQuery(name = "WebInns.findByEmail", query = "SELECT w FROM WebInns w WHERE w.email = :email")})
public class WebInns implements Serializable {
    @Column(name = "DATE_ADD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAdd;
    @Column(name = "DATE_DELETED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDeleted;
    @Column(name = "INNER_")
    private Integer inner;
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_INNS")
    private Integer idInns;
    @Column(name = "ID_PLAT")
    private Integer idPlat;
    @Size(max = 10)
    @Column(name = "EDRPOU")
    private String edrpou;
    @Size(max = 25)
    @Column(name = "INN")
    private String inn;
    @Size(max = 100)
    @Column(name = "FIO")
    private String fio;
    @Size(max = 100)
    @Column(name = "COMMENT_")
    private String comment;
    @Column(name = "ID_USER")
    private Integer idUser;
    @Size(max = 50)
    @Column(name = "E_MAIL_")
    private String email;

    public WebInns() {
    }

    public WebInns(Integer idInns) {
        this.idInns = idInns;
    }

    public Integer getIdInns() {
        return idInns;
    }

    public void setIdInns(Integer idInns) {
        this.idInns = idInns;
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public String getEdrpou() {
        if (edrpou == null){
            return "null";
        } else if (edrpou.trim().equals("")) {
           return "null";}
        else
           return edrpou;
    }

    public void setEdrpou(String edrpou) {
        this.edrpou = edrpou;
    }

    public String getInn() {
        if (inn == null){
           return null; 
        } else if (inn.trim().equals("")) {
           return "null";}
        else
           return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Date getDateAdd() {
        return dateAdd;
    }

    public void setDateAdd(Date dateAdd) {
        this.dateAdd = dateAdd;
    }

    public Date getDateDeleted() {
        return dateDeleted;
    }

    public void setDateDeleted(Date dateDeleted) {
        this.dateDeleted = dateDeleted;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String comment) {
        this.email = email;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInns != null ? idInns.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebInns)) {
            return false;
        }
        WebInns other = (WebInns) object;
        if ((this.idInns == null && other.idInns != null) || (this.idInns != null && !this.idInns.equals(other.idInns))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.WebInns[ idInns=" + idInns + " ]";
    }

    public Integer getInner() {
        return inner;
    }

    public void setInner(Integer inner) {
        this.inner = inner;
    }
    
}
