package com.kg.PhotoAlbum.vxpackage.vxutil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import com.kg.PhotoAlbum.common.GetAccessTokenUtil;
import com.kg.PhotoAlbum.common.Stateconstant;
import net.sf.json.JSONObject;

/**
 * @ClassName UploadUtil
 * @Describe 文件上传工具类
 * @Autor OnlyMyself
 * @Date 2019-05-23 17:49
 */
public class UploadUtil {

//      @Value("${upload_url}")
//      private static  String UPLOAD_URL;

      /**
       * 文件上传
       * @param filePath
       * @param accessToken
       * @param type
       * @return
       * @throws IOException
       * @throws NoSuchAlgorithmException
       * @throws NoSuchProviderException
       * @throws KeyManagementException
       */
      public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
            File file = new File(filePath);
            if (!file.exists() || !file.isFile()) {
                  throw new IOException("文件不存在");
            }

            String url = Stateconstant.UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);

            URL urlObj = new URL(url);
            //连接
            HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

            con.setRequestMethod("POST");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);

            //设置请求头信息
            con.setRequestProperty("Connection", "Keep-Alive");
            con.setRequestProperty("Charset", "UTF-8");

            //设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

            StringBuilder sb = new StringBuilder();
            sb.append("--");
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");

            byte[] head = sb.toString().getBytes("utf-8");

            //获得输出流
            OutputStream out = new DataOutputStream(con.getOutputStream());
            //输出表头
            out.write(head);

            //文件正文部分
            //把文件已流文件的方式 推入到url中
            DataInputStream in = new DataInputStream(new FileInputStream(file));
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = in.read(bufferOut)) != -1) {
                  out.write(bufferOut, 0, bytes);
            }
            in.close();

            //结尾部分
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

            out.write(foot);

            out.flush();
            out.close();

            StringBuffer buffer = new StringBuffer();
            BufferedReader reader = null;
            String result = null;
            try {
                  //定义BufferedReader输入流来读取URL的响应
                  reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                  String line = null;
                  while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                  }
                  if (result == null) {
                        result = buffer.toString();
                  }
            } catch (IOException e) {
                  e.printStackTrace();
            } finally {
                  if (reader != null) {
                        reader.close();
                  }
            }

            JSONObject jsonObj = JSONObject.fromObject(result);
            String typeName = "media_id";
            if("thumb".equals(type)){
                  typeName = type + "_media_id";
            }
            String mediaId = jsonObj.getString(typeName);
            return mediaId;
      }

      public static void main(String[] args) {
            String access_toke= GetAccessTokenUtil.getAccess_Token();
            String path = "f:/1.jpg";
            try {
                  String id = UploadUtil.upload(path,access_toke,"image");
                  System.out.println(id);
            }catch (KeyManagementException | NoSuchAlgorithmException
                  | NoSuchProviderException | IOException e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
            }
      }
}
