package com.kg.PhotoAlbum.vxpackage.vxutil;

import com.kg.PhotoAlbum.common.GetAccessTokenUtil;
import com.kg.PhotoAlbum.vxpackage.message.ImageMessage;
import com.kg.PhotoAlbum.vxpackage.model.Image;
import com.kg.PhotoAlbum.vxpackage.util.BaseMessageUtil;
import com.thoughtworks.xstream.XStream;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;

/**
 * @ClassName ImageMessageUtil
 * @Describe 回复图片消息
 * @Autor OnlyMyself
 * @Date 2019-05-23 17:53
 */
public class ImageMessageUtil implements BaseMessageUtil<ImageMessage> {

      /**
       * 转换xml格式
       * @param imageMessage
       * @return
       */
      @Override
      public String messageToxml(ImageMessage imageMessage) {
            XStream xtream = new XStream();
            xtream.alias("xml", imageMessage.getClass());
            xtream.alias("Image", new Image().getClass());
            return xtream.toXML(imageMessage);
      }

      /**
       * 封装信息
       * @param FromUserName
       * @param ToUserName
       * @return
       */
      @Override
      public String initMessage(String FromUserName, String ToUserName) {
            Image image = new Image();
            image.setMediaId(getmediaId());
            ImageMessage message = new ImageMessage();
            message.setFromUserName(ToUserName);
            message.setToUserName(FromUserName);
            message.setCreateTime(new Date().getTime());
            message.setImage(image);
            message.setMsgType("image");
            return messageToxml(message);
      }

      /**
       * 获取Media
       * @return
       */
      public String getmediaId(){
            String path = "f:/1.jpg";
            String accessToken = GetAccessTokenUtil.getAccess_Token();
            String mediaId = null;
            try {
                  mediaId = UploadUtil.upload(path, accessToken, "image");

            } catch (KeyManagementException | NoSuchAlgorithmException
                  | NoSuchProviderException | IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
            }
            return mediaId;
      }

}
