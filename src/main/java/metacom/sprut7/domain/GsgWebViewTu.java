/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "GSG_WEB_VIEW_TU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GsgWebViewTu.findAll", query = "SELECT g FROM GsgWebViewTu g")})
public class GsgWebViewTu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "ID_TU")
    @Id
    private BigInteger idTu;
    @Column(name = "ID_PLAT")
    private BigInteger idPlat;
    @Column(name = "CODTU")
    private BigInteger codtu;
    @Column(name = "NAMETU")
    private String nametu;
    @Column(name = "ADRLINE")
    private String adrline;
    @Column(name = "ID_TU_PARENT")
    private Integer idTuParent;
//    private Integer parent_id_tu;
    @Column(name = "ORDERS")
    private BigInteger orders;

////////@Column(name="idTuParent")    
    
    @Transient
    private Boolean superDepartment;
    @ManyToOne
  //  @JoinColumn(name="ID_TU_PARENT")
    private GsgWebViewTu parent;
    
    public GsgWebViewTu getParent() {
        return parent;
    }
    public void setParent(GsgWebViewTu parent) {
        this.parent = parent;
    }
    public Boolean isSuperDepartment() {
        if (superDepartment == null) {
////            superDepartment = getPersons().size() == 0;
//            superDepartment = 0 == 0;
              superDepartment = idTuParent  == null; 
        }
        return superDepartment;
    }
    @Transient
    public String getHierarchicalName() {
        if (parent != null) {
            return parent.toString() + " : " + nametu;
        }
        return nametu;
    }
    public String toString() {
       return getHierarchicalName();
    }    
    
    
    
    public GsgWebViewTu() {
    }

    public BigInteger getIdTu() {
        return idTu;
    }

    public void setIdTu(BigInteger idTu) {
        this.idTu = idTu;
    }

    public BigInteger getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(BigInteger idPlat) {
        this.idPlat = idPlat;
    }

    public BigInteger getCodtu() {
        return codtu;
    }

    public void setCodtu(BigInteger codtu) {
        this.codtu = codtu;
    }

    public String getNametu() {
        return nametu;
    }

    public void setNametu(String nametu) {
        this.nametu = nametu;
    }

    public String getAdrline() {
        return adrline;
    }

    public void setAdrline(String adrline) {
        this.adrline = adrline;
    }

    public Integer getIdTuParent() {
        return this.idTuParent;
    }

    public void setIdTuParent(Integer idTuParent) {
        this.idTuParent = idTuParent;
    }

    public BigInteger getOrders() {
        return orders;
    }

    public void setOrders(BigInteger orders) {
        this.orders = orders;
    }
    
}
