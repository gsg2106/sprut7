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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
//import org.eclipse.persistence.annotations.ReadOnly;

/**
 *
 * @author Сергей
 */
//@ReadOnly
@Entity
@Table(name = "SPUTU")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sputu.findAll", query = "SELECT s FROM Sputu s"),
    @NamedQuery(name = "Sputu.findByIdPutu", query = "SELECT s FROM Sputu s WHERE s.idPutu = :idPutu"),
    @NamedQuery(name = "Sputu.findByZavno", query = "SELECT s FROM Sputu s WHERE s.zavno = :zavno"),
    @NamedQuery(name = "Sputu.findByIdPlat", query = "SELECT s FROM Sputu s WHERE s.idPlat = :idPlat"),
    @NamedQuery(name = "Sputu.findByIdTu", query = "SELECT s FROM Sputu s WHERE s.idTu = :idTu"),
    @NamedQuery(name = "Sputu.findByKtr", query = "SELECT s FROM Sputu s WHERE s.ktr = :ktr"),
    @NamedQuery(name = "Sputu.findByDatesnat", query = "SELECT s FROM Sputu s WHERE s.datesnat = :datesnat"),
/*    
    @NamedQuery(name = "Sputu.findByTypecnt", query = "SELECT s FROM Sputu s WHERE s.typecnt = :typecnt"),
    @NamedQuery(name = "Sputu.findByEng", query = "SELECT s FROM Sputu s WHERE s.eng = :eng"),
    @NamedQuery(name = "Sputu.findByDatnar", query = "SELECT s FROM Sputu s WHERE s.datnar = :datnar"),
    @NamedQuery(name = "Sputu.findByDatesnat", query = "SELECT s FROM Sputu s WHERE s.datesnat = :datesnat"),
    @NamedQuery(name = "Sputu.findByGener", query = "SELECT s FROM Sputu s WHERE s.gener = :gener"),
    @NamedQuery(name = "Sputu.findByIdHistCounter", query = "SELECT s FROM Sputu s WHERE s.idHistCounter = :idHistCounter"),
    @NamedQuery(name = "Sputu.findByStatus", query = "SELECT s FROM Sputu s WHERE s.status = :status"),
    @NamedQuery(name = "Sputu.findByMono", query = "SELECT s FROM Sputu s WHERE s.mono = :mono"),
    @NamedQuery(name = "Sputu.findByIdTrans", query = "SELECT s FROM Sputu s WHERE s.idTrans = :idTrans"),
    @NamedQuery(name = "Sputu.findByNump", query = "SELECT s FROM Sputu s WHERE s.nump = :nump"),
    @NamedQuery(name = "Sputu.findByNachPok", query = "SELECT s FROM Sputu s WHERE s.nachPok = :nachPok"),
    @NamedQuery(name = "Sputu.findByNumPlomb", query = "SELECT s FROM Sputu s WHERE s.numPlomb = :numPlomb"),
    @NamedQuery(name = "Sputu.findByDateGos", query = "SELECT s FROM Sputu s WHERE s.dateGos = :dateGos"),
    @NamedQuery(name = "Sputu.findBySterg", query = "SELECT s FROM Sputu s WHERE s.sterg = :sterg"),
    @NamedQuery(name = "Sputu.findByLast", query = "SELECT s FROM Sputu s WHERE s.last = :last"),
    @NamedQuery(name = "Sputu.findByKlVtPlomb", query = "SELECT s FROM Sputu s WHERE s.klVtPlomb = :klVtPlomb"),
    @NamedQuery(name = "Sputu.findByKlSchPlomb", query = "SELECT s FROM Sputu s WHERE s.klSchPlomb = :klSchPlomb"),
    @NamedQuery(name = "Sputu.findByKorobPlomb", query = "SELECT s FROM Sputu s WHERE s.korobPlomb = :korobPlomb"),
    @NamedQuery(name = "Sputu.findByTNar", query = "SELECT s FROM Sputu s WHERE s.tNar = :tNar"),
    @NamedQuery(name = "Sputu.findByTPr", query = "SELECT s FROM Sputu s WHERE s.tPr = :tPr"),
    @NamedQuery(name = "Sputu.findByIdZamer", query = "SELECT s FROM Sputu s WHERE s.idZamer = :idZamer"),
    @NamedQuery(name = "Sputu.findByZnachn", query = "SELECT s FROM Sputu s WHERE s.znachn = :znachn"),
    @NamedQuery(name = "Sputu.findByPrior", query = "SELECT s FROM Sputu s WHERE s.prior = :prior"),
    @NamedQuery(name = "Sputu.findByNext", query = "SELECT s FROM Sputu s WHERE s.next = :next")
    */
    })
