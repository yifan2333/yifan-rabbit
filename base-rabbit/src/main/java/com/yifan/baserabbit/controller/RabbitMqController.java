package com.yifan.baserabbit.controller;

import com.alibaba.fastjson.JSONObject;
import com.yifan.baserabbit.sender.DirectExchangeSender;
import com.yifan.baserabbit.sender.FanoutExchangeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;

/**
 * @author wuyifan
 * @since 2017年12月20日
 */
@RestController
public class RabbitMqController {

    @Autowired
    private DirectExchangeSender directExchangeSender;
    @Autowired
    private FanoutExchangeSender fanoutExchangeSender;


    @GetMapping("/send")
    public String send(HttpServletRequest request, @RequestParam(required = true) String msg) {

        Test test = new Test();
        test.setId(msg);

        directExchangeSender.send(JSONObject.toJSONString(test));
        return "directExchangeSender Send OK.";
    }


    @GetMapping("/send2")
    public String send2(HttpServletRequest request, String msg) {

        Test test = new Test();
        test.setId(msg);

        fanoutExchangeSender.send(JSONObject.toJSONString(test));
        return "fanoutExchangeSender Send OK.";
    }

    public class Test implements Serializable{
        private static final long serialVersionUID = 1501336869263764258L;
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

}
