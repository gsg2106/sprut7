/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "RA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ra.findAll", query = "SELECT r FROM Ra r"),
    @NamedQuery(name = "Ra.findByYea", query = "SELECT r FROM Ra r WHERE r.raPK.yea = :yea"),
    @NamedQuery(name = "Ra.findByMon", query = "SELECT r FROM Ra r WHERE r.raPK.mon = :mon"),
    @NamedQuery(name = "Ra.findByIdPlat", query = "SELECT r FROM Ra r WHERE r.raPK.idPlat = :idPlat"),
    @NamedQuery(name = "Ra.findByIdTu", query = "SELECT r FROM Ra r WHERE r.raPK.idTu = :idTu"),
    @NamedQuery(name = "Ra.findByEng", query = "SELECT r FROM Ra r WHERE r.raPK.eng = :eng"),
    @NamedQuery(name = "Ra.findByNump", query = "SELECT r FROM Ra r WHERE r.raPK.nump = :nump"),
    @NamedQuery(name = "Ra.findByIdPutu", query = "SELECT r FROM Ra r WHERE r.idPutu = :idPutu"),
    @NamedQuery(name = "Ra.findByCodZone", query = "SELECT r FROM Ra r WHERE r.raPK.codZone = :codZone"),
    @NamedQuery(name = "Ra.findByCodtu", query = "SELECT r FROM Ra r WHERE r.codtu = :codtu"),
    @NamedQuery(name = "Ra.findByNachalo", query = "SELECT r FROM Ra r WHERE r.nachalo = :nachalo"),
    @NamedQuery(name = "Ra.findByDanrap", query = "SELECT r FROM Ra r WHERE r.danrap = :danrap"),
    @NamedQuery(name = "Ra.findByRazn", query = "SELECT r FROM Ra r WHERE r.razn = :razn"),
    @NamedQuery(name = "Ra.findByKvtchrazn", query = "SELECT r FROM Ra r WHERE r.kvtchrazn = :kvtchrazn"),
    @NamedQuery(name = "Ra.findByMarkZon", query = "SELECT r FROM Ra r WHERE r.raPK.markZon = :markZon"),
    @NamedQuery(name = "Ra.findByIdScale", query = "SELECT r FROM Ra r WHERE r.idScale = :idScale"),
/*    
    @NamedQuery(name = "Ra.findByPott", query = "SELECT r FROM Ra r WHERE r.pott = :pott"),
    @NamedQuery(name = "Ra.findByPotl", query = "SELECT r FROM Ra r WHERE r.potl = :potl"),
    @NamedQuery(name = "Ra.findByKvtchv", query = "SELECT r FROM Ra r WHERE r.kvtchv = :kvtchv"),
    @NamedQuery(name = "Ra.findByPzstr", query = "SELECT r FROM Ra r WHERE r.pzstr = :pzstr"),
    @NamedQuery(name = "Ra.findByDate1", query = "SELECT r FROM Ra r WHERE r.date1 = :date1"),
    @NamedQuery(name = "Ra.findByDate2", query = "SELECT r FROM Ra r WHERE r.date2 = :date2"),
    @NamedQuery(name = "Ra.findByTrasp", query = "SELECT r FROM Ra r WHERE r.trasp = :trasp"),
    @NamedQuery(name = "Ra.findByOrig", query = "SELECT r FROM Ra r WHERE r.orig = :orig"),
    @NamedQuery(name = "Ra.findByKNagrProc", query = "SELECT r FROM Ra r WHERE r.kNagrProc = :kNagrProc"),
    @NamedQuery(name = "Ra.findByNedoEe", query = "SELECT r FROM Ra r WHERE r.nedoEe = :nedoEe"),
    @NamedQuery(name = "Ra.findByTexno", query = "SELECT r FROM Ra r WHERE r.texno = :texno"),
    @NamedQuery(name = "Ra.findByIsTrasp", query = "SELECT r FROM Ra r WHERE r.isTrasp = :isTrasp"),
    @NamedQuery(name = "Ra.findByIdZamer", query = "SELECT r FROM Ra r WHERE r.idZamer = :idZamer"),
    @NamedQuery(name = "Ra.findByMinProc", query = "SELECT r FROM Ra r WHERE r.minProc = :minProc")
    */
    })
public class Ra implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RaPK raPK;
    @Column(name = "CODTU")
    private Short codtu;
    @Size(max = 14)
    @Column(name = "NACHALO")
    private String nachalo;
    @Size(max = 14)
    @Column(name = "DANRAP")
    private String danrap;
    @Size(max = 8)
    @Column(name = "RAZN")
    private String razn;
    @Column(name = "KVTCHRAZN")
    private Integer kvtchrazn;
    @Column(name = "ID_SCALE")
    private Integer idScale;

    public Integer getIdScale() {
        return idScale;
    }

    public void setIdScale(Integer idScale) {
        this.idScale = idScale;
    }
    
    
