package com.lsm.backend.service;

import com.lsm.backend.model.Image;
import com.lsm.backend.payload.board.ImageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ImageService {
    //List<Image> uploadImage(List<MultipartFile> files) throws IOException;
    List<Image> saveImages(List<MultipartFile> files, Long courseId);

}
