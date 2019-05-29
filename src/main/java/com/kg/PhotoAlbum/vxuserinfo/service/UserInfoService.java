package com.kg.PhotoAlbum.vxuserinfo.service;

import com.kg.PhotoAlbum.vxuserinfo.model.UserInfo;

import java.util.Map;

public interface UserInfoService {
      /**
       * 获取到微信个人用户的信息
       * @param accessToken
       * @param openId
       * @return
       */
      UserInfo getUserInfo(String accessToken, String openId);

      /**
       *用于获取网页授权后的信息字段，其中主要是获取openId
       * @param code  授权码
       * @return
       */
      Map<String , String > getAuthInfo(String code);

      /**
       * 进行网页授权的认证
       * @param code 授权码
       * @return
       */
      Map<String,String> oauth2GetOpenid(String code);
}
