package kr.ac.hansung;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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

	public static String ReadFile(File file) {
		BufferedReader br = null;
		String temp = null;
		try {
			String s;
			br = new BufferedReader(new FileReader(file));
			while ((s = br.readLine()) != null) {
				temp = s;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return temp;
	}

	public static void CreateFile(String decryptedString) {
		File file = new File("decryptionFile.txt");
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] content = decryptedString.getBytes();
			fos.write(content);
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("파일 내용을 복호화한 후 새로운 파일(decryptionFile) 생성");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = ReadFile(new File("originFile.txt")); // 파일읽은 후 저장
		
		String encryptedString = encrypt(str); // 파일을 암호화
		System.out.println("파일의 내용을 암호화:" + encryptedString);
		
		String decryptedString = decrypt(encryptedString);// 파일 복호화
		CreateFile(decryptedString);// 복호화 한 내용으로 파일생성

	}

}
