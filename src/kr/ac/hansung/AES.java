package kr.ac.hansung;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	private static final String key = "aesEncryptionKey"; // 16byte = 128bit
	private static final String initVector = "encryptionIntVec"; // 16byte
	
	private static final Base64.Encoder enc = Base64.getEncoder();
	private static final Base64.Decoder dec = Base64.getDecoder();

	 
	public static String encrypt(String value) {
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	 
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
	 
	        byte[] encrypted = cipher.doFinal(value.getBytes());
	        return enc.encodeToString(encrypted);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return null;
	}

	public static String decrypt(String encrypted) {
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");
	 
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
	        byte[] original = cipher.doFinal(dec.decode(encrypted));
	 
	        return new String(original);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	 
	    return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String plainText = "​[오직 나만이, 이 세계의 결말을 알고 있다.] " + 
				"무려 3149편에 달하는 장편 판타지 소설, '멸망한 세계에서 살아남는 세 가지 방법'이 현실이 되어버렸다. " + 
				"그리고 그 작품을 완독한 이는 단 한 명뿐이었다.";
	    
		System.out.println("Original String to encrypt - " + plainText);
	    
	    String encryptedString = encrypt(plainText);
	    System.out.println("Encrypted String - " + encryptedString);
	    
	    String decryptedString = decrypt(encryptedString);
	    System.out.println("After decryption - " + decryptedString);
	}

}
