/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.beans;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import metacom.sprut7.Statistika;
import metacom.sprut7.domain.GsgWebDolgi;

/**
 *
 * @author Сергей
 */
public class Dolgi extends VerticalLayout implements IComponentContainer{
    private Integer idplat;
    private String linkRes;
    private JPAContainer<GsgWebDolgi> dolgi;
//    private Table tableDolgi;

    private Panel pan;
    private Panel infoPan = null;
    private Component contentInfoPan;

    public Dolgi() {
    }

    public void init() {
        idplat = (Integer) VaadinSession.getCurrent().getAttribute("idplat");
        //linkRes =(String) VaadinSession.getCurrent().getAttribute("linkRes");
        StatePlat statePlat = (StatePlat) VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();


//        config = (Config) VaadinSession.getCurrent().getAttribute("config");
//        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");

        setMargin(true);
        setHeight("100%");
        setStyleName("DolgiWindow");
        setSizeFull();

        Component contentInfoPan = getContentInfoPan();
        if (contentInfoPan != null){
            infoPan = new Panel("Сообщения от администрации энергоснабжающей организации.");
            infoPan.setWidth("100%");
            infoPan.setHeightUndefined();
            infoPan.setContent(contentInfoPan);
        }
        pan = new Panel("Состояние задолженности на текущий момент");
        pan.setWidth("100%");
        pan.setHeight("585px");

        BuildMain();
        if (infoPan != null) {
            addComponent(infoPan);
        }
        addComponent(pan);
        setImmediate(true);
        //addComponent(tableDolgi);
        //throw new UnsupportedOperationException("Not yet implemented");


    }

    private Component getContentInfoPan() {
        VerticalLayout infoLayout = null;
        EntityManager em = Persistence
                .createEntityManagerFactory(linkRes)
                .createEntityManager();
        Collection emps;
//        emps = em.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon and (g.created is not null) and (g.deleted is null) and (g.idgroup = 0 and g.ready is null )").
//                setParameter("idplat", idplat).setParameter("yea", yea).setParameter("mon", mon).getResultList();
        emps = em.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and (g.created is not null) and (g.deleted is null) and (g.idgroup = 0 and g.ready is null )").
                setParameter("idplat", idplat).getResultList();
        Boolean docExists = !emps.isEmpty();
        if (docExists){
            String ms ="<i><p style=\"color:#0000ff\">"+
                    "Внимание. Обнаружены документы которые требуют Вашего внимания и электронной подписи с Вашей стороны."+
                    "Нажмите кнопку \"Посмотреть документы\", чтобы перейти на страницу \"горящих\" документов."+
                    "</p> </i>";
            HorizontalLayout hl = new HorizontalLayout();
            hl.setWidth("100%");
            //hl.addComponent(new Label("Нажмите кнопку \"Посмотреть документы\" чтобы перейти на страницу горящих документов."));
            Button fireDoc = new Button("Посмотреть документы");
            fireDoc.addClickListener(new Button.ClickListener(){
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    getUI().getNavigator().navigateTo("lispDocOnSignum");
                }
            });
            hl.addComponent(fireDoc);
            hl.setComponentAlignment(fireDoc, Alignment.MIDDLE_CENTER);

            infoLayout = new VerticalLayout();
            infoLayout.setMargin(true);
            infoLayout.addComponent(new Label(ms, ContentMode.HTML));
            infoLayout.addComponent(hl);
        }

