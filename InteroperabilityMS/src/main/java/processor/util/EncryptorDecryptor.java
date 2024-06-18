package processor.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;


@Component
public class EncryptorDecryptor {

	public static String encrypt(String plainText, String secretKey) {
		try {
			byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);
			SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

			byte[] encryptedBytes = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

			return Base64.getEncoder().encodeToString(encryptedBytes);
		} catch (Exception e) {
			throw new RuntimeException("Error while encrypting: " + e.toString());
		}
	}
	
	
	
	public static String decrypt(String encryptedText, String secretKey) {
	    try {
	        byte[] key = secretKey.getBytes(StandardCharsets.UTF_8);
	        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");

	        Cipher cipher = Cipher.getInstance("AES");
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

	        byte[] decodedBytes = Base64.getDecoder().decode(encryptedText);
	        byte[] decryptedBytes = cipher.doFinal(decodedBytes);

	        return new String(decryptedBytes, StandardCharsets.UTF_8);
	    } catch (Exception e) {
	        throw new RuntimeException("Error while decrypting: " + e.toString());
	    }
	}
}