/*    
    @Column(name = "POTT")
    private Integer pott;
    @Column(name = "POTL")
    private Integer potl;
    @Column(name = "KVTCHV")
    private Integer kvtchv;
    @Size(max = 5)
    @Column(name = "PZSTR")
    private String pzstr;
    @Column(name = "DATE1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date1;
    @Column(name = "DATE2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date2;
    @Column(name = "TRASP")
    private Character trasp;
    @Column(name = "ORIG")
    private Integer orig;
    @Size(max = 7)
    @Column(name = "K_NAGR_PROC")
    private String kNagrProc;
    @Column(name = "NEDO_EE")
    private Integer nedoEe;
    @Column(name = "TEXNO")
    private Character texno;
    @Column(name = "IS_TRASP")
    private Character isTrasp;
    @Column(name = "ID_ZAMER")
    private Integer idZamer;
    @Size(max = 3)
    @Column(name = "MIN_PROC")
    private String minProc;
*/    
//------------------------------------------------------------------------------
@JoinColumn(name = "ID_PUTU", referencedColumnName = "ID_PUTU", insertable=false, updatable=false)
@ManyToOne
private Sputu sputu;
//private Sputu idPutu;
@Column(name = "ID_PUTU")
private Integer idPutu;
//------------------------------------------------------------------------------
//@JoinColumns({ это интересно
@JoinColumn(name = "ID_TU", referencedColumnName = "ID_TU", insertable=false, updatable=false)
//})
////@JoinColumn(name = "ID_TU")
@ManyToOne
private Stua stua;
//------------------------------------------------------------------------------
@JoinColumn(name = "ID_TU", referencedColumnName = "ID_TU", insertable=false, updatable=false)
@ManyToOne
private Stur stur;
//------------------------------------------------------------------------------
@JoinColumn(name = "COD_ZONE", referencedColumnName = "COD_ZONE", insertable=false, updatable=false)
@ManyToOne
private Pzone pzone;
//------------------------------------------------------------------------------

    @Transient
//    @Size(max = 3)
//    @Column(name = "MARK_ZON")
    private String markZon;
    public String getMarkZon() {
        return raPK.getMarkZon();
    }

    public void setMarkZon(String markZon) {
        raPK.setMarkZon(markZon);
        //markZon = markZon;
    }
    
    
    
    //@Column(name = "KTR")
    @Transient
    private BigDecimal ktr;    

    public BigDecimal getKtr() {
        //return ktr;
        if (sputu == null) {
            return null;
        } else {
            return sputu.getKtr();
        }    
    }

    @Transient
    private String zavno;    
//    public String getsputu() {
    public String getzavno() {
        if (sputu == null) {
            return "?";
        } else {
            return sputu.getZavno();
        }    
    }
    
    @Transient
    private Character offPu;    

    public Character getOffPu() {
        return stua.getOff();
    }
    
    @Transient
    private Character nametu;    

    public String getNametu() {
        return stua.getNametu();
    }
    
/* Непонятно что это такое и кгда это я написал   
    public void setCodtu(BigDecimal ktr) {
        this.idPutu.setKtr(ktr) ;
    }
 */   
    
    @Transient
    private char eng;
    public char getEng() {
        return raPK.getEng();
    }
    public void setEng(char eng) {
        raPK.setEng(eng);
    }
    
    @Transient
    private String codZone;
    public String getCodZone() {
        return raPK.getCodZone();
    }

    @Transient
    private String nameZone;
    public String getNameZone() {
        return pzone.getNameZone();
    }

//------------------------------------------------------------------------------
    @Transient
    private Character puOn;    
            
    public Character getpuOn() {
        char ret = '0';
        if (('1' == stua.getPu()) && ('A' == getEng() )){
            ret = '1';
        } else
        if (('1' == stua.getPu()) && ('X' == getEng() )){
            ret = '1';
        } else
        if (stur != null) {
           if (('1' == stur.getPu()) && ('R' == getEng() )){
              ret = '1';
           }
        }    
        return ret;
    }
    
    @Transient
    public String stateRecord;
    
    public String getStateRecord(){
        String ret;
        if (getOffPu() == '1') {
           ret = "Точка отключена"; 
        } else{ if (getpuOn() == '1') {
                ret = "По показаниям";
              } else {
                 ret = "Безучетная";
              }
        }
        return ret;
    }

    @Transient
    private Character snat;    
    public Character getSnat() {
        if (sputu == null) {
            return '1';
        } else {
            if (sputu.getDatesnat()!=null){
                return '1';
            } else return '0';
        }    
    }
    
    
    @Transient
    public Integer readOnly;
    
    public Integer getReadOnly(){
        Integer ret;
        if (getSnat() == '1') { ret = 1;}
        else if (getOffPu() == '1') {ret = 1;}
        else if (getpuOn() == '1') {ret = 0;}
                else {ret =1;}
        return ret;
    }
    
