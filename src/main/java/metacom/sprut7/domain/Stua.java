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
@Table(name = "STUA")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stua.findAll", query = "SELECT s FROM Stua s"),
    @NamedQuery(name = "Stua.findByIdTu", query = "SELECT s FROM Stua s WHERE s.idTu = :idTu"),
    @NamedQuery(name = "Stua.findByIdPlat", query = "SELECT s FROM Stua s WHERE s.idPlat = :idPlat"),
    @NamedQuery(name = "Stua.findByOff", query = "SELECT s FROM Stua s WHERE s.off = :off"),
    @NamedQuery(name = "Stua.findByPu", query = "SELECT s FROM Stua s WHERE s.pu = :pu"),
    @NamedQuery(name = "Stua.findByMethod", query = "SELECT s FROM Stua s WHERE s.method = :method"),
    @NamedQuery(name = "Stua.findByNametu", query = "SELECT s FROM Stua s WHERE s.nametu = :nametu"),
/*    
    @NamedQuery(name = "Stua.findByCodtu", query = "SELECT s FROM Stua s WHERE s.codtu = :codtu"),
    @NamedQuery(name = "Stua.findByIdtarif", query = "SELECT s FROM Stua s WHERE s.idtarif = :idtarif"),
    @NamedQuery(name = "Stua.findByBlst", query = "SELECT s FROM Stua s WHERE s.blst = :blst"),
    @NamedQuery(name = "Stua.findByNompittp", query = "SELECT s FROM Stua s WHERE s.nompittp = :nompittp"),
    @NamedQuery(name = "Stua.findByUstpotw", query = "SELECT s FROM Stua s WHERE s.ustpotw = :ustpotw"),
    @NamedQuery(name = "Stua.findByUstpots", query = "SELECT s FROM Stua s WHERE s.ustpots = :ustpots"),
    @NamedQuery(name = "Stua.findByKodrezvtu", query = "SELECT s FROM Stua s WHERE s.kodrezvtu = :kodrezvtu"),
    @NamedQuery(name = "Stua.findByTranzit", query = "SELECT s FROM Stua s WHERE s.tranzit = :tranzit"),
    @NamedQuery(name = "Stua.findByOtopl", query = "SELECT s FROM Stua s WHERE s.otopl = :otopl"),
    @NamedQuery(name = "Stua.findByKodminis", query = "SELECT s FROM Stua s WHERE s.kodminis = :kodminis"),
    @NamedQuery(name = "Stua.findByBezu", query = "SELECT s FROM Stua s WHERE s.bezu = :bezu"),
    @NamedQuery(name = "Stua.findByGru", query = "SELECT s FROM Stua s WHERE s.gru = :gru"),
    @NamedQuery(name = "Stua.findByNist", query = "SELECT s FROM Stua s WHERE s.nist = :nist"),
    @NamedQuery(name = "Stua.findByIdAdr", query = "SELECT s FROM Stua s WHERE s.idAdr = :idAdr"),
    @NamedQuery(name = "Stua.findByIdPotr", query = "SELECT s FROM Stua s WHERE s.idPotr = :idPotr"),
    @NamedQuery(name = "Stua.findByIdN23", query = "SELECT s FROM Stua s WHERE s.idN23 = :idN23"),
    @NamedQuery(name = "Stua.findById1b", query = "SELECT s FROM Stua s WHERE s.id1b = :id1b"),
    @NamedQuery(name = "Stua.findById35energo", query = "SELECT s FROM Stua s WHERE s.id35energo = :id35energo"),
    @NamedQuery(name = "Stua.findByPzpoint", query = "SELECT s FROM Stua s WHERE s.pzpoint = :pzpoint"),
    @NamedQuery(name = "Stua.findByDel", query = "SELECT s FROM Stua s WHERE s.del = :del"),
    @NamedQuery(name = "Stua.findByGen", query = "SELECT s FROM Stua s WHERE s.gen = :gen"),
    @NamedQuery(name = "Stua.findByAdress", query = "SELECT s FROM Stua s WHERE s.adress = :adress"),
    @NamedQuery(name = "Stua.findByYesRea", query = "SELECT s FROM Stua s WHERE s.yesRea = :yesRea"),
    @NamedQuery(name = "Stua.findByNolimit", query = "SELECT s FROM Stua s WHERE s.nolimit = :nolimit"),
    @NamedQuery(name = "Stua.findByBudget", query = "SELECT s FROM Stua s WHERE s.budget = :budget"),
    @NamedQuery(name = "Stua.findByNot100", query = "SELECT s FROM Stua s WHERE s.not100 = :not100"),
    @NamedQuery(name = "Stua.findByIdN23new", query = "SELECT s FROM Stua s WHERE s.idN23new = :idN23new"),
    @NamedQuery(name = "Stua.findByNaprlin", query = "SELECT s FROM Stua s WHERE s.naprlin = :naprlin"),
    @NamedQuery(name = "Stua.findByYesNadb", query = "SELECT s FROM Stua s WHERE s.yesNadb = :yesNadb"),
    @NamedQuery(name = "Stua.findByGkh", query = "SELECT s FROM Stua s WHERE s.gkh = :gkh"),
    @NamedQuery(name = "Stua.findByPoteri", query = "SELECT s FROM Stua s WHERE s.poteri = :poteri"),
    @NamedQuery(name = "Stua.findByNomPd", query = "SELECT s FROM Stua s WHERE s.nomPd = :nomPd"),
    @NamedQuery(name = "Stua.findByUstanRashod", query = "SELECT s FROM Stua s WHERE s.ustanRashod = :ustanRashod"),
    @NamedQuery(name = "Stua.findByMinusVDog", query = "SELECT s FROM Stua s WHERE s.minusVDog = :minusVDog"),
    @NamedQuery(name = "Stua.findByStrPok", query = "SELECT s FROM Stua s WHERE s.strPok = :strPok"),
    @NamedQuery(name = "Stua.findByAutoNtt", query = "SELECT s FROM Stua s WHERE s.autoNtt = :autoNtt"),
    @NamedQuery(name = "Stua.findByMps", query = "SELECT s FROM Stua s WHERE s.mps = :mps"),
    @NamedQuery(name = "Stua.findByKategory", query = "SELECT s FROM Stua s WHERE s.kategory = :kategory"),
    @NamedQuery(name = "Stua.findByDateOff", query = "SELECT s FROM Stua s WHERE s.dateOff = :dateOff"),
    @NamedQuery(name = "Stua.findByRejChas", query = "SELECT s FROM Stua s WHERE s.rejChas = :rejChas"),
    @NamedQuery(name = "Stua.findByRegVix", query = "SELECT s FROM Stua s WHERE s.regVix = :regVix"),
    @NamedQuery(name = "Stua.findByIdTuUstanRashod", query = "SELECT s FROM Stua s WHERE s.idTuUstanRashod = :idTuUstanRashod"),
    @NamedQuery(name = "Stua.findByTecOtS", query = "SELECT s FROM Stua s WHERE s.tecOtS = :tecOtS"),
    @NamedQuery(name = "Stua.findByTecGvKol", query = "SELECT s FROM Stua s WHERE s.tecGvKol = :tecGvKol"),
    @NamedQuery(name = "Stua.findByTecOtTip", query = "SELECT s FROM Stua s WHERE s.tecOtTip = :tecOtTip"),
    @NamedQuery(name = "Stua.findByPrigElektro", query = "SELECT s FROM Stua s WHERE s.prigElektro = :prigElektro"),
    @NamedQuery(name = "Stua.findByVodokanal", query = "SELECT s FROM Stua s WHERE s.vodokanal = :vodokanal"),
    @NamedQuery(name = "Stua.findByDetlager", query = "SELECT s FROM Stua s WHERE s.detlager = :detlager"),
    @NamedQuery(name = "Stua.findByTecKods", query = "SELECT s FROM Stua s WHERE s.tecKods = :tecKods"),
    @NamedQuery(name = "Stua.findByCodZone", query = "SELECT s FROM Stua s WHERE s.codZone = :codZone"),
    @NamedQuery(name = "Stua.findByUchetnatp", query = "SELECT s FROM Stua s WHERE s.uchetnatp = :uchetnatp"),
    @NamedQuery(name = "Stua.findByUchetnorm", query = "SELECT s FROM Stua s WHERE s.uchetnorm = :uchetnorm"),
    @NamedQuery(name = "Stua.findByUchetfasad", query = "SELECT s FROM Stua s WHERE s.uchetfasad = :uchetfasad"),
    @NamedQuery(name = "Stua.findByIdpu", query = "SELECT s FROM Stua s WHERE s.idpu = :idpu"),
    @NamedQuery(name = "Stua.findByDateOn", query = "SELECT s FROM Stua s WHERE s.dateOn = :dateOn"),
    @NamedQuery(name = "Stua.findByIszons", query = "SELECT s FROM Stua s WHERE s.iszons = :iszons"),
    @NamedQuery(name = "Stua.findByTransp", query = "SELECT s FROM Stua s WHERE s.transp = :transp"),
    @NamedQuery(name = "Stua.findByVidEs", query = "SELECT s FROM Stua s WHERE s.vidEs = :vidEs"),
    @NamedQuery(name = "Stua.findByIdEn32", query = "SELECT s FROM Stua s WHERE s.idEn32 = :idEn32"),
    @NamedQuery(name = "Stua.findByTarifSub", query = "SELECT s FROM Stua s WHERE s.tarifSub = :tarifSub"),
    @NamedQuery(name = "Stua.findByRaspoint", query = "SELECT s FROM Stua s WHERE s.raspoint = :raspoint"),
    @NamedQuery(name = "Stua.findByLinkTu", query = "SELECT s FROM Stua s WHERE s.linkTu = :linkTu"),
    @NamedQuery(name = "Stua.findByIdMuch", query = "SELECT s FROM Stua s WHERE s.idMuch = :idMuch"),
    @NamedQuery(name = "Stua.findByHnuzhd", query = "SELECT s FROM Stua s WHERE s.hnuzhd = :hnuzhd"),
    @NamedQuery(name = "Stua.findByIdSobstv", query = "SELECT s FROM Stua s WHERE s.idSobstv = :idSobstv"),
    @NamedQuery(name = "Stua.findByOkonchArenda", query = "SELECT s FROM Stua s WHERE s.okonchArenda = :okonchArenda"),
    @NamedQuery(name = "Stua.findByUstNagrTu", query = "SELECT s FROM Stua s WHERE s.ustNagrTu = :ustNagrTu"),
    @NamedQuery(name = "Stua.findByRegRabTu", query = "SELECT s FROM Stua s WHERE s.regRabTu = :regRabTu"),
    @NamedQuery(name = "Stua.findByShina", query = "SELECT s FROM Stua s WHERE s.shina = :shina"),
    @NamedQuery(name = "Stua.findByTehnopot", query = "SELECT s FROM Stua s WHERE s.tehnopot = :tehnopot"),
    @NamedQuery(name = "Stua.findByIdInspector", query = "SELECT s FROM Stua s WHERE s.idInspector = :idInspector"),
    @NamedQuery(name = "Stua.findByIsMtx", query = "SELECT s FROM Stua s WHERE s.isMtx = :isMtx"),
    @NamedQuery(name = "Stua.findByOnArenda", query = "SELECT s FROM Stua s WHERE s.onArenda = :onArenda"),
    @NamedQuery(name = "Stua.findByCerkov", query = "SELECT s FROM Stua s WHERE s.cerkov = :cerkov"),
    @NamedQuery(name = "Stua.findByIsIndikator", query = "SELECT s FROM Stua s WHERE s.isIndikator = :isIndikator"),
    @NamedQuery(name = "Stua.findByNomrub", query = "SELECT s FROM Stua s WHERE s.nomrub = :nomrub"),
    @NamedQuery(name = "Stua.findByProcV", query = "SELECT s FROM Stua s WHERE s.procV = :procV"),
    @NamedQuery(name = "Stua.findByProcK", query = "SELECT s FROM Stua s WHERE s.procK = :procK"),
    @NamedQuery(name = "Stua.findByProcT", query = "SELECT s FROM Stua s WHERE s.procT = :procT"),
    @NamedQuery(name = "Stua.findByRazrNagrTu", query = "SELECT s FROM Stua s WHERE s.razrNagrTu = :razrNagrTu"),
    @NamedQuery(name = "Stua.findByNompittp1", query = "SELECT s FROM Stua s WHERE s.nompittp1 = :nompittp1"),
    @NamedQuery(name = "Stua.findByYeantt", query = "SELECT s FROM Stua s WHERE s.yeantt = :yeantt"),
    @NamedQuery(name = "Stua.findByMonntt", query = "SELECT s FROM Stua s WHERE s.monntt = :monntt"),
    */
