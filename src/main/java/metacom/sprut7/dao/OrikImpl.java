package metacom.sprut7.dao;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.JPAContainerFactory;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.filter.Compare;
import metacom.sprut7.domain.SzajNagr;
import metacom.sprut7.domain.GsgWebArea;
import metacom.sprut7.domain.SzajPlan;
import metacom.sprut7.domain.WebPlat;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 28.07.16
 * Time: 20:48
 * Извлечение данных для орик
 */
//@Component
//@Scope("prototype")
//@Repository
//@Component
//@Scope("prototype")

public class OrikImpl implements Orik{
    public OrikImpl(){};
    /** формируем и запонляем бинарный контейнер для списка площадок */
    public BeanItemContainer<GsgWebArea> getLispArea(String linkRes, Integer idplat){
        BeanItemContainer<GsgWebArea> beanItemContainer = new BeanItemContainer<GsgWebArea>(GsgWebArea.class);
        EntityManager emTu = Persistence
                .createEntityManagerFactory(linkRes)
                .createEntityManager();
        beanItemContainer.addAll(emTu.createQuery("SELECT g FROM GsgWebArea g WHERE g.idPlat = :idPlat").
                setParameter("idPlat", idplat).getResultList());
        emTu.close();
        return beanItemContainer;
    }
    /** формируем и запонляем бинарный контейнер для списка потребления */
//    public JPAContainer<SzajPlan> getLispPotr(String linkRes, Integer idPotr, Integer year){
//        //BeanItemContainer<SzajPlan> beanItemContainer = new BeanItemContainer<SzajPlan>(SzajPlan.class);
//        EntityManager emTu = Persistence
//                .createEntityManagerFactory(linkRes)
//                .createEntityManager();
////        JPAContainer container = JPAContainerFactory.makeReadOnly(SzajPlan.class,linkRes);
//        JPAContainer container = JPAContainerFactory.make(SzajPlan.class,linkRes);
//        container.addContainerFilter(new Compare.Equal("idPotr", idPotr));
//        container.addContainerFilter(new Compare.Equal("years",year));
//        container.applyFilters();
//        // addAll( emTu.createQuery("SELECT g FROM SzajPlan g WHERE g.idPotr = :idPotr and g.years = :year").
//        //        setParameter("idPotr", idPotr).setParameter("year",year).getResultList());
//        //emTu.close();
//        return container;
//    }

    public void savePlan(String linkRes, Item item){

        Integer idPotr = (Integer) item.getItemProperty("idPotr").getValue();
        Integer years = (Integer) item.getItemProperty("years").getValue();
        Integer month = (Integer) item.getItemProperty("month").getValue();

        EntityManager em2 = Persistence
                .createEntityManagerFactory(linkRes)
                .createEntityManager();
        Collection emps2 = em2.createQuery("SELECT g FROM SzajPlan g WHERE g.idPotr = :idPotr and g.years = :years and g.month = :month").setParameter("idPotr", idPotr).setParameter("years", years).setParameter("month", month).setMaxResults(1).getResultList();
        //SzajPlan szajPlan = (SzajPlan) em2.createQuery("SELECT g FROM SzajPlan g WHERE g.idPotr = :idPotr and g.years = :years and g.month = :month").setParameter("idPotr", idPotr).setParameter("years", years).setParameter("month", month).setMaxResults(1).getSingleResult();
        Boolean platexists = !emps2.isEmpty();
        if (platexists) {
            SzajPlan e2 = (SzajPlan) emps2.iterator().next();
            em2.getTransaction().begin();
                e2.setZajVal((Integer) item.getItemProperty("zajVal").getValue());
                e2.setMonth((Integer) item.getItemProperty("month").getValue());
                e2.setChas((Integer) item.getItemProperty("chas").getValue());
                e2.setDayOffWeek((Integer) item.getItemProperty("dayOffWeek").getValue());
                e2.setHoliday((Integer) item.getItemProperty("holiday").getValue());
                e2.setPotrHoli((Integer) item.getItemProperty("potrHoli").getValue());
                e2.setDateProvDoc(new Date());
                e2.setHand(2);

//            for (Object id : item.getItemPropertyIds()){
//                String ids = (String) id;
//                item.getItemProperty(ids).getValue());
//                e2.setZajVal(item.getItemProperty(ids).getValue());
//            }
            em2.persist(e2);
            em2.getTransaction().commit();
        }
        em2.close();

    }


    public BeanItemContainer<SzajPlan> getLispPotr(String linkRes, Integer idPotr, Integer year){
        BeanItemContainer<SzajPlan> beanItemContainer = new BeanItemContainer<SzajPlan>(SzajPlan.class);
        EntityManager emTu = Persistence
                .createEntityManagerFactory(linkRes)
                .createEntityManager();
        beanItemContainer.addAll( emTu.createQuery("SELECT g FROM SzajPlan g WHERE g.idPotr = :idPotr and g.years = :year").
                setParameter("idPotr", idPotr).setParameter("year",year).getResultList());
        emTu.close();
        return beanItemContainer;
    }



