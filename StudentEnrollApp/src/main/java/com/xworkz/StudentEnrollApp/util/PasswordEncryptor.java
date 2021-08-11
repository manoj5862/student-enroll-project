package com.xworkz.StudentEnrollApp.util;

import java.security.MessageDigest;

public class PasswordEncryptor {
	public static String passwordEncrypt(String password) {

		String algorithm = "SHA";

		byte[] plainText = password.getBytes();
		StringBuilder sb = null;
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);

			md.reset();
			md.update(plainText);
			byte[] encodedPassword = md.digest();

			sb = new StringBuilder();
			for (int i = 0; i < encodedPassword.length; i++) {
				if ((encodedPassword[i] & 0xff) < 0x10) {
					sb.append("0");
				}

				sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
			}

			System.out.println("Plain    : " + password);
			System.out.println("Encrypted: " + sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}
}
