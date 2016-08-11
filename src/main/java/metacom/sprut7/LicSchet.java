/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.data.Property;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.J2EEConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.*;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.domain.GsgWebOborot;

/**
 *
 * @author Сергей
 */
public class LicSchet  extends VerticalLayout implements IComponentContainer {
    private Integer idplat;
    private String linkRes;
    private InlineDateField datetime;
    private Integer year;
    private Integer month;    
    private Panel pan;
    private DateFormat df = new SimpleDateFormat("dd/MM/yy");
    private StatePlat statePlat;
    private String linkPool;
    private java.util.Calendar calendar = GregorianCalendar.getInstance();
    private Integer idTipM = null;
    private Integer sostav = 0;
    private ComboBox comboBoxVidNacisl;
    private ComboBox comboBoxForma;


    LicSchet() {
    }
    
    public void init(){
        //linkRes =(String) VaadinSession.getCurrent().getAttribute("linkRes");
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        this.idplat = statePlat.getIdPlat();
        linkPool = statePlat.getLinkPool();

        setMargin(true);
        setStyleName("DolgiWindow");
//        setHeight("100%");        
//        setSizeFull();
        BuildMain();
        setImmediate(true);

    }
    private void BuildMain(){
       Statistika stat = new Statistika();
       stat.init(idplat, Statistika.st_lic );

        HorizontalLayout hl = new HorizontalLayout();
        hl.setMargin(true);
       // CheckBox checkBox
        J2EEConnectionPool connectionPool = new J2EEConnectionPool(linkPool);
        FreeformQuery dlg =  new FreeformQuery("select id_tipm, MENU from STIPDEB_M", connectionPool,"id_tipm");
        dlg.setDelegate(new ViewDelegate("STIPDEB_M"));
        SQLContainer vidNacisl = null;
        try {
            vidNacisl = new SQLContainer(dlg);
        } catch (SQLException ex) {
            Logger.getLogger(LispTuWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        Object itemId = vidNacisl.addItem();
        //Next Lin Nullpointer
        vidNacisl.getItem(itemId).getItemProperty("ID_TIPM").setValue(-1);
        vidNacisl.getItem(itemId).getItemProperty("MENU").setValue("Все виды начислений");

        comboBoxVidNacisl = new ComboBox("Виды начислений",vidNacisl);
        comboBoxVidNacisl.setInputPrompt("Все виды начислений");
        comboBoxVidNacisl.setItemCaptionPropertyId("MENU");
        comboBoxVidNacisl.setPageLength(25);
        comboBoxVidNacisl.setNullSelectionAllowed(true);


//        String nullitem = "Все виды начислений";
//        comboBoxVidNacisl.addItem(nullitem);
        comboBoxVidNacisl.setNullSelectionItemId(itemId);

        comboBoxVidNacisl.addValueChangeListener(new ComboBox.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                Object value = valueChangeEvent.getProperty().getValue();
                if (value == null) {  //|| !(value instanceof ComboBox)) {
                    //Notification.show("Введена неверная дата");
                    idTipM = null;
                } else {
                    String s = valueChangeEvent.getProperty().getValue().toString();
                    idTipM = Integer.valueOf(s);
                }
                refresh(year, month, idTipM, sostav);
            }
        });
        comboBoxForma = new ComboBox("Форма представления");
        comboBoxForma.setNullSelectionAllowed(false);
        comboBoxForma.addItems("Краткая форма", "подробная форма");
        comboBoxForma.setValue("Краткая форма");
        comboBoxForma.addValueChangeListener(new ComboBox.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                String value = (String) valueChangeEvent.getProperty().getValue();
                if (value.equals("Краткая форма")){
                    sostav = 0;
                } else {
                    sostav = 1;
                }
                refresh(year, month, idTipM, sostav);

            }
        });


        datetime = new InlineDateField("Укажите за какой месяц и год вы желаете посмотреть лицевой счет.");
        // текущая дата
        Date dt = new java.util.Date();
	    calendar.setTime(dt);
        year = calendar.get(java.util.Calendar.YEAR);
        month = calendar.get(java.util.Calendar.MONTH)+1;
        datetime.setValue(dt);
        datetime.setResolution(InlineDateField.RESOLUTION_MONTH);
        Locale ru = new Locale("ru");
        datetime.setLocale(ru);
        datetime.setImmediate(true);
        datetime.addListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Object value = event.getProperty().getValue();
                if (value == null || !(value instanceof Date)) {
                    Notification.show("Введена неверная дата");
                } else {
                    calendar.setTime((Date) value);
                    year = calendar.get(java.util.Calendar.YEAR);
                    month = calendar.get(java.util.Calendar.MONTH) + 1;

                    //Object idTipM1 = comboBoxVidNacisl.getValue();
                    //Object sostav1 = comboBoxForma.getValue();

                    refresh(year, month, idTipM, sostav);
                }
            }
        });
        //datetime.setWidth();
        //datetime.setWidthUndefined();
        hl.addComponent(comboBoxVidNacisl);
        hl.addComponent(comboBoxForma);
        hl.addComponent(datetime);
        addComponent(hl);
        
        pan = new Panel("Оборотная ведомость (Начальное сальдо, начисления, выставления,оплаты и конечное сальдо)");
        pan.setWidth("100%");
        pan.setHeight("100%");
        pan.setHeight("585px");
        addComponent(pan);

        //Object idTipM1 = comboBoxVidNacisl.getValue();
        //sostav1 = comboBoxForma.getValue();
        //comboBoxForma.getItemI(comboBoxForma.getValue());

        refresh(year, month, idTipM, sostav);
       
    }
    
    private void refresh(Integer year, Integer month,Integer idTipM, Integer sostav){
        //pan.removeAllComponents();
        pan.setContent(null);
        
        StringBuffer output = new StringBuffer(110);
        EntityManager em = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
//        Collection emps = em.createQuery("SELECT g FROM GsgWebOborot g WHERE g.idPlat = :idplat and g.yea = :yea  and g.mon = :mon order by g.hh, g.dateDoc").
//                setParameter("idplat", idplat).setParameter("yea", year).setParameter("mon", month).getResultList();
        Collection emps = null;
        String ss1 = idTipM == null ? "": " and g.idTipm = :idTipM";
        String ss2 = sostav == 1 ? "": " and SUBSTRING(g.hh ,cast(2 as integer) ,cast(2 as integer)) != cast('0' as varchar(1))";
        String sql = "SELECT g FROM GsgWebOborot g WHERE g.idPlat = :idplat and g.yea = :yea  and g.mon = :mon " + ss1 +ss2 + " order by g.hh, g.dateDoc";
        if (idTipM == null){
        //        emps = em.createNamedQuery(sql).
            emps = em.createQuery(sql).
                    setParameter("idplat", idplat).setParameter("yea", year).setParameter("mon", month).getResultList();
        } else {
            emps = em.createQuery(sql).
                setParameter("idplat", idplat).setParameter("yea", year).setParameter("mon", month).setParameter("idTipM",idTipM).getResultList();
        }

        Boolean oborotExists = !emps.isEmpty();
        if (oborotExists) {
            Iterator it = emps.iterator();
            String hh; String oldhh="00";
            Boolean tableActive = false;
            while (it.hasNext()){
                GsgWebOborot e = (GsgWebOborot) it.next();
                hh = e.getHh();
                if (hh.charAt(0) != oldhh.charAt(0)){
                   if (tableActive) {
                      output.append("</tbody></table>");        
                      tableActive = false;
                   }   
                   if (hh.charAt(0) == '1') {
                      // вывести на экран шапку начального сальдо
                      tableActive = true;
                       output.append("<H2>Сальдо на начало месяца</H2>");
//                       output.append("<H2>Начальное сальдо на ");
//                       String  ss = "1 "+getMonthsRodPadeg(month)+" "+ year.toString()+" года";
////                       String  ss = getMonths(month)+ " месяц " + year.toString()+" года";
//                       output.append(ss);
//                       output.append("</H2>");
                      GenHeaderSaldo(output);
                   } else
                   if (hh.charAt(0) == '2') {
                      // вывести на экран шапку начислено
                      tableActive = true;
                       output.append("<H2>Начислено в ");
                       String ss = getMonthsPredPad(month) + " "+ year.toString()+" г.";
                       output.append(ss);
                       output.append("</H2>");

                      GenHeaderNachisl(output);
                   } else
                   if (hh.charAt(0) == '3') {
                      // вывести на экран шапку выставлено
                      tableActive = true;
                      output.append("<H2>Счета на предоплату и плановые счета выписанные в ");
                      String ss = getMonthsPredPad(month) + " "+ year.toString()+" г.";
                      if (e.getNonActual().intValue() == 1){
                         ss = ss + " - неактуальны, уже есть начисление по факту";
                      }

                       output.append(ss);

                      output.append("</H2>");

                      GenHeaderVistavl(output);
                   } else
                   if (hh.charAt(0) == '4') {
                      // вывести на экран шапку оплачено
                      tableActive = true;
                      output.append("<H2>Оплата поступившая в ");
                      String ss = getMonthsPredPad(month) + " "+ year.toString()+" г.";
                      output.append(ss);
                      output.append("</H2>");

                      GenHeaderOplata(output);
                   } else
                   if (hh.charAt(0) == '5') {
                      // вывести на экран шапку конечное сальдо
                      tableActive = true;
                       output.append("<H2>Сальдо на конец месяца</H2>");
//                      output.append("<H2>Конечное сальдо на ");
////                       String  ss = "1 "+getMonthsRodPadeg(month)+ year.toString()+" года";
//                       String  ss = getMonths(month)+ " месяц " + year.toString()+" года";
//                       output.append(ss);
//                       output.append("</H2>");
                      GenHeaderSaldo(output);
                   }
                   oldhh = hh;
                }
                //----------вывести на экран содержимое строки----------
                if (hh.charAt(0) == '1') {
                   GenBodySaldo(output, e);
                } else
                if (hh.charAt(0) == '2') {
                   GenBodyNachisl(output, e);
                } else
                if (hh.charAt(0) == '3') {
                   GenBodyVistavl(output, e);
                } else
                if (hh.charAt(0) == '4') {
                   GenBodyOplata(output, e);
                } else
                if (hh.charAt(0) == '5') {
                   GenBodySaldo(output, e);
                }
                //------------------------------------------------------
            }
            if (tableActive) {
              output.append("</tbody></table>");        
            }   
        } else {
            output.append("<H1></H1>");
        }
        emps.clear();
        em.close();
        
        try {
            CustomLayout custom = new CustomLayout(new ByteArrayInputStream(output.toString().getBytes("UTF-8")));
            pan.setContent(custom);
        } catch (IOException ex) {
            Logger.getLogger(Schets.class.getName()).log(Level.SEVERE, null, ex);
        }
        pan.setImmediate(true);
    }
    
    private void GenHeaderSaldo(StringBuffer output){
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
//        output.append(" <td rowspan=\"2\">Дата</td>");
        output.append(" <td rowspan=\"2\">Статья учета</td>");
        output.append(" <td colspan=\"3\" width=\"240\">Сумма по дебету (долги)</td>");
        output.append(" <td colspan=\"3\" width=\"240\">Сумма по кредиту</td>");
        output.append("</tr>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        output.append(" <td width=\"80\">Без НДС</td>");
        output.append(" <td width=\"80\">НДС</td>");
        output.append(" <td width=\"80\">Всего</td>");
        output.append(" <td width=\"80\">Без НДС</td>");
        output.append(" <td width=\"80\">НДС</td>");
        output.append(" <td width=\"80\">Всего</td>");
        output.append("</tr>");
    };
    
    private void GenBodySaldo(StringBuffer output, GsgWebOborot e){
       String hh = e.getHh();
       if (hh.charAt(1) == '1') {
           output.append("<tr align=\"left\"  bgcolor=\"#FFDEAD\">");   // коричневый цвет
           if (sostav.intValue() == 1){
               output.append(" <td >Итого "+e.getNameIdTipM()+"</td>");
           } else {
               output.append(" <td >"+e.getNameIdTipM()+"</td>");
           }
       } else        
       if (hh.charAt(1) == '2') {
           output.append("<tr align=\"left\"  bgcolor=\"#E6E6FA\">");      // синий
               output.append(" <td align=\"center\">Итого сальдо.</td>");
       } else {
           output.append(" <td>"+e.getNameIdTipM()+"</td>");
       }  
       
       GenDebet(output, e);
       GenKredit(output, e);
       output.append("</tr>");
    }
               
    private void GenDebet(StringBuffer output, GsgWebOborot e){
       if (e.getSaldoD() == null){output.append(" <td></td>");} else {
           output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(e.getSaldoD())+"</td>");
       }
       if (e.getSaldoDNds() == null){output.append(" <td></td>");} else {
           output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(e.getSaldoDNds())+"</td>");
       }    
       if (e.getSaldoDFull() == null){output.append(" <td></td>");} else {
          output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(e.getSaldoDFull())+"</td>");
       }   
    }

    private void GenKredit(StringBuffer output, GsgWebOborot e){
       if (e.getSaldoK() == null){output.append(" <td></td>");} else {
          output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(e.getSaldoK())+"</td>");
       }   
       if (e.getSaldoKNds() == null){output.append(" <td></td>");} else {
           output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(e.getSaldoKNds())+"</td>");
       }    
       if (e.getSaldoKFull() == null){output.append(" <td></td>");} else {
          output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(e.getSaldoKFull())+"</td>");
       }   
    }
    
    
    private void GenHeaderNachisl(StringBuffer output){
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        if (sostav.intValue() == 1){
        output.append(" <td rowspan=\"2\">Вид</td>");
        output.append(" <td colspan=\"2\" width=\"80\">Документ</td>");
        output.append(" <td rowspan=\"2\" width=\"40\">Срок оплаты</td>");

        output.append(" <td rowspan=\"2\">Статья учета</td>");
        } else{
            output.append(" <td colspan=\"5\" rowspan=\"2\">Статья учета</td>");

        }
        output.append(" <td colspan=\"2\" width=\"140\">Количество</td>");
        output.append(" <td colspan=\"3\" width=\"240\">Сумма</td>");
        output.append("</tr>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        if (sostav.intValue() == 1){
        output.append(" <td width=\"80\">№</td>");
        output.append(" <td width=\"40\">Дата</td>");
        }
        output.append(" <td width=\"70\">кВт*ч</td>");
        output.append(" <td width=\"70\">кВАр*ч</td>");
        output.append(" <td width=\"80\">Без НДС</td>");
        output.append(" <td width=\"80\">НДС</td>");
        output.append(" <td width=\"80\">Всего</td>");
        output.append("</tr>");
    }

    private void GenBodyNachisl(StringBuffer output, GsgWebOborot e){
       String hh = e.getHh();

        if (hh.charAt(1) == '1') {
            output.append("<tr align=\"left\"  bgcolor=\"#FFDEAD\">");   // коричневый цвет
            if (sostav.intValue() == 1){
                output.append(" <td colspan=\"5\">Итого "+e.getNameBrefIdTipM()+"</td>");
            } else {
                output.append(" <td colspan=\"5\">"+e.getNameBrefIdTipM()+"</td>");
            }
        } else
        if (hh.charAt(1) != '0') {
           output.append("<tr align=\"left\"  bgcolor=\"#E6E6FA\">");
               output.append(" <td colspan=\"5\" align=\"center\">Итого начислено:</td>");
       } else {
          output.append(" <td>Счет</td>");
          if (e.getNumDoc() == null){output.append(" <td></td>");} else {
             output.append(" <td align=\"center\">"+e.getNumDoc()+"</td>");
          }
          if (e.getDateDoc() == null){output.append(" <td></td>");} else {
              output.append(" <td align=\"center\">"+df.format(e.getDateDoc())  +"</td>");
          }    
          if (e.getDateOpl() == null){output.append(" <td></td>");} else {
              output.append(" <td align=\"center\">"+df.format(e.getDateOpl())+"</td>");
          }    
//       if (e.getNameIdTipM() e.getNameIdTip() == null){output.append(" <td></td>");} else {
          String nameTip;
          if (e.getNameIdTipM().equals(e.getNameIdTip())) {
            nameTip = e.getNameBrefIdTipM();
          } else {
            if ((e.getIdTip().intValue() == 12) || (e.getIdTip().intValue() == 25)){
                nameTip = e.getNameBrefIdTipM()+" ("+e.getNameIdTip()+") ";
            } else {
                nameTip = e.getNameBrefIdTipM();
            }
          }
          output.append(" <td align=\"left\">"+nameTip+"</td>");
//          output.append(" <td align=\"left\">"+e.getNameIdTipM()+"("+e.getNameIdTip()+")</td>");
//           output.append(" <td align=\"left\">"+e.getNameIdTip()+"</td>");
//       }    
       }   
       if (e.getKvt() == null){output.append(" <td></td>");} else {
           output.append(" <td align=\"center\">"+e.getKvt()+"</td>");
       }    
       if (e.getKvarch() == null){output.append(" <td></td>");} else {
           output.append(" <td align=\"center\">"+e.getKvarch()+"</td>");
       }    
       GenDebet(output, e);
       output.append("</tr>");
    }
    
    private void GenHeaderVistavl(StringBuffer output){
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
//        if (sostav.intValue() == 1){
            output.append(" <td colspan=\"2\" width=\"120\">Документ</td>");
            output.append(" <td rowspan=\"2\" width=\"80\">Срок оплаты</td>");
            output.append(" <td rowspan=\"2\" width=\"80\">Дата расчета</td>");
            output.append(" <td rowspan=\"2\" width=\"80\">Вид</td>");
//        } else {

//        }
//        output.append(" <td colspan=\"2\" width=\"80\">Количество</td>");
        output.append(" <td width=\"80\">Количество</td>");
        output.append(" <td rowspan=\"2\" width=\"80\">Тариф</td>");
        output.append(" <td colspan=\"3\" width=\"240\">Сумма</td>");
        output.append("</tr>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        output.append(" <td width=\"80\">№</td>");
        output.append(" <td width=\"40\">Дата</td>");
        output.append(" <td width=\"40\">кВт*ч</td>");
//        output.append(" <td width=\"40\">кВАр*ч</td>");
        output.append(" <td width=\"80\">Без НДС</td>");
        output.append(" <td width=\"80\">НДС</td>");
        output.append(" <td width=\"80\">Всего</td>");
        output.append("</tr>");
          
    }
    
    private void GenBodyVistavl(StringBuffer output, GsgWebOborot e){
       String hh = e.getHh();
       if (hh.charAt(1) == '1') {
            output.append("<tr align=\"left\"  bgcolor=\"#FFDEAD\">");   // коричневый цвет
            output.append(" <td colspan=\"7\">Итого "+e.getNameIdTipM()+"</td>");
       } else
       if (hh.charAt(1) != '0') {
           output.append("<tr align=\"left\"  bgcolor=\"#E6E6FA\">");
               output.append(" <td colspan=\"7\" align=\"center\">Итого выставлено:</td>");
       } else {
          if (e.getNumDoc() == null){output.append(" <td></td>");} else {
             output.append(" <td align=\"center\">"+e.getNumDoc()+"</td>");
          }
          if (e.getDateDoc() == null){output.append(" <td></td>");} else {
              output.append(" <td align=\"center\">"+df.format(e.getDateDoc())  +"</td>");
          }    
          if (e.getDateOpl() == null){output.append(" <td></td>");} else {
              output.append(" <td align=\"center\">"+df.format(e.getDateOpl())+"</td>");
          }    
          if (e.getDateCalc() == null){output.append(" <td></td>");} else {
             output.append(" <td align=\"center\">"+df.format(e.getDateCalc())+"</td>");
          }    
          if (e.getName4() == null){output.append(" <td></td>");} else {
             output.append(" <td align=\"center\">"+e.getName4()+"</td>");
          }   
          if (e.getKvt() == null){output.append(" <td></td>");} else {
             output.append(" <td align=\"center\">"+e.getKvt()+"</td>");
          }    
//          if (e.getKvarch() == null){output.append(" <td></td>");} else {
//              output.append(" <td align=\"center\">"+e.getKvarch()+"</td>");
//          }
          if (e.getTar() == null){output.append(" <td></td>");} else {
              output.append(" <td align=\"center\">"+new DecimalFormat("#.####").format(e.getTar())+"</td>");
          }    
       } 
       GenDebet(output, e);
       output.append("</tr>");
    }
    
    private void GenHeaderOplata(StringBuffer output){
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        if (sostav.intValue() == 1){
            output.append(" <td rowspan=\"2\">Дата</td>");
            output.append(" <td rowspan=\"2\">Статья учета</td>");
            output.append(" <td rowspan=\"2\">Форма оплаты</td>");
        } else {
            output.append(" <td colspan=\"3\" rowspan=\"2\">Статья учета</td>");
        }
        output.append(" <td colspan=\"3\" width=\"240\">Сумма</td>");
        output.append(" <td rowspan=\"2\" width=\"80\">№ документа</td>");
        output.append(" <td colspan=\"3\" width=\"80\">За какой период</td>");
        output.append(" <td rowspan=\"2\" width=\"8\">график</td>");
        output.append("</tr>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        output.append(" <td width=\"80\">Без НДС</td>");
        output.append(" <td width=\"80\">НДС</td>");
        output.append(" <td width=\"80\">Всего</td>");
        output.append(" <td width=\"80\">№ счета</td>");
        output.append(" <td width=\"80\">Год</td>");
        output.append(" <td width=\"80\">Месяц</td>");
        output.append("</tr>");
    }
    
    private void GenBodyOplata(StringBuffer output, GsgWebOborot e){
       String hh = e.getHh();
        if (hh.charAt(1) == '1') {
            output.append("<tr align=\"left\"  bgcolor=\"#FFDEAD\">");   // коричневый цвет
            if (sostav.intValue() == 1){
                output.append(" <td colspan=\"3\">Итого "+e.getNameBrefIdTipM()+"</td>");
            } else {
                output.append(" <td colspan=\"3\">"+e.getNameBrefIdTipM()+"</td>");
            }
        } else
       if (hh.charAt(1) != '0') {
           output.append("<tr align=\"left\"  bgcolor=\"#E6E6FA\">");
               output.append(" <td colspan=\"3\" align=\"center\">Итого оплачено:</td>");
       } else {
           output.append("<tr align=\"left\">");
           if (e.getDateDoc() == null){output.append(" <td></td>");} else {
              output.append(" <td align=\"center\">"+df.format(e.getDateDoc())  +"</td>");
           }    
           if (e.getNameBrefIdTipM() == null){output.append(" <td></td>");} else {
             String nameTip;
             if (e.getNameBrefIdTipM().equals(e.getNameIdTip())) {
                nameTip = e.getNameBrefIdTipM();
             } else {
                 if ((e.getIdTip().intValue() == 12) || (e.getIdTip().intValue() == 25)){
                     nameTip = e.getNameBrefIdTipM()+" ("+e.getNameIdTip()+") ";
                 } else {
                     nameTip = e.getNameBrefIdTipM();
                 }
//                nameTip = e.getNameIdTipM()+"("+e.getNameIdTip()+")";
             }
              output.append(" <td align=\"left\">"+nameTip+"</td>");
//              output.append(" <td align=\"left\">"+e.getNameIdTipM()+"("+e.getNameIdTip()+")</td>");
           }    
           if (e.getNameMet() == null){output.append(" <td></td>");} else {
               output.append(" <td align=\"left\">"+e.getNameMet()+"</td>");
           }
       }   
       GenKredit(output, e);
       if (hh.charAt(1) != '0') {
          output.append(" <td colspan=\"5\"></td>");
       } else {
          if (e.getNumDoc() == null){output.append(" <td></td>");} else {
              output.append(" <td align=\"center\">"+e.getNumDoc()+"</td>");
          }
          if (e.getNumSchet() == null){output.append(" <td></td>");} else {
              output.append(" <td align=\"center\">"+e.getNumSchet()+"</td>");
          }    
       
          if (e.getYr() == null){output.append(" <td></td>");} else {
              output.append(" <td align=\"center\">"+e.getYr()+"</td>");
          }    
          if (e.getMnth() == null){output.append(" <td></td>");} else {
              output.append(" <td align=\"center\">"+e.getMnth()+"</td>");
          }    
          if (e.getIdGra() == null){output.append(" <td></td>");} else {
              if (e.getIdGra().intValue() == 1){
                  output.append(" <td align=\"center\">");
                  output.append(" <img src=\"../img/16/Ok.gif\"  height=\"16\" width=\"16\">");
                  output.append("</td>");
              } else {
                  output.append(" <td align=\"center\">");
                  output.append("</td>");
              }
//              output.append(" <td align=\"center\">"+e.getIdGra()+"</td>");
          }
       }   
       output.append("</tr>");
    }

    public String getMonthsRodPadeg(int mes ) {
        String[] mesString = {"января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря"};
        return mesString[mes-1];
    }
    public String getMonths(int mes ) {
        String[] mesString = {"январь", "февраль", "март", "апрель", "май", "июнь",
                "июль", "август", "сентябрь", "октябрь", "ноябрь", "декабрь"};
        return mesString[mes-1];
    }
    public String getMonthsPredPad(int mes ) {
        String[] mesString = {"январе", "феврале", "марте", "апреле", "мае", "июне",
                "июле", "августе", "сентябре", "октябре", "ноябре", "декабре"};
        return mesString[mes-1];
    }


}
