package com.atguigu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.Test;

public class QinniuTest {
    /*
    测试向七牛云上传文件，看注册的地方是哪里就选择哪里的
    Zone.zone0() 华东
    Zone.zone1() 华北
    Zone.zone2() 华南
     */

    // 上传本地文件
    @Test
    public void uploadFile(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //去个人中心，密钥管理中将AK和SK拷贝过来
        String accessKey = "W2l0J8s-iJpJJviDFXH2VPLyGa25NdUGPpJgE82m";
        String secretKey = "xyRzsJwKzio35LVhBnk6PDQaWPsCCB-4jT4wtq_7";
        //设置空间的名字
        String bucket = "admin2022";
        //设置本地路径
        String localFilePath = "C:/Users/宋/Pictures/Camera Roll/641.webp";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException e) {
                e.printStackTrace();
            }
        }
    }
}
