/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * http://commons.apache.org/proper/commons-pool/
 */
package metacom.sprut7;
   import com.amb.api.pdf.text.api.ApiSignaturePdf;
import com.amb.api.pdf.text.pdf.AcroFields;
   import com.amb.api.pdf.text.pdf.PdfPKCS7;
import com.amb.asn1.x509.X509Utils;
   import com.amb.security.provider.AMBProvider;

   import java.io.FileInputStream;

   import java.security.Security;
   import java.security.cert.X509Certificate;
import java.util.ArrayList;

   import java.util.Vector;

/**
 *
 * @author Сергей
 */
public class AmbTestSignPDF {
	/* Документ PDF подписанный */
        private Vector<String> names;
        private ArrayList<String> edrpous = new ArrayList<String>();
        private ArrayList<String> inns = new ArrayList<String>();
	String fname;             // = "D:\\HelloWorld.pdf" ;
        private byte[] digest;          
        /* Хранилище ключей типа pkcs12 */
	//static String fileKey;          // = "C:\\MonRep\\MaClef.p12" ;
	// Пароль хранилища ключей
	//static	String fileKeyPassword;  // = "MonPassword" ;
        

        private static int code = 1;
        //валидность сертификатов и их цепочек не проверяется !!
        //код ошибки:  
        //1 - системная ошибка Java
        //2 - не задан аргумент pdfFile
        //3 - отсутствует провайдер Java AMBProvider
        //4 - ошибка чтения входного PDF файла
        //5 - ошибка структуры входного PDF файла
        //6 - ошибка одной (нескольких) подписей
        //7 - в документ были внесены изменения между подписываниями редакций документа
        //8 - документ не подписан
        //
        //-X - проверено успешно; возвращает как отрицательное число (X) подписей на документе.
        
//    public AmbTestSignPDF(String keystore, String keystorePassword) {
    public AmbTestSignPDF() {
        //fileKey = keystore;
        //fileKeyPassword = keystorePassword;
       //обязательно подключать крипто провайдера (динамически или можно статически)
       if (Security.getProvider("AMB") == null)
          Security.insertProviderAt(new AMBProvider(), 1);
    }
    
    public Integer verifySignPDF(String FileName){
        fname=FileName;

        //проверяем только подпись, не проверяем валидность сертификата и цепочки
        FileInputStream fis;
        try {
           fis = new FileInputStream(fname);
        } catch (Exception e) {
           return 4;
        }
            
        ApiSignaturePdf pdfapi = null;
        try {
          pdfapi = new ApiSignaturePdf();
          pdfapi.initVerify(fis);
        } catch (Exception e) {
          return 5;
        }
            
        //проверяем все имеющиеся подписи редакций документа
        PdfPKCS7[] pkcs7s = null;
        try {
          pkcs7s = pdfapi.verify();      
        } catch (Exception e) {
          return 6;
        }

        if (pkcs7s == null)
           return 6;

        //проверяем внесены ли изменения между подписываниям редакций документа
        try {
          if (pdfapi.isChangedRevision())
            return 7;
        } catch (Exception e) {
          return 5;
        }
            
        //получить имена подписей
        names = pdfapi.getSignatureNames();
        //получить дайджест
        digest =  pdfapi.getContextHash();
        //получить список списки ЕДРПОУ и ИНН
        AcroFields acroFields = pdfapi.getAcroField();
        PdfPKCS7 p7 = null;
        
        edrpous.clear();
        inns.clear();
        
        for (int i = 0; i < names.size(); i++) 
        {
           p7 = acroFields.verifySignature(names.get(i));
           X509Certificate cert = p7.getSigningCertificate();
           String[] attrs = X509Utils.getSubjectDirectoryAttributesUA(cert);
           if (attrs.length >=1)
              System.out.println("attrs: " + attrs[0]);
           if (attrs.length >=2)
              System.out.println("attrs: " + attrs[1]);
//           edrpous.add(attrs[0]);
//           inns.add(attrs[1]);
//attrs[0]="00131268";
//attrs[1]="2385211316";
//attrs[0]="2385211316";         
//attrs[1]=null;
           
           String a1; String a2;
           if (attrs.length >=2){
              a2 = (attrs[1]==null ? "null" : attrs[1]);
              a1 = (attrs[0]==null ? "null" : attrs[0]);
           } else{
              if (attrs.length >=1){
                 a2 = "null";
                 a1 = (attrs[0]==null ? "null" : attrs[0]);
              }else{
                 a2 = "null";
                 a1 = "null";
              }   
           }   
           edrpous.add(a1.length() <= 8 ? a1 : a2.length()<= 8 ? a2 : "null");
// неправильно           inns.add(   a1.length() >= 10 ? a1 : a2.length() >=10 ? a1 : "null");
           inns.add(   a1.length() >= 10 ? a1 : a2.length() >=10 ? a2 : "null");
           
           
//           edrpous.add(attrs[1]==null ? "null" : attrs[1]);
//           inns.add(attrs[0]==null ? "null" : attrs[0]);
           
           //Массив attrs  имеет два элемента: с индексом 0 идет ИПН или null,
           //если его нет; с индексом 1 идет ЕДРПОУ или null, если его нет.
        }
          //         X509Certificate cert =  pdfapi.getCertificate();
          //         pdfapi. getCertificates(cert)
        
        
        
        return ((-1)*names.size());
        
    }
    
    public Vector<String> getSignatureNames(){
        return names;
    }

    public String getDigest(){
        return bytArrayToHex(digest);
    }
    
    public ArrayList<String> getINNS(){
        return inns;
    }

    public ArrayList<String> getEDRPOUs(){
        return edrpous;
    }
    
    public static String bytArrayToHex(byte[] a) {
        StringBuilder sb = new StringBuilder();
        for(byte b: a)
            sb.append(String.format("%02x", b&0xff));
        return sb.toString();
    }            

}