//нет такого    @NamedQuery(name = "Stua.findByIdZone", query = "SELECT s FROM Stua s WHERE s.idZone = :idZone")
})
public class Stua implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TU")
    private Integer idTu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_PLAT")
    private int idPlat;
    @Column(name = "OFF")
    private Character off;
    @Column(name = "PU")
    private Character pu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "METHOD")
    private char method;
    @Size(max = 35)
    @Column(name = "NAMETU")
    private String nametu;
/*    
    @Column(name = "CODTU")
    private Short codtu;
    @Column(name = "IDTARIF")
    private Integer idtarif;
    @Column(name = "BLST")
    private Character blst;
    @Column(name = "NOMPITTP")
    private Integer nompittp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "USTPOTW")
    private Double ustpotw;
    @Column(name = "USTPOTS")
    private Double ustpots;
    @Column(name = "KODREZVTU")
    private Double kodrezvtu;
    @Column(name = "TRANZIT")
    private Character tranzit;
    @Column(name = "OTOPL")
    private Character otopl;
    @Size(max = 5)
    @Column(name = "KODMINIS")
    private String kodminis;
    @Column(name = "BEZU")
    private Character bezu;
    @Column(name = "GRU")
    private Character gru;
    @Column(name = "NIST")
    private Integer nist;
    @Column(name = "ID_ADR")
    private Integer idAdr;
    @Column(name = "ID_POTR")
    private Integer idPotr;
    @Column(name = "ID_N23")
    private Short idN23;
    @Column(name = "ID_1B")
    private Character id1b;
    @Column(name = "ID_35ENERGO")
    private Short id35energo;
    @Column(name = "PZPOINT")
    private Character pzpoint;
    @Column(name = "DEL")
    @Temporal(TemporalType.TIMESTAMP)
    private Date del;
    @Column(name = "GEN")
    private Character gen;
    @Size(max = 35)
    @Column(name = "ADRESS")
    private String adress;
    @Column(name = "YES_REA")
    private Character yesRea;
    @Column(name = "NOLIMIT")
    private Character nolimit;
    @Size(max = 24)
    @Column(name = "BUDGET")
    private String budget;
    @Column(name = "NOT100")
    private Character not100;
    @Column(name = "ID_N23NEW")
    private Integer idN23new;
    @Column(name = "NAPRLIN")
    private Double naprlin;
    @Column(name = "YES_NADB")
    private Character yesNadb;
    @Column(name = "GKH")
    private Character gkh;
    @Column(name = "POTERI")
    private Character poteri;
    @Column(name = "NOM_PD")
    private Integer nomPd;
    @Size(max = 1)
    @Column(name = "USTAN_RASHOD")
    private String ustanRashod;
    @Size(max = 17)
    @Column(name = "MINUS_V_DOG")
    private String minusVDog;
    @Size(max = 12)
    @Column(name = "STR_POK")
    private String strPok;
    @Column(name = "AUTO_NTT")
    private Character autoNtt;
    @Column(name = "MPS")
    private Character mps;
    @Size(max = 2)
    @Column(name = "KATEGORY")
    private String kategory;
    @Column(name = "DATE_OFF")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOff;
    @Column(name = "REJ_CHAS")
    private BigDecimal rejChas;
    @Column(name = "REG_VIX")
    private Short regVix;
    @Column(name = "ID_TU_USTAN_RASHOD")
    private Integer idTuUstanRashod;
    @Column(name = "TEC_OT_S")
    private Double tecOtS;
    @Column(name = "TEC_GV_KOL")
    private Short tecGvKol;
    @Column(name = "TEC_OT_TIP")
    private Short tecOtTip;
    @Column(name = "PRIG_ELEKTRO")
    private Character prigElektro;
    @Column(name = "VODOKANAL")
    private Character vodokanal;
    @Column(name = "DETLAGER")
    private Character detlager;
    @Column(name = "TEC_KODS")
    private Short tecKods;
    @Column(name = "COD_ZONE")
    private Character codZone;
    @Column(name = "UCHETNATP")
    private Character uchetnatp;
    @Column(name = "UCHETNORM")
    private Character uchetnorm;
    @Column(name = "UCHETFASAD")
    private Character uchetfasad;
    @Column(name = "IDPU")
    private Short idpu;
    @Column(name = "DATE_ON")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOn;
    @Column(name = "ISZONS")
    private Character iszons;
    @Column(name = "TRANSP")
    private Character transp;
    @Column(name = "VID_ES")
    private Character vidEs;
    @Column(name = "ID_EN32")
    private Integer idEn32;
    @Column(name = "TARIF_SUB")
    private Integer tarifSub;
    @Column(name = "RASPOINT")
    private Character raspoint;
    @Column(name = "LINK_TU")
    private Integer linkTu;
    @Column(name = "ID_MUCH")
    private Integer idMuch;
    @Lob
    @Size(max = 2147483647)
    @Column(name = "REM")
    private String rem;
    @Column(name = "HNUZHD")
    private Character hnuzhd;
    @Column(name = "ID_SOBSTV")
    private Integer idSobstv;
    @Column(name = "OKONCH_ARENDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date okonchArenda;
    @Column(name = "UST_NAGR_TU")
    private Double ustNagrTu;
    @Size(max = 25)
    @Column(name = "REG_RAB_TU")
    private String regRabTu;
    @Column(name = "SHINA")
    private Character shina;
    @Column(name = "TEHNOPOT")
    private Character tehnopot;
    @Column(name = "ID_INSPECTOR")
    private Integer idInspector;
    @Column(name = "IS_MTX")
    private Character isMtx;
    @Column(name = "ON_ARENDA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date onArenda;
    @Column(name = "CERKOV")
    private Character cerkov;
    @Column(name = "IS_INDIKATOR")
    private Character isIndikator;
    @Column(name = "NOMRUB")
    private Integer nomrub;
    @Column(name = "PROC_V")
    private Double procV;
    @Column(name = "PROC_K")
    private Double procK;
    @Column(name = "PROC_T")
    private Double procT;
    @Column(name = "RAZR_NAGR_TU")
    private Double razrNagrTu;
    @Column(name = "NOMPITTP1")
    private Integer nompittp1;
    @Column(name = "YEANTT")
    private Integer yeantt;
    @Column(name = "MONNTT")
    private Integer monntt;
    */
