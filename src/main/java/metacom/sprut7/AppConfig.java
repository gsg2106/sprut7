/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

/**
 *
 * @author Сергей
 */
//import com.app.domain.UserService_;
//import com.app.domain.UserServiceImpl;
import com.vaadin.navigator.View;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import metacom.sprut7.beans.*;
import metacom.sprut7.dao.Orik;
import metacom.sprut7.dao.OrikImpl;
import metacom.sprut7.firebird.HistTarif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/*
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"org.company"})
@ImportResource({"classpath:security.xml"})
public class CompanyWebMvcConfiguration extends WebMvcConfigurerAdapter {
} 
 */

//@AnnotationDrivenConfig
@Configuration
@ComponentScan(basePackages = {"metacom.sprut7","metacom.sprut7.beans","metacom.sprut7.domain","metacom.sprut7.firebird"})
public class AppConfig {

/*    
@Bean //(name="availableUser")
    public Users users(){
       UsersImpl res = new UsersImpl();
       return res;
    }
*/
    @Bean
    @Scope("prototype")
    public TabSheetMainLayout getTabSheetMainLayout(){
        return new TabSheetMainLayout();
    }

    @Bean //(name="loadPlat")
    @Scope("prototype")
    public LoadPlat loadPlat(){
        return new LoadPlatImpl();
    }

    @Bean(name = "daoOrik")
    @Scope("singleton")
    public Orik orik(){
       return new OrikImpl();
    }


    @Bean(name = "application")
    @Scope("prototype")
    public View application(){
        CommonView applicationView = new CommonView();
        applicationView.setComponentContainer(getApplications());
        return applicationView;
    }

    @Bean
    @Scope("prototype")
    //@Scope("session")
    public Applications getApplications() {
        return new Applications();
    }

    @Bean
    @Scope("prototype")
    public OrikRightImpl orikRight(){
        return new OrikRightImpl();
    }

    @Bean
    @Scope("prototype")
    public OrikKvitImpl orikKvit(){
        return new OrikKvitImpl();
    }

    @Bean
    @Scope("prototype")
    public OrikKvitPowerImpl orikKvitPower(){
        return new OrikKvitPowerImpl();
    }

    @Bean(name = "correction")
    //@Scope(UIScope)
    @Scope("prototype")
    public View correction(){
        CommonView correctionView = new CommonView();
        correctionView.setComponentContainer(new Corrections());
        return correctionView;
    }

@Bean(name="loginView")
@Scope("prototype")
    public View loginView(){
        CommonView myForma = new CommonView();
        return new LoginView();
    }
@Bean(name="dolgiView")
@Scope("prototype")
    public View dolgiView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Dolgi());
        return myForma;
    }
@Bean(name="kabinetView")
@Scope("prototype")
    public View kabinetView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Kabinet());
        return myForma;
    }
@Bean(name="helpView")
@Scope("prototype")
    public View helpView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Help());
        return myForma;
    }

@Bean(name="licSchetView")
@Scope("prototype")
    public View licSchetView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new LicSchet());
        return myForma;
    }


@Bean(name="lispTuView")
@Scope("prototype")
    public View lispTuView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new LispTuWindow());
        return myForma;
    }

@Bean(name="schetsView")
@Scope("prototype")
    public View schetsView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Schets());
        return myForma;
    }

@Bean(name="lispRaView")
@Scope("prototype")
    public View lispRaView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new LispRaWindow());
        return myForma;
    }

    @Bean(name="lispDocOnSignum")
    @Scope("prototype")
    public View lispDocOnSignum(){
        CommonView myForma = new CommonView();
//        myForma.setComponentContainer(new LispRaWindow());
        myForma.setComponentContainer(new LispDocOnSignum());
        return myForma;
    }


@Bean(name="nalogNakladView")
@Scope("prototype")
    public View nalogNakladView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new Nalog());
        return myForma;
    }
    

@Bean(name="lispAllPuView")
@Scope("prototype")
    public View lispAllPuView(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new AllPu());
        return myForma;
    }

@Bean(name="historyRaports")
@Scope("prototype")
    public CommonView historyRaports(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new HistoryNalNak());
        return myForma;
    }

    @Bean(name="historyTarif")
    @Scope("prototype")
    public CommonView historyTarifs(){
        CommonView myForma = new CommonView();
        myForma.setComponentContainer(new HistTarif());
        return myForma;
    }
/*
@Bean
@Component(value = "monApplication") @Scope(value = "session") 
@Scope(value = "session")
@Scope(ApplicationScope.NAME)
@Scope(ScopeType.APPLICATION)
    public LinkPoolImpl linkPoolImpl(){
        return new LinkPoolImpl();
    }
@Bean
  public AuthManager authManager() {
    AuthManager res = new AuthManager();
    return res;
  }
  @Bean
  public UserService userService() {
    UserService res = new UserService();
    return res;
  }
  @Bean
  public LoginFormListener loginFormListener() {
    return new LoginFormListener();
  }
*/    
}