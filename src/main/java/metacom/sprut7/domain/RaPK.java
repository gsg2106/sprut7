/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Сергей
 */
@Embeddable
public class RaPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "YEA")
    private short yea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MON")
    private short mon;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLAT")
    private int idPlat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TU")
    private int idTu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ENG")
    private char eng;
    @Basic(optional = false)
    @NotNull
    @Column(name = "NUMP")
    private short nump;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COD_ZONE")
//    private char codZone;
    private String codZone;
    @Basic(optional = false)
    @NotNull
    @Column(name = "MARK_ZON")
    private String markZon;
    

    public RaPK() {
    }

//    public RaPK(short yea, short mon, int idPlat, int idTu, char eng, short nump, char codZone) {
    public RaPK(short yea, short mon, int idPlat, int idTu, char eng, short nump, String codZone, String markZon) {
        this.yea = yea;
        this.mon = mon;
        this.idPlat = idPlat;
        this.idTu = idTu;
        this.eng = eng;
        this.nump = nump;
        this.codZone = codZone;
        this.markZon = markZon;
    }

    public short getYea() {
        return yea;
    }

    public void setYea(short yea) {
        this.yea = yea;
    }

    public short getMon() {
        return mon;
    }

    public void setMon(short mon) {
        this.mon = mon;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public int getIdTu() {
        return idTu;
    }

    public void setIdTu(int idTu) {
        this.idTu = idTu;
    }

    public char getEng() {
        return eng;
    }

    public void setEng(char eng) {
        this.eng = eng;
    }

    public short getNump() {
        return nump;
    }

    public void setNump(short nump) {
        this.nump = nump;
    }

//    public char getCodZone() {
    public String getCodZone() {
        return codZone;
    }

//    public void setCodZone(char codZone) {
    public void setCodZone(String codZone) {
        this.codZone = codZone;
    }
    
    public String getMarkZon() {
        return markZon;
    }
    
    public void setMarkZon(String markZon) {
        this.markZon = markZon;
    }

    @Override
    public int hashCode() {
/*        
        String hash = "";
        hash += (String) yea.;
        hash += (String) mon. ;
        hash += (String) idPlat;
        hash += (String) idTu;
        hash += (String) eng;
        hash += (String) nump;
        hash += (String) codZone;
   */
        int hash = 0;
        hash += (int) yea;
        hash += (int) mon;
        hash += (int) idPlat;
        hash += (int) idTu;
        hash += (int) eng;
        hash += (int) nump;
        hash += (int) codZone.hashCode();
                 
        hash += (int) markZon.hashCode();
/*        
        int hash = 0;
        long i = 0;
        i += (int) yea;
        i += (int) mon;
        i += (int) idPlat;
        i += (int) idTu;
        
        i += (int) eng;
        
        i += (int) nump;
        i += (int) codZone;
        
//    static int hashCode(long i) { return (int) ((i » 32) + (int)i); }  
        hash = (int) ((i >> 32) + (int)i);
*/        
        
        return hash;
    }
 

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RaPK)) {
            return false;
        }
        RaPK other = (RaPK) object;
        if (this.yea != other.yea) {
            return false;
        }
        if (this.mon != other.mon) {
            return false;
        }
        if (this.idPlat != other.idPlat) {
            return false;
        }
        if (this.idTu != other.idTu) {
            return false;
        }
        if (this.eng != other.eng) {
            return false;
        }
        if (this.nump != other.nump) {
            return false;
        }
//        if (this.codZone != other.codZone) {
        if (!this.codZone.equals(other.codZone)) {
            return false;
        }
//        if (this.markZon != other.markZon) {
        if (!this.markZon.equals(other.markZon)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.RaPK[ yea=" + yea + ", mon=" + mon + ", idPlat=" + idPlat + ", idTu=" + idTu + ", eng=" + eng + ", nump=" + nump + ", codZone=" + codZone + ", markZon="+markZon+ " ]";
    }

}
