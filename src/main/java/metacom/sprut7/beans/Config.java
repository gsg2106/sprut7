/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7.beans;

//import com.google.common.cache.Cache;
//import com.google.common.cache.CacheBuilder;
//import com.google.common.cache.CacheLoader;
//import com.google.common.cache.LoadingCache;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Сергей
 */
public class Config {
  private String pathToNoSignLS;
  private String pathToSignLS;
  private String pathToReadyLS;
  private String pathToTmp;
  private String pathKeystore;
//  private String pathToNsNn;
//  private String keystorePassword;
  private String tested;
  private String goestEnabled;
  private  Map<String, String> lispReses;
  private  Map<String, String> lispKeystores;
  private  Map<String, String> lispKeystorePasswords;

  public String getPathToNoSignLS(){
     return pathToNoSignLS;
  }
  public void setPathToNoSignLS(String pathToNoSignLS){
     this.pathToNoSignLS = pathToNoSignLS;
  }
    
  public String getPathToSignLS(){
     return pathToSignLS;
  }
  public void setPathToSignLS(String pathToSignLS){
     this.pathToSignLS = pathToSignLS;
  }
  
  public String getPathToReadyLS(){
     return pathToReadyLS;
  }
  public void setPathToReadyLS(String pathToReadyLS){
     this.pathToReadyLS = pathToReadyLS;
  }
  
  public String getPathToTmp  (){
     return pathToTmp;
  }
  public void setPathToTmp  (String pathToTmp ){
     this.pathToTmp = pathToTmp ;
  }
    
  public String getPathKeystore(){
     return pathKeystore;
  }
  public void setPathKeystore(String pathKeystore){
     this.pathKeystore = pathKeystore;
  }
/*
  public String getPathToNsNn(){
     return pathToNsNn;
  }
  public void setPathToNsNn(String pathToNsNn){
     this.pathToNsNn = pathToNsNn;
  }
*/
  
  
//  public String getKeystorePassword(){
//     return keystorePassword;
//  }
//  public void setKeystorePassword(String keystorePassword){
//     this.keystorePassword = keystorePassword;
//  }
    
  public String getTested(){
     return tested;
  }
  public void setTested(String tested){
     this.tested = tested;
  }
  
  public String getGoestEnabled(){
     return goestEnabled; 
  }

  public void setGoestEnabled(String goestEnabled){
     this.goestEnabled = goestEnabled; 
  }
  
  public Map<String, String> getLispReses(){
     return lispReses;
  }
  public void setLispReses(Map<String, String> lispReses){
     this.lispReses = lispReses;
  }

  public Map<String, String> getLispKeystores(){
     return lispKeystores;
  }
  public void setLispKeystores(Map<String, String> lispKeystores){
     this.lispKeystores = lispKeystores;
  }
    
  public Map<String, String> getLispKeystorePasswords(){
     return lispKeystorePasswords;
  }
  public void setLispKeystorePasswords(Map<String, String> lispKeystorePasswords){
     this.lispKeystorePasswords = lispKeystorePasswords;
  }

/*
  private String loadINN(String key){
      // воб здесь по ключу и извлечь значение из базы данных
      return "2385211316,00131268";
  }
*/
}
