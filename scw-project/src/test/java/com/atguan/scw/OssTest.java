package com.atguan.scw;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
public class OssTest {

    @Test
    public static void main(String[] args) throws FileNotFoundException {
        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = "http://oss-cn-shanghai.aliyuncs.com";
// 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = "LTAI4Fz8WH8m42eLAa5p2z1L";
        String accessKeySecret = "hSlJRAmLZaMCEGvojHv4mxq1XVOKll";

// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
        InputStream inputStream = new FileInputStream("D:/图片/3.jpg");
        ossClient.putObject("20200613scw", "pic/3.jpg", inputStream);

// 关闭OSSClient。
        ossClient.shutdown();
    }
}
