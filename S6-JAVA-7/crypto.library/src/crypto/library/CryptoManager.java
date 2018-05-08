package crypto.library;

import javax.crypto.Cipher;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.security.PublicKey;

public class CryptoManager
{
    public void encrypt(String filePath, PublicKey key) throws Exception
    {
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));

        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key);

        byte[] encryptedFileBytes = cipher.doFinal(fileBytes);
        FileOutputStream stream = new FileOutputStream(filePath);
        stream.write(encryptedFileBytes);
        stream.close();
    }

    public void decrypt(String filePath, PrivateKey key) throws Exception
    {
        byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
        
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);

        byte[] decryptedFileBytes = cipher.doFinal(fileBytes);
        FileOutputStream stream = new FileOutputStream(filePath);
        stream.write(decryptedFileBytes);
        stream.close();
    }

}
