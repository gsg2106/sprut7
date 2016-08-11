/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

//import com.mycompany.maven_sprut.domain.GsgWebKs;
//import com.mycompany.maven_sprut.domain.GsgWebViewPu;
//import com.mycompany.maven_sprut.domain.GsgWebViewRa;
//import com.mycompany.maven_sprut.domain.GsgWebViewTu;
import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Container;
import com.vaadin.data.Container.Filter;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Compare.Equal;
import com.vaadin.data.util.sqlcontainer.SQLContainer;
import com.vaadin.data.util.sqlcontainer.connection.J2EEConnectionPool;
import com.vaadin.data.util.sqlcontainer.query.FreeformQuery;
import com.vaadin.server.Resource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.domain.GsgWebKs;
import metacom.sprut7.domain.GsgWebViewPu;
import metacom.sprut7.domain.GsgWebViewRa;
import metacom.sprut7.domain.GsgWebViewTu;
/**
 *
 * @author Сергей
 */
public class LispTuWindow  extends HorizontalSplitPanel implements
        IComponentContainer {
    private BeanItemContainer<GsgWebViewRa> biRa;
    private BeanItemContainer<GsgWebViewTu> biTu;
    private BeanItemContainer<GsgWebViewPu> biPu;
    private BeanItemContainer<GsgWebKs> biKs;
    private Table raTable;
    private Table puTable;
    private Table ksTable;
    private Table treeTu;
    private Integer idplat;
    private String nameplat;
    private String adres;
    private String state;
    private Integer yearraport;
    private Integer monthraport;
    private GsgWebViewTu tuFilter;
    private GsgWebViewRa tuRa;
    private GsgWebViewPu puFilter;
    private String[] longColumnsHeader;
    private Label[] labels;
    private String linkRes;
    private String linkPool;
    private TabSheet t;
    private StatePlat statePlat;
    private SQLContainer hisContainer;
    private Table hisTable;
    
    public LispTuWindow(){
    }        
    
    public void init(){
        //linkRes =(String) VaadinSession.getCurrent().getAttribute("linkRes");
        //config = (Config) VaadinSession.getCurrent().getAttribute("config");
        setHeight("600px");

        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        linkPool = statePlat.getLinkPool();
        this.idplat=statePlat.getIdPlat(); 
        this.nameplat=statePlat.getNamePlat(); // nameplat;
        this.adres=statePlat.getAdres(); //  adres;
        this.state=statePlat.getState(); // state;
        this.yearraport=statePlat.getYearRaport(); //yearraport;
        this.monthraport=statePlat.getMonthRaport();  //monthraport;
    
        Statistika stat = new Statistika();
        stat.init(idplat, Statistika.st_tu );
    

        buildTree();
        buildMain();
        treeTu.select(treeTu.getCurrentPageFirstItemId());
        setSplitPosition(40);          
    }    
    
    private void buildTree(){
       VerticalLayout verticalLeftLayout = new VerticalLayout();
       verticalLeftLayout.setStyleName("Layoutra");
       verticalLeftLayout.setMargin(true);
       verticalLeftLayout.setHeight("100%");

//??        tus = new HierarchicalDepartmentContainer();
//??        TreeTable treeTu = new TreeTable("Список точек учета",tus);
        biTu = new BeanItemContainer<GsgWebViewTu>(GsgWebViewTu.class);
        EntityManager emTu = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
        biTu.addAll( emTu.createQuery("SELECT g FROM GsgWebViewTu g WHERE g.idPlat = :idplat").
                setParameter("idplat", idplat).getResultList());
        emTu.close();
//?/        Table treeTu = new TreeTable("Список точек учета",tus);
        treeTu = new TreeTable("Список точек учета",biTu);
        treeTu.setSizeFull();       
        treeTu.setImmediate(true);
        treeTu.setHeight("100%");
        //setWidth("100%");
/*???        
        Filter filter = new Or( new Compare.Equal("idPlat", idplat), new Compare.Equal("idTu", 0));
//        Filter filter = new Compare.Equal("idPlat", idplat);
        tus.addContainerFilter(filter);
        tus.applyFilters();
        for (Object rootId: tus.rootItemIds())
           treeTu.setCollapsed(rootId, false);
*/        
        treeTu.setVisibleColumns(new Object[] { "nametu","codtu","adrline" });
        treeTu.setColumnHeaders(new String[] {"Название","Номер","Адрес"});
        treeTu.setImmediate(true);
        treeTu.setSelectable(true);
        
        treeTu.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Object id = event.getProperty().getValue();
                if (id != null) {
                    GsgWebViewTu entity = biTu.getItem(id).getBean();
                    tuFilter = entity;
                } else if (tuFilter != null) {
                    tuFilter = null;
                }
                updateFilters();
                viewFields()       ;
                ToFirstRecordPu();
            }

        });
        
        addComponent(verticalLeftLayout);
        verticalLeftLayout.addComponent(treeTu);
        
        setFirstComponent(verticalLeftLayout);        
        
    }

    private void buildMain() {
//        throw new UnsupportedOperationException("Not yet implemented");
       Resource icon1 = null;
       Resource icon2 = null;
        
       VerticalLayout vl1 = new VerticalLayout();
       //verticalLayout.setSpacing(true);
       vl1.setHeight("100%");
       vl1.setStyleName("Layoutra");
       //verticalLayout.setSizeUndefined();
       vl1.setMargin(true);

       // Tab 2 content
       VerticalLayout vl2;
       vl2 = new VerticalLayout();
       vl2.setMargin(true);
       VerticalLayout vl3;
       vl3 = new VerticalLayout();
       vl3.setMargin(true);
       
       t = new TabSheet();  
       t.setHeight("100%"); //t.setHeight("200px");
       t.setWidth("100%");  //t.setWidth("400px");
       t.addTab(vl1, "Последние показания.", icon1).setStyleName("vl1");
       t.addTab(vl2, "Контрольные съемы.", icon2);
       t.addTab(vl3, "История показаний.", icon2);
       t.setStyleName(Reindeer.TABSHEET_MINIMAL);

//???       ra = JPAContainerFactory.makeReadOnly(GsgWebViewRa.class,linkRes);
       biRa = new BeanItemContainer<GsgWebViewRa>(GsgWebViewRa.class);
//       pu = JPAContainerFactory.makeReadOnly(GsgWebViewPu.class,linkRes);
       biPu = new BeanItemContainer<GsgWebViewPu>(GsgWebViewPu.class);
//       ks = JPAContainerFactory.makeReadOnly(GsgWebKs.class,linkRes);
       biKs = new BeanItemContainer<GsgWebKs>(GsgWebKs.class);

       
       tuFilter = null;
       updateFilters();
       
//Collection<Object> gf = ra.getItemIds();        
//Integer sz1 = gf.size();
//GsgWebViewRa ii = (GsgWebViewRa) gf.iterator().next();
 
       raTable = new Table("Последние показания по рапорту ("+yearraport+"год "+monthraport+"мес.)", biRa);
//Boolean emp = raTable.getContainerDataSource().getItemIds().isEmpty();
//Integer sz = raTable.getContainerDataSource().getItemIds().size();
//   Object id = raTable.firstItemId();

       raTable.setSizeFull();       
       raTable.setSelectable(true);
       raTable.setImmediate(true);
       raTable.setHeight("240px"); //setHeight(150);
//       raTable.setHeight("100%");
       raTable.setVisibleColumns(new Object[] { "codtu","eng","codZone","markZon","nachalo","danrap", "ktr","kvtchrazn","pott","potl" });//  });
       raTable.setColumnHeaders(new String[] {"№ ТУ","А/Р","Зона","Шкала","Нач.пок","Тек.пок.","Ктр","Квт-ч нач.","Пот.ТР","Пот.Лин."   });
       raTable.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Object id = event.getProperty().getValue();
                if (id != null) {
                    GsgWebViewRa entity = biRa.getItem(id).getBean();
                    tuRa = entity;
                } else if (tuRa != null) {
                    tuRa = null;
                }
                updateFields();
            }

       });       
       longColumnsHeader=new String[] {"Номер точки учета","Энергия","Зона","Начальные показания","Текущие показания","Коэффициент трансформации","Начисленные Квт-ч.","Потери в трансформаторах","Потери в линии"};
       vl1.addComponent(raTable);
       /*----------------- нарисуем поля ----------------- */
       Panel pan = new Panel();
       pan.setCaption("Расшифровка строки рапорта");
       pan.setStyleName("panelFields");
       pan.setWidth("450px");
       FormLayout formlayout = new FormLayout();
       formlayout.setSizeFull();
       vl1.addComponent(pan);
       vl1.setComponentAlignment(pan, Alignment.TOP_CENTER);
       
       pan.setContent(formlayout);
       labels=new Label[9];
       
        for (int i = 0; i < 9; i++) {
            Label lb =new Label();
            lb.setCaption(longColumnsHeader[i]+":");
            labels[i] = lb;            
            formlayout.addComponent(lb);
        }
       /*--------------------------------------------------- */
       puTable = new Table("Приборы учета потребителя.", biPu);
       puTable.setSizeFull();       
       puTable.setSelectable(true);
       puTable.setImmediate(true);
       puTable.setHeight("240px"); //setHeight(150);
