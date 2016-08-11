/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import java.io.OutputStream;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import metacom.sprut7.beans.Config;
import metacom.sprut7.beans.StatePlat;
import metacom.sprut7.domain.GsgWebViewPlat;
import metacom.sprut7.domain.WebInns;
import metacom.sprut7.domain.WebSchets;
//import com.vaadin.ui.Window.Notification;

/**
 *
 * @author Сергей
 */

@SuppressWarnings("serial")

public class UploadPDF extends VerticalLayout {
    private static String PuthToReadyLS;
    private static String PuthToTmp;
    private static String PuthToSignLS;
    
//    private Label status = new Label("Пожалуйста выберите файл счета для выгрузки");
    private Label status = new Label("");
    private ProgressIndicator pi = new ProgressIndicator();
    private MyReceiver receiver = new MyReceiver();
    private HorizontalLayout progressLayout = new HorizontalLayout();
    private Upload upload = new Upload("Выберите файл счета для отправки в энергоснабжающую организацию.", receiver);
//    private static  AmbTestSignPDF testPDF = null;  
    private AmbTestSignPDF testPDF = null;  
    private Integer idplat;
    //----------- реквизиты энергетиков -----------------
    private ArrayList<String> baseEDRPOUs = null;
    private ArrayList<String> baseINNs = null;
    //----------- реквизиты клиента ---------------------
    private ArrayList<String> userINNs = null;
    private ArrayList<String> userEDRPOUs = null;
    private Integer yea;
    private Integer mon;
//    private LispReadySchets ls;
//    private final Window window  = SprutVaadinApplication.getInstance().getMainWindow();
    private String linkRes;
    static Config config = null;
//    private LispRaWindow lispRa;
    private RefreshRecipient refreshRecipient = null;
    private StatePlat statePlat;
//    private final Integer code_nalNak = 4;
    //---------------------------------------------------
//    private File file;
//    private FileOutputStream fos;
//    private SprutVaadinApplication application = SprutVaadinApplication.getInstance();

//    private Label result = new Label();
//    private LineBreakCounter counter = new LineBreakCounter();
//    private Upload upload = new Upload("Подписанный файл счета необходимо выгрузить обратно на сервер", counter);


