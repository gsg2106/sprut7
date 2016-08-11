/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Сергей
 */
@Entity
@Table(name = "SCHET")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schet.findAll", query = "SELECT s FROM Schet s"),
    @NamedQuery(name = "Schet.findByTypecnt", query = "SELECT s FROM Schet s WHERE s.typecnt = :typecnt"),
    @NamedQuery(name = "Schet.findByNTyps", query = "SELECT s FROM Schet s WHERE s.nTyps = :nTyps"),
    @NamedQuery(name = "Schet.findByZnachn", query = "SELECT s FROM Schet s WHERE s.znachn = :znachn"),
    @NamedQuery(name = "Schet.findByEngs", query = "SELECT s FROM Schet s WHERE s.engs = :engs"),
    @NamedQuery(name = "Schet.findByIdClasspu", query = "SELECT s FROM Schet s WHERE s.idClasspu = :idClasspu"),
    @NamedQuery(name = "Schet.findByNaprMin", query = "SELECT s FROM Schet s WHERE s.naprMin = :naprMin"),
    @NamedQuery(name = "Schet.findByNaprMax", query = "SELECT s FROM Schet s WHERE s.naprMax = :naprMax"),
    @NamedQuery(name = "Schet.findByTokMin", query = "SELECT s FROM Schet s WHERE s.tokMin = :tokMin"),
    @NamedQuery(name = "Schet.findByCountSh", query = "SELECT s FROM Schet s WHERE s.countSh = :countSh"),
    @NamedQuery(name = "Schet.findByFaza", query = "SELECT s FROM Schet s WHERE s.faza = :faza"),
    @NamedQuery(name = "Schet.findByImport1", query = "SELECT s FROM Schet s WHERE s.import1 = :import1"),
    @NamedQuery(name = "Schet.findByIdMeasure", query = "SELECT s FROM Schet s WHERE s.idMeasure = :idMeasure"),
    @NamedQuery(name = "Schet.findByIdProizvod", query = "SELECT s FROM Schet s WHERE s.idProizvod = :idProizvod"),
    @NamedQuery(name = "Schet.findByOldBad", query = "SELECT s FROM Schet s WHERE s.oldBad = :oldBad"),
    @NamedQuery(name = "Schet.findByElectronics", query = "SELECT s FROM Schet s WHERE s.electronics = :electronics"),
    @NamedQuery(name = "Schet.findByIdTypecCounter", query = "SELECT s FROM Schet s WHERE s.idTypecCounter = :idTypecCounter"),
    @NamedQuery(name = "Schet.findByCodUnit", query = "SELECT s FROM Schet s WHERE s.codUnit = :codUnit"),
    @NamedQuery(name = "Schet.findByPeriodTest", query = "SELECT s FROM Schet s WHERE s.periodTest = :periodTest"),
    @NamedQuery(name = "Schet.findByNaprNom", query = "SELECT s FROM Schet s WHERE s.naprNom = :naprNom"),
    @NamedQuery(name = "Schet.findByTokNom", query = "SELECT s FROM Schet s WHERE s.tokNom = :tokNom"),
    @NamedQuery(name = "Schet.findByIdUslug", query = "SELECT s FROM Schet s WHERE s.idUslug = :idUslug"),
    @NamedQuery(name = "Schet.findBySmena", query = "SELECT s FROM Schet s WHERE s.smena = :smena"),
    @NamedQuery(name = "Schet.findByMarkirovka", query = "SELECT s FROM Schet s WHERE s.markirovka = :markirovka"),
    @NamedQuery(name = "Schet.findByPorogChuvst", query = "SELECT s FROM Schet s WHERE s.porogChuvst = :porogChuvst"),
    @NamedQuery(name = "Schet.findByClassTochn", query = "SELECT s FROM Schet s WHERE s.classTochn = :classTochn"),
    @NamedQuery(name = "Schet.findByKtr", query = "SELECT s FROM Schet s WHERE s.ktr = :ktr"),
    @NamedQuery(name = "Schet.findByUchetReak", query = "SELECT s FROM Schet s WHERE s.uchetReak = :uchetReak"),
    @NamedQuery(name = "Schet.findByUchetGener", query = "SELECT s FROM Schet s WHERE s.uchetGener = :uchetGener"),
    @NamedQuery(name = "Schet.findByUchetDvuhst", query = "SELECT s FROM Schet s WHERE s.uchetDvuhst = :uchetDvuhst"),
    @NamedQuery(name = "Schet.findByObogrev", query = "SELECT s FROM Schet s WHERE s.obogrev = :obogrev"),
    @NamedQuery(name = "Schet.findByClassTochnReak", query = "SELECT s FROM Schet s WHERE s.classTochnReak = :classTochnReak"),
    @NamedQuery(name = "Schet.findByTokMax", query = "SELECT s FROM Schet s WHERE s.tokMax = :tokMax"),
    @NamedQuery(name = "Schet.findByZnachn0", query = "SELECT s FROM Schet s WHERE s.znachn0 = :znachn0"),
    @NamedQuery(name = "Schet.findByIdUser", query = "SELECT s FROM Schet s WHERE s.idUser = :idUser")})