//"nTyps" отсутствует но есть NTyps полный бред
       puTable.setVisibleColumns(new Object[] { "zavno","NTyps","namescale", "kontrp","kontrpDtFomat","ktr","znachn","dateGosFomat" });//  });
       puTable.setColumnHeaders(new String[] {"Зав. №","Тип","шкала","контр показ.","дата контр.","КТР","Значность","Гос.пов."   });
       puTable.addListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Object id = event.getProperty().getValue();
                if (id != null) {
                    GsgWebViewPu entity = biPu.getItem(id).getBean();
                    puFilter = entity;
                    updateKsFilters();
                } else if (puFilter != null) {
                    puFilter = null;
                }
            }

       });       
       vl2.addComponent(puTable);
//       ks.setSortProperty("dt", "desc");
      
       
       
       ksTable = new Table("Контрольные съемы", biKs);
       ksTable.setSizeFull();       
       ksTable.setSelectable(true);
       ksTable.setImmediate(true);
       ksTable.setHeight("240px"); //setHeight(150);
//       raTable.setHeight("100%");
       ksTable.setVisibleColumns(new Object[] { "dtFormat","kontrp","namevid","ndoc"});//  });
       ksTable.setColumnHeaders(new String[] {"Дата","Показания","Операция","Документ"});
       
       ksTable.setSortContainerPropertyId("dt");
       ksTable.setSortAscending(false);
       ksTable.sort();
       
       puFilter = null;
       updateKsFilters();
       
       vl2.addComponent(ksTable);
       /*----------------------------------------------------*/
       J2EEConnectionPool connectionPool = new J2EEConnectionPool(linkPool);
       FreeformQuery dlg =  new FreeformQuery("select * from GSG_WEB_VIEW_RA_HIST", connectionPool,"id");
       dlg.setDelegate(new ViewDelegate("GSG_WEB_VIEW_RA_HIST"));
        try {
            hisContainer = new SQLContainer(dlg);
        } catch (SQLException ex) {
            Logger.getLogger(LispTuWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
        hisContainer.addContainerFilter(new Equal("ID_TU",-1)); // чтобы не было выборки
//        hisContainer.addContainerFilter(new Equal("ID_TU",4412));
        
        //hisContainer.setAutoCommit(true);

//       hisContainer = new SqlContainerRaHist(linkPool,tuFilter.getIdTu()).getContainer();
//BigInteger dd = BigInteger.valueOf(0);
//       hisContainer = new SqlContainerRaHist(linkPool, dd ).getContainer();
       hisTable = new Table("История показаний по точке учета", hisContainer);
       hisTable.setSizeFull();       
       hisTable.setSelectable(true);
       hisTable.setVisibleColumns(new Object[] { "ID", "YEA", "MON", "NACHALO", "DANRAP", "ZAVNO", "COD_ZONE", "RAZN","KVTCHRAZN", "POTT", "POTL","FULL_"});
       hisTable.setColumnHeaders(new String[] {"№","Год","Месяц","Пред.показ.","Наст показ.","Зав № сч.","Зона","Разн.","Расход","Потери в тр.","Потери на линии","Расход с потерями"});
       hisTable.setImmediate(true);
       hisTable.setHeight("510px");
       
       vl3.addComponent(hisTable);
       /*--------------------------------------------------- */
       setSecondComponent(t);        
       vl1.setImmediate(true);
       vl3.setImmediate(true);
       t.setImmediate(true);
       viewFields()       ;
       ToFirstRecordPu();
        
    }
    
    private void updateFilters() {
       biRa.removeAllItems();
       biKs.removeAllItems();
       EntityManager emRa = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
       if (tuFilter != null) {
            if (tuFilter.getParent() != null)
               biRa.addAll( emRa.createQuery("SELECT g FROM GsgWebViewRa g WHERE g.idTu = :idTu and g.idPlat = :idplat and g.yea = :yea and g.mon = :mon").
                 setParameter("idTu",tuFilter.getIdTu()).setParameter("idplat", idplat).setParameter("yea", yearraport).setParameter("mon", monthraport).getResultList());
            else
               biRa.addAll( emRa.createQuery("SELECT g FROM GsgWebViewRa g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon").
                 setParameter("idplat", idplat).setParameter("yea", yearraport).setParameter("mon", monthraport).getResultList());
       } else
               biRa.addAll( emRa.createQuery("SELECT g FROM GsgWebViewRa g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon").
                 setParameter("idplat", idplat).setParameter("yea", yearraport).setParameter("mon", monthraport).getResultList());
        emRa.close();
/*??        
        ra.setApplyFiltersImmediately(false);
        ra.removeAllContainerFilters();
        if (tuFilter != null) {
            // two level hierarchy at max in our demo
            if (tuFilter.getParent() == null) {
                // ---------  Фильтр не нужен--------
                //ra.addContainerFilter(new Compare.Equal("tus.parent",
                //        tuFilter));
                //
            } else {
                ra.addContainerFilter(new Compare.Equal("idTu",
                        tuFilter.getIdTu()));
            }
        }
        //------------фильтр по отчетному периоду------------------
        ra.addContainerFilter(new Compare.Equal("idPlat",idplat));
        ra.addContainerFilter(new Compare.Equal("yea", yearraport));
        ra.addContainerFilter(new Compare.Equal("mon", monthraport));
        ra.applyFilters();
        biRa.removeAllItems();
        for (Object id :ra.getItemIds()){
           biRa.addItem(ra.getItem(id));  // GsgWebViewRa
        }
*/
       //---------------Наложим фильтры на список приборов учета---------------
       biPu.removeAllItems();
       EntityManager emPu = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
       if (tuFilter != null) {
            if (tuFilter.getParent() != null)
               biPu.addAll( emPu.createQuery("SELECT g FROM GsgWebViewPu g WHERE g.idTu = :idTu and g.idPlat = :idPlat").
                 setParameter("idTu", tuFilter.getIdTu()).setParameter("idPlat", idplat).getResultList());
            else
               biPu.addAll( emPu.createQuery("SELECT g FROM GsgWebViewPu g WHERE g.idPlat = :idPlat").
                 setParameter("idPlat", idplat).getResultList());
       } else
               biPu.addAll( emPu.createQuery("SELECT g FROM GsgWebViewPu g WHERE g.idPlat = :idPlat").
                 setParameter("idPlat", idplat).getResultList());
       
       if (hisContainer != null){
              Collection<Filter> ff = hisContainer.getContainerFilters();
              Filter fl = (Filter) ff.toArray()[0];
              hisContainer.addContainerFilter(new Equal("ID_TU", tuFilter.getIdTu().intValue()));
              hisContainer.removeContainerFilter(fl);
              hisContainer.setPageLength(50);
       }
       emPu.close();
       
/*       
        pu.setApplyFiltersImmediately(false);
        pu.removeAllContainerFilters();
        if (tuFilter != null) {
            // two level hierarchy at max in our demo
            if (tuFilter.getParent() == null) {
                // ---------  Фильтр не нужен--------
                //ra.addContainerFilter(new Compare.Equal("tus.parent",
                //        tuFilter));
                //
            } else {
                pu.addContainerFilter(new Compare.Equal("idTu",
                        tuFilter.getIdTu()));
            }
        }
        pu.addContainerFilter(new Compare.Equal("idPlat",idplat));
        pu.applyFilters();
*/
        //----------------------------------------------------------------
    }
    
    private void viewFields(){
                Object id = raTable.firstItemId();
                if (id != null) {
                    raTable.select(id);
                    GsgWebViewRa entity = biRa.getItem(id).getBean(); //.getEntity();
                    tuRa = entity;
                } else if (tuRa != null) {
                    tuRa = null;
                }
                updateFields();
    }

    private void ToFirstRecordPu(){
                Object idpu = puTable.firstItemId();
                if (idpu != null) {
                    puTable.select(idpu);
                    GsgWebViewPu entity = biPu.getItem(idpu).getBean();
                    puFilter = entity;
                } else if (puFilter != null) {
                    puFilter = null;
                }
    }
    
    private void updateFields() {
    //                throw new UnsupportedOperationException("Not yet implemented");
        if (tuRa != null) {
           labels[0].setValue(tuRa.getCodtu() != null ? tuRa.getCodtu().toString() : ""); 
           labels[1].setValue(tuRa.getEng() != null ? tuRa.getEng().toString() : ""); 
           labels[2].setValue(tuRa.getCodZone() != null ? tuRa.getCodZone().toString(): ""); 
           labels[3].setValue(tuRa.getNachalo() != null ? tuRa.getNachalo().toString() : ""); 
           labels[4].setValue(tuRa.getDanrap() != null ? tuRa.getDanrap().toString() : ""); 
           labels[5].setValue(tuRa.getKtr() != null ? tuRa.getKtr().toString() : ""); 
           labels[6].setValue(tuRa.getKvtchrazn() != null ? tuRa.getKvtchrazn().toString() : ""); 
           labels[7].setValue(tuRa.getPott() != null ? tuRa.getPott().toString() : ""); 
           labels[8].setValue(tuRa.getPotl() != null ? tuRa.getPotl().toString() : ""); 
        }

    }
    
    
    private void updateKsFilters() {
       biKs.removeAllItems();
       EntityManager emKsu = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
       if (puFilter != null)
          biKs.addAll( emKsu.createQuery("SELECT g FROM GsgWebKs g WHERE g.idPutu = :idPutu and g.idPlat = :idPlat and g.eng = :eng and g.idScale= :idScale").
                 setParameter("idPutu", puFilter.getIdPutu()).
                 setParameter("eng",    puFilter.getEng()).
                 setParameter("idScale",puFilter.getIdScale()).
                 setParameter("idPlat", idplat).getResultList());
       else
          biKs.addAll( emKsu.createQuery("SELECT g FROM GsgWebKs g WHERE g.idPlat = :idPlat").
                 setParameter("idPlat", idplat).getResultList());
       emKsu.close();
/*        
        ks.setApplyFiltersImmediately(false);
        ks.removeAllContainerFilters();
        ks.addContainerFilter(new Compare.Equal("idPlat",idplat));
        if (puFilter != null) {
           ks.addContainerFilter(new Compare.Equal("idPutu",puFilter.getIdPutu()));
           ks.addContainerFilter(new Compare.Equal("eng",puFilter.getEng()));
           ks.addContainerFilter(new Compare.Equal("idScale",puFilter.getIdScale()));
        }   
        
        ks.applyFilters();
       // ra.setApplyFiltersImmediately(true);
*/    
    }
    
}





//        ViewTu = new HierarchicalContainer<GsgWebViewTu>();
//        ViewTu = new HierarchicalViewTuContainer();        
//        treetable= new  TreeTable(null,ViewTu); 
//        setFirstComponent(treetable);
        
//        tuTable = new Table(null, tus);
//        tuTable.setSelectable(true);
//        tuTable.setImmediate(true);
        
//        tuTable.setVisibleColumns(new Object[] { "NAMETU", });
        
//        TreeTU= new TuTree();
//        addComponent(TreeTU);
//        super("Authentication Required !");
//        setName ( "login" );
//        initUI();
//        addComponent(tuTable);
//        verticalLayout.setExpandRatio(tuTable, 1);

//    addComponent(MyLayout);


//!!!!     getWindow().showNotification("Value is:", value);