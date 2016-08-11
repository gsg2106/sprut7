/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package metacom.sprut7.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "GSG_WEB_VIEW_GETKEY_STATIST")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "GsgWebViewGetkeyStatist.findAll", query = "SELECT g FROM GsgWebViewGetkeyStatist g"),
    @NamedQuery(name = "GsgWebViewGetkeyStatist.findById", query = "SELECT g FROM GsgWebViewGetkeyStatist g WHERE g.id = :id")})
public class GsgWebViewGetkeyStatist implements Serializable {
    private static final long serialVersionUID = 1L;
    @Column(name = "ID")
    @Id
    private Integer id;

    public GsgWebViewGetkeyStatist() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}
