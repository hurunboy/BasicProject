package com.pdb.common.crypt;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AESUtils {

  public static final String VIPARA = "0392039203920300";
  public static final String KEY = "hcwt888888888888";


  public static final String BM = "utf-8";


  private static final String KEY_ALGORITHM = "AES";
  //默认的加密算法
  private static final String DEFAULT_CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

  /**
   * 生成加密秘钥
   *
   * @return
   */
  private static SecretKeySpec getSecretKey(final String password) {
    try {
      //返回生成指定算法密钥生成器的 KeyGenerator 对象
      KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
      SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
      secureRandom.setSeed(password.getBytes());

      //AES 要求密钥长度为 128
      kg.init(128, secureRandom);

      //生成一个密钥
      SecretKey secretKey = kg.generateKey();

      return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
    } catch (NoSuchAlgorithmException ex) {
      ex.printStackTrace();
    }
    return null;
  }

  /**
   * AES 加密
   *
   * @param content 明文
   * @param password 生成秘钥的关键字
   */

  public static String AESEncode(String password, String content) {
    try {
      IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
      SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_CBC);
      cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
      byte[] encryptedData = cipher.doFinal(content.getBytes(BM));
      return java.util.Base64.getEncoder().encodeToString(encryptedData);
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * AES 解密
   *
   * @param content 密文
   * @param password 生成秘钥的关键字
   */
  public static String AESDecode(String password, String content) {
    try {
      byte[] byteMi = java.util.Base64.getDecoder().decode(content);
      // byte[] byteMi = Base64.decode(content);
//          byte[] byteMi=  str2ByteArray(content);
      IvParameterSpec zeroIv = new IvParameterSpec(VIPARA.getBytes());
      SecretKeySpec key = new SecretKeySpec(password.getBytes(), "AES");
      Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM_CBC);
      cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
      byte[] decryptedData = cipher.doFinal(byteMi);
      return new String(decryptedData, "UTF-8");
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

}