public class Sputu implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PUTU")
    private Integer idPutu;
    @Size(max = 20)
    @Column(name = "ZAVNO")
    private String zavno;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLAT")
    private int idPlat;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TU")
    private int idTu;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "KTR")
    private BigDecimal ktr;
    @Column(name = "DATESNAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datesnat;
/*    
    @Column(name = "TYPECNT")
    private Short typecnt;
    @Column(name = "ENG")
    private Character eng;
    @Column(name = "DATNAR")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datnar;
    @Column(name = "DATESNAT")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datesnat;
    @Column(name = "GENER")
    private Character gener;
    @Column(name = "ID_HIST_COUNTER")
    private Integer idHistCounter;
    @Size(max = 1)
    @Column(name = "STATUS")
    private String status;
    @Size(max = 1)
    @Column(name = "MONO")
    private String mono;
    @Column(name = "ID_TRANS")
    private Integer idTrans;
    @Column(name = "NUMP")
    private Short nump;
    @Size(max = 14)
    @Column(name = "NACH_POK")
    private String nachPok;
    @Size(max = 40)
    @Column(name = "NUM_PLOMB")
    private String numPlomb;
    @Column(name = "DATE_GOS")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateGos;
    @Column(name = "STERG")
    private Character sterg;
    @Column(name = "LAST_")
    private Character last;
    @Size(max = 12)
    @Column(name = "KL_VT_PLOMB")
    private String klVtPlomb;
    @Size(max = 12)
    @Column(name = "KL_SCH_PLOMB")
    private String klSchPlomb;
    @Size(max = 12)
    @Column(name = "KOROB_PLOMB")
    private String korobPlomb;
    @Column(name = "T_NAR")
    private Double tNar;
    @Column(name = "T_PR")
    private Double tPr;
    @Column(name = "ID_ZAMER")
    private Integer idZamer;
    @Size(max = 14)
    @Column(name = "ZNACHN")
    private String znachn;
    @Column(name = "PRIOR_")
    private Integer prior;
    @Column(name = "NEXT_")
    private Integer next;
    */
//-----------------------------------------------------------------------------
@OneToMany(mappedBy = "sputu")
private Collection<Ra> raCollection;
//-----------------------------------------------------------------------------
    
    public Sputu() {
    }

    public Sputu(Integer idPutu) {
        this.idPutu = idPutu;
    }

    public Sputu(Integer idPutu, int idPlat, int idTu) {
        this.idPutu = idPutu;
        this.idPlat = idPlat;
        this.idTu = idTu;
    }

    public Integer getIdPutu() {
        return idPutu;
    }

    public void setIdPutu(Integer idPutu) {
        this.idPutu = idPutu;
    }

    public String getZavno() {
        return zavno;
    }

    public void setZavno(String zavno) {
        this.zavno = zavno;
    }


    public Date getDatesnat() {
        return datesnat;
    }

    public void setDatesnat(Date datesnat) {
        this.datesnat = datesnat;
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

    public BigDecimal getKtr() {
        return ktr;
    }

    public void setKtr(BigDecimal ktr) {
        this.ktr = ktr;
    }

    
/*    
    public Short getTypecnt() {
        return typecnt;
    }

    public void setTypecnt(Short typecnt) {
        this.typecnt = typecnt;
    }

    public Character getEng() {
        return eng;
    }

    public void setEng(Character eng) {
        this.eng = eng;
    }

    public Date getDatnar() {
        return datnar;
    }

    public void setDatnar(Date datnar) {
        this.datnar = datnar;
    }

    public Date getDatesnat() {
        return datesnat;
    }

    public void setDatesnat(Date datesnat) {
        this.datesnat = datesnat;
    }

    public Character getGener() {
        return gener;
    }

    public void setGener(Character gener) {
        this.gener = gener;
    }

    public Integer getIdHistCounter() {
        return idHistCounter;
    }

    public void setIdHistCounter(Integer idHistCounter) {
        this.idHistCounter = idHistCounter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMono() {
        return mono;
    }

    public void setMono(String mono) {
        this.mono = mono;
    }

    public Integer getIdTrans() {
        return idTrans;
    }

    public void setIdTrans(Integer idTrans) {
        this.idTrans = idTrans;
    }

    public Short getNump() {
        return nump;
    }

    public void setNump(Short nump) {
        this.nump = nump;
    }

    public String getNachPok() {
        return nachPok;
    }

    public void setNachPok(String nachPok) {
        this.nachPok = nachPok;
    }

    public String getNumPlomb() {
        return numPlomb;
    }

    public void setNumPlomb(String numPlomb) {
        this.numPlomb = numPlomb;
    }

    public Date getDateGos() {
        return dateGos;
    }

    public void setDateGos(Date dateGos) {
        this.dateGos = dateGos;
    }

    public Character getSterg() {
        return sterg;
    }

    public void setSterg(Character sterg) {
        this.sterg = sterg;
    }

    public Character getLast() {
        return last;
    }

    public void setLast(Character last) {
        this.last = last;
    }

    public String getKlVtPlomb() {
        return klVtPlomb;
    }

    public void setKlVtPlomb(String klVtPlomb) {
        this.klVtPlomb = klVtPlomb;
    }

    public String getKlSchPlomb() {
        return klSchPlomb;
    }

    public void setKlSchPlomb(String klSchPlomb) {
        this.klSchPlomb = klSchPlomb;
    }

    public String getKorobPlomb() {
        return korobPlomb;
    }

    public void setKorobPlomb(String korobPlomb) {
        this.korobPlomb = korobPlomb;
    }

    public Double getTNar() {
        return tNar;
    }

    public void setTNar(Double tNar) {
        this.tNar = tNar;
    }

    public Double getTPr() {
        return tPr;
    }

    public void setTPr(Double tPr) {
        this.tPr = tPr;
    }

    public Integer getIdZamer() {
        return idZamer;
    }

    public void setIdZamer(Integer idZamer) {
        this.idZamer = idZamer;
    }

    public String getZnachn() {
        return znachn;
    }

    public void setZnachn(String znachn) {
        this.znachn = znachn;
    }

    public Integer getPrior() {
        return prior;
    }

    public void setPrior(Integer prior) {
        this.prior = prior;
    }

    public Integer getNext() {
        return next;
    }

    public void setNext(Integer next) {
        this.next = next;
    }
*/
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPutu != null ? idPutu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sputu)) {
            return false;
        }
        Sputu other = (Sputu) object;
        if ((this.idPutu == null && other.idPutu != null) || (this.idPutu != null && !this.idPutu.equals(other.idPutu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.Sputu[ idPutu=" + idPutu + " ]";
    }
    
}
