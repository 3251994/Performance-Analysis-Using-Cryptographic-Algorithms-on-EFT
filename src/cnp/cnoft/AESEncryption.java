
package cnp.cnoft;

import static cnp.cnoft.DES.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;


public class AESEncryption {
    
    static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
  static final String URL = "jdbc:mysql://localhost:3306/Login?autoReconnect=true&useSSL=false";
  static final String USER="root";
  static final String PASS="anitson";
  
  Connection con;
  Statement smt;
    
  public AESEncryption(String acno,String amount) throws Exception{
        String s = acno;
        String amt = amount;
        String plainText = acno;
        String plainText1 = amount;
        int id=0;
        SecretKey secKey = getSecretEncryptionKey();
        byte[] AESencryptedData = encryptText(plainText, secKey);
        byte[] AESencryptedData1 = encryptText(plainText1, secKey);
        //String decryptedText = decryptText(cipherText, secKey);
        
        //System.out.println("Original Text:" + plainText+"\n"+plainText1);
        //System.out.println("AES Key (Hex Form):"+bytesToHex(secKey.getEncoded()));
        System.out.println("Encrypted message:\n"+bytesToHex(AESencryptedData)+"\n"+bytesToHex(AESencryptedData1));
        //System.out.println("Descrypted Text:"+decryptedText);
        System.out.println(AESencryptedData);
        System.out.println(AESencryptedData1);
        System.out.println(id);
        JOptionPane.showMessageDialog(null,"Data encrypted");
        try
  {
     Class.forName("com.mysql.jdbc.Driver");
     con=DriverManager.getConnection(URL,USER,PASS);
     smt=con.createStatement();
     //String sql="delete from AESstore";
      //smt.executeUpdate(sql);
      //smt.executeUpdate("insert into AESstore (id) values('"+id+"')");
      id=id+1;
     smt.executeUpdate("insert into AESstore (id,AESencryptedData,AESencryptedData1,seckey) values('"+id+"','"+AESencryptedData+"','"+AESencryptedData1+"','"+secKey+"')");
     System.out.println("Successfully stored!");
     }
    catch(Exception e)
    {
    }
 }
    
        
        
        
        /*String plainText = "Hello World";
        SecretKey secKey = getSecretEncryptionKey();
        byte[] cipherText = encryptText(plainText, secKey);
        //String decryptedText = decryptText(cipherText, secKey);
        
        System.out.println("Original Text:" + plainText);
        System.out.println("AES Key (Hex Form):"+bytesToHex(secKey.getEncoded()));
        //System.out.println("Encrypted Text (Hex Form):"+bytesToHex(cipherText));
        //System.out.println("Descrypted Text:"+decryptedText);
        */
    /**
     *
     * @return
     * @throws Exception
     */
    public static SecretKey getSecretEncryptionKey() throws Exception{
        KeyGenerator generator = KeyGenerator.getInstance("AES");
        generator.init(128); // The AES key size in number of bits
        SecretKey secKey = generator.generateKey();
        return secKey;
    }
    
    public static byte[] encryptText(String plainText,SecretKey secKey) throws Exception{
		// AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.ENCRYPT_MODE, secKey);
        byte[] byteCipherText = aesCipher.doFinal(plainText.getBytes());
        return byteCipherText;
    }
    
   /*
    public static String decryptText(byte[] byteCipherText, SecretKey secKey) throws Exception {
		// AES defaults to AES/ECB/PKCS5Padding in Java 7
        Cipher aesCipher = Cipher.getInstance("AES");
        aesCipher.init(Cipher.DECRYPT_MODE, secKey);
        byte[] bytePlainText = aesCipher.doFinal(byteCipherText);
        return new String(bytePlainText);
    }
    */
    
    private static String  bytesToHex(byte[] hash) {
        return DatatypeConverter.printHexBinary(hash);
    }
public static void main(String[] args) throws Exception {
    
}
}
