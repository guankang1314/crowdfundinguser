package com.atguan.scw.project.component;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


@Data
@ToString
@Slf4j
public class OssTemplate {


    String endpoint;
    String accessKeyId;
    String accessKeySecret;

    String bucket;

    public String upload(String filename,InputStream inputStream) {
        try {


// 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

// 上传文件流。
            //InputStream inputStream = new FileInputStream("D:/图片/3.jpg");
            ossClient.putObject(bucket, "pic/"+filename, inputStream);


// 关闭OSSClient。
            ossClient.shutdown();

            String filepath = "http://"+bucket+"."+endpoint+"/pic/"+filename;

            log.debug("文件上传成功-{}",filepath);

            return filepath;

        } catch (Exception e) {
            e.printStackTrace();
            log.debug("文件上传失败-{}",filename);


            return null;
        }
    }
}