    //public UploadPDF(Integer idplatI, Integer yearraport, Integer monthraport, final LispReadySchets ls, final LispRaWindow lispRa) {
    public UploadPDF(Integer idplatI, Integer yearraport, Integer monthraport, final RefreshRecipient refreshRecipient) {
        this.idplat = idplatI;
        this.mon = monthraport;
        this.yea = yearraport;
//        this.ls = ls;
//        this.lispRa = lispRa;
        this.refreshRecipient = refreshRecipient;
        statePlat = (StatePlat)  VaadinSession.getCurrent().getAttribute("statePlat");
        linkRes = statePlat.getLinkRes();
//        linkRes =(String) VaadinSession.getCurrent().getAttribute("linkRes");
        
        config = (Config) VaadinSession.getCurrent().getAttribute("config");
        
        // считаем необхобимые реквизиты
        readEDRPOUs_INNs();
        // ------------------------------
        setSpacing(true);
 //       SprutVaadinApplication application = SprutVaadinApplication.getInstance();        
        PuthToReadyLS = config.getPathToReadyLS(); //application.getPuthToReadyLS();
        PuthToSignLS  = config.getPathToSignLS(); //application.getPuthToSignLS();
        PuthToTmp     = config.getPathToTmp();  //application.getPuthTmp();
        // Slow down the upload
        receiver.setSlow(true);

        addComponent(status);
        upload.setDescription("Переслать подписанный пользователем файл документа в энергоснабжающую организацию. ");
        addComponent(upload);
        
        upload.setDebugId("uploadFile");
        
        addComponent(progressLayout);

        // Make uploading start immediately when file is selected
        //====================================================================
        upload.setImmediate(true);  // Если включить то исчезает поле ввода
        //====================================================================
        upload.setButtonCaption("Отправить файл в Э/с орг.");

        progressLayout.setSpacing(true);
        progressLayout.setVisible(false);
        progressLayout.addComponent(pi);
        progressLayout.setComponentAlignment(pi, Alignment.MIDDLE_LEFT);
        
//        upload.setButtonCaption("Выгрузить");
//        addComponent(upload
//        addComponent(result);
        final Button cancelProcessing = new Button("Cancel");
        cancelProcessing.addListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
                upload.interruptUpload();
            }
        });
        cancelProcessing.setStyleName("small");
        progressLayout.addComponent(cancelProcessing);

        /**
         * =========== Add needed listener for the upload component: start,
         * progress, finish, success, fail ===========
         */
        upload.addStartedListener(new Upload.StartedListener() {
            public void uploadStarted(StartedEvent event) {
                // This method gets called immediatedly after upload is started
       Statistika stat = new Statistika();
       stat.init(idplat, Statistika.st_ECP );
                
                
                upload.setVisible(false);
                progressLayout.setVisible(true);
                pi.setValue(0f);
                pi.setPollingInterval(500);
                status.setValue("Выгрузка файла \"" + event.getFilename()
                        + "\"");
            }
        });

        upload.addListener(new Upload.ProgressListener() {
            public void updateProgress(long readBytes, long contentLength) {
                // This method gets called several times during the update
                pi.setValue(new Float(readBytes / (float) contentLength));
            }

        });

        upload.addListener(new Upload.SucceededListener() {
            public void uploadSucceeded(SucceededEvent event) {
                // This method gets called when the upload finished successfully
                status.setValue("Выгрузка файла \"" + event.getFilename()
                        + "\" завершилась успешно");
            }
        });

        upload.addListener(new Upload.FailedListener() {
            public void uploadFailed(FailedEvent event) {
                // This method gets called when the upload failed
                status.setValue("Выгрузка прервана");
            }
        });

        upload.addListener(new Upload.FinishedListener() {
            public void uploadFinished(FinishedEvent event) {
                // This method gets called always when the upload finished,
                // either succeeding or failing
//lispRa.LockRa("nnnn");
//?                String filenameSign = event.getFilename();
//?                filenameSign = cleaningFilename(filenameSign);
                progressLayout.setVisible(false);
                upload.setVisible(true);
                upload.setCaption("Выберите следующй файл");
                
//------------------Проверка подписи ---------------------------------------------------------------------               
                       // String keystore = SprutVaadinApplication.getInstance().getPuthKeystore();
                       // String keystorePassword = SprutVaadinApplication.getInstance().getKeystorePassword();
                       // TestSignPDF test = new TestSignPDF(keystore, keystorePassword);
                       if (testPDF == null) 
                          testPDF = new AmbTestSignPDF(); 
                       String mess = null;
//                       Integer codRet = testPDF.verifySignPDF(PuthToReadyLS+"/"+ event.getFilename());
                       Integer codRet = testPDF.verifySignPDF(PuthToTmp+"/"+ event.getFilename());
                    
                       if (codRet.equals(1)){
                          mess = "1 - системная ошибка Java";
                       } else if (codRet.equals(2)){
                          mess = "2 - не задан аргумент pdfFile";
                       } else if (codRet.equals(3)){
                          mess = "3 - отсутствует провайдер Java AMBProvider";
                       } else if (codRet.equals(4)){
                          mess = "4 - ошибка чтения входного PDF файла";
                       } else if (codRet.equals(5)){
                          mess = "5 - ошибка структуры входного PDF файла";
                       } else if (codRet.equals(6)){
                          mess = "6 - ошибка одной (нескольких) подписей";
                       } else if (codRet.equals(7)){
                          mess = "7 - в документ были внесены изменения между подписываниями редакций документа";
                       } else if (codRet.equals(8)){
                          mess = "8 - документ не подписан";
                       } else if (codRet.equals(-1)){
                          mess = "На документе единственная подпись";
                       } else {
                           Vector sign = testPDF.getSignatureNames();
                           //String nameSign = sign.firstElement().toString();
                           //if (!nameSign.equals("Energo_0") ){
                           if (sign.indexOf("Energo_0") == -1) {
                               mess = "На документе нет подписи энергоснабжающей организации";
                           } else {
                               codRet = verifyINN_EDRPOU();
                               if (codRet.equals(1)){
                                  mess = "9 - Отсутствует подпись энергоснабжающей организации";
                               } else if (codRet.equals(2)){
                                  mess = "10 - реквизиты энергоснабжающей организации некорректны";
                               } else if (codRet.equals(3)){
                                  mess = "11 - подпись клиента не содержит инн";
                               } else if (codRet.equals(4)){
                                  mess = "12 - подпись клиента содержит неверные ИНН и ЕДРПОУ";
                               } else {
                                  codRet = registerSignedFile(event.getFilename());
                                  if (codRet.equals(1)){
                                     mess = "13 - Вы прислали посторонний файл.";
                                  } else if (codRet.equals(2)){
                                     mess = "14 - Название файла не соответствует оригиналу";
                                  } else if (codRet.equals(3)){
                                     mess = "15 - Файл потерял актуальность вследствие пересчета";
                                  } else if (codRet.equals(5)){
                                     mess = "16 - Сбой при копировании файла в хранилище.";
                                  } else if (codRet.equals(6)){
                                     mess = "17 - Название файла слишком длинное. (возможно, файл подписан несколько раз)";
                                  } else if (codRet.equals(4)){
                                     mess = null;
                                     //mess = "16 - Вы прислали файл повторно";
                                  }   
                               }
                           }
                       }
                       // сборщик мусора (иначе не работает)
//                       System.gc();    
                       if (mess == null){
                          //----- удалить исходный файл ------------------
                          new File(PuthToSignLS+"/"+ event.getFilename() ).delete();
                          Notification.show("Присланный файл получен и успешно зарегистрирован.");
                          // отключим пока и заменим другой проверкой
                          /*
                          String mm = getEnabledRaport();
                          if (mm != null){
                              window.showNotification("Расчет за потребленную энергию завершен. ");
                              upload.setVisible(false);
                              
                              lispRa.LockRa(mm);

                          }
                          */
                          //-------------------------------------------------------
                       } else {    
                          //--------------- удалить присланный файл --------------- 
                          //new File(PuthToReadyLS+"/"+ event.getFilename() ).delete();
                          new File(PuthToTmp+"/"+ event.getFilename() ).delete();
                           
                          Notification.show(mess+" Присланный Файл удален.",Notification.Type.ERROR_MESSAGE);
//           lispRa.custom.removeComponent("main"); 
//           lispRa.custom.addComponent(new LicSchet(idplat),"main");   
                          
//                          getWindow().showNotification(mess+"Присланный Файл удален.");
                       }

                       refreshRecipient.refresh(yea, mon);
//                        ls.Refresh(yea, mon);
//
//                       if (ls.getAllReady()){
//                          lispRa.LockRa("Все документы подписаны и получены энергоснабжающей организацией. Работа завершена.");
//                       }
//----------------------------------------------------------------------------------------------------------                       
            }
        });

    }

    
