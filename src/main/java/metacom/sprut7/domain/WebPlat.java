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
@Table(name = "WEB_PLAT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WebPlat.findAll", query = "SELECT w FROM WebPlat w"),
    @NamedQuery(name = "WebPlat.findByIdPlat", query = "SELECT w FROM WebPlat w WHERE w.idPlat = :idPlat"),
    @NamedQuery(name = "WebPlat.findByLogin", query = "SELECT w FROM WebPlat w WHERE w.login = :login"),
    @NamedQuery(name = "WebPlat.findByPw", query = "SELECT w FROM WebPlat w WHERE w.pw = :pw"),
    @NamedQuery(name = "WebPlat.findByDtAdd", query = "SELECT w FROM WebPlat w WHERE w.dtAdd = :dtAdd"),
    @NamedQuery(name = "WebPlat.findByDtEnd", query = "SELECT w FROM WebPlat w WHERE w.dtEnd = :dtEnd"),
    @NamedQuery(name = "WebPlat.findByIdIsp", query = "SELECT w FROM WebPlat w WHERE w.idIsp = :idIsp"),
    @NamedQuery(name = "WebPlat.findByPause", query = "SELECT w FROM WebPlat w WHERE w.pause = :pause"),
    @NamedQuery(name = "WebPlat.findByYeaStart", query = "SELECT w FROM WebPlat w WHERE w.yeaStart = :yeaStart"),
    @NamedQuery(name = "WebPlat.findByMonStart", query = "SELECT w FROM WebPlat w WHERE w.monStart = :monStart")})
public class WebPlat implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLAT")
    private Integer idPlat;
    @Size(max = 40)
    @Column(name = "LOGIN")
    private String login;
    @Size(max = 40)
    @Column(name = "PW")
    private String pw;
    @Column(name = "DT_ADD")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtAdd;
    @Column(name = "DT_END")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dtEnd;
    @Column(name = "ID_ISP")
    private Integer idIsp;
    @Column(name = "PAUSE")
    private Integer pause;
    @Column(name = "YEA_START")
    private Integer yeaStart;
    @Column(name = "MON_START")
    private Integer monStart;

    public WebPlat() {
    }

    public WebPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public Date getDtAdd() {
        return dtAdd;
    }

    public void setDtAdd(Date dtAdd) {
        this.dtAdd = dtAdd;
    }

    public Date getDtEnd() {
        return dtEnd;
    }

    public void setDtEnd(Date dtEnd) {
        this.dtEnd = dtEnd;
    }

    public Integer getIdIsp() {
        return idIsp;
    }

    public void setIdIsp(Integer idIsp) {
        this.idIsp = idIsp;
    }

    public Integer getPause() {
        return pause;
    }

    public void setPause(Integer pause) {
        this.pause = pause;
    }

    public Integer getYeaStart() {
        return yeaStart;
    }

    public void setYeaStart(Integer yeaStart) {
        this.yeaStart = yeaStart;
    }

    public Integer getMonStart() {
        return monStart;
    }

    public void setMonStart(Integer monStart) {
        this.monStart = monStart;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPlat != null ? idPlat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebPlat)) {
            return false;
        }
        WebPlat other = (WebPlat) object;
        if ((this.idPlat == null && other.idPlat != null) || (this.idPlat != null && !this.idPlat.equals(other.idPlat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.WebPlat[ idPlat=" + idPlat + " ]";
    }
    
}
