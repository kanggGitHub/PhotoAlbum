package com.kg.PhotoAlbum.vxpackage.vxutil;

import com.kg.PhotoAlbum.vxpackage.message.TextMessage;
import com.kg.PhotoAlbum.vxpackage.util.BaseMessageUtil;
import com.thoughtworks.xstream.XStream;

import java.util.Date;

public class TextMessageUtil implements BaseMessageUtil<TextMessage> {
      /**
       * 将发送消息封装成对应的xml格式
       */
      @Override
      public  String messageToxml(TextMessage message) {
            XStream xstream  = new XStream();
            xstream.alias("xml", message.getClass());
            return xstream.toXML(message);
      }
      /**
       * 封装发送消息对象,封装时，需要将调换发送者和接收者的关系
       * @param FromUserName
       * @param ToUserName
       */
      @Override
      public  String initMessage(String FromUserName, String ToUserName) {
            TextMessage text = new TextMessage();
            text.setToUserName(FromUserName);
            text.setFromUserName(ToUserName);
            text.setContent("欢迎关注！");
            text.setCreateTime(new Date().getTime());
            text.setMsgType("text");
            return  messageToxml(text);
      }
}
