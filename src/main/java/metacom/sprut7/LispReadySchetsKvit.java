package metacom.sprut7;

import com.vaadin.server.FileDownloader;
import com.vaadin.server.FileResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.domain.WebSchets;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 11.06.16
 * Time: 12:19
 * To change this template use File | Settings | File Templates.
 */
public class LispReadySchetsKvit extends VerticalLayout{
    public static Map<Integer,String> tipDocs = new HashMap<Integer,String>();
    private Integer yea;
    private Integer mon;
    private Integer idplat;
    private String PuthNoSign;
    private StatePlat statePlat;
    private String linkRes = null;
    //private String PuthReady;
    private Table table;
    private boolean kvitExists = false;
    static Config config = null;
    private static final ThemeResource ICON = new ThemeResource(
            "img/16/action_save.gif");
    public LispReadySchetsKvit(){
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        this.idplat=statePlat.getIdPlat();  //idplat;
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        this.yea = statePlat.getYearRaport();
        this.mon = statePlat.getMonthRaport();
        //PuthReady = config.getPathToReadyLS();
        PuthNoSign = config.getPathToSignLS();

        table = new Table("Список квитанций на выписанные счета (квинанции не требуется подписывать)");
        addComponent(table);
        table.setWidth("100%");
        table.setHeight("170px");
        //table.setHeight("100%");
        //table.setSizeUndefined();
        //table.setSizeFull() ;
        table.setSelectable(true);
        table.setMultiSelect(true);
        table.setImmediate(true); // react at once when something is selected

        table.addContainerProperty("Файл документа", Link.class,  null);
        table.addContainerProperty("Операция", Button.class,  null);
        table.addContainerProperty("Вид документа",  String.class,  null);
        table.addContainerProperty("Состояние документа",String.class, null);

        table.setColumnWidth("Операция", 75);
        table.setColumnWidth("Вид документа", 180);
        table.setColumnWidth("Подписан", 55);
        Refresh(statePlat.getYearRaport(), statePlat.getMonthRaport());

    }
    public void Refresh(Integer yearraport, Integer monthraport){
        this.yea = yearraport;
        this.mon = monthraport;
        table.removeAllItems();
        EntityManager em = Persistence
                .createEntityManagerFactory(linkRes)
                .createEntityManager();
        Collection emps = em.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon and (g.created is not null) and (g.deleted is null) and (g.idgroup = 2 )").
                setParameter("idplat", idplat).setParameter("yea", yea).setParameter("mon", mon).getResultList();
        kvitExists = !emps.isEmpty();
        if (kvitExists) {
            Iterator it = emps.iterator();
            Integer i = 0;
//            allReady = true;
            while (it.hasNext()){
                WebSchets e = (WebSchets) it.next();

                String fileNameLink = e.getFileName();

                //read = new Embedded("",okImage);
                String fullFileName = PuthNoSign+"/"+fileNameLink;
                FileResource resource = new FileResource(new File(fullFileName));
                Integer viddoc = e.getViddoc();
                Link link = new Link(fileNameLink, resource);
                //link.setTargetName("_top");
                link.setIcon(ICON);
                final Button linkButton = new Button("Скачать",new Button.ClickListener(){
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        String fname = (String) event.getButton().getData();
                        //------------------------ работает одинаково ---------------------------------
                        FileDownloadResource resource = new FileDownloadResource(new File(fname));
                        FileDownloader fd = new FileDownloader(resource);
                        fd.extend(event.getButton());
                    }
                });
                linkButton.setData(fullFileName);
                table.addItem(new Object[] {
                        link,
                        linkButton,
                        // new Embedded("",okImage),
                        tipDocs.get(viddoc)},
                        new Integer(i+1));
                i++;
            }
            setImmediate(true);
        }

    }
    public boolean getKvitExists(){
        return kvitExists;
    }
}
