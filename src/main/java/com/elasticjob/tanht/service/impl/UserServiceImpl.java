package com.elasticjob.tanht.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.elasticjob.tanht.Entity.User;
import com.elasticjob.tanht.exception.TanException;
import com.elasticjob.tanht.mapper.UserMapper;
import com.elasticjob.tanht.service.UserService;
import com.elasticjob.tanht.util.TanUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Author:Tanht
 * @Description:
 * @Date:2:22 PM 6/12/2020
 */

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    UserMapper userMapper;

    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private String seven = "11:06";

    private static final String SEND_EMAIL_MSG = "";

//    private String nine = "9:00";
//
//    private String twelve = "13:04";
//
//    private String sevenTeen = "17:00";
//
//    private String twentyTwo = "22:00";
//
//    private String testTime = "21:28";


    @Override
    public void sendSms(){
        System.out.println("发送邮件开始。。。。。。。");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String nowTime = sdf.format(new Date());
        try{
            logger.info("现在时间{}",nowTime);
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("1073232234@qq.com");
            simpleMailMessage.setTo("tanht@asiainfo.com");
            boolean isSend = false;
            //早上7点
            if(CompareTime(nowTime,seven)){
                simpleMailMessage.setSubject("今日天气预报");
                String result = TanUtil.httpGetSubmitJsonWithHeaders("https://www.tianqiapi.com/api?version=v61&appid=75575711&appsecret=I87F7mYF&city=贵阳", null, null, null, 20);
                if (StringUtils.isBlank(result)) {
                    throw new TanException("-2000", "查询天气异常");
                }
                simpleMailMessage.setText(dealMsg(JSONObject.parseObject(result)));
                isSend = true;
            }
            if(isSend){
                javaMailSender.send(simpleMailMessage);
                logger.info("发送邮件结束。。。。。。。");
                return;
            }
        }catch(Exception e){
            logger.error("发送失败", e);
        }
        logger.info("没发送邮件。。。。。。。");
    }

    @Override
    public void sendMsg(JSONObject weather){
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4G9BaRxyd8oQszmEVi98", "7bVOjogv5eUMKkQdRVyuzSmfC30pUJ");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "17725020167");
        request.putQueryParameter("SignName", "谭皓天");
        request.putQueryParameter("TemplateCode", "SMS_205139209");
        request.putQueryParameter("TemplateParam", weather.toJSONString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<JSONObject> queryTable(JSONObject tableName) {
        return userMapper.queryTable(tableName.toJSONString());
    }

    public boolean CompareTime(String nowDate, String compareDate) throws ParseException {
//        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
//        Date nowTime = format.parse(nowDate);
//        Date compareTime =format.parse(compareDate);
          String nowHour = nowDate.substring(0,2);
          String compareHour = compareDate.substring(0,2);
          if(nowHour.equals(compareHour)){
              return true;
          }
          return false;
//        System.out.println(nowTime);
//        System.out.println(compareTime);
//        return nowTime.compareTo(compareTime) == 0;
    }

    public String dealMsg(JSONObject weather) {
        String sendMsg = String.format(SEND_EMAIL_MSG, weather.getString("week"),weather.getString("tem1"),
                weather.getString("tem2"),weather.getString("tem"),weather.getString("humidity"),
                weather.getString("visibility"),weather.getString("air_tips"));
        return sendMsg;
    }
}