        return infoLayout;  //To change body of created methods use File | Settings | File Templates.
    }


    private void BuildMain(){
       Statistika stat = new Statistika();
       stat.init(idplat, Statistika.st_dolg );
       
        pan.setContent(null);
        //pan.removeAllComponents();
        StringBuffer output = new StringBuffer(110);
        output.append("<table bgcolor=\"#F0FFFF\" border=\"1\" cellpadding=\"4\" cellspacing=\"0\" width=\"100%\">");
        output.append("<tbody>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
//        output.append(" <td rowspan=\"2\">Дата</td>");
        output.append(" <td rowspan=\"2\">Статья</td>");
        output.append(" <td colspan=\"3\" width=\"240\">Сумма по дебету (долги)</td>");
        output.append(" <td colspan=\"3\" width=\"240\">Сумма по кредиту (кредит)</td>");
        output.append("</tr>");
        output.append("<tr align=\"center\" bgcolor=\"#ADD8E6\">");
        output.append(" <td width=\"80\">Без НДС</td>");
        output.append(" <td width=\"80\">НДС</td>");
        output.append(" <td width=\"80\">Всего</td>");
        output.append(" <td width=\"80\">Без НДС</td>");
        output.append(" <td width=\"80\">НДС</td>");
        output.append(" <td width=\"80\">Всего</td>");
        output.append("</tr>");
        
        Double dt = 0.;
        Double dtNds = 0.;
        Double dtFull = 0.;
        Double kt = 0.;
        Double ktNds = 0.;
        Double ktFull = 0.;
        EntityManager em = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
        Collection emps = em.createQuery("SELECT g FROM GsgWebDolgi g WHERE g.idPlat = :idplat").
                 setParameter("idplat", idplat).getResultList();
        Boolean dolgExists = !emps.isEmpty();  
        if (dolgExists) {
            Iterator it = emps.iterator();
            int i = 0;
            int count = 0;
            while (it.hasNext()){
                GsgWebDolgi e = (GsgWebDolgi) it.next();
                if (e.getSaldoD() != null){dt = dt + e.getSaldoD();}
                if (e.getSaldoDNds() != null) {dtNds = dtNds + e.getSaldoDNds();}
                if (e.getSaldoDFull() != null) {dtFull = dtFull + e.getSaldoDFull();}
                if (e.getSaldoK() != null) {kt = kt + e.getSaldoK();}
                if (e.getSaldoKNds() != null) {ktNds = ktNds + e.getSaldoKNds();}
                if (e.getSaldoKFull() != null) {ktFull = ktFull + e.getSaldoKFull();}
/*                
                tableDolgi.addItem(new Object[] {
                           e.getTxt(),
                           e.getSaldoD(),
                           e.getSaldoK(),
                           e.getSaldoDNds(),
                           e.getSaldoKNds(),
                           e.getSaldoDFull(),
                           e.getSaldoKFull()},
                           new Integer(i+1));
*/
                if (i == 1) {
                   output.append("<tr align=\"left\"  bgcolor=\"#FFFAFA\">");
                } else {
                   output.append("<tr align=\"left\">");
                } 
                output.append(" <td align=\"left\">"+e.getTxt()+"</td>");
                GenDebet(output, e);
                GenKredit(output, e);
                output.append("</tr>");
                if (i==0) {i=1;} else {i=0;};
                count++;
            }
            if (count>1){
               output.append("<tr align=\"left\"  bgcolor=\"#FFDEAD\">");
               output.append(" <td align=\"left\">Итого</td>");
               output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(dt)+"</td>");
               output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(dtNds)+"</td>");
               output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(dtFull)+"</td>");
               output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(kt)+"</td>");
               output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(ktNds)+"</td>");
               output.append(" <td align=\"right\">"+new DecimalFormat("#0.00").format(ktFull)+"</td>");
               output.append("</tr>");
            }
        }    
//        setExpandRatio(tableDolgi, 1);
        //setImmediate(true);
        output.append("</tbody></table>");        
        emps.clear();
        em.close();
        
        try {
            CustomLayout custom = new CustomLayout(new ByteArrayInputStream(output.toString().getBytes("UTF-8")));
            pan.setContent(custom);
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        pan.setImmediate(true);
        
    }
               
    private void GenDebet(StringBuffer output, GsgWebDolgi e){
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

    private void GenKredit(StringBuffer output, GsgWebDolgi e){
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


//    @Override
//    public void forEach(Consumer<? super Component> action) {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }

//    @Override
//    public Spliterator<Component> spliterator() {
//        throw new UnsupportedOperationException("Not supported yet.");
//    }
}
