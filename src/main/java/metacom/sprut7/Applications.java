package metacom.sprut7;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.OrikRightImpl;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.dao.Orik;
import metacom.sprut7.domain.GsgWebArea;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.GregorianCalendar;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 20.07.16
 * Time: 12:45
 * Просмотр и редактирование заявок на лимиты потребления и мощность
 */

//@SpringUI
//@Component
//@Repository
//@Service
@Scope("prototype")
public class Applications extends HorizontalSplitPanel implements IComponentContainer{
    private Integer idplat;
    private GsgWebArea currentWebArea = null;
    private String linkRes;
    private int year;
    private int month;
    private String linkPool;
    private Table treeTu;
    //private TabSheet tabSheet;
    private InlineDateField datetime;
    private java.util.Calendar calendar = GregorianCalendar.getInstance();
    private boolean prepared = false;

    @Autowired
    private Orik dao;
    @Autowired
    OrikRightImpl ownerBox;

    public Applications(){}

    @Override
    public void init() {
        StatePlat statePlat = (StatePlat) VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        this.idplat = statePlat.getIdPlat();
        linkPool = statePlat.getLinkPool();
        ApplicationContext applicationContext = (ApplicationContext) VaadinSession.getCurrent().getAttribute("applicationContext");
        //dao = (Orik) applicationContext.getBean("daoOrik");
        setHeight("600px");
        buildTree();
        buildMain();
        treeTu.select(treeTu.getCurrentPageFirstItemId());
        setSplitPosition(40);
        prepared = true;
    }

    /** Разметить область и построить список площадок */
    private void buildTree() {
        VerticalLayout verticalLeftLayout = new VerticalLayout();
        verticalLeftLayout.setStyleName("Layoutra");
        verticalLeftLayout.setMargin(true);
        verticalLeftLayout.setHeight("100%");

        final Container biTu;
        /** формируем и запонляем бинарный контейнер для списка площадок */
        biTu = dao.getLispArea(linkRes,idplat);
        /** Строим список на экране и вешаем обрвботчик событий */
        treeTu = getTableArea(biTu);
        treeTu.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                if (event.getProperty().getValue() != null){
                    currentWebArea = (GsgWebArea) event.getProperty().getValue();
                    if (prepared){
                        ownerBox.update(currentWebArea);
                    }
                }
//                if (id != null) {
//                    currentWebArea = (GsgWebArea) id;  //(GsgWebArea) biTu.getItem(id); //.getBean();
//                } else if (currentWebArea != null) {
//                    currentWebArea = null;
//                }
//                updateFilters();
//                viewFields()       ;
//                ToFirstRecordPu();
            }

        });
        verticalLeftLayout.addComponent(treeTu);
        setFirstComponent(verticalLeftLayout);
        ToFirstRecordTu();
    }

    /** формируем таблицу площадок */
    private Table getTableArea(Container biTu){
        //TODO: Имеет смысл сделать один метод, передовать ему параметрами списки полей и колонок.
        Table tableArea = new TreeTable("Список плошадок",biTu);
        tableArea.setSizeFull();
        tableArea.setImmediate(true);
        tableArea.setHeight("100%");
        tableArea.setVisibleColumns(new Object[] {"nplos","namPotr" });
        tableArea.setColumnHeaders(new String[]{"№","Название"});
        tableArea.setImmediate(true);
        tableArea.setSelectable(true);
        return tableArea;
    }

    /** сформируем правый экран с заявками */
    private void buildMain() {
        ownerBox.init(linkRes, currentWebArea);
        setSecondComponent(ownerBox);
        isImmediate();
    }

    private void ToFirstRecordTu(){
        Object entity = treeTu.firstItemId();
        if (entity != null) {
            treeTu.select(entity);
        } else if (currentWebArea != null) {
            currentWebArea = null;
        }
    }



}
