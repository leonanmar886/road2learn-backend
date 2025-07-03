package com.r2l.authService.util;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public final class PemUtils {

	private PemUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static PrivateKey parsePrivateKey(String key) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		try (PemReader pemReader = new PemReader(new StringReader(key))) {
			PemObject pemObject = pemReader.readPemObject();
			byte[] content = pemObject.getContent();
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(content);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			return keyFactory.generatePrivate(keySpec);
		}
	}
}
