package metacom.sprut7.domain;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 25.07.16
 * Time: 22:46
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "SZAJPLAN")
@XmlRootElement
@IdClass(LimitKey.class)
public class SzajPlan implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID_POTR")
    private Integer idPotr;
    @Id
    @Column(name = "YEARS")
    private Integer years;
    @Id
    @Column(name = "MONTHS")
    private Integer month;
    @Column(name = "ZAJ_VAL")
    private Integer zajVal;
    @Column(name = "DATE_PROVDOC")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateProvDoc;
    @Column(name = "ID_ISP_PROV")
    private Integer idIspProv;
    @Column(name = "HAND")
    private Integer hand;
    @Column(name = "HOLIDAY")
    private Integer holiday;
    @Column(name = "POTR_HOLI")
    private Integer potrHoli;
    @Column(name = "DAY_OFF_WEEK")
    private Integer dayOffWeek;   //, рабочих дней в неделю – режим работы
    @Column(name = "CHAS")
    private Integer chas;  // рабочих часов в день – режим работы
    @Column(name = "WEB_VERIFY")
    private Character webVerify; // WEB  данные прошли проверку - 1 -да
    @Column(name = "WEB_PODPIS")
    private Character webPodpis;  // – данные подписаны пользователем 0 – нет, 1 – да
    @Transient
    private String nameMonth;

    public Integer getIdPotr() {
        return idPotr;
    }

    public void setIdPotr(Integer idPotr) {
        this.idPotr = idPotr;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getZajVal() {
        return zajVal;
    }

    public void setZajVal(Integer zajVal) {
        this.zajVal = zajVal;
    }

    public Date getDateProvDoc() {
        return dateProvDoc;
    }

    public void setDateProvDoc(Date dateProvDoc) {
        this.dateProvDoc = dateProvDoc;
    }

    public Integer getIdIspProv() {
        return idIspProv;
    }

    public void setIdIspProv(Integer idIspProv) {
        this.idIspProv = idIspProv;
    }

    public Integer getHand() {
        return hand;
    }

    public void setHand(Integer hand) {
        this.hand = hand;
    }

    public Integer getHoliday() {
        return holiday;
    }

    public void setHoliday(Integer holiday) {
        this.holiday = holiday;
    }

    public Integer getPotrHoli() {
        return potrHoli;
    }

    public void setPotrHoli(Integer potrHoli) {
        this.potrHoli = potrHoli;
    }

    public Integer getDayOffWeek() {
        return dayOffWeek;
    }

    public void setDayOffWeek(Integer dayOffWeek) {
        this.dayOffWeek = dayOffWeek;
    }

    public Integer getChas() {
        return chas;
    }

    public void setChas(Integer chas) {
        this.chas = chas;
    }

    public Character getWebVerify() {
        return webVerify;
    }

    public void setWebVerify(Character webVerify) {
        this.webVerify = webVerify;
    }

    public Character getWebPodpis() {
        return webPodpis;
    }

    public void setWebPodpis(Character webPodpis) {
        this.webPodpis = webPodpis;
    }

    public String getNameMonth() {
        if (month != null){
            Month mon = Month.of(month);
            Locale loc = Locale.forLanguageTag("ru");
            return mon.getDisplayName(TextStyle.FULL_STANDALONE, loc);
        } else return  null;
    }

    public void setNameMonth(String nameMonth) {}
}
