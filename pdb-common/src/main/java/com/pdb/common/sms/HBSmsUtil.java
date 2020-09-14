package com.pdb.common.sms;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 昊博短信 工具类
 */
public class HBSmsUtil {

  private static final Logger logger = LoggerFactory.getLogger(HBSmsUtil.class);

  // 请求地址
  private static String urlAddress = "http://101.227.68.49:7891/mt?";

  // 签名
  private static String LABEL = "祥瑞互娱";

  //dc 数据类型
  private static final int DATACODING = 15; //定死
  //rf 响应格式
  private static final int REPSPONSEFORMAT = 2; //定死
  //rd 是否需要状态报告
  private static final int REPORTDATA = 1; //定死
  //tf 短信内容的传输编码
  private static final int TRANSFERENCODING = 3; //定死

  private static String hexString = "0123456789ABCDEF";

  public static Boolean sendMessage(String phone, String content, String uname, String pwd) {

    content = "【"+ LABEL +"】" + content;

    logger.info("sendMessage phone => "+ phone +"content => " + content);

    Map<String, Object> paramMap = new HashMap<>();
    //dc 数据类型
    paramMap.put("dc", DATACODING);
    //rf 响应格式
    paramMap.put("rf", REPSPONSEFORMAT);
    //rd 是否需要状态报告
    paramMap.put("rd", REPORTDATA);
    //tf 短信内容的传输编码
    paramMap.put("tf", TRANSFERENCODING);
    // 用户名
    paramMap.put("un", uname);
    // 密码
    paramMap.put("pw", pwd);
    //单一内容时群发  将手机号用;隔开
    paramMap.put("da", phone);

    PrintWriter out = null;
    BufferedReader in = null;
    String result = "";
    try {
      URL realUrl = new URL(urlAddress);
      // 打开和URL之间的连接
      URLConnection conn = realUrl.openConnection();
      // 设置通用的请求属性
      conn.setRequestProperty("accept", "*/*");
      conn.setRequestProperty("connection", "Keep-Alive");
      conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
      // conn.setRequestProperty("Charset", "UTF-8");
      // 发送POST请求必须设置如下两行
      conn.setDoOutput(true);
      conn.setDoInput(true);
      // 获取URLConnection对象对应的输出流
      out = new PrintWriter(conn.getOutputStream());

      //做URLEncoder - UTF-8编码
      String sm = URLEncoder.encode(content, "utf8");
      paramMap.put("sm", sm);
      // 设置请求属性
      String param = "";
      if (paramMap != null && paramMap.size() > 0) {
        Iterator<String> ite = paramMap.keySet().iterator();
        while (ite.hasNext()) {
          String key = ite.next();// key
          Object value = paramMap.get(key);
          param += key + "=" + value + "&";
        }
        param = param.substring(0, param.length() - 1);
      }

      // 发送请求参数
      out.print(param);
      // flush输出流的缓冲
      out.flush();
      // 定义BufferedReader输入流来读取URL的响应
      in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
      String line;
      while ((line = in.readLine()) != null) {
        result += line;
      }
    } catch (Exception e) {
      System.err.println("发送 POST 请求出现异常！" + e);
      e.printStackTrace();
    } finally {
      try {
        if (out != null) {
          out.close();
        }
        if (in != null) {
          in.close();
        }
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }

    logger.info("sendMessage result => " + result);

    // 发送短信  失败 r=9103 , r=9101  |  成功  id=154545211884963170
    if (result.contains("true")) {
      return true;
    } else {
      return false;
    }
  }

  public static void main(String[] args) {

    String content = "测试短信";

    System.out.println(sendMessage("18521580705", content, "10690412", "whzpyz123"));

  }

}
