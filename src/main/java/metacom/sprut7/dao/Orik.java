package metacom.sprut7.dao;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import metacom.sprut7.domain.SzajNagr;
import metacom.sprut7.domain.GsgWebArea;
import metacom.sprut7.domain.SzajPlan;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 29.07.16
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public interface Orik {
    public BeanItemContainer<GsgWebArea> getLispArea(String linkRes, Integer idplat);
    public BeanItemContainer<SzajPlan> getLispPotr(String linkRes, Integer idPotr, Integer year);
//    public JPAContainer<SzajPlan> getLispPotr(String linkRes, Integer idPotr, Integer year);
    public void savePlan(String linkRes, Item item);
    public void saveNagr(String linkRes, Item item);
    public BeanItemContainer<SzajNagr> getLispNagr(String linkRes, Integer idPotr, Integer year);
    public void prepareLispPotr(String linkRes, Integer idPotr, Integer year);
    public void clearLispPotr(String linkRes, Integer idPotr, Integer year);
    public String sinchrinizeLispPotr(String linkRes, Integer idPotr, Integer year);

    void prepareLispPower(String linkRes, Integer idPotr, Integer year);
    void clearLispPower(String linkRes, Integer idPotr, Integer year);
    public String sinchrinizeLispPower(String linkRes, Integer idPotr, Integer year);
    public Boolean isZayavkaReadOnly(String linkRes, Integer idPlat, Integer year);
    public Boolean isNewPutZayavka(String linkRes, Integer idPlat);

}
