package com.pdb.common;

/**
 * Created by liuzhuang on 2016/5/23.
 */
public class ByteArrayUtils {

  public static int byteIndexchangeOf(byte[] b, String s, int start) {
    return byteIndexchangeOf(b, s.getBytes(), start);
  }

  public static int byteIndexchangeOf(byte[] b, byte[] s, int start) {
    int i;
    if (s.length == 0) {
      return 0;
    }

    int max = b.length - s.length;
    if (max < 0) {
      return -1;
    }

    if (start > max) {
      return -1;
    }

    if (start < 0) {
      start = 0;
    }

    //在b中找到s的第一个元素
    search:
    for (i = start; i <= max; i++) {
      if (b[i] == s[0]) {
        //找到了s中的第一个元素后，比较剩余的部分是否相等
        int k = 1;
        while (k < s.length) {
          if (b[k + i] != s[k]) {
            continue search;
          }
          k++;
        }
        return i;
      }
    }
    return -1;
  }

  public static byte[] subBytes(byte[] b, int from, int end) {
    byte[] result = new byte[end - from];
    System.arraycopy(b, from, result, 0, end - from);
    return result;
  }


  /**
   * 截取字符串
   * @param array
   * @param startIndexInclusive
   * @param endIndexExclusive
   * @return
   */
  public static byte[] subarray(final byte[] array, int startIndexInclusive, int endIndexExclusive) {
    if (array == null) {
      return null;
    }
    if (startIndexInclusive < 0) {
      startIndexInclusive = 0;
    }
    if (endIndexExclusive > array.length) {
      endIndexExclusive = array.length;
    }
    final int newSize = endIndexExclusive - startIndexInclusive;
    if (newSize <= 0) {
      return new byte[0];
    }

    final byte[] subarray = new byte[newSize];
    System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
    return subarray;
  }

  /**
   * 合并字符串
   * @param byte1
   * @param byte2
   * @return
   */
  public static byte[] byteMerger(byte[] byte1, byte[] byte2) {
    byte[] byte_3 = new byte[byte1.length + byte2.length];
    System.arraycopy(byte1, 0, byte_3, 0, byte1.length);
    System.arraycopy(byte2, 0, byte_3, byte1.length, byte2.length);
    return byte_3;
  }
}
