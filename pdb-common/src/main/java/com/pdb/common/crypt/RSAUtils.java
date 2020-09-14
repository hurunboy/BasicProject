package com.pdb.common.crypt;

import com.pdb.common.ByteArrayUtils;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.Cipher;

/**
 * Java & PHP & Javascript 通用 RSA 加密 解密 (长字符串)
 * 生成RSA公私钥分成三步生成，第1、2步可以满足php的使用，
 * 由于java的私钥要转化为PKCS8格式才能使用，所以执行第3步来实现。
 *
 * 1、生成私钥
 *  openssl genrsa -out rsa_private_key.pem 1024
 * 2、生成公钥
 *  openssl rsa -in rsa_private_key.pem -pubout -out rsa_public_key.pem
 * 3、将RSA私钥转换成PKCS8格式
 *  openssl pkcs8 -topk8 -inform PEM -in rsa_private_key.pem -outform PEM -nocrypt > java_rsa_private_key.pem
 */
public class RSAUtils {

  /**
   * RSA最大加密明文大小
   */
  private static final int MAX_ENCRYPT_BLOCK = 117;

  /**
   * RSA最大解密密文大小
   */
  private static final int MAX_DECRYPT_BLOCK = 128;

  public static final String SIGN_ALGORITHMS = "SHA256withRSA";

  private static String ALGORITHM_RSA = "RSA";

  /**
   * 使用公钥将数据加密
   * @param sourceData
   * @param publicKey
   * @return
   */
  public static String publicEncrypt(String sourceData, String publicKey){
    return rsaEncrypt(sourceData, publicKey,false);
  }

  /**
   * 使用私钥将数据加密
   * @param sourceData
   * @param privateKey
   * @return
   */
  public static String privateEncrypt(String sourceData, String privateKey){
    return rsaEncrypt(sourceData,privateKey,true);
  }


  /**
   * 使用公钥解密
   * @param encryptedData
   * @param privateKey
   * @return
   */
  public static String publicDecrypt(String encryptedData, String privateKey) {
    return rsaDecrypt(encryptedData,privateKey,false);
  }

  /**
   * 使用私钥解密
   * @param encryptedData
   * @param privateKey
   * @return
   */
  public static String privateDecrypt(String encryptedData, String privateKey) {
    return rsaDecrypt(encryptedData,privateKey,true);
  }

