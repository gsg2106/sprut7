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
 * @author locus
 */
@Entity
@Table(name = "RA_WEB")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RaWeb.findAll", query = "SELECT r FROM RaWeb r"),
    @NamedQuery(name = "RaWeb.findByIdPlat", query = "SELECT r FROM RaWeb r WHERE r.idPlat = :idPlat"),
    @NamedQuery(name = "RaWeb.findByYea", query = "SELECT r FROM RaWeb r WHERE r.yea = :yea"),
    @NamedQuery(name = "RaWeb.findByMon", query = "SELECT r FROM RaWeb r WHERE r.mon = :mon"),
    @NamedQuery(name = "RaWeb.findByPrepared", query = "SELECT r FROM RaWeb r WHERE r.prepared = :prepared"),
    @NamedQuery(name = "RaWeb.findByReqRecalc", query = "SELECT r FROM RaWeb r WHERE r.reqRecalc = :reqRecalc"),
    @NamedQuery(name = "RaWeb.findByStateSchet", query = "SELECT r FROM RaWeb r WHERE r.stateSchet = :stateSchet"),
    @NamedQuery(name = "RaWeb.findByLocked", query = "SELECT r FROM RaWeb r WHERE r.locked = :locked"),
    @NamedQuery(name = "RaWeb.findByTimePrepare", query = "SELECT r FROM RaWeb r WHERE r.timePrepare = :timePrepare"),
    @NamedQuery(name = "RaWeb.findByTimeReqSchet", query = "SELECT r FROM RaWeb r WHERE r.timeReqSchet = :timeReqSchet"),
    @NamedQuery(name = "RaWeb.findByTimeIsReady", query = "SELECT r FROM RaWeb r WHERE r.timeIsReady = :timeIsReady"),
    @NamedQuery(name = "RaWeb.findByTimeLock", query = "SELECT r FROM RaWeb r WHERE r.timeLock = :timeLock"),
    @NamedQuery(name = "RaWeb.findByMessPrepare", query = "SELECT r FROM RaWeb r WHERE r.messPrepare = :messPrepare"),
    @NamedQuery(name = "RaWeb.findByMessSchet", query = "SELECT r FROM RaWeb r WHERE r.messSchet = :messSchet")})
public class RaWeb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLAT")
    private Integer idPlat;
    @Column(name = "YEA")
    private Integer yea;
    @Column(name = "MON")
    private Integer mon;
    @Column(name = "PREPARED")
    private Character prepared;
    @Column(name = "REQ_RECALC")
    private Character reqRecalc;
    @Column(name = "STATE_SCHET")
    private Integer stateSchet;
    @Column(name = "LOCKED")
    private Character locked;
    @Column(name = "TIME_PREPARE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timePrepare;
    @Column(name = "TIME_REQ_SCHET")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeReqSchet;
    @Column(name = "TIME_IS_READY")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeIsReady;
    @Column(name = "TIME_LOCK")
    @Temporal(TemporalType.TIMESTAMP)
    private Date timeLock;
    @Size(max = 150)
    @Column(name = "MESS_PREPARE")
    private String messPrepare;
    @Size(max = 150)
    @Column(name = "MESS_SCHET")
    private String messSchet;

    public RaWeb() {
    }

    public RaWeb(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public Integer getYea() {
        return yea;
    }

    public void setYea(Integer yea) {
        this.yea = yea;
    }

    public Integer getMon() {
        return mon;
    }

    public void setMon(Integer mon) {
        this.mon = mon;
    }

    public Character getPrepared() {
        return prepared;
    }

    public void setPrepared(Character prepared) {
        this.prepared = prepared;
    }

    public Character getReqRecalc() {
        return reqRecalc;
    }

    public void setReqRecalc(Character reqRecalc) {
        this.reqRecalc = reqRecalc;
    }

    public Integer getStateSchet() {
        return stateSchet;
    }

    public void setStateSchet(Integer stateSchet) {
        this.stateSchet = stateSchet;
    }

    public Character getLocked() {
        return locked;
    }

    public void setLocked(Character locked) {
        this.locked = locked;
    }

    public Date getTimePrepare() {
        return timePrepare;
    }

    public void setTimePrepare(Date timePrepare) {
        this.timePrepare = timePrepare;
    }

    public Date getTimeReqSchet() {
        return timeReqSchet;
    }

    public void setTimeReqSchet(Date timeReqSchet) {
        this.timeReqSchet = timeReqSchet;
    }

    public Date getTimeIsReady() {
        return timeIsReady;
    }

    public void setTimeIsReady(Date timeIsReady) {
        this.timeIsReady = timeIsReady;
    }

    public Date getTimeLock() {
        return timeLock;
    }

    public void setTimeLock(Date timeLock) {
        this.timeLock = timeLock;
    }

    public String getMessPrepare() {
        return messPrepare;
    }

    public void setMessPrepare(String messPrepare) {
        this.messPrepare = messPrepare;
    }

    public String getMessSchet() {
        return messSchet;
    }

    public void setMessSchet(String messSchet) {
        this.messSchet = messSchet;
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
        if (!(object instanceof RaWeb)) {
            return false;
        }
        RaWeb other = (RaWeb) object;
        if ((this.idPlat == null && other.idPlat != null) || (this.idPlat != null && !this.idPlat.equals(other.idPlat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.RaWeb[ idPlat=" + idPlat + " ]";
    }
    
}
