/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import java.io.File;
import java.util.Date;

/**
 *
 * @author Сергей
 */
public class SingletonCleaning {
    private static volatile SingletonCleaning instance;
    public static SingletonCleaning getInstance() {
        SingletonCleaning localInstance = instance;
        if (localInstance == null) {
            synchronized (SingletonCleaning.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new SingletonCleaning();
                }
            }
        }
        return localInstance;
        
    }

    private  Date  dateLastCleaning;
    public SingletonCleaning(){
      dateLastCleaning  =  null;
      // чистим
      //cleaningFiles();
    }

    public void Clean(String puthToTmp){
       String puthToTmp_ = puthToTmp;
       if (dateLastCleaning == null){
          cleaningFiles(puthToTmp_);
       } else {
           Date current = new Date(); 
//           Date diff = new Date(current.getTime() - dateLastCleaning.getTime());
           Long diffI =current.getTime() - dateLastCleaning.getTime();
//??           if (diff.compareTo(new Date(86400000))>0 ){
           if ( diffI.compareTo(new Long(86400000)) > 0) {
              // чистим
              cleaningFiles(puthToTmp_);
           }   
       }
       dateLastCleaning  =  new Date();
    }    
    
    private void cleaningFiles(String puthToTmp){
        // чистим каталог
        File myFolder = new File(puthToTmp);
        File[] files = myFolder.listFiles();
        for (int i=0; i<files.length; i++){
           File fl = files[i]; 
           if (fl.exists()){
               if (fl.isFile()){
                  Date dt = new Date(fl.lastModified()); 
                  if (dateLastCleaning == null){
                      // только запущен синглетон. (мтартует программа.
// отключено по просьбе ковалюхи fl.delete(); 
                  } else {
                    if (dt != null) { 
                       //Date diff = new Date(dateLastCleaning.getTime() - dt.getTime());
                       Long diffI =dateLastCleaning.getTime() - dt.getTime();
//                     if (diff.compareTo(new Date(86400000))>0 ){
                       if ( diffI.compareTo(new Long(86400000)) > 0) {
// отключено по просьбе ковалюхи fl.delete();
                       }
                    }
                 }
               }   
           }
        }
    }
    
}