  protected static String rsaEncrypt(String sourceData, String key,boolean isPrivate){
    try {
      Key key1 = isPrivate ? loadPrivateKey(key) : loadPublicKey(key);
      byte[] data = sourceData.getBytes();
      byte[] dataReturn = new byte[0];
      Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
      cipher.init(Cipher.ENCRYPT_MODE, key1);

      // 加密时超过117字节就报错。为此采用分段加密的办法来加密
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < data.length; i += MAX_ENCRYPT_BLOCK) {
        byte[] doFinal = cipher.doFinal(ByteArrayUtils.subarray(data, i,i + MAX_ENCRYPT_BLOCK));
        sb.append(new String(doFinal));
        dataReturn = ByteArrayUtils.byteMerger(dataReturn, doFinal);
      }

      return Base64.byteArrayToBase64(dataReturn);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  protected static String rsaDecrypt(String encryptedData, String key,boolean isPrivate){
    try {
      Key key1 = isPrivate ? loadPrivateKey(key) : loadPublicKey(key);
      byte[] data = Base64.base64ToByteArray(encryptedData);

      Cipher cipher = Cipher.getInstance(ALGORITHM_RSA);
      cipher.init(Cipher.DECRYPT_MODE, key1);

      // 解密时超过128字节就报错。为此采用分段解密的办法来解密
      byte[] dataReturn = new byte[0];
      for (int i = 0; i < data.length; i += MAX_DECRYPT_BLOCK) {
        byte[] doFinal = cipher.doFinal(ByteArrayUtils.subarray(data, i, i + MAX_DECRYPT_BLOCK));
        dataReturn = ByteArrayUtils.byteMerger(dataReturn, doFinal);
      }

      return new String(dataReturn);
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * 私钥加签名
   * @param encryptData
   * @param privateKey
   * @return
   */
  public static String rsaSign(String encryptData, String privateKey) {
    try {
      Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
      signature.initSign(loadPrivateKey(privateKey));
      signature.update(encryptData.getBytes());
      byte[] signed = signature.sign();
      return Base64.byteArrayToBase64(signed);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * 公钥验签
   * @param encryptStr
   * @param sign
   * @param publicKey
   * @return
   * @throws Exception
   */
  public static boolean verifySign(String encryptStr, String sign, String publicKey)throws Exception {
    try {
      Signature signature = Signature.getInstance(SIGN_ALGORITHMS);
      signature.initVerify(loadPublicKey(publicKey));
      signature.update(encryptStr.getBytes());
      return signature.verify(Base64.base64ToByteArray(sign));
    }  catch (NoSuchAlgorithmException e) {
      throw new Exception(String.format("验证数字签名时没有[%s]此类算法", SIGN_ALGORITHMS));
    } catch (InvalidKeyException e) {
      throw new Exception("验证数字签名时公钥无效");
    } catch (SignatureException e) {
      throw new Exception("验证数字签名时出现异常");
    }
  }

  public static PublicKey loadPublicKey(String publicKeyStr) throws Exception {
    byte[] buffer = Base64.base64ToByteArray(publicKeyStr);
    KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
    X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
    return keyFactory.generatePublic(keySpec);
  }

  public static PrivateKey loadPrivateKey(String privateKeyStr) throws Exception {
    byte[] buffer = Base64.base64ToByteArray(privateKeyStr);
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
    KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
    return keyFactory.generatePrivate(keySpec);
  }

  public  static String urlsafe_encode (String encryptStr){
    return encryptStr.replaceAll("\\+","-").replaceAll("/","_").replaceAll("=","").replaceAll("(\r\n|\r|\n|\n\r)","");

  }

  public  static String urlsafe_decode(String encryptStr){
    encryptStr= encryptStr.replaceAll("-","+").replaceAll("_","/");
    int mob = encryptStr.length()%4;
    if (mob > 0) {
      encryptStr += "====".substring(mob);
    }
    return encryptStr;
  }

  public  static  void main(String[ ] asdfs) throws Exception {
    String publicKeyStr =
        "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChXKeiECTbSfoN2wWmr7o2uRfe" +
            "X4nU2l11jit/O/ysWPFGQjwC5t8wbrKXLZrCKiCoK3QGZEV3tcO6ELF9oyrfTGRh" +
            "Xo93Eda7VB7kHCc9FgE2rJTkzJBsniCZn8NsHZm7tfs72+PtvK9iP9ZRnDJdZrrW" +
            "S2lDtfUYcMjgqfUVHwIDAQAB";

    String privateKeyStr =
        "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKFcp6IQJNtJ+g3b" +
            "Baavuja5F95fidTaXXWOK387/KxY8UZCPALm3zBuspctmsIqIKgrdAZkRXe1w7oQ" +
            "sX2jKt9MZGFej3cR1rtUHuQcJz0WATaslOTMkGyeIJmfw2wdmbu1+zvb4+28r2I/" +
            "1lGcMl1mutZLaUO19RhwyOCp9RUfAgMBAAECgYAX/R1Sw5GnJC1PiAMkD7XgUs4Q" +
            "miV0hm4kftlKlhFvUd20sz4WtFnz6lEUlowOV5L5TVR3asrFDDmn3Ll2Ux6ZTWPe" +
            "V3Ye8tE2BNXyAxMRmStc1Gk5YJ1JRE0tKgI0Mqw2qcZ5MHWZkbrRx12VkYVnpex0" +
            "V0mUOoxHIgxd3fO+aQJBAM0/tO4RQz7w2lINkXeeyF0RzlL02asWQi8qyfDn4x51" +
            "6nnD0Jka/L0XXtxUf59xqMpXP0OqfhQP1LVI5J0JI10CQQDJQuWvQKDyWGQRYxhx" +
            "RJG5W0s+N/kBTICgjmfkDimD+hVXTX278h0vTKstgHthmRP0ijUpYTEzNfbeYWBI" +
            "Qu6rAkAJbT2gGYbfIyzDy/+8YLyPGbxCLyC4LOOJSwvPVrcltQC56vZvsb+d567h" +
            "q7fY2N2JvYwYTYJQ5ZE9akWluL+5AkA0E8lXBcQfsVuDXD7w4CtM6SCZIuadvWvz" +
            "H6YmTrvTWJy5EGYegdGncZFBnuNOJ8wQFMsYv/t5IteUR2IgkKEtAkBUJgdsRomM" +
            "0XO1We+UeemhN5tRZFDkJlooyW6xsjwbg3Q+gr/FJ6SOHooAQiu8L7GLXuupJODr" +
            "PdYTtBkjLA2i";

    //加密
    String data = "20886327772424640.01";
    String privateEncryptStr = RSAUtils.privateEncrypt(data, privateKeyStr);
    String publicEncryptStr = RSAUtils.publicEncrypt(data, publicKeyStr);
    String privateEncryptSign = RSAUtils.rsaSign(privateEncryptStr, privateKeyStr);
    String publicEncryptSign = RSAUtils.rsaSign(publicEncryptStr, privateKeyStr);

    System.out.println("source:" + data);
    System.out.println("private encryptStr: " + privateEncryptStr);
    System.out.println("public encryptStr: " + publicEncryptStr);
    System.out.println("private encrypt sign: " + privateEncryptSign);
    System.out.println("public encrypt sign: " + publicEncryptSign);
    System.out.println("public decrypt:" + RSAUtils.publicDecrypt(privateEncryptStr, publicKeyStr));

    System.out.println("private decrypt:" + RSAUtils.privateDecrypt("OdYZljOCUAPAa2NYnkqcLeZb3IPwbli7HHI31tWh21/CK7ZmYhuNg2b/1pqnp0LF0nTf3kQ19n6bVSWFyP+2pvQs1oBzIGboi6pqy6BC+c3QmamwaCV5x9IwVMS3BOBqDf8cfiXmn9Q1Dy/K805JKSjGvJtxfrr/5ki6zQLoriA=", privateKeyStr));

    System.out.println("verifySign1: " + RSAUtils.verifySign(privateEncryptStr,privateEncryptSign,publicKeyStr));
    System.out.println("verifySign2: " + RSAUtils.verifySign(publicEncryptStr,publicEncryptSign,publicKeyStr));

  }
}
