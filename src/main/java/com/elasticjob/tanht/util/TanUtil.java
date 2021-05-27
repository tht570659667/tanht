package com.elasticjob.tanht.util;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.MapUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author:Tanht
 * @Description:工具类
 * @Date:10:46 AM 9/14/2020
 */


public class TanUtil {

    private static Logger log = LoggerFactory.getLogger(TanUtil.class);
    private static final String NULL = "null";

    private static final String ZERO = "0";
    /**
     * 获取request请求客户端ip
     * @param request
     * @return
     */
    public static String getRequestIp(HttpServletRequest request){
        String ip = "";
        if(request.getHeader("Cdn-Src-Ip") != null)
        {
            ip = request.getHeader("Cdn-Src-Ip");
        }else
        {
            ip= request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        }
        if(ip!=null&&!"".equals(ip)&&ip.indexOf(",")!=-1){
            String ips[]=ip.split(",");
            ip=ips[ips.length-1];
        }
        return ip;

    }

    /**
     * 判断字符串是否为空（这方法有问题，直接用lang3.StringUtils工具类）
     * @param str
     * @return
     */
    public static boolean isBlank(String str){
        if(str.trim().length() == 0 || str == null || "".equals(str) || NULL.equals(str)){
            return true;
        }
        return false;
    }

    /**
     * 获得中文格式时间(发邮件时使用）
     * @return
     */
    public static String getChineseDate(String clock){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String date = sdf.format(new Date());
        String month = date.substring(4,6);
        String day = date.substring(6,8);
        if(ZERO.equals(month.substring(0,1))){
            month = month.substring(1,1);
        }
        if(ZERO.equals(day.substring(0,1))){
            day = day.substring(1,2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(month);
        stringBuilder.append("月");
        stringBuilder.append(day);
        stringBuilder.append("日");
        stringBuilder.append(" ");
        stringBuilder.append(clock);
        return stringBuilder.toString();
    }

    /**
     * post调用接口.
     * @param url 接口地址
     * @param headers 添加头部信息
     * @param jsonData JSON字符串参数
     * @param charset 字符集
     * @param timeout 超时时间，单位：秒
     * @return String
     */
    public static String httpPostSubmitJsonWithHeaders(String url, Map<String, String> headers,
                                                       String jsonData, String charset, int timeout) {
        HttpClient httpClient = new HttpClient();
        PostMethod postMethod = new PostMethod(url);
        try {
            RequestEntity entity = new StringRequestEntity(jsonData, "application/json", charset);
            postMethod.setRequestEntity(entity);
            postMethod.setRequestHeader("Content-Type","application/json");
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    postMethod.setRequestHeader(entry.getKey(), entry.getValue());
                }
            }
            postMethod.releaseConnection();

            HttpConnectionManagerParams managerParams = httpClient.getHttpConnectionManager().getParams();
            //设置连接超时时间(单位毫秒)
            managerParams.setConnectionTimeout(timeout * 1000);
            //设置读数据超时时间(单位毫秒)
            managerParams.setSoTimeout(timeout * 1000);

//            log.error("HttpPost提交：" + url + " | " + jsonData  + " | ");
            int statusCode = httpClient.executeMethod( postMethod );
            if ( statusCode == HttpStatus.SC_OK ) {
                String response = postMethod.getResponseBodyAsString();
//                log.info("HttpPost返回：" + url + " : " +  response ) ;
                return response;
            } else {
//                log.info("HttpPost返回：" + url +", status=" + statusCode) ;
            }
        } catch (UnsupportedEncodingException e) {
//            log.error("HttpPost错误： StringRequestEntity excp: " + e);
        } catch (IOException e) {
//            log.error("HttpPost错误：executeMethod excp: " + e);
        } finally {
            postMethod.abort();
            postMethod.releaseConnection();
            if( httpClient != null){
                httpClient = null;
            }
        }
        return "";
    }

    public static String httpGetSubmitJsonWithHeaders(String url, Map<String, String> headers,
                                                      String jsonData, String charset, int timeout){

        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;

    }

    //上传文件
    //        // 得到格式化后的日期
//        String format = sdf.format(new Date());
//        // 获取上传的文件名称
//        String fileName = file.getOriginalFilename();
//        // 时间 和 日期拼接
//        String newFileName = format + "_" + fileName;
//        String filePath = "D:\\";
//        // 得到文件保存的位置以及新文件名
//        File dest = new File("D:\\" + newFileName);
//        try {
//            // 上传的文件被保存了
//            file.transferTo(dest);
//            logger.info("=====文件保存成功=====，当前上传的文件保存在{}",filePath + newFileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        JSONObject result = new JSONObject();
//        result.put("filePath",filePath + newFileName);
//        result.put("weather", JSONObject.parseObject(TanUtil.httpGetSubmitJsonWithHeaders("https://www.tianqiapi.com/api?version=v61&appid=75575711&appsecret=I87F7mYF",null,null,null,20)));
//        return result;

    /**
     * 校验该目标是否为空
     * @param instance 对象
     * @return
     */
    public static boolean isNull(Object instance){
        if(instance instanceof String){
            return StringUtils.isBlank(instance.toString());
        } else if(instance instanceof Map){
            return MapUtils.isEmpty((Map)instance);
        } else if(instance instanceof Collection){
            return CollectionUtils.isEmpty((Collection)instance);
        } else if(instance instanceof JSONObject){
            log.info("instance : [{}]",JSONObject.toJSONString(instance));
            return (instance == null || StringUtils.isBlank(JSONObject.toJSONString(instance)));
        } else if(instance.getClass().isArray()){
            return ArrayUtils.isEmpty((Object[])instance);
        }
        return true;
    }
}
