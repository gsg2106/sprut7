/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.LoadingCache;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import metacom.sprut7.domain.WebInns;

/**
 *
 * @author Сергей
 * Кеширует ИНН и едрпоу по названию соединения и id документа
 * ключ       - Название соединения;ID документа
 * Возвращает - ИНН;ЕДРПОУ
 */
public class SingletonLispINNs {
    private static volatile SingletonLispINNs instance;
    
    public static SingletonLispINNs getInstance() {
        SingletonLispINNs localInstance = instance;
        if (localInstance == null) {
            synchronized (SingletonLispINNs.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SingletonLispINNs();
                }
            }
        }
        return localInstance;
        
    }

    private  LoadingCache<String, String>  lispINNs;
    public SingletonLispINNs(){
      lispINNs  =  CacheBuilder.newBuilder()
       .maximumSize(500)
       .expireAfterWrite(24, TimeUnit.HOURS)
       .build(
           new CacheLoader() {
            @Override
            public Object load(Object arg0) throws Exception {
//                throw new UnsupportedOperationException("Not supported yet.");
           String key =(String) arg0;     
               String[] tokens = key.split(" ");
               String linkRes = tokens[0];
               String result="";
               EntityManager em = Persistence
 	          .createEntityManagerFactory(linkRes)
	          .createEntityManager();
               Collection emps = em.createQuery("SELECT g FROM WebInns g WHERE g.inner = 1 and (g.dateDeleted is null)")
                    .setMaxResults(1).getResultList();
               Boolean innsExists = !emps.isEmpty();  
               if (innsExists) {
                  WebInns e2 = (WebInns) emps.iterator().next();
                  result = e2.getInn()+","+e2.getEdrpou();
               } else {
                 throw new Exception("Подписант со стороны энергоснабжающей организации не найден");
               }
               em.close();
               return result;
            }
           });          
        
    }

    public LoadingCache<String, String> getCache(){
       return lispINNs;
    }    
    
}