//    private String cleaningFilename(String FileNameSign){
        /* очистим название файла от номеров версий файла */
//        int i2 = FileNameSign.indexOf(").pdf");
//        String fn; 
//        return fn;    
//    }
    
    public static class MyReceiver implements Receiver {
//    public class MyReceiver implements Receiver {

        private String fileName;
        private String mtype;
        private Boolean sleep;
        private int total = 0;

        public OutputStream receiveUpload(String filename, String mimetype) {
            fileName = filename;
            mtype = mimetype;
            FileOutputStream fos = null;
//            File file = new File(PuthToReadyLS+"/" + filename);
            File file = new File(PuthToTmp+"/" + filename);
            
            try {
                    fos = new FileOutputStream(file);
/*------ это не работает поскольку этот метод не вызывается надо както переделать-------
                    {
                         @Override
                         public void write(int b) throws IOException {
                            total++;
                            if (sleep && total % 10000 == 0) {
                                try {
                                    Thread.sleep(100);
                                } catch (InterruptedException e) {
                                   // TODO Auto-generated catch block
                                   e.printStackTrace();
                                }
                            }
                         }    
                    };
*/            
            } catch (final java.io.FileNotFoundException e) {
//                    window.showNotification(
                    //  здесь отказаться от получения не получается из за статик
                    Notification.show(
                            "Невозможно открыть файл<br/>", e.getMessage(),
                            Notification.Type.ERROR_MESSAGE);
                    return null;
            }
            return fos;
        };

        public String getFileName() {
            return fileName;
        }

        public String getMimeType() {
            return mtype;
        }

        public void setSlow(Boolean value) {
            sleep = value;
        }
                    

    }
    
    private Integer  verifyINN_EDRPOU(){
        // возвращает -1 если все в порядке
        //             1 нет подписи энергоснабжающей организации
        //             2 реквизиты энергоснабжающей организации некорректны
        //             3 подпись клиента не содержит инн
        //             4 подпись клиента содержит не верные ИНН и ЕДРПОУ
        //----------------------------------------------------------------
        
        // проверить        
        //правильность реквизитов энергоснабжающей организации
        //наличиие подписанта в списке возможных подписантов клиента
        Integer retcode = 0;
        Vector<String> t_names = testPDF.getSignatureNames();
        ArrayList<String> t_EDPOUs =testPDF.getEDRPOUs();        
        ArrayList<String> t_INNs =testPDF.getINNS();
        
//        if (!t_names.get(0).equals("Energo_0"))
        Integer baseId = t_names.indexOf("Energo_0");
        if (baseId == -1)
           return 1;
//--- отладка -----------
/*        
        String edpou = t_EDPOUs.get(baseId);
        String inn1 = t_INNs.get(baseId);
        Integer iEDRPOUs = baseEDRPOUs.indexOf(edpou);
        Integer iINNs = baseINNs.indexOf(inn1);
*/
// ----------------------
/*        
        String ss = "baseId ="+baseId+"/edrpou="+t_EDPOUs.get(baseId)+"/ИНН="+t_INNs.get(baseId)
+"Итого="+baseEDRPOUs.indexOf(t_EDPOUs.get(baseId))+"/"+baseINNs.indexOf(t_INNs.get(baseId))+
        baseEDRPOUs.toString()+baseINNs.toString();
Notification.show(ss,Notification.Type.ERROR_MESSAGE);        
*/

        if ((baseEDRPOUs.indexOf(t_EDPOUs.get(baseId))<0) || (baseINNs.indexOf(t_INNs.get(baseId))<0) )
           return 2;
        
        Boolean ok = false;
        for (int i = 0; i < t_names.size(); i++){
            if (!t_names.get(i).equals("Energo_0")){
               String edrpou = t_EDPOUs.get(i);
               String inn = t_INNs.get(i);
               if ((inn == null)) {return 3;}
               else {   
//String ss1 = "edrpou="+edrpou+"/inn="+inn+userINNs.toString();
//Notification.show(ss1,Notification.Type.ERROR_MESSAGE);        
                   
edrpou=null; // Внимание присвоение сделано для того, чтобы отключить проверку
             // ЕДРПОУ по требованию донецка. Дальше код не менялся

                  if ((edrpou == null)) {
                     // ЧАСТНОЕ ЛИЦО 
                     if (userINNs.indexOf(inn) >= 0) {
                        ok = true; // подписант есть в списке 
                     }
                  } else {
                     // ПРЕДПРИЯТИЕ
                     if ((userEDRPOUs.indexOf(edrpou) >= 0) && (userINNs.indexOf(inn) >= 0) ) {
                        ok = true; // подписант есть в списке 
                     }
                  }
               }
            }
        }
        if (!ok) return 4;
        else return -1;
    }
    
    private void readEDRPOUs_INNs(){
        if (baseEDRPOUs == null) {baseEDRPOUs = new ArrayList<String>();}
        if (baseINNs == null)    { baseINNs = new ArrayList<String>();}
        if (userEDRPOUs == null) {userEDRPOUs = new ArrayList<String>();}
        if (userINNs == null)    { userINNs = new ArrayList<String>();}

        EntityManager em = Persistence
 	     .createEntityManagerFactory(linkRes)
	     .createEntityManager();
        Collection emps = em.createQuery("SELECT g FROM WebInns g WHERE ((g.idPlat = :idplat) or (g.inner = 1)) and (g.dateDeleted is null)").
                 setParameter("idplat", idplat).getResultList();
        Boolean innsExists = !emps.isEmpty();  
        if (innsExists) {
            Iterator it = emps.iterator();
            Integer i = 0;
 //?? Непонятно зачем           AmbSignPDF signer = null;
            while (it.hasNext()){
               WebInns e = (WebInns) it.next();
         //      if e.getInner() == null
               if ((e.getInner() != null) && e.getInner().equals(1)) {
                   baseEDRPOUs.add(e.getEdrpou());
                   baseINNs.add(e.getInn());
               } else {
                   userEDRPOUs.add(e.getEdrpou());
                   userINNs.add(e.getInn());
               }
            }
        }
        em.close();
    }
    
    private Integer registerSignedFile(String fileName){
        String alterFileName =fileName;
        // итог = -1 все в порядке
        //         1 Такой счет не выписывался для данного плательщика (дайджест не совпалает).
        //         2 Файл переименован
        //         3 Файл потерял актуальность 
        //         4 файл уже был обрабонан ранее
        //         5 Файл не копируется в хранилище
        //         6 Имя файла слишком длинное 
        //---------------------------------------------------------------------
        // Открыть запрос и проверить 
        // соответствие двцджеста документу.
        //если все в порядке то прописать в базе данных отметку времени
        //поступления файла и признак готовности файла.
        // Особенности: будет подписываться ряд документов подряд
        //---------------------------------------------------------------------
        Integer ret = null;
        String digest = testPDF.getDigest();
        Date dateLastUpload = new Date();
        java.util.Calendar calendar = GregorianCalendar.getInstance();
	calendar.setTime(dateLastUpload);
        
        
        EntityManager emDoc = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
           int ln = fileName.length();
           if (ln>60 ){
               ret = 6;
               return ret;
           }
        Collection emps = emDoc.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon and (g.signatura= :signatura) and (g.idgroup =0) and (g.deleted is null) and (g.fileNameSign != :filename)").
                     setParameter("idplat", idplat).setParameter("yea", yea).setParameter("mon", mon).setParameter("signatura",digest).setParameter("filename", fileName).getResultList();
        Boolean schetExists = !emps.isEmpty();  
        if (schetExists) {
            // есть неудаленный файл с той же сигнатурой но под другим именем.
            Iterator it = emps.iterator();
            ret = 3; // файл потрял актуальность и удален
            while (it.hasNext()){
               WebSchets e = (WebSchets) it.next();
               alterFileName = e.getFileNameSign();
            }
        }
        emDoc.close();
        // теперь можем работать с альтернативным именем. 
        // такое дикое решения связано с проблемами поиска в базе данных.
        
        emDoc = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
        //emDoc.clear();
       // String alterFilename = fileName+".sign.pdf";
        //...... проверка на неудаленный файл с таким же именем, сигнатурой.....
        emps = emDoc.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon and (g.signatura= :signatura) and (g.deleted is null) and (g.fileNameSign = :filename)").
                     setParameter("idplat", idplat).setParameter("yea", yea).setParameter("mon", mon).setParameter("signatura",digest).setParameter("filename", alterFileName).getResultList();
        schetExists = !emps.isEmpty();  
        if (schetExists) {
            //boolean foundDeleted = false;
            Iterator it = emps.iterator();
            ret = 3; // файл потрял актуальность и удален
            while (it.hasNext()){
               ret = 3; // файл потрял актуальность и удален
               WebSchets e = (WebSchets) it.next();
               Boolean isReady = false;
               if ( e.getReady() != null){
                  if ( e.getReady().intValue() == 1){
                     // подпись уже есть. Операция лишняя 
                     isReady = true;
                  }
               }   
               if (isReady){
                  ret = 4;
                  break;
               } else {
                   
                  //............ скопипуем файл на место хранения ..............
                  try {
                            java.nio.file.Files.copy(Paths.get(PuthToTmp+"/"+fileName), Paths.get(PuthToReadyLS+"/"+alterFileName),java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException ex) {
                            // копирование не прошло
                            ret = 5;
                            Logger.getLogger(UploadPDF.class.getName()).log(Level.SEVERE, null, ex);
                            break;
                  }
                   
                  //----- СКОРРЕКТИРУЕМ РЕЕСТР СЧЕТОВ ---------------------
                  emDoc.getTransaction().begin();
                  e.setReady(new Short("1"));
                  e.setUploaded(dateLastUpload);
                  emDoc.persist(e);
                  emDoc.getTransaction().commit();
                  //if (!fileName.equals(alterFileName)) {
                    // скопируем файл на место хранения.
                    //File source = new File(PuthToTmp+"/"+fileName);
                    //File dest = new File(PuthToReadyLS+"/"+alterFileName);
                 // }   
                  //-------------------------------------------------------
//Здесь следует проверить rperiod.web_ready  и если выставлено то 
//        заблокировать органы управления и взвести переменную
                  ret = -1;
                  break;
               } 
            }
        } else
            ret = 1;
        emDoc.close();

        if ((ret.intValue() == 1) || (ret.intValue() == 3)){
          EntityManager emDoc1 = Persistence
    	   .createEntityManagerFactory(linkRes)
	   .createEntityManager();
//          Collection emps1 = emDoc1.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon and (g.signatura= :signatura) and (g.deleted is not null)").
//                     setParameter("idplat", idplat).setParameter("yea", yea).setParameter("mon", mon).setParameter("signatura",digest).getResultList();
          Collection emps1 = emDoc1.createQuery("SELECT g FROM WebSchets g WHERE g.idPlat = :idplat and g.yea = :yea and g.mon = :mon and (g.signatura= :signatura) and (g.deleted is not null) and (g.fileNameSign = :filename)").
                     setParameter("idplat", idplat).setParameter("yea", yea).setParameter("mon", mon).setParameter("signatura",digest).setParameter("filename", alterFileName).getResultList();
          
          Boolean schetExists1 = !emps1.isEmpty();  
          if (schetExists1) {
              ret = 3;
          } else {
              ret = 1;
          }
          emDoc1.close();
        }
        
        return ret;

    }
    
    private String getEnabledRaport(){
        String mess = null;
        EntityManager em1 = Persistence
 	   .createEntityManagerFactory(linkRes)
      	   .createEntityManager();
        Collection emps1 = em1.createQuery("SELECT g FROM GsgWebViewPlat g WHERE g.idPlat = :idPlat ").setParameter("idPlat", idplat).setMaxResults(1).getResultList();
        Boolean platexists1 = !emps1.isEmpty();  
        if (platexists1) {
            GsgWebViewPlat e = (GsgWebViewPlat) emps1.iterator().next();
            Integer raportenabled = e.getRaportEnabled();
            mess = e.getMess();
        }
        em1.close();      
        return mess;
    }
            
    public static String getFileNameWithoutExtension(String fileName) {
        File tmpFile = new File(fileName);
        tmpFile.getName();
        int whereDot = tmpFile.getName().lastIndexOf('.');
        if (0 < whereDot && whereDot <= tmpFile.getName().length() - 2 ) {
            return tmpFile.getName().substring(0, whereDot);
            //extension = filename.substring(whereDot+1);
        }
        return "";
    }
    public void setPeriod(Integer yearraport, Integer monthraport){
       yea = yearraport;
       mon = monthraport;
    }

    public interface RefreshRecipient{
        public void refresh(Integer yea, Integer mon);
    };
}
                
        
        
        
        
        
//============================================================================        
/*        
        upload.addListener(new Upload.FinishedListener() {
            public void uploadFinished(FinishedEvent event) {
                result.setValue("Uploaded file contained "
                        + counter.getLineBreakCount() + " linebreaks"
                        );
                        
                        String keystore = SprutVaadinApplication.getInstance().getPuthKeystore();
                        String keystorePassword = SprutVaadinApplication.getInstance().getKeystorePassword();
                        
                        TestSignPDF test = new TestSignPDF(keystore, keystorePassword);
                try {
                    if (test.verifySignPDF(PuthToReadyLS+"/"+ event.getFilename())){
                       //Документ в порядке 
                       getWindow().showNotification("Документ прошел проверку<br/>"); 
                    }else{
                       //Документ имеет проблемы 
                      //getWindow().showNotification("Документ проверку не прошел<br/>"); 
                      String comm = "Документ проверку не прошел<br/>";
                      ArrayList<Map<String,Object>> res = test.getResultVerify();
                      if (res.isEmpty()){comm = "Документ не имеет подписей<br/>";}
                      if (res.size()==1){comm ="Документ должен иметь две подписи<br/>";}
                      for (Object result : res){
                         Map<String,Object> resultSign =(Map<String,Object>) result; 
                         if ((Boolean)resultSign.get("DocumentModified")){
                            comm = comm+ "Документ был модифицирован и не соответсвуетонной подписи<br/>";
                         }
                         if ((Boolean)resultSign.get("CertificateRight")){
                            comm = comm+ "Сертификат не прошел проверку<br/>";
                         }
                             
                      }
                      getWindow().showNotification(comm);
                    }
                        
                } catch (SignatureException ex) {
                    Logger.getLogger(UploadPDF.class.getName()).log(Level.SEVERE, null, ex);
                } catch (KeyStoreException ex) {
                    Logger.getLogger(UploadPDF.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(UploadPDF.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(UploadPDF.class.getName()).log(Level.SEVERE, null, ex);
                } catch (CertificateException ex) {
                    Logger.getLogger(UploadPDF.class.getName()).log(Level.SEVERE, null, ex);
                }
//                if (test.getDocumentModified()){
//                   getWindow().showNotification("Декумент изменен<br/>"); 
//                } else{
//                   getWindow().showNotification("Декумент не менялся<br/>"); 
//                }
            }
                        
        });

    }

//    public static class LineBreakCounter implements Receiver {
    public class LineBreakCounter implements Receiver {

        private String fileName;
        private String mtype;
        private int counter;
        public File file;
*/
        /**
         * return an OutputStream that simply counts lineends
         */