//нет такого    @Column(name = "ID_ZONE")
//нет такого    private Integer idZone;
//-----------------------------------------------------------------------------
@OneToMany(mappedBy = "stua")
private Collection<Ra> raCollection;    
//-----------------------------------------------------------------------------
    
    public Stua() {
    }

    public Stua(Integer idTu) {
        this.idTu = idTu;
    }

    public Stua(Integer idTu, int idPlat, char method) {
        this.idTu = idTu;
        this.idPlat = idPlat;
        this.method = method;
    }

    public Integer getIdTu() {
        return idTu;
    }

    public void setIdTu(Integer idTu) {
        this.idTu = idTu;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public Character getOff() {
        return off;
    }

    public void setOff(Character off) {
        this.off = off;
    }

    public Character getPu() {
        return pu;
    }

    public void setPu(Character pu) {
        this.pu = pu;
    }

    public char getMethod() {
        return method;
    }

    public void setMethod(char method) {
        this.method = method;
    }


    public String getNametu() {
        return nametu;
    }

    public void setNametu(String nametu) {
        this.nametu = nametu;
    }
    
/*    
    public Short getCodtu() {
        return codtu;
    }

    public void setCodtu(Short codtu) {
        this.codtu = codtu;
    }

    public String getNametu() {
        return nametu;
    }

    public void setNametu(String nametu) {
        this.nametu = nametu;
    }

    public Integer getIdtarif() {
        return idtarif;
    }

    public void setIdtarif(Integer idtarif) {
        this.idtarif = idtarif;
    }

    public Character getBlst() {
        return blst;
    }

    public void setBlst(Character blst) {
        this.blst = blst;
    }

    public Integer getNompittp() {
        return nompittp;
    }

    public void setNompittp(Integer nompittp) {
        this.nompittp = nompittp;
    }

    public Double getUstpotw() {
        return ustpotw;
    }

    public void setUstpotw(Double ustpotw) {
        this.ustpotw = ustpotw;
    }

    public Double getUstpots() {
        return ustpots;
    }

    public void setUstpots(Double ustpots) {
        this.ustpots = ustpots;
    }

    public Double getKodrezvtu() {
        return kodrezvtu;
    }

    public void setKodrezvtu(Double kodrezvtu) {
        this.kodrezvtu = kodrezvtu;
    }

    public Character getTranzit() {
        return tranzit;
    }

    public void setTranzit(Character tranzit) {
        this.tranzit = tranzit;
    }

    public Character getOtopl() {
        return otopl;
    }

    public void setOtopl(Character otopl) {
        this.otopl = otopl;
    }

    public String getKodminis() {
        return kodminis;
    }

    public void setKodminis(String kodminis) {
        this.kodminis = kodminis;
    }

    public Character getBezu() {
        return bezu;
    }

    public void setBezu(Character bezu) {
        this.bezu = bezu;
    }

    public Character getGru() {
        return gru;
    }

    public void setGru(Character gru) {
        this.gru = gru;
    }

    public Integer getNist() {
        return nist;
    }

    public void setNist(Integer nist) {
        this.nist = nist;
    }

    public Integer getIdAdr() {
        return idAdr;
    }

    public void setIdAdr(Integer idAdr) {
        this.idAdr = idAdr;
    }

    public Integer getIdPotr() {
        return idPotr;
    }

    public void setIdPotr(Integer idPotr) {
        this.idPotr = idPotr;
    }

    public Short getIdN23() {
        return idN23;
    }

    public void setIdN23(Short idN23) {
        this.idN23 = idN23;
    }

    public Character getId1b() {
        return id1b;
    }

    public void setId1b(Character id1b) {
        this.id1b = id1b;
    }

    public Short getId35energo() {
        return id35energo;
    }

    public void setId35energo(Short id35energo) {
        this.id35energo = id35energo;
    }

    public Character getPzpoint() {
        return pzpoint;
    }

    public void setPzpoint(Character pzpoint) {
        this.pzpoint = pzpoint;
    }

    public Date getDel() {
        return del;
    }

    public void setDel(Date del) {
        this.del = del;
    }

    public Character getGen() {
        return gen;
    }

    public void setGen(Character gen) {
        this.gen = gen;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public Character getYesRea() {
        return yesRea;
    }

    public void setYesRea(Character yesRea) {
        this.yesRea = yesRea;
    }

    public Character getNolimit() {
        return nolimit;
    }

    public void setNolimit(Character nolimit) {
        this.nolimit = nolimit;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Character getNot100() {
        return not100;
    }

    public void setNot100(Character not100) {
        this.not100 = not100;
    }

    public Integer getIdN23new() {
        return idN23new;
    }

    public void setIdN23new(Integer idN23new) {
        this.idN23new = idN23new;
    }

    public Double getNaprlin() {
        return naprlin;
    }

    public void setNaprlin(Double naprlin) {
        this.naprlin = naprlin;
    }

    public Character getYesNadb() {
        return yesNadb;
    }

    public void setYesNadb(Character yesNadb) {
        this.yesNadb = yesNadb;
    }

    public Character getGkh() {
        return gkh;
    }

    public void setGkh(Character gkh) {
        this.gkh = gkh;
    }

    public Character getPoteri() {
        return poteri;
    }

    public void setPoteri(Character poteri) {
        this.poteri = poteri;
    }

    public Integer getNomPd() {
        return nomPd;
    }

    public void setNomPd(Integer nomPd) {
        this.nomPd = nomPd;
    }

    public String getUstanRashod() {
        return ustanRashod;
    }

    public void setUstanRashod(String ustanRashod) {
        this.ustanRashod = ustanRashod;
    }

    public String getMinusVDog() {
        return minusVDog;
    }

    public void setMinusVDog(String minusVDog) {
        this.minusVDog = minusVDog;
    }

    public String getStrPok() {
        return strPok;
    }

    public void setStrPok(String strPok) {
        this.strPok = strPok;
    }

    public Character getAutoNtt() {
        return autoNtt;
    }

    public void setAutoNtt(Character autoNtt) {
        this.autoNtt = autoNtt;
    }

    public Character getMps() {
        return mps;
    }

    public void setMps(Character mps) {
        this.mps = mps;
    }

    public String getKategory() {
        return kategory;
    }

    public void setKategory(String kategory) {
        this.kategory = kategory;
    }

    public Date getDateOff() {
        return dateOff;
    }

    public void setDateOff(Date dateOff) {
        this.dateOff = dateOff;
    }

    public BigDecimal getRejChas() {
        return rejChas;
    }

    public void setRejChas(BigDecimal rejChas) {
        this.rejChas = rejChas;
    }

    public Short getRegVix() {
        return regVix;
    }

    public void setRegVix(Short regVix) {
        this.regVix = regVix;
    }

    public Integer getIdTuUstanRashod() {
        return idTuUstanRashod;
    }

    public void setIdTuUstanRashod(Integer idTuUstanRashod) {
        this.idTuUstanRashod = idTuUstanRashod;
    }

    public Double getTecOtS() {
        return tecOtS;
    }

    public void setTecOtS(Double tecOtS) {
        this.tecOtS = tecOtS;
    }

    public Short getTecGvKol() {
        return tecGvKol;
    }

    public void setTecGvKol(Short tecGvKol) {
        this.tecGvKol = tecGvKol;
    }

    public Short getTecOtTip() {
        return tecOtTip;
    }

    public void setTecOtTip(Short tecOtTip) {
        this.tecOtTip = tecOtTip;
    }

    public Character getPrigElektro() {
        return prigElektro;
    }

    public void setPrigElektro(Character prigElektro) {
        this.prigElektro = prigElektro;
    }

    public Character getVodokanal() {
        return vodokanal;
    }

    public void setVodokanal(Character vodokanal) {
        this.vodokanal = vodokanal;
    }

    public Character getDetlager() {
        return detlager;
    }

    public void setDetlager(Character detlager) {
        this.detlager = detlager;
    }

    public Short getTecKods() {
        return tecKods;
    }

    public void setTecKods(Short tecKods) {
        this.tecKods = tecKods;
    }

    public Character getCodZone() {
        return codZone;
    }

    public void setCodZone(Character codZone) {
        this.codZone = codZone;
    }

    public Character getUchetnatp() {
        return uchetnatp;
    }

    public void setUchetnatp(Character uchetnatp) {
        this.uchetnatp = uchetnatp;
    }

    public Character getUchetnorm() {
        return uchetnorm;
    }

    public void setUchetnorm(Character uchetnorm) {
        this.uchetnorm = uchetnorm;
    }

    public Character getUchetfasad() {
        return uchetfasad;
    }

    public void setUchetfasad(Character uchetfasad) {
        this.uchetfasad = uchetfasad;
    }

    public Short getIdpu() {
        return idpu;
    }

    public void setIdpu(Short idpu) {
        this.idpu = idpu;
    }

    public Date getDateOn() {
        return dateOn;
    }

    public void setDateOn(Date dateOn) {
        this.dateOn = dateOn;
    }

    public Character getIszons() {
        return iszons;
    }

    public void setIszons(Character iszons) {
        this.iszons = iszons;
    }

    public Character getTransp() {
        return transp;
    }

    public void setTransp(Character transp) {
        this.transp = transp;
    }

    public Character getVidEs() {
        return vidEs;
    }

    public void setVidEs(Character vidEs) {
        this.vidEs = vidEs;
    }

    public Integer getIdEn32() {
        return idEn32;
    }

    public void setIdEn32(Integer idEn32) {
        this.idEn32 = idEn32;
    }

    public Integer getTarifSub() {
        return tarifSub;
    }

    public void setTarifSub(Integer tarifSub) {
        this.tarifSub = tarifSub;
    }

    public Character getRaspoint() {
        return raspoint;
    }

    public void setRaspoint(Character raspoint) {
        this.raspoint = raspoint;
    }

    public Integer getLinkTu() {
        return linkTu;
    }

    public void setLinkTu(Integer linkTu) {
        this.linkTu = linkTu;
    }

    public Integer getIdMuch() {
        return idMuch;
    }

    public void setIdMuch(Integer idMuch) {
        this.idMuch = idMuch;
    }

    public String getRem() {
        return rem;
    }

    public void setRem(String rem) {
        this.rem = rem;
    }

    public Character getHnuzhd() {
        return hnuzhd;
    }

    public void setHnuzhd(Character hnuzhd) {
        this.hnuzhd = hnuzhd;
    }

    public Integer getIdSobstv() {
        return idSobstv;
    }

    public void setIdSobstv(Integer idSobstv) {
        this.idSobstv = idSobstv;
    }

    public Date getOkonchArenda() {
        return okonchArenda;
    }

    public void setOkonchArenda(Date okonchArenda) {
        this.okonchArenda = okonchArenda;
    }

    public Double getUstNagrTu() {
        return ustNagrTu;
    }

    public void setUstNagrTu(Double ustNagrTu) {
        this.ustNagrTu = ustNagrTu;
    }

    public String getRegRabTu() {
        return regRabTu;
    }

    public void setRegRabTu(String regRabTu) {
        this.regRabTu = regRabTu;
    }

    public Character getShina() {
        return shina;
    }

    public void setShina(Character shina) {
        this.shina = shina;
    }

    public Character getTehnopot() {
        return tehnopot;
    }

    public void setTehnopot(Character tehnopot) {
        this.tehnopot = tehnopot;
    }

    public Integer getIdInspector() {
        return idInspector;
    }

    public void setIdInspector(Integer idInspector) {
        this.idInspector = idInspector;
    }

    public Character getIsMtx() {
        return isMtx;
    }

    public void setIsMtx(Character isMtx) {
        this.isMtx = isMtx;
    }

    public Date getOnArenda() {
        return onArenda;
    }

    public void setOnArenda(Date onArenda) {
        this.onArenda = onArenda;
    }

    public Character getCerkov() {
        return cerkov;
    }

    public void setCerkov(Character cerkov) {
        this.cerkov = cerkov;
    }

    public Character getIsIndikator() {
        return isIndikator;
    }

    public void setIsIndikator(Character isIndikator) {
        this.isIndikator = isIndikator;
    }

    public Integer getNomrub() {
        return nomrub;
    }

    public void setNomrub(Integer nomrub) {
        this.nomrub = nomrub;
    }

    public Double getProcV() {
        return procV;
    }

    public void setProcV(Double procV) {
        this.procV = procV;
    }

    public Double getProcK() {
        return procK;
    }

    public void setProcK(Double procK) {
        this.procK = procK;
    }

    public Double getProcT() {
        return procT;
    }

    public void setProcT(Double procT) {
        this.procT = procT;
    }

    public Double getRazrNagrTu() {
        return razrNagrTu;
    }

    public void setRazrNagrTu(Double razrNagrTu) {
        this.razrNagrTu = razrNagrTu;
    }

    public Integer getNompittp1() {
        return nompittp1;
    }

    public void setNompittp1(Integer nompittp1) {
        this.nompittp1 = nompittp1;
    }

    public Integer getYeantt() {
        return yeantt;
    }

    public void setYeantt(Integer yeantt) {
        this.yeantt = yeantt;
    }

    public Integer getMonntt() {
        return monntt;
    }

    public void setMonntt(Integer monntt) {
        this.monntt = monntt;
    }
*/
//нет такого    public Integer getIdZone() {
//нет такого        return idZone;
//нет такого    }

//нет такого    public void setIdZone(Integer idZone) {
//нет такого        this.idZone = idZone;
//нет такого    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTu != null ? idTu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stua)) {
            return false;
        }
        Stua other = (Stua) object;
        if ((this.idTu == null && other.idTu != null) || (this.idTu != null && !this.idTu.equals(other.idTu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.maven_sprut.domain.Stua[ idTu=" + idTu + " ]";
    }
    
}