    @Override
    public BeanItemContainer<SzajNagr> getLispNagr(String linkRes, Integer idPotr, Integer year) {
        BeanItemContainer<SzajNagr> beanItemContainer = new BeanItemContainer<SzajNagr>(SzajNagr.class);
        EntityManager emTu = Persistence
                .createEntityManagerFactory(linkRes)
                .createEntityManager();
        beanItemContainer.addAll( emTu.createQuery("SELECT g FROM SzajNagr g WHERE g.idPotr = :idPotr and g.years = :year").
                setParameter("idPotr", idPotr).setParameter("year",year).getResultList());
        emTu.close();
        return beanItemContainer;
    }

    public void saveNagr(String linkRes, Item item){

        Integer idPotr = (Integer) item.getItemProperty("idPotr").getValue();
        Integer years = (Integer) item.getItemProperty("years").getValue();
        Integer months = (Integer) item.getItemProperty("months").getValue();
        Character storno = (Character) item.getItemProperty("storno").getValue();
        Date dateDoc = (Date) item.getItemProperty("dateDoc").getValue();

        EntityManager em2 = Persistence
                .createEntityManagerFactory(linkRes)
                .createEntityManager();
        Collection emps2 = em2.createQuery("SELECT g FROM SzajNagr g WHERE g.idPotr = :idPotr and g.years = :years and g.months = :months and g.storno = :storno and g.dateDoc = :dateDoc").setParameter("idPotr", idPotr).setParameter("years", years).setParameter("months", months).setParameter("storno",storno).setParameter("dateDoc",dateDoc).setMaxResults(1).getResultList();
        //SzajPlan szajPlan = (SzajPlan) em2.createQuery("SELECT g FROM SzajPlan g WHERE g.idPotr = :idPotr and g.years = :years and g.month = :month").setParameter("idPotr", idPotr).setParameter("years", years).setParameter("month", month).setMaxResults(1).getSingleResult();
        Boolean platexists = !emps2.isEmpty();
        if (platexists) {
            SzajNagr e2 = (SzajNagr) emps2.iterator().next();
            em2.getTransaction().begin();
            e2.setZajValU((Double) item.getItemProperty("zajValU").getValue());
            e2.setZajValV((Double) item.getItemProperty("zajValV").getValue());
            e2.setHand(2);
            e2.setDateDoc(new Date());
            em2.persist(e2);
            em2.getTransaction().commit();
        }
        em2.close();

    }

    public void prepareLispPotr(String linkRes, Integer idPotr, Integer year){
        EntityManager em = Persistence
                .createEntityManagerFactory(linkRes)
                .createEntityManager();
        Query q = em.createNativeQuery("execute procedure BOB_ZAJPLAN_INI_ONE(?,?)");
        q.setParameter(1, year);
        q.setParameter(2, idPotr);
        em.getTransaction().begin();
        int i = q.executeUpdate();
        em.getTransaction().commit();
//        log.info("New order id: " + on.toString());
//        Query q = em.createNativeQuery("SELECT a.firstname, a.lastname FROM Author a");
//        BOB_ZAJPLAN_INI_ONE(:YEA, :ID_POTR)
    }

    public void clearLispPotr(String linkRes, Integer idPotr, Integer year){
        EntityManager em = Persistence
                .createEntityManagerFactory(linkRes)
                .createEntityManager();
        Query q = em.createNativeQuery("delete from SzajPlan s where s.id_Potr = ? and s.years = ?");
        q.setParameter(2, year);
        q.setParameter(1, idPotr);
        em.getTransaction().begin();
        int i = q.executeUpdate();
        em.getTransaction().commit();
    }

    public void sinchrinizeLispPotr(String linkRes, Integer idPotr, Integer year){

    }

    public void prepareLispPower(String linkRes, Integer idPotr, Integer year){
        EntityManager em = Persistence
                .createEntityManagerFactory(linkRes)
                .createEntityManager();
        Query q = em.createNativeQuery("execute procedure BOB_INI_ONE_RECORD_NAGR(?,?,'ZAJ')");
        q.setParameter(2, year);
        q.setParameter(1, idPotr);
        em.getTransaction().begin();
        int i = q.executeUpdate();
        em.getTransaction().commit();

    }
    public void clearLispPower(String linkRes, Integer idPotr, Integer year){
        EntityManager em = Persistence
                .createEntityManagerFactory(linkRes)
                .createEntityManager();
        Query q = em.createNativeQuery("delete from SzajNagr s where s.id_Potr = ? and s.years = ?");
        q.setParameter(2, year);
        q.setParameter(1, idPotr);
        em.getTransaction().begin();
        int i = q.executeUpdate();
        em.getTransaction().commit();
    }
    public void sinchrinizeLispPower(String linkRes, Integer idPotr, Integer year){

    }


}
