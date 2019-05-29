package com.kg.PhotoAlbum.vxpackage.vxcontroller;

import com.kg.PhotoAlbum.vxpackage.util.MessageUtil;
import com.kg.PhotoAlbum.vxpackage.vxutil.TextMessageUtil;
import com.kg.PhotoAlbum.vxpackage.util.WeiXinSignUtil;
import com.kg.PhotoAlbum.vxpackage.vxutil.ImageMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 *
 * @Description: 微信请求处理的核心类
 * @Parameters:
 * @Return:
 * @Create Date:
 * @Version: V1.00
 * @author: OnlyMyself
*/

@RestController
@RequestMapping(value="/wechat")
public class WeixinCoreController {

    private static Logger log = LoggerFactory.getLogger(WeixinCoreController.class);

    @Autowired
    private WeiXinSignUtil weixinSignUtil;


    /**
     * @Description: 验证请求是否来自微信服务器
     * @Parameters: WeixinCoreController
     * @Return: 返回微信服务器发过来的验证字符
     * @Create Date:
     * @Version: V1.00
     * @author:来日可期
     */
    @RequestMapping(value="/access", method= RequestMethod.GET	)
    public String WeChatInterface(HttpServletRequest request, HttpServletResponse response)throws Exception {
        System.out.println("-------------验证微信服务号信息开始----------");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        log.info("signature is :" + signature + "timestamp is" + timestamp + "nonce is :" + nonce);
        if (weixinSignUtil.checkSignature(signature, timestamp, nonce)) {
            System.out.println("-----------验证微信服务号结束------------");
            return echostr;
        } else {

            // 此处可以实现其他逻辑
            log.warn("不是微信服务器发过来的请求，请小心！");
            return null;
        }
    }


    @RequestMapping(value = "access",method=RequestMethod.POST)
    public void dopost(HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        PrintWriter out = null;
        //将微信请求xml转为map格式，获取所需的参数
        Map<String,String> map = MessageUtil.xmlToMap(request);
        String ToUserName = map.get("ToUserName");
        String FromUserName = map.get("FromUserName");
        String MsgType = map.get("MsgType");
        String Content = map.get("Content");

        String message = null;
        //处理文本类型，实现输入1，回复相应的封装的内容
        if("text".equals(MsgType)){
            if("图片".equals(Content)){
                ImageMessageUtil util = new ImageMessageUtil();
                message = util.initMessage(FromUserName, ToUserName);
            }else{
                TextMessageUtil textMessage = new TextMessageUtil();
                message = textMessage.initMessage(FromUserName, ToUserName);
            }
        }else{
            TextMessageUtil textMessage = new TextMessageUtil();
            message = textMessage.initMessage(FromUserName, ToUserName);
        }
        try {
            out = response.getWriter();
            out.write(message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            log.info("入口错误！！！！");
        }
        out.close();
    }


}