public class Schet implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
 //  @GeneratedValue(strategy=SEQUENCE, generator= "")
    @Column(name = "TYPECNT")
    private Integer typecnt;
    @Size(max = 34)
    @Column(name = "N_TYPS")
    private String nTyps;
    @Size(max = 14)
    @Column(name = "ZNACHN")
    private String znachn;
    @Column(name = "ENGS")
    private String engs;
    @Column(name = "ID_CLASSPU")
    private Integer idClasspu;
    @Column(name = "NAPR_MIN")
    private Integer naprMin;
    @Column(name = "NAPR_MAX")
    private Integer naprMax;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "TOK_MIN")
    private Double tokMin;
    @Column(name = "COUNT_SH")
    private Integer countSh;
    @Column(name = "FAZA")
    private String faza;
    @Column(name = "IMPORT")
    private Character import1;
    @Column(name = "ID_MEASURE")
    private Integer idMeasure;
    @Column(name = "ID_PROIZVOD")
    private Integer idProizvod;
    @Column(name = "OLD_BAD")
    private String  oldBad;
    @Column(name = "ELECTRONICS")
    private Character electronics;
    @Column(name = "ID_TYPEC_COUNTER")
    private Integer idTypecCounter;
    @Column(name = "COD_UNIT")
    private Short codUnit;
    @Column(name = "PERIOD_TEST")
    private Integer periodTest;
    @Column(name = "NAPR_NOM")
    private Integer naprNom;
    @Column(name = "TOK_NOM")
    private Integer tokNom;
    @Column(name = "ID_USLUG")
    private Character idUslug;
    @Column(name = "SMENA")
    private Integer smena;
    @Size(max = 30)
    @Column(name = "MARKIROVKA")
    private String markirovka;
    @Size(max = 10)
    @Column(name = "POROG_CHUVST")
    private String porogChuvst;
    @Size(max = 6)
    @Column(name = "CLASS_TOCHN")
    private String classTochn;
    @Column(name = "KTR")
    private Double ktr;
    @Column(name = "UCHET_REAK")
    private String uchetReak;
    @Column(name = "UCHET_GENER")
    private String uchetGener;
    @Column(name = "UCHET_DVUHST")
    private String uchetDvuhst;
    @Column(name = "OBOGREV")
    private String obogrev;
    @Size(max = 6)
    @Column(name = "CLASS_TOCHN_REAK")
    private String classTochnReak;
    @Column(name = "TOK_MAX")
    private Double tokMax;
    @Size(max = 14)
    @Column(name = "ZNACHN0")
    private String znachn0;
    @Column(name = "ID_USER")
    private Integer idUser;
    //------------------------------------------------------------------------------
    @JoinColumn(name = "ID_TYPEC_COUNTER", referencedColumnName = "ID_TYPEC_COUNTER", insertable=false, updatable=false)
    @ManyToOne
    private TypeCounter typeCounter;
    //------------------------------------------------------------------------------
    @Transient
    @Size(max = 16)
    private String engName;
    @Transient
    @Size(max = 20)
    private String nameType;
    @Transient 
    @Size(max =14)
    private String nameZnachn;
    @Transient
    private Boolean uchetDvuhstBul;
    @Transient
    private Boolean uchetGenReak;
    @Transient 
    private  Boolean baskue;
    @Transient 
    private  Boolean bobogrev;
    public Schet() {
    }

    public Schet(Integer typecnt) {
        this.typecnt = typecnt;
    }

    public Integer getTypecnt() {
        return typecnt;
    }

    public void setTypecnt(Integer typecnt) {
        this.typecnt = typecnt;
    }

    public String getNTyps() {
        return nTyps;
    }

    public void setNTyps(String nTyps) {
        this.nTyps = nTyps;
    }

    public String getZnachn() {
        return znachn;
    }

    public void setZnachn(String znachn) {
        this.znachn = znachn;
    }

    public String getEngs(){
        return engs;
    }

    public void setEngs(String engs) {
        this.engs = engs;
    }

    public Integer getIdClasspu() {
        return idClasspu;
    }

    public void setIdClasspu(Integer idClasspu) {
        this.idClasspu = idClasspu;
    }

    public Integer getNaprMin() {
        return naprMin;
    }

    public void setNaprMin(Integer naprMin) {
        this.naprMin = naprMin;
    }

    public Integer getNaprMax() {
        return naprMax;
    }

    public void setNaprMax(Integer naprMax) {
        this.naprMax = naprMax;
    }

    public Double getTokMin() {
        return tokMin;
    }

    public void setTokMin(Double tokMin) {
        this.tokMin = tokMin;
    }

    public Integer getCountSh() {
        return countSh;
    }

    public void setCountSh(Integer countSh) {
        this.countSh = countSh;
    }

    public String getFaza() {
        return faza;
    }

    public void setFaza(String faza) {
        this.faza = faza;
    }

    public Character getImport1() {
        return import1;
    }

    public void setImport1(Character import1) {
        this.import1 = import1;
    }

    public Integer getIdMeasure() {
        return idMeasure;
    }

    public void setIdMeasure(Integer idMeasure) {
        this.idMeasure = idMeasure;
    }

    public Integer getIdProizvod() {
        return idProizvod;
    }

    public void setIdProizvod(Integer idProizvod) {
        this.idProizvod = idProizvod;
    }

    public String getOldBad() {
        return oldBad;
    }

    public void setOldBad(String oldBad) {
        this.oldBad = oldBad;
    }

    public Character getElectronics() {
        return electronics;
    }

    public void setElectronics(Character electronics) {
        this.electronics = electronics;
    }

    public Integer getIdTypecCounter() {
        return idTypecCounter;
    }

    public void setIdTypecCounter(Integer idTypecCounter) {
        this.idTypecCounter = idTypecCounter;
    }

    public Short getCodUnit() {
        return codUnit;
    }

    public void setCodUnit(Short codUnit) {
        this.codUnit = codUnit;
    }

    public Integer getPeriodTest() {
        return periodTest;
    }

    public void setPeriodTest(Integer periodTest) {
        this.periodTest = periodTest;
    }

    public Integer getNaprNom() {
        return naprNom;
    }

    public void setNaprNom(Integer naprNom) {
        this.naprNom = naprNom;
    }

    public Integer getTokNom() {
        return tokNom;
    }

    public void setTokNom(Integer tokNom) {
        this.tokNom = tokNom;
    }

    public Character getIdUslug() {
        return idUslug;
    }

    public void setIdUslug(Character idUslug) {
        this.idUslug = idUslug;
    }

    public Integer getSmena() {
        return smena;
    }

    public void setSmena(Integer smena) {
        this.smena = smena;
    }

    public String getMarkirovka() {
        return markirovka;
    }

    public void setMarkirovka(String markirovka) {
        this.markirovka = markirovka;
    }

    public String getPorogChuvst() {
        return porogChuvst;
    }

    public void setPorogChuvst(String porogChuvst) {
        this.porogChuvst = porogChuvst;
    }

    public String getClassTochn() {
        return classTochn;
    }

    public void setClassTochn(String classTochn) {
        this.classTochn = classTochn;
    }

    public Double getKtr() {
           return ktr;
    }

    public void setKtr(Double ktr) {
        this.ktr = ktr;
    }

    public String getUchetReak() {
        return uchetReak;
    }

    public void setUchetReak(String uchetReak) {
        this.uchetReak = uchetReak;
    }

    public String  getUchetGener() {
        return uchetGener;
    }

    public void setUchetGener(String uchetGener) {
        this.uchetGener = uchetGener;
    }

    public String getUchetDvuhst() {
        return uchetDvuhst;
    }

    public void setUchetDvuhst(String uchetDvuhst) {
        this.uchetDvuhst = uchetDvuhst;
    }

    public String getObogrev() {
        return obogrev;
    }

    public void setObogrev(String obogrev) {
        this.obogrev = obogrev;
    }

    public String getClassTochnReak() {
        return classTochnReak;
    }

    public void setClassTochnReak(String classTochnReak) {
        this.classTochnReak = classTochnReak;
    }

    public Double getTokMax() {
        return tokMax;
    }

    public void setTokMax(Double tokMax) {
        this.tokMax = tokMax;
    }

    public String getZnachn0() {
        return znachn0;
    }

    public void setZnachn0(String znachn0) {
        this.znachn0 = znachn0;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    //------------------------------------------------------------------------------
    public TypeCounter getTypeCounter(){
        return typeCounter;
    }

    public void setStur(TypeCounter typeCounter){
        this.typeCounter = typeCounter;
    }
    //------------------------------------------------------------------------------
    public String getEngName(){
      if ( getEngs().equals("A")){
         return "Активный"; 
      } else if ( getEngs().equals("R")){
         return "Реактивный"; 
      } else {
         return "Комбинированный"; 
      } 
    }
    public void setEngName(String engName){
     this.engName=engName;   
    }
    public String getNameType (){
       if (typeCounter == null)
           return "Неопределен";
       else
         return typeCounter.getNameType();
    }    
    
   public void setNameType(String nameType){
       this.nameType=nameType;
   } 
    public String getNameZnachn(){
     if (getZnachn()==null) {   
      return "Программ.";}
      else
       return getZnachn();  
   }
    
   public void setNameZnachn (String nameZnachn){
       this.nameZnachn=nameZnachn;
   }

    public Boolean getUchetDvuhstBul() {
        if (getUchetDvuhst() == null)
            return null;
        else if (getUchetDvuhst().equals("1"))
            return true;
        else 
            return false;
    }

    public void setUchetDvuhstBul(Boolean uchetDvuhstBul) {
        if (uchetDvuhstBul == null)
            this.uchetDvuhst = null;
        else if (uchetDvuhstBul)
            this.uchetDvuhst = "1";
        else 
            this.uchetDvuhst ="0";
        this.uchetDvuhstBul = uchetDvuhstBul;
    }
   
    public Boolean getUchetGenReak( ) {
        if (getUchetGener() == null)
            return null;
        else if (getUchetGener().equals("1"))
            return true;
        else 
            return false;
    }  
 
    public void setUchetGenReak(Boolean uchetGenReak) {
        if (uchetGenReak == null)
            this.uchetGener = null;
        else if (uchetGenReak)
            this.uchetGener = "1";
        else 
            this.uchetGener ="0";
        this.uchetGenReak = uchetGenReak;
    }  
  public Boolean getBaskue() {
        if (getOldBad() == null)
            return null;
        else if (getOldBad().equals("1"))
            return true;
        else 
            return false;
    }
   public void setBaskue(Boolean baskue) {
        if (baskue == null)
            this.oldBad = null;
        else if (baskue)
            this.oldBad = "1";
        else 
            this.oldBad ="0";
        this.baskue = baskue;
    }    
  public Boolean getBobogrev( ) {
        if (getObogrev() == null)
            return null;
        else if (getObogrev().equals("1"))
            return true;
        else 
            return false;
    } 
   public void setBobogrev(Boolean bobogrev) {
        if (bobogrev == null)
            this.obogrev = null;
        else if (bobogrev)
            this.obogrev = "1";
        else 
            this.obogrev ="0";
        this.bobogrev = bobogrev;
    }     
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (typecnt != null ? typecnt.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Schet)) {
            return false;
        }
        Schet other = (Schet) object;
        if ((this.typecnt == null && other.typecnt != null) || (this.typecnt != null && !this.typecnt.equals(other.typecnt))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "metacom.sprut7.domain.Schet[ typecnt=" + typecnt + " ]";
    }
    
}
