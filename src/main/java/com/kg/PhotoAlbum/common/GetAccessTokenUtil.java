package com.kg.PhotoAlbum.common;

import com.kg.PhotoAlbum.vxpackage.model.AccessToken;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import redis.clients.jedis.Jedis;

import net.sf.json.JSONObject;
import java.io.IOException;

/**
 * @ClassName WeiXinUtil
 * @Describe 获取凭证access_token
 * @Autor OnlyMyself
 * @Date 2019-05-23 16:56
 */
public class GetAccessTokenUtil {

      /**
       * 处理doget请求
       * @param url
       * @return
       */
      public static JSONObject doGetstr(String url){
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            JSONObject jsonObject = null;
            try {
                  CloseableHttpResponse response = httpclient.execute(httpGet);
                  HttpEntity entity = response.getEntity();
                  if(entity!=null){
                        String result = EntityUtils.toString(entity);
                        jsonObject = JSONObject.fromObject(result);
                  }
            } catch (IOException e) {
                  e.printStackTrace();
            }
            return jsonObject;

      }
      /**
       * 处理post请求
       * @param url
       * @return
       */
      public static JSONObject doPoststr(String url,String outStr){
            CloseableHttpClient httpclient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            JSONObject jsonObject = null;
            try {
                  httpPost.setEntity(new StringEntity(outStr, "utf-8"));
                  CloseableHttpResponse response = httpclient.execute(httpPost);
                  String result = EntityUtils.toString(response.getEntity(),"utf-8");
                  jsonObject =JSONObject.fromObject(result);
            } catch (IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
            }
            return jsonObject;
      }

      public static AccessToken getAccessToken(){
            System.out.println("从接口中获取");
            Jedis jedis  = RedisUtil.getJedis();
            AccessToken token = new AccessToken();
            String url = Stateconstant.ACCESS_TOKEN_URL.replace("APPID", Stateconstant.APPID).replace("APPSECRET", Stateconstant.APPSECRET);
            JSONObject json = doGetstr(url);
            if(json!=null){
                  token.setAccess_token(json.getString("access_token"));
                  token.setExpires_in(json.getInt("expires_in"));
                  jedis.set("access_token", json.getString("access_token"));
                  jedis.expire("access_token", 60*60*2);
            }
            RedisUtil.returnResource(jedis);
            return token;
      }
      /**
       * 获取凭证
       * @return
       */
      public static String  getAccess_Token(){
            System.out.println("从缓存中读取");
            Jedis jedis  = RedisUtil.getJedis();
            String access_token = jedis.get("access_token");
            if(access_token==null){
                  AccessToken token = getAccessToken();
                  access_token = token.getAccess_token();
            }
            RedisUtil.returnResource(jedis);
            return access_token;
      }

      /**
       * 测试
       * @param args
       */
      public static void main(String[] args) {
            String access_token = GetAccessTokenUtil.getAccess_Token();
            System.out.println("调用成功access_token:"+access_token);
      }
}
