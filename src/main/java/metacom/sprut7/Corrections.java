package metacom.sprut7;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.InlineDateField;
import com.vaadin.ui.VerticalLayout;
import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 20.07.16
 * Time: 15:07
 * To change this template use File | Settings | File Templates.
 */
public class Corrections  extends VerticalLayout implements IComponentContainer {
    private Integer idplat;
    private String linkRes;
    private InlineDateField datetime;
    private Integer year;
    private Integer month;
    private StatePlat statePlat;
    private String linkPool;

    Corrections(){};

    @Override
    public void init() {
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        this.idplat = statePlat.getIdPlat();
        linkPool = statePlat.getLinkPool();

    }
}
