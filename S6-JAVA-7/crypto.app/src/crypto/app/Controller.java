package crypto.app;

import crypto.library.CryptoManager;
import javafx.event.ActionEvent;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public class Controller
{

    private CryptoManager cm;
    private String filePath = "D:\\pwr\\S6-JAVA\\S6-JAVA-7\\data.txt";

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public void initialize()
    {
        cm = new CryptoManager();
        loadKeystore();
    }

    public void encrypt()
    {
        try
        {
            cm.encrypt(filePath, publicKey);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    public void decrypt()
    {
        try
        {
            cm.decrypt(filePath, privateKey);
        }catch (Exception ex)
        {
            System.out.println(ex);
        }
    }

    private void loadKeystore()
    {
        try
        {
            char[] password = "keystorepass".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(new FileInputStream("D:\\pwr\\S6-JAVA\\S6-JAVA-7\\keystore.jks"), password);
            Certificate certificate = ks.getCertificate("jpk");
            KeyStore.PrivateKeyEntry privateKeyEntry = (KeyStore.PrivateKeyEntry)ks.getEntry("jpk", new KeyStore.PasswordProtection(password));
            privateKey = privateKeyEntry.getPrivateKey();
            publicKey = certificate.getPublicKey();

        }catch (Exception ex)
        {
            System.out.println(ex);
        }

    }

}
