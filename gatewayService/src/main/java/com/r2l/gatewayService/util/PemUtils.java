package com.r2l.gatewayService.util;

import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

import java.io.IOException;
import java.io.StringReader;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public final class PemUtils {

  private PemUtils() {
    throw new IllegalStateException("Utility class");
  }

  public static PublicKey parsePublicKey(String key)
      throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    try (PemReader pemReader = new PemReader(new StringReader(key))) {
      PemObject pemObject = pemReader.readPemObject();
      byte[] content = pemObject.getContent();
      X509EncodedKeySpec keySpec = new X509EncodedKeySpec(content);
      KeyFactory keyFactory = KeyFactory.getInstance("RSA");
      return keyFactory.generatePublic(keySpec);
    }
  }
}
