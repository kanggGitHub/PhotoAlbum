package com.kg.PhotoAlbum.vxuserinfo.controller;

import com.kg.PhotoAlbum.common.GetAccessTokenUtil;
import com.kg.PhotoAlbum.vxuserinfo.model.UserInfo;
import com.kg.PhotoAlbum.vxuserinfo.service.UserInfoService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WeiXinUserInfoController
 * @Describe 获取用户信息
 * @Autor OnlyMyself
 * @Date 2019-05-24 8:56
 */
@Controller
@RequestMapping("/tologin")
public class WeiXinUserInfoController extends GetAccessTokenUtil {

      private static final Logger log = Logger.getLogger(WeiXinUserInfoController.class);

      @Autowired
      private UserInfoService userService;

      @RequestMapping("/index")
      public String index(){
            return "index";
      }
      /**
       * 进行网页授权，便于获取到用户的绑定的内容
       * @param request
       * @param session
       * @param map
       * @return
       */
      @RequestMapping("/userinfo")
      @ResponseBody
      public UserInfo check(HttpServletRequest request , HttpSession session, Map<String, Object> map) {
            //首先判断一下session中，是否有保存着的当前用户的信息，有的话，就不需要进行重复请求信息
            UserInfo  userInfo = null ;
            if(session.getAttribute("currentUser") != null){
                  userInfo = (UserInfo) session.getAttribute("currentUser");
            }else {
                  /**
                   * 进行获取openId，必须的一个参数，这个是当进行了授权页面的时候，再重定向了我们自己的一个页面的时候，
                   * 会在request页面中，新增这个字段信息，要结合这个ProjectConst.Get_WEIXINPAGE_Code这个常量思考
                   */
                  String code = request.getParameter("code");
                  log.info("获取到用户code===============》》》》"+code);
                  try {
                        //得到当前用户的信息(具体信息就看weixinUser这个javabean)
                        userInfo = getTheCode(session, code);
                        //将获取到的用户信息，放入到session中
                        session.setAttribute("currentUser", userInfo);
                  } catch (Exception e) {
                        e.printStackTrace();
                  }
            }
            map.put("weiXinUser", userInfo);
            return userInfo;
      }

      /**
       * 获取用户的openId
       * @param session
       * @param code
       * @return 返回封装的微信用户的对象
       */
      private UserInfo getTheCode(HttpSession session, String code) {
            Map<String , String>  authInfo = new HashMap<>();
            String openId = "";
            if (code != null)
            {
                  // 调用根据用户的code得到需要的授权信息
                  authInfo= userService.getAuthInfo(code);
                  //获取到openId
                  openId = authInfo.get("Openid");
                  log.info("根据用户code获取到openid===============》》》》》》》"+openId);
            }
            // 获取基础刷新的接口访问凭证（目前还没明白为什么用authInfo.get("AccessToken");这里面的access_token就不行）
            String accessToken = getAccess_Token();
            log.info("获取凭证accessToken======》》》》》》》》》"+accessToken);
            //获取到微信用户的信息
            UserInfo userInfo = userService.getUserInfo(accessToken ,openId);

            return userInfo;
      }
}
