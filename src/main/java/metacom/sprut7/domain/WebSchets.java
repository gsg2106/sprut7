/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

//import com.vaadin.terminal.ThemeResource;
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
@Table(name = "WEB_SCHETS")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "WebSchets.findAll", query = "SELECT w FROM WebSchets w"),
    @NamedQuery(name = "WebSchets.findByIdSchets", query = "SELECT w FROM WebSchets w WHERE w.idSchets = :idSchets"),
    @NamedQuery(name = "WebSchets.findByIdPlat", query = "SELECT w FROM WebSchets w WHERE w.idPlat = :idPlat"),
    @NamedQuery(name = "WebSchets.findByYea", query = "SELECT w FROM WebSchets w WHERE w.yea = :yea"),
    @NamedQuery(name = "WebSchets.findByMon", query = "SELECT w FROM WebSchets w WHERE w.mon = :mon"),
    @NamedQuery(name = "WebSchets.findByFileName", query = "SELECT w FROM WebSchets w WHERE w.fileName = :fileName"),
    @NamedQuery(name = "WebSchets.findBySignatura", query = "SELECT w FROM WebSchets w WHERE w.signatura = :signatura"),
    @NamedQuery(name = "WebSchets.findByCreated", query = "SELECT w FROM WebSchets w WHERE w.created = :created"),
    @NamedQuery(name = "WebSchets.findByPrepared", query = "SELECT w FROM WebSchets w WHERE w.prepared = :prepared"),
    @NamedQuery(name = "WebSchets.findByDownloaded", query = "SELECT w FROM WebSchets w WHERE w.downloaded = :downloaded"),
    @NamedQuery(name = "WebSchets.findByUploaded", query = "SELECT w FROM WebSchets w WHERE w.uploaded = :uploaded"),
    @NamedQuery(name = "WebSchets.findBySigned", query = "SELECT w FROM WebSchets w WHERE w.signed = :signed"),
    @NamedQuery(name = "WebSchets.findByReady", query = "SELECT w FROM WebSchets w WHERE w.ready = :ready"),
    @NamedQuery(name = "WebSchets.findByComment", query = "SELECT w FROM WebSchets w WHERE w.comment = :comment"),
    @NamedQuery(name = "WebSchets.findByDeleted", query = "SELECT w FROM WebSchets w WHERE w.deleted = :deleted"),
    @NamedQuery(name = "WebSchets.findByViddoc", query = "SELECT w FROM WebSchets w WHERE w.viddoc = :viddoc"),
    @NamedQuery(name = "WebSchets.findByIdgroup", query = "SELECT w FROM WebSchets w WHERE w.idgroup = :idgroup"),
    @NamedQuery(name = "WebSchets.findByFileNameSign", query = "SELECT w FROM WebSchets w WHERE w.fileNameSign = :fileNameSign"),
})
public class WebSchets implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_SCHETS")
    private Integer idSchets;
    @Column(name = "ID_PLAT")
    private Integer idPlat;
    @Column(name = "YEA")
    private Short yea;
    @Column(name = "MON")
    private Short mon;
    @Size(max = 50)
    @Column(name = "FILE_NAME")
    private String fileName;
    @Size(max = 512)
    @Column(name = "SIGNATURA")
    private String signatura;
    @Column(name = "CREATED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created;
    @Column(name = "PREPARED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date prepared;
    @Column(name = "DOWNLOADED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date downloaded;
    @Column(name = "UPLOADED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploaded;
    @Column(name = "SIGNED")
    private Short signed;
    @Column(name = "READY")
    private Short ready;
    @Size(max = 60)
    @Column(name = "COMMENT_")
    private String comment;
    @Column(name = "DELETED")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deleted;
    @Size(max = 60)
    @Column(name = "FILE_NAME_SIGN")
    private String fileNameSign;
    
    @Column(name = "VID_DOC")
    private Integer viddoc;
    @Column(name = "ID_GROUP")
    private Integer idgroup;
    
    @Transient    
    private  com.vaadin.ui.Embedded proba;
            
    public WebSchets() {
    }

    public WebSchets(Integer idSchets) {
        this.idSchets = idSchets;
    }

    public Integer getIdSchets() {
        return idSchets;
    }

    public void setIdSchets(Integer idSchets) {
        this.idSchets = idSchets;
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public Short getYea() {
        return yea;
    }

    public void setYea(Short yea) {
        this.yea = yea;
    }

    public Short getMon() {
        return mon;
    }

    public void setMon(Short mon) {
        this.mon = mon;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileNameSign() {
        return fileNameSign;
    }

    public void setFileNameSign(String fileNameSign) {
        this.fileNameSign = fileNameSign;
    }    
    
    public String getSignatura() {
        return signatura;
    }

    public void setSignatura(String signatura) {
        this.signatura = signatura;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getPrepared() {
        return prepared;
    }

    public void setPrepared(Date prepared) {
        this.prepared = prepared;
    }

    public Date getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(Date downloaded) {
        this.downloaded = downloaded;
    }

    public Date getUploaded() {
        return uploaded;
    }

    public void setUploaded(Date uploaded) {
        this.uploaded = uploaded;
    }

    public Short getSigned() {
        return signed;
    }

    public void setSigned(Short signed) {
        this.signed = signed;
    }

    public Short getReady() {
        return ready;
    }

    public void setReady(Short ready) {
        this.ready = ready;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }
    
    public Integer getViddoc() {
        return viddoc;
    }

    public void setViddoc(Integer viddoc) {
        this.viddoc = viddoc;
    }
    
    public Integer getIdgroup() {
        return idgroup;
    }

    public void setIdgroup(Integer idgroup) {
        this.idgroup = idgroup;
    }

/*    
    public void setProba( com.vaadin.ui.Embedded proba){
        this.proba =  proba;
    }
    public com.vaadin.ui.Embedded getProba(){
        ThemeResource okImage = new ThemeResource("img/16/Ok.gif");
        com.vaadin.ui.Embedded image = new com.vaadin.ui.Embedded("",okImage);  // new Embedded("Yes, logo:",
        return image;
    }
*/
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSchets != null ? idSchets.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof WebSchets)) {
            return false;
        }
        WebSchets other = (WebSchets) object;
        if ((this.idSchets == null && other.idSchets != null) || (this.idSchets != null && !this.idSchets.equals(other.idSchets))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.WebSchets[ idSchets=" + idSchets + " ]";
    }
    
}
