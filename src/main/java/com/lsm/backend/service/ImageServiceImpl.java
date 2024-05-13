package com.lsm.backend.service;

import com.lsm.backend.model.Image;
import com.lsm.backend.payload.board.ImageDTO;
import com.lsm.backend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{

    private final String FOLDER_PATH = "C:\\Users\\dlwns\\IdeaProjects\\tutoringProjectBEa\\src\\main\\resources\\Images";
    private final ImageRepository imageRepository;
    @Override
    public List<Image> uploadImage(List<MultipartFile> files) {
        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String filePath = FOLDER_PATH + File.separator + file.getOriginalFilename();
                File newFile = new File(filePath);
                file.transferTo(newFile);

                Image image = imageRepository.save(
                        Image.builder()
                                .fileName(file.getOriginalFilename())
                                .type(file.getContentType())
                                .filePath(filePath)
                                .build()
                );
                images.add(image);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return images;
    }
}
