package com.pdb.web.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 转换类
 */
public class YvanUtil {

  private static ObjectMapper objectMapper;

  static {
    objectMapper = new ObjectMapper();
    objectMapper.writerWithDefaultPrettyPrinter();
  }

  /**
   * 将Json字符串序列化成指定对象
   */
  public static <T> T jsonToObj(String jsonStr, Class<T> clazz) {
    try {
      return objectMapper.readValue(jsonStr, clazz);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将对象序列化成json
   */
  public static String toJson(Object obj) {
    try {
      return objectMapper.writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 将Json字符串序列化成json
   */
  public static Map<?, ?> jsonToMap(String jsonInput) {
    try {
      return objectMapper.readValue(jsonInput, Map.class);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  public static String urlEncoding(String value) {
    byte[] bs = Base64.getUrlEncoder().encode(value.getBytes(StandardCharsets.UTF_8));
    return new String(bs, StandardCharsets.UTF_8);
  }

  public static String encodeBase64(String s) {
    return Base64.getUrlEncoder().encodeToString(s.getBytes());
  }

  /**
   * 将 BASE64 编码的字符串 s 进行解码
   * @param s
   * @return
   */
  public static String decodeBase64(String s) {
    try {
      byte[] b = Base64.getDecoder().decode(s);
      return new String(b, StandardCharsets.UTF_8);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 得到当前request请求的所有cookie
   *
   * @return cookie数组
   * @author jianguo.xu
   */
  public static Cookie[] getCookies() {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes()).getRequest();
    return request == null ? null : request.getCookies();
  }

  /**
   * 根据cookie名字取得cookie
   *
   * @param name cookie名字
   * @return cookie
   * @author jianguo.xu
   */
  public static Cookie getCookie(String name) {
    Cookie[] cookies = getCookies();
    return getCookieByName(cookies,name);
  }

  public static Cookie getCookieByName(Cookie[] cookies, String name){
    if (cookies != null && cookies.length > 0) {
      for (int i = 0; i < cookies.length; i++) {
        Cookie cookie = cookies[i];
        String cookName = cookie.getName();
        if (cookName != null && cookName.equals(name)) {
          return cookie;
        }
      }
    }
    return null;
  }

  public static String getCookieValue(String name) {
    Cookie cookie = getCookie(name);
    if (cookie == null) {
      return null;
    }
    return cookie.getValue();
  }

  /**
   * 将cookie写入客户端
   *
   * @author jianguo.xu
   */
  public static void writeCookie(Cookie cookie) {
    if (cookie == null) {
      return;
    }
    ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes());
    HttpServletResponse response = attributes.getResponse();
    HttpServletRequest request = attributes.getRequest();
    /*
     * if (request != null) { String host = request.getHeader("Host"); if (ConfigUtils
     * .WEBSITE.equals(host))
     * cookie.setDomain("." + ConfigUtils.DOMAIN); }
     */
    if (response != null) {
      response.addCookie(cookie);
    }
  }

  public static void removeCookie(String cookieName, String path) {
    ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder
        .currentRequestAttributes());
    HttpServletResponse response = attributes.getResponse();
    HttpServletRequest request = attributes.getRequest();

    Cookie[] cookies = request.getCookies();
    if (cookies == null || cookies.length == 0) {
      return;
    }

    for (int i = 0; i < cookies.length; i++) {
      Cookie cookie = cookies[i];
      if (cookie.getName().equals(cookieName)) {
        cookie.setMaxAge(0);
        cookie.setPath(path);
        /*
         * String host = request.getHeader("Host"); if (ConfigUtils.WEBSITE.equals(host))
         * cookie.setDomain("." +
         * ConfigUtils.DOMAIN);
         */
        response.addCookie(cookie);
        break;
      }
    }

  }


  @SuppressWarnings("unchecked")
  public static <K, V> Map<K, V> bean2Map(Object javaBean) {
    Map<K, V> ret = new HashMap<K, V>();
    try {
      Method[] methods = javaBean.getClass().getDeclaredMethods();
      for (Method method : methods) {
        if (method.getName().startsWith("get")) {
          String field = method.getName();
          field = field.substring(field.indexOf("get") + 3);
          field = field.toLowerCase().charAt(0) + field.substring(1);
          Object value = method.invoke(javaBean, (Object[]) null);
          ret.put((K) field, (V) (null == value ? "" : value));
        }
      }
    } catch (Exception e) {
    }
    return ret;
  }

}
