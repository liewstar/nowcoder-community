package com.nowcoder.community.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {
        String uploadImg(MultipartFile img);
}