//    @Transient
//    private double kpokaz;
//    
//    public double getKpokaz(){
//        return kpokaz;
//    }
//    
//    public void setKpokaz(double kp){
//        kpokaz = kp;
//    }
//------------------------------------------------------------------------------
    
    public void setCodZone(String codZone) {
        raPK.setCodZone(codZone);
    }
    
    
    
    public Ra() {
    }

    public Ra(RaPK raPK) {
        this.raPK = raPK;
    }

//    public Ra(short yea, short mon, int idPlat, int idTu, char eng, short nump, char codZone) {
    public Ra(short yea, short mon, int idPlat, int idTu, char eng, short nump, String codZone) {
        this.raPK = new RaPK(yea, mon, idPlat, idTu, eng, nump, codZone, markZon);
    }

    public RaPK getRaPK() {
        return raPK;
    }

    public void setRaPK(RaPK raPK) {
        this.raPK = raPK;
    }

    public Short getCodtu() {
        return codtu;
    }

    public void setCodtu(Short codtu) {
        this.codtu = codtu;
    }

    public String getNachalo() {
        return nachalo;
    }

    public void setNachalo(String nachalo) {
        this.nachalo = nachalo;
    }

    public String getDanrap() {
        return danrap;
    }

    public void setDanrap(String danrap) {
        this.danrap = danrap;
    }

    public String getRazn() {
        return razn;
    }

    public void setRazn(String razn) {
        this.razn = razn;
    }

    public Integer getKvtchrazn() {
        return kvtchrazn;
    }

    public void setKvtchrazn(Integer kvtchrazn) {
        this.kvtchrazn = kvtchrazn;
    }

/*    
    public Integer getPott() {
        return pott;
    }

    public void setPott(Integer pott) {
        this.pott = pott;
    }

    public Integer getPotl() {
        return potl;
    }

    public void setPotl(Integer potl) {
        this.potl = potl;
    }

    public Integer getKvtchv() {
        return kvtchv;
    }

    public void setKvtchv(Integer kvtchv) {
        this.kvtchv = kvtchv;
    }

    public String getPzstr() {
        return pzstr;
    }

    public void setPzstr(String pzstr) {
        this.pzstr = pzstr;
    }
   
    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Character getTrasp() {
        return trasp;
    }

    public void setTrasp(Character trasp) {
        this.trasp = trasp;
    }

    public Integer getOrig() {
        return orig;
    }

    public void setOrig(Integer orig) {
        this.orig = orig;
    }

    public String getKNagrProc() {
        return kNagrProc;
    }

    public void setKNagrProc(String kNagrProc) {
        this.kNagrProc = kNagrProc;
    }

    public Integer getNedoEe() {
        return nedoEe;
    }

    public void setNedoEe(Integer nedoEe) {
        this.nedoEe = nedoEe;
    }

    public Character getTexno() {
        return texno;
    }

    public void setTexno(Character texno) {
        this.texno = texno;
    }

    public Character getIsTrasp() {
        return isTrasp;
    }

    public void setIsTrasp(Character isTrasp) {
        this.isTrasp = isTrasp;
    }


    public Integer getIdZamer() {
        return idZamer;
    }

    public void setIdZamer(Integer idZamer) {
        this.idZamer = idZamer;
    }

    public String getMinProc() {
        return minProc;
    }

    public void setMinProc(String minProc) {
        this.minProc = minProc;
    }
*/
    
//------------------------------------------------------------------------------
public Sputu getSputu() {
        return sputu;
    }

public void setSputu(Sputu sputu) {
        this.sputu = sputu;
    }
//------------------------------------------------------------------------------
public Stua getStua(){
    return stua;
}

public void setStua(Stua stua){
    this.stua = stua;
}
//------------------------------------------------------------------------------
public Stur getStur(){
    return stur;
}

public void setStur(Stur stur){
    this.stur = stur;
}
//------------------------------------------------------------------------------
        
public Integer getIdPutu() {
        return idPutu;
}

public void setIdPutu(Integer idPutu) {
        this.idPutu = idPutu;
}


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (raPK != null ? raPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ra)) {
            return false;
        }
        Ra other = (Ra) object;
        if ((this.raPK == null && other.raPK != null) || (this.raPK != null && !this.raPK.equals(other.raPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.Ra[ raPK=" + raPK + " ]";
    }
    
}
