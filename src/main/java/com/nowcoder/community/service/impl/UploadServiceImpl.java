package com.nowcoder.community.service.impl;

import com.nowcoder.community.service.UploadService;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Data
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {
    @Override
    public String uploadImg(MultipartFile img) {
        //TODO 上传图片
        return null;
    }
}
