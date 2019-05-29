package com.kg.PhotoAlbum.common;

/**
 * @ClassName Stateconstant
 * @Describe TODO
 * @Autor OnlyMyself
 * @Date 2019-05-23 20:44
 */
public class Stateconstant {
      public static final String localhost = "http://";
      //文件上传
      public static final String UPLOAD_URL ="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
      //创建菜单地址
      public static final String CTRATE_MENU_URL  = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
      /**
       * 开发者id
       */
      public static final String APPID = "wxdeba2c4731f21fae";
      /**
       * 开发者秘钥
       */
      public static final String APPSECRET="ae2e5683f341ae26c329149147e12399";
      //获取token的地址
      public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
      //获取用户信息
      public static final String GET_WEIXIN_USER_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID";
      //用户授权跳转界面
      public final static String redirect_uri=localhost+"/tologin/userinfo&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect";


      //用于进行网页授权验证的接口URL，通过这个才可以得到opendID等字段信息
      public final static String GET_WEBAUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

}
