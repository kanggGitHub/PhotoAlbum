package com.kg.PhotoAlbum.vxpackage.vxutil;

import com.kg.PhotoAlbum.common.GetAccessTokenUtil;
import com.kg.PhotoAlbum.common.Stateconstant;
import com.kg.PhotoAlbum.vxpackage.model.mune.Button;
import com.kg.PhotoAlbum.vxpackage.model.mune.ClickButton;
import com.kg.PhotoAlbum.vxpackage.model.mune.Menu;
import com.kg.PhotoAlbum.vxpackage.model.mune.ViewButton;
import net.sf.json.JSONObject;

/**
 * @ClassName MenuUtil
 * @Describe 菜单工具
 * @Autor OnlyMyself
 * @Date 2019-05-23 20:37
 */
public class MenuUtil {

      /**
       * 创建菜单
       * @param accessToken
       * @param Menu 菜单json格式字符串
       * @return
       */
      public static int createMenu(String accessToken,String Menu){
            int result = Integer.MIN_VALUE;
            String url = Stateconstant.CTRATE_MENU_URL.replaceAll("ACCESS_TOKEN", accessToken);
            JSONObject json = GetAccessTokenUtil.doPoststr(url, Menu);
            if(json!=null){
                  //从返回的数据包中取数据{"errcode":0,"errmsg":"ok"}
                  result = json.getInt("errcode");
            }
            return result;
      }

      public static String initMenu(){
            String result = "";
            //创建点击一级菜单
            ViewButton button11 = new ViewButton();
            button11.setName("个人中心");
            button11.setType("view");
            button11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxdeba2c4731f21fae" +
                  "&redirect_uri=http://gstnj8.natappfree.cc" +
                  "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");

            //创建跳转型一级菜单
            ViewButton button21 = new ViewButton();
            button21.setName("我得照片库");
            button21.setType("view");
            button21.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxdeba2c4731f21fae" +
                  "&redirect_uri=http://gstnj8.natappfree.cc/phototel" +
                  "&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");
            //创建其他类型的菜单与click型用法一致
            ClickButton button31 = new ClickButton();
            button31.setName("拍照发图");
            button31.setType("pic_photo_or_album");
            button31.setKey("31");

            ClickButton button32 = new ClickButton();
            button32.setName("发送位置");
            button32.setKey("32");
            button32.setType("location_select");

            //封装到一级菜单
            Button button = new Button();
            button.setName("菜单");
            button.setType("click");
            button.setSub_button(new Button[]{button31,button32});

            //封装菜单
            Menu menu = new Menu();
            menu.setButton(new Button[]{button,button21,button11});
            return JSONObject.fromObject(menu).toString();
      }

      public static void main(String[] args) {
            String accessToken  = GetAccessTokenUtil.getAccess_Token();
            String menu = MenuUtil.initMenu();
            System.out.println(menu);
            int result = MenuUtil.createMenu(accessToken,menu);
            if(result==0){
                  System.out.println("菜单创建成功");
            }else{
                  System.out.println("错误码"+result);
            }
      }

}
