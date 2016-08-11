/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "GSG_WEB_GOEST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GsgWebGoest.findAll", query = "SELECT g FROM GsgWebGoest g"),
    @NamedQuery(name = "GsgWebGoest.findByIdPlat", query = "SELECT g FROM GsgWebGoest g WHERE g.idPlat = :idPlat"),
    @NamedQuery(name = "GsgWebGoest.findByNumDog", query = "SELECT g FROM GsgWebGoest g WHERE g.numDog = :numDog"),
    @NamedQuery(name = "GsgWebGoest.findByNampotr", query = "SELECT g FROM GsgWebGoest g WHERE g.nampotr = :nampotr"),
    @NamedQuery(name = "GsgWebGoest.findByLogin", query = "SELECT g FROM GsgWebGoest g WHERE g.login = :login"),
    @NamedQuery(name = "GsgWebGoest.findByPw", query = "SELECT g FROM GsgWebGoest g WHERE g.pw = :pw"),
    @NamedQuery(name = "GsgWebGoest.findByAdrline", query = "SELECT g FROM GsgWebGoest g WHERE g.adrline = :adrline"),
    @NamedQuery(name = "GsgWebGoest.findByName", query = "SELECT g FROM GsgWebGoest g WHERE g.name = :name"),
    @NamedQuery(name = "GsgWebGoest.findByPuthNsSchets", query = "SELECT g FROM GsgWebGoest g WHERE g.puthNsSchets = :puthNsSchets")})
public class GsgWebGoest implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "ID_PLAT")
    @Id
    private Integer idPlat;
    @Size(max = 6)
    @Column(name = "NUM_DOG")
    private String numDog;
    @Size(max = 40)
    @Column(name = "NAMPOTR")
    private String nampotr;
    @Size(max = 6)
    @Column(name = "LOGIN")
    private String login;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PW")
    private Double pw;
    @Size(max = 200)
    @Column(name = "ADRLINE")
    private String adrline;
    @Size(max = 30)
    @Column(name = "NAME")
    private String name;
    @Size(max = 50)
    @Column(name = "PUTH_NS_SCHETS")
    private String puthNsSchets;

    public GsgWebGoest() {
    }

    public Integer getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(Integer idPlat) {
        this.idPlat = idPlat;
    }

    public String getNumDog() {
        return numDog;
    }

    public void setNumDog(String numDog) {
        this.numDog = numDog;
    }

    public String getNampotr() {
        return nampotr;
    }

    public void setNampotr(String nampotr) {
        this.nampotr = nampotr;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Double getPw() {
        return pw;
    }

    public void setPw(Double pw) {
        this.pw = pw;
    }

    public String getAdrline() {
        return adrline;
    }

    public void setAdrline(String adrline) {
        this.adrline = adrline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPuthNsSchets() {
        return puthNsSchets;
    }

    public void setPuthNsSchets(String puthNsSchets) {
        this.puthNsSchets = puthNsSchets;
    }
    
}
