package com.nowcoder.community.service.impl;

import com.nowcoder.community.service.UploadService;
import com.nowcoder.community.utils.PathUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Data
public class UploadServiceImpl implements UploadService {
    @Override
    public String uploadImg(MultipartFile img) {
        // 获取文件初始名
        String originalFilename = img.getOriginalFilename();
        String filePath = PathUtil.generateFilePath(originalFilename);
        String url = uploadOss(img, filePath);
        return url;
    }

    // 上传至七牛云
    @Value("${qiniu.oss.accessKey}")
    private String accessKey;
    @Value("${qiniu.oss.secretKey}")
    private String secretKey;
    @Value("${qiniu.oss.bucket}")
    private String bucket;

    private String uploadOss(MultipartFile imgFile,String filePath) {
        Configuration cfg = new Configuration(Region.autoRegion());
        UploadManager uploadManager = new UploadManager(cfg);
        String key = filePath;
        // 输入流
        InputStream inputStream = null;
        try {
            inputStream = imgFile.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Auth auth =Auth.create(accessKey,secretKey);
        String uptoken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(inputStream, key, uptoken, null, null);
        } catch (QiniuException e) {
            e.printStackTrace();
        }
        return "https://cdn.gulch.top/"+key;
    }
}
