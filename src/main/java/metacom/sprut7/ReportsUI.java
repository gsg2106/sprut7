package metacom.sprut7;

import com.google.common.cache.LoadingCache;
import com.vaadin.annotations.PreserveOnRefresh;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.StatePlat;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.support.WebApplicationContextUtils;
//import org.codehaus.jackson.map.ObjectMapper;

//@PreserveOnRefresh     // сохранение состояния инстанции а не выпуск новой и потому
                       // обеспечивает хранение пользовательских данных в сессии
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//@PreserveOnRefresh

@Theme("mytheme")
@SuppressWarnings("serial")
public class ReportsUI extends UI
{
    private ApplicationContext applicationContext;

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = ReportsUI.class, widgetset = "metacom.sprut7.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }
    private StatePlat statePlat = null;
    //private static String link = "java:comp/env/jdbc/sprut";
    private static String linkResRoot = "sprut";
    static Config config = null;
    static LoadingCache<String, String>  lispINNs = SingletonLispINNs.getInstance().getCache();
    
    @Override
    protected void init(VaadinRequest request) {
        //--------- извлечем контекст для SPRING-----------
        WrappedSession session = request.getWrappedSession();
        HttpSession httpSession = ((WrappedHttpSession)
                session).getHttpSession();
        ServletContext servletContext =
                httpSession.getServletContext();
        applicationContext = WebApplicationContextUtils.
                getRequiredWebApplicationContext(servletContext);
        //------------------------------------------------
        try {
            // Загрузим конфигурационный файл задачи setup.json
            setConfig();
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        // Состояние плательщика (пока незаполнено)
        statePlat = (StatePlat) VaadinSession.getCurrent().getAttribute("statePlat");
        if (statePlat == null){
            statePlat = new StatePlat();
            VaadinSession.getCurrent().setAttribute("statePlat", statePlat);
        }
        //------------------------------------------------
        //VaadinSession.getCurrent().setAttribute("link", link);
        VaadinSession.getCurrent().setAttribute("linkResRoot", linkResRoot);
        VaadinSession.getCurrent().setAttribute("config", config);
//        VaadinSession.getCurrent().setAttribute("statePlat", statePlat);
        VaadinSession.getCurrent().setAttribute("lispINNs",lispINNs);
        VaadinSession.getCurrent().setAttribute("applicationContext",applicationContext);

         Navigator navigator = new Navigator(this, this);
         navigator.addView("",(View) this.getApplicationContext().getBean("loginView"));
//         navigator.addView("", new LoginView(link));
//        navigator.addView("dolgi", (View) this.getApplicationContext().getBean("dolgiView"));
        navigator.addView("dolgi", new DolgiView());
//         navigator.addView("main", new MainView());
//         navigator.addView("spschet", new SpSchetW(link));

        navigator.addView("kabinet",(View) this.getApplicationContext().getBean("kabinetView"));
         navigator.addView("help",(View) this.getApplicationContext().getBean("helpView"));
         navigator.addView("licSchet",(View) this.getApplicationContext().getBean("licSchetView"));
         navigator.addView("lispTu",(View) this.getApplicationContext().getBean("lispTuView"));
         navigator.addView("schets",(View) this.getApplicationContext().getBean("schetsView"));
         navigator.addView("lispRa",(View) this.getApplicationContext().getBean("lispRaView"));
         navigator.addView("nalogNaklad",(View) this.getApplicationContext().getBean("nalogNakladView"));
         navigator.addView("lispAllPu",(View) this.getApplicationContext().getBean("lispAllPuView"));
        navigator.addView("historyRaports",(View) this.getApplicationContext().getBean("historyRaports"));
        navigator.addView("historyTarif",(View) this.getApplicationContext().getBean("historyTarif"));
        navigator.addView("lispDocOnSignum",(View) this.getApplicationContext().getBean("lispDocOnSignum"));
        navigator.addView("application",(View) this.getApplicationContext().getBean("application"));
        navigator.addView("correction",(View) this.getApplicationContext().getBean("correction"));



//        if (getSession().getAttribute("idplat") != null){
//            getNavigator().navigateTo("dolgi");
//        }

        //
        // We use a view change handler to ensure the user is always redirected
        // to the login view if the user is not logged in.
        //
        getNavigator().addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                // Check if a user has logged in
                Boolean isLoggedIn = getSession().getAttribute("idplat") != null;
                Boolean isLoginView = event.getNewView() instanceof LoginView;

                if (!isLoggedIn && !isLoginView) {
                    // Если пользователь не залогинен но пытается работать
                    // перенаправляем но логин
                    // Redirect to login view always if a user has not yet
                    // logged in
                    getNavigator().navigateTo("");
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // Если пользователь уже залогинен но пытается логинится повторно
                    // не пускаем.
                    getNavigator().navigateTo("dolgi");
                    return false;
                }

                return true;
            }
            
            @Override
            public void afterViewChange(ViewChangeEvent event) {
                
            }
        });


    }
    //public String getLink(){
    //    return link;
   // }
    
    public void setConfig() throws IOException{
       if (config == null) {
          String IniFile;
          IniFile = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath()+"\\..\\..\\SETUP_SPRUT\\setup.json";
          File fsetup;
          fsetup = new File(IniFile);
          String pt;
          pt = fsetup.getCanonicalPath(); // имя только для контроля
          if(!fsetup.exists()) {
             IniFile = VaadinSession.getCurrent().getService().getBaseDirectory().getAbsolutePath()+"\\SETUP\\setup.json";
             fsetup = new File(IniFile);
             pt = fsetup.getCanonicalPath(); // имя только для контроля
          }       
       
          ObjectMapper mapper = new ObjectMapper();
          config = mapper.readValue(fsetup, Config.class);       
       }
    }
    
  public ApplicationContext getApplicationContext() {
    return applicationContext;
  }
}

