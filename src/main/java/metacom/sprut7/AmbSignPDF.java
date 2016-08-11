/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package metacom.sprut7;

//import com.amb.api.pdf.text.Rectangle;
//import com.amb.api.pdf.text.api.ApiSignaturePdf;
import com.amb.api.pdf.text.Rectangle;
import com.amb.api.pdf.text.api.ApiSignaturePdf;
import com.vaadin.ui.Notification;
import java.io.*;
import java.security.SignatureException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Сергей
 * На замену SignPDF
 */
public class AmbSignPDF {
  private static final int[][] arrayRestangles =
  { { 30, 10, 155, 45 }, { 160, 10, 285, 45 }, { 290, 10, 415, 45 },
    { 420, 10, 545, 45 } };
  //максимальное число подписей
  private static final int MAX_SIGNATURES = 4;
  /** исходный PDF для подписывания */
  private String PDF;
  private String PDF_OUT;
  /** PFX для подписи */
//  private static String PFX1 = null;
  private String PFX1 = null;
//  private static String PFX1_PASSWORD = null;
  private String PFX1_PASSWORD = null;
  //private static ApiSignaturePdf apipdf = null;
  private ApiSignaturePdf apipdf = null;
  private byte[] digest;      
  
//  public AmbSignPDF(String keystore, String keystorePassword) throws SignatureException {
  public AmbSignPDF(String keystore, String keystorePassword){
      if (PFX1 == null){ PFX1 = keystore;}
      if (PFX1_PASSWORD ==null){ PFX1_PASSWORD = keystorePassword;}
//      String nameProvider = new AMBProvider().getName();
//      if (Security.getProvider("AMB") == null)
//        Security.insertProviderAt(new AMBProvider(), 1);
      FileInputStream fis_pfx;
      if (apipdf == null) {
            try {
               fis_pfx = new FileInputStream(PFX1);
               try {
                   apipdf = new ApiSignaturePdf(fis_pfx, PFX1_PASSWORD);
               } catch (SignatureException ex) {
                   Logger.getLogger(AmbSignPDF.class.getName()).log(Level.SEVERE, null, ex);
                   Notification.show("Ошибка инициализации криптографической системы. "+ex,Notification.Type.ERROR_MESSAGE);
               }
            } catch (FileNotFoundException ex) {
               Logger.getLogger(AmbSignPDF.class.getName()).log(Level.SEVERE, null, ex);
               Notification.show("Файл хранилища ключей энергоснабжающей организации не найден.",Notification.Type.ERROR_MESSAGE);
            }
      }
  }
        
//  public static final void signPdf(String FileNameIn,String FileNameOut)
  public void signPdf(String FileNameIn,String FileNameOut)
			throws IOException, Exception
  {
    PDF     = FileNameIn;
    PDF_OUT = FileNameOut;
    //обязательно подключать крипто провайдера (динамически или можно статически)
    try {
        start();     
    } catch (Exception e) {
      throw new RuntimeException(e.toString());
    }
  }                
                
//  static void start() throws Exception {
  void start() throws Exception {
    
    //исходный PDF файл
    File tmp = new File(PDF);
    FileInputStream fis = new FileInputStream(tmp);
    //поток для подписанного документа
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    //число имеющихся подписей
    int sigNumber = 0;
    //положение подписи
    Rectangle rectangle =
      new Rectangle(arrayRestangles[sigNumber][0], arrayRestangles[sigNumber][1],
                    arrayRestangles[sigNumber][2],
                    arrayRestangles[sigNumber][3]);
    
    String hashAlg = "SHA256";
    apipdf.initSign(apipdf.getSignAlgorithm(hashAlg,
                                            apipdf.getKeyAlgorithm()));      
    fis = new FileInputStream(tmp);
/*  ------------- Списки отозванных сертификатов надо формировать ежедневно и подставлять готовые-----------------
    //добавим метку времени
    String tsaUrl = //"http://tsp.iaik.at/tsp/TspRequest";
                    "http://cesaris.itsway.kiev.ua/tsa/srv/tsa.aspx";
    
    //добавим CRL
    RevocationInfoArchival revocationInfoArchival = null;
    Certificate[] certs = apipdf.getCertificates(apipdf.getCertificate());
        
    ArrayList list = new ArrayList();
    CRLRequestor requestor = new CRLRequestor();
    X509CRL crl;
    boolean error = false;//нельзя подписать
    boolean warning = false;//не удалось проверить
    // *  Errors:
    // *  1 - certificate error
    // *  2 - Certificate blocked
    // *  Warnings could not verify the CRL:
    // *  3 - unknown DistributionPoint
    // *  4 - error getting CRL
    // *  5 - timed
    // *  6 - CRL is out of date
    // *  7 - valid CRL has not yet come
    for (int i=0; i<certs.length-1; i++) {//кроме корневого
      //System.out.println(((X509Certificate)certs[i]).getSubjectDN().getName());
      
      crl = requestor.getCRL((X509Certificate)certs[i], 10, false);//base CRL
      if (crl != null && requestor.getErrorCode() == 0)
        list.add(crl);
      else {
        int code = requestor.getErrorCode();
        System.out.println("Base CRL ErrorCode: " + code);
        if (code == 1 || code == 2)
          error = true;
        if (code > 2)
          warning = true;
      }
      
      crl = requestor.getCRL((X509Certificate)certs[i], 10, true);//base CRL
      if (crl != null && requestor.getErrorCode() == 0)
        list.add(crl);
      else {
        int code = requestor.getErrorCode();
        System.out.println("Delta CRL ErrorCode: " + code);
        if (code == 1 || code == 2)
          error = true;
        if (code > 2)
          warning = true;
      }
    }
    
    X509CRL[] crls;
    if (!error && list.size() > 0) {
      crls = new X509CRL[list.size()];
       for (int i=0; i<list.size(); i++) {
         crls[i] = (X509CRL)list.get(i);
       }
      //добавить CRL к подписи
      revocationInfoArchival = new RevocationInfoArchival(crls, null, null);      
    }
*/        
    //подпись помещаем на последней странице в окне с Rectangle
    //если URL на метку времени = null, то метка не добавляется
    //если revocationInfoArchival = null, то списки отзыва отсутсвуют
    //apipdf.sign(fis, new Date(), tsaUrl, revocationInfoArchival, rectangle, -1,
    //            "Sign_" + sigNumber, output);
    
    
    apipdf.sign(fis, new Date(), null, null, rectangle, -1,
                "Energo_" + sigNumber, output);
   digest =  apipdf.getContextHash();
//    метод public byte[] getContextHash() возвращает originalHash после выполнения initVerify() и sign()
//[10:27:01] Sergii Martynenko: класс ApiSignaturePdfметод public byte[] getContextHash() возвращает originalHash после выполнения initVerify() и sign()
//[10:27:01] Sergii Martynenko: класс ApiSignaturePdf
    
//    FileOutputStream fos  = new FileOutputStream(PDF + ".sign.pdf");
    FileOutputStream fos  = new FileOutputStream(PDF_OUT);
    fos.write(output.toByteArray());
    fos.close();
    fis.close();
//Сборка мусора    System.gc();    
    tmp.delete();
    tmp = null;
  }
                
  public String getDigest(){
      return bytArrayToHex(digest);
/*              
            byte[] array = mda.digest(s.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i=0;i<array.length;i++) {
                sb.append(Integer.toHexString((int) array[i]));
            }
            System.out.println(sb.toString());
*/            
  }
  
  public static String bytArrayToHex(byte[] a) {
     StringBuilder sb = new StringBuilder();
     for(byte b: a)
        sb.append(String.format("%02x", b&0xff));
     return sb.toString();
 }            
}