/*        
        public OutputStream receiveUpload(String filename, String MIMEType) {
            counter = 0;
            fileName = filename;
            mtype = MIMEType;
            PuthToReadyLS = SprutVaadinApplication.getInstance().getPuthToReadyLS();
                // Create upload stream
                FileOutputStream fos = null; // Output stream to write to
                try {
                    // Open the file for writing.
                    file = new File(PuthToReadyLS+"/" + filename);
                    fos = new FileOutputStream(file);
                } catch (final java.io.FileNotFoundException e) {
//                    SprutVaadinApplication.getInstance().getMainWindow().showNotification(
//                            "Could not open file<br/>", e.getMessage(),
//                            Notification.TYPE_ERROR_MESSAGE);
//                    return null;
                    getWindow().showNotification(
                            "Could not open file<br/>", e.getMessage(),
                            Notification.TYPE_ERROR_MESSAGE);
                    return null;
                }
                return fos; // Return the output stream to write to            
            //---------------------
            //return new OutputStream() {
            //    private static final int searchedByte = '\n';
            //
            //    @Override
            //    public void write(int b) throws IOException {
            //        if (b == searchedByte) {
            //            counter++;
            //        }
            //    }
            //};
            //--------------------
        }

        public String getFileName() {
            return fileName;
        }

        public String getMimeType() {
            return mtype;
        }

        public int getLineBreakCount() {
            return counter;
        }
*/
    
