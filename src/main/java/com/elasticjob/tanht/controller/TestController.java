package com.elasticjob.tanht.controller;

import com.alibaba.fastjson.JSONObject;
import com.elasticjob.tanht.Entity.Ip;
import com.elasticjob.tanht.annotation.DecryptData;
import com.elasticjob.tanht.annotation.IpLimit;
import com.elasticjob.tanht.controller.baseController.BaseController;
import com.elasticjob.tanht.exception.TanException;
import com.elasticjob.tanht.service.IpService;
import com.elasticjob.tanht.service.UserService;
import com.elasticjob.tanht.util.TanUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author:Tanht
 * @Description:
 * @Date:9:57 AM 8/13/2020
 */

//@IpLimit(key = "test1")
@Slf4j
@RestController()
@RequestMapping(value = "tanht/test")
public class TestController extends BaseController<Ip, String> {

    @Autowired
    UserService userService;

    @Autowired
    IpService ipService;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    private static final String SEND_EMAIL_MSG = "";

    /**
     * 查询天气接口并发送短信
     * @param
     * @return
     * @throws TanException
     */
    @RequestMapping(value = "queryWeather")
    public String queryWeather() throws Exception {
        //每天早上7点查询天气，并发送短信至目标用户
        String result = TanUtil.httpGetSubmitJsonWithHeaders("https://www.tianqiapi.com/api?version=v61&appid=75575711&appsecret=I87F7mYF&city=贵阳", null, null, null, 20);
        JSONObject weather;
        if (TanUtil.isBlank(result)) {
            throw new TanException("-2000", "查询天气异常");
        }
//        throw new TanException("-1","查询天气异常");
//        weather = JSONObject.parseObject(result);
//        JSONObject resultJson = new JSONObject();
//        resultJson.put("weather", weather);
//        return resultJson;
        return dealMsg(JSONObject.parseObject(result));
    }

    /**
     * 根据表名查询数据库
     * @param tableName
     * @return
     */
    @RequestMapping(value = "queryTable")
    @DecryptData(decryptKey = "testtesttesttest" ,redisKey = "redisTest")
    public List<JSONObject> queryTable(JSONObject result){
        return userService.queryTable(result);
    }

    /**
     * 上传文件保存到指定位置
     * @param request
     * @param file
     * @return
     */
    @IpLimit(key = "test111")
    @RequestMapping(value = "uploadImg", method = RequestMethod.POST)
    public JSONObject uploadImg(HttpServletRequest request, MultipartFile file) {
        // 得到格式化后的日期
        String format = sdf.format(new Date());
        // 获取上传的文件名称
        String fileName = file.getOriginalFilename();
        // 时间 和 日期拼接
        String newFileName = format + "_" + fileName;
        String filePath = "D:\\";
        // 得到文件保存的位置以及新文件名
        File dest = new File("D:\\" + newFileName);
        try {
            // 上传的文件被保存了
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("msg", "文件保存成功，当前上传的文件保存在" + filePath + newFileName);
        return result;
    }

    @RequestMapping(value = "queryCarInfo")
    @IpLimit(key = "queryCar")
    public boolean queryCarInfo(@RequestBody JSONObject requestJson) throws TanException {
           return ipService.testManage();
    }

    @RequestMapping(value = "queryPayment")
    public JSONObject queryPayment(HttpServletRequest request){
        String result = TanUtil.httpGetSubmitJsonWithHeaders("http://localhost/consumer/payment/get/1",null,null,"utf-8",60);
        return JSONObject.parseObject(result);
    }

    public String dealMsg(JSONObject weather) {
        String sendMsg = String.format(SEND_EMAIL_MSG, weather.getString("week"),weather.getString("tem1"),
                weather.getString("tem2"),weather.getString("tem"),weather.getString("humidity"),
                weather.getString("visibility"),weather.getString("air_tips"));
        return sendMsg;
    }

}
