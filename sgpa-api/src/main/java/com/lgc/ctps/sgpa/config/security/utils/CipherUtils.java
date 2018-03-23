package com.lgc.ctps.sgpa.config.security.utils;

import java.security.MessageDigest;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CipherUtils {

	private static final Logger logger = LoggerFactory.getLogger(CipherUtils.class);

	private static final String DES_ALGORITHM = "DESede";
	private static final String ALGORITHM = "md5";
	private static final String ENCODE = "utf-8";
	private static final String KEY = "1#lanpet#2";
	private static final String LOGIN_PARAM = "login=";

	private CipherUtils() {
	}

	public static String encrypt(String message) {
		byte[] base64Bytes = null;

		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			byte[] digestOfPassword = md.digest(KEY.getBytes(ENCODE));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}
			KeySpec keySpec = new DESedeKeySpec(keyBytes);
			SecretKey key = SecretKeyFactory.getInstance(DES_ALGORITHM)
				.generateSecret(keySpec);

			Cipher cipher = Cipher.getInstance(DES_ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			byte[] plainTextBytes = message.getBytes(ENCODE);
			byte[] buf = cipher.doFinal(plainTextBytes);
			base64Bytes = Base64.encodeBase64(buf);

		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}

		return new String(base64Bytes);
	}

	public static String decrypt(String encryptedText) {
		String userId = null;
		try {
			byte[] message = Base64.decodeBase64(corrigeSpecialChar(encryptedText).getBytes(ENCODE));

			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			byte[] digestOfPassword = md.digest(KEY.getBytes(ENCODE));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			for (int j = 0, k = 16; j < 8;) {
				keyBytes[k++] = keyBytes[j++];
			}

			KeySpec keySpec = new DESedeKeySpec(keyBytes);
			SecretKey key = SecretKeyFactory.getInstance(DES_ALGORITHM)
				.generateSecret(keySpec);

			Cipher decipher = Cipher.getInstance(DES_ALGORITHM);
			decipher.init(Cipher.DECRYPT_MODE, key);

			byte[] plainText = decipher.doFinal(message);
			userId = new String(plainText, "UTF-8");
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return userId;
	}

	public static String corrigeSpecialChar(String encryptedText) {
		return encryptedText.replaceAll(" ", "+");
	}

	public static String getUserId(String simpleToken) {
		String key = "";
		try {
			key = decrypt(simpleToken);
			return key.substring(key.indexOf(LOGIN_PARAM) + LOGIN_PARAM.length());
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage());
		}
		return key;
	}
}
