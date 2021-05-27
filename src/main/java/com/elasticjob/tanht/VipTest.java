package com.elasticjob.tanht;

import com.alibaba.fastjson.JSONObject;
import com.elasticjob.tanht.util.TanUtil;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author:tht
 * @Description:
 * @create:6:30 PM 5/13/2021
 */


public class VipTest implements Runnable {
    final AtomicInteger number = new AtomicInteger();
    volatile boolean bol = false;

    private static String url = "http://localhost:7002/h5/server/ElectronicChannel2021Member/vipQxReceiveFlow?vipStatus=vip0";

    @Override
    public void run() {
//        synchronized (this) {
            try {
//                if (!bol) {
                    JSONObject param = new JSONObject();
                    param.put("vipStatus","vip0");
                    String result = TanUtil.httpPostSubmitJsonWithHeaders(url,null,param.toJSONString(),"utf-8",3000);
                    bol = true;
                    System.out.println("赠送结果:" + result);
//                    Thread.sleep(1000);
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

//        }

    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        VipTest test = new VipTest();
        for (int i = 0; i < 50; i++) {
            pool.execute(test);
        }
    }
}
