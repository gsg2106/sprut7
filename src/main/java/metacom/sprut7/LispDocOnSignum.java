package metacom.sprut7;


import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.IComponentContainer;
import metacom.sprut7.beans.StatePlat;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: Сергей
 * Date: 11.06.16
 * Time: 17:15
 * To change this template use File | Settings | File Templates.
 */
public class LispDocOnSignum extends VerticalLayout implements IComponentContainer {
    private Integer idplat;
    private String nameplat;
    private String adres;
    private String state;
    private String numdog;
    private String linkRes = null;
    private Date dtLock;
    private StatePlat statePlat;
    static Config config = null;
    private String basepath;
    private TabSheet t;
    private LispReadySchets ls;
    private VerticalLayout vl1;
    private VerticalLayout vl2;
    Resource icon1 = null;
    private UploadPDF uploadpdf1;

    public LispDocOnSignum(){
    }
    @Override
    public void init() {
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
        this.idplat=statePlat.getIdPlat();  //idplat;
        this.nameplat=statePlat.getNamePlat();  //nameplat;
        this.adres= statePlat.getAdres(); //adres;
        this.state= statePlat.getState(); //state;
        this.numdog= statePlat.getNumdog(); //numdog;
        this.dtLock = statePlat.getDtLock(); //dtLock;
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        basepath = config.getPathToSignLS();
        setMargin(true);
        setHeight("100%");
        setStyleName("LispRaWindow");
        this.setHeight("700px");
        Resource icon1 = null;
        Resource icon2 = null;

        buildMain();
        setImmediate(true);
    }

    private void buildMain() {
        //------------------- создадим панель закладок ----------------------
        vl1 = new VerticalLayout();
        vl1.setMargin(true);

        String ms1 ="<i><p style=\"color:#0000ff\">"+
                "Посмотрите список сформированных документов и  загрузите их в свой компьютер,  нажав на   ссылку. "+
                "Нажмите кнопку \"Подписать счет\" и перейдите на закладку \"Подписать файл счета\""+
                "</p> </i>";
        vl1.addComponent(new Label(ms1, ContentMode.HTML)); // .CONTENT_XHTML));
        vl1.addComponent(new Ruler());

        if (ls == null){
//           ls = new LispReadySchets(basepath,idplat, numdog,statePlat.getYearRaport(),statePlat.getMonthRaport());
            ls = new LispReadySchets();
            ls.setOnlyNotReadyDocs(true);
        }
        vl1.addComponent(ls);
        vl1.setComponentAlignment(ls, Alignment.TOP_CENTER);

        vl1.addComponent(new Ruler());
        HorizontalLayout operationbar = new HorizontalLayout();
        operationbar.addComponent(new Label("После скачивания файла счета его необходимо подписать"));


//        uploadpdf1 = new UploadPDF(idplat,statePlat.getYearRaport(),statePlat.getMonthRaport(), ls, this);
        uploadpdf1 = new UploadPDF(idplat,statePlat.getYearRaport(),statePlat.getMonthRaport(),
                new UploadPDF.RefreshRecipient(){
                    @Override
                    public void refresh(Integer yea, Integer mon) {
                        ls.Refresh(yea, mon);
                        if (ls.getAllReady()){
                            //LockRa("Все документы подписаны и получены энергоснабжающей организацией. Работа завершена.");
                            statePlat.setMess("Все документы подписаны и получены энергоснабжающей организацией. Работа завершена.");

                        }
                    }
                }
        );



        Button htmlButton = new Button("Подписать счет");
        htmlButton.setDescription("Подписывание загруженного файла документа электронной подписью пользователя");
        htmlButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                if (vl2 == null) {
                    // Tab 3 content
                    Resource icon3 = null;
                    vl2 = new VerticalLayout();
                    vl2.setMargin(true);

                    String ms2 ="<i><p style=\"color:#0000ff\">"+
                            "Подпишите загруженные файлы документов и отправьте их в энергоснабжающую организацию. Учтите, имя подписанного файла отличается от исходного, наличием фразы '.sign' в конце названия файла."+
                            "</p> </i>";
                    vl2.addComponent(new Label(ms2,ContentMode.HTML));
                    vl2.addComponent(new Ruler());

                    t.addTab(vl2, "Подписать файл счета.", icon3);
                    WebEmbedApplet();
                    vl2.addComponent(new Ruler());
                    vl2.addComponent(uploadpdf1)   ;
                    vl2.addComponent(new Ruler());
                    //-----------------------------------------------------------
                } else {
                    t.getTab(vl2).setVisible(true);
                }
                t.setSelectedTab(vl2);  // setTabIndex(3);
            }
        });


        operationbar.addComponent(htmlButton)   ;
        vl1.addComponent(operationbar);
        vl1.addComponent(new Ruler());


        t = new TabSheet();
        t.setHeight("100%"); //t.setHeight("200px");
        t.setWidth("100%");  //t.setWidth("400px");
        t.addTab(vl1, "Список неподписанных документов.", icon1);
        addComponent(t);

    }
    private void WebEmbedApplet() {
        //--------------- разместить апплет на панели ------------------------
//       Embedded e = new Embedded("", new ThemeResource("signPdf.html"));
//       Embedded e = new Embedded("", new ThemeResource("signPdf_utf8.html"));
//        Embedded e = new Embedded("", new ThemeResource("signPdfutf8.html"));
        Embedded e = new Embedded("", new ThemeResource("signPdfPfx_utf8.html"));

        //e.setAlternateText("Vaadin web site");
        e.setType(Embedded.TYPE_BROWSER);
        e.setWidth("100%");
        e.setHeight("380px");
        vl2.addComponent(e);
    }

}
