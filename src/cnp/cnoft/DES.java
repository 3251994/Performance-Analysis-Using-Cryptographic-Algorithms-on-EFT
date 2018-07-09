

package cnp.cnoft;

import javax.swing.*;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Random ;
import java.sql.*;

public class DES {
byte[] skey = new byte[1000];
String skeyString;
static byte[] raw;
String encryptedData,decryptedMessage;

  static final String JDBC_DRIVER="com.mysql.jdbc.Driver";
  static final String URL = "jdbc:mysql://localhost:3306/Login?autoReconnect=true&useSSL=false";
  static final String USER="root";
  static final String PASS="anitson";
  
  Connection con;
  Statement smt;

    public DES(String acno, String amount) {
        try {
            generateSymmetricKey();
//inputMessage1=JOptionPane.showInputDialog(null,"Enter Account Number");
//byte[] ibyte = inputMessage1.getBytes();
String s = acno;
String amt = amount;
byte[] ibyte =s.getBytes();
byte[] ibyte1 =amt.getBytes();
//inputMessage2=JOptionPane.showInputDialog(null,"Enter Amount");
//byte[] ibyte1 = inputMessage2.getBytes();
byte[] ebyte=encrypt(raw, ibyte);
byte[] ebyte1=encrypt(raw, ibyte1);
String DESencryptedData = new String(ebyte);
String DESencryptedData1 = new String(ebyte1);
System.out.println("Encrypted message\n"+DESencryptedData+"\n"+DESencryptedData1);

//JOptionPane.showMessageDialog(null,"Account number:" + inputMessage1);
//JOptionPane.showMessageDialog(null,"Amount:" + inputMessage2);
JOptionPane.showMessageDialog(null,"Data encrypted");
try
  {
     Class.forName("com.mysql.jdbc.Driver");
     con=DriverManager.getConnection(URL,USER,PASS);
     smt=con.createStatement();
     String sql="delete from DESstore";
      smt.executeUpdate(sql);
     smt.executeUpdate("insert into DESstore (DESencryptedData,DESencryptedData1,DESkey) values('"+DESencryptedData+"','"+DESencryptedData1+"','"+skeyString+"')");
     System.out.println("Successfully stored!");
     }
    catch(Exception e)
    {
    }
/*
byte[] dbyte= decrypt(raw,ebyte);
String decryptedMessage = new String(dbyte);
System.out.println("Decrypted message "+decryptedMessage);
JOptionPane.showMessageDialog(null,"Decrypted Data "/*);
//JOptionPane.showMessageDialog(null,"Decrypted Data "+"\n"+decryptedMessage);
*/        
}catch(Exception e) {
            System.out.println(e);
        }
}
        

    private DES() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
void generateSymmetricKey() {
try {
Random r = new Random();
int num = r.nextInt(10000);
String knum = String.valueOf(num);
byte[] knumb = knum.getBytes();
skey=getRawKey(knumb);
skeyString = new String(skey);
System.out.println("DES Symmetric key = "+skeyString);
}
catch(Exception e) {
System.out.println(e);
}
}
private static byte[] getRawKey(byte[] seed) throws Exception {
KeyGenerator kgen = KeyGenerator.getInstance("DES");
SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
sr.setSeed(seed);
kgen.init(56, sr);
SecretKey skey = kgen.generateKey();
raw = skey.getEncoded();
return raw;
}
private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
SecretKeySpec skeySpec = new SecretKeySpec(raw, "DES");
Cipher cipher = Cipher.getInstance("DES");
cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
byte[] encrypted = cipher.doFinal(clear);
return encrypted;
}

/*
private static byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
SecretKeySpec skeySpec = new SecretKeySpec(raw, "DES");
Cipher cipher = Cipher.getInstance("DES");
cipher.init(Cipher.DECRYPT_MODE, skeySpec);
byte[] decrypted = cipher.doFinal(encrypted);
return decrypted;
}
*/
public static void main(String args[]) {

}
}
