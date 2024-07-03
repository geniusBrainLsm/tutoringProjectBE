package com.lsm.backend.service;

import com.lsm.backend.model.Course;
import com.lsm.backend.model.Image;
import com.lsm.backend.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private static final String UPLOAD_DIR = "C:\\Users\\USER\\IdeaProjects\\tutoringProjectBElast\\src\\main\\resources\\Images";
    private final ImageRepository imageRepository;

    @Override
    public List<Image> saveImages(List<MultipartFile> files, Long courseId) {
        if (files == null || files.isEmpty()) {
            return Collections.emptyList();
        }

        List<Image> images = new ArrayList<>();
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFilePath(UPLOAD_DIR + File.separator + file.getOriginalFilename());
                image.setType(file.getContentType());
                // courseId를 설정하여 이미지와 코스를 연결
                image.setCourse(Course.builder().id(courseId).build());
                images.add(image);

                // 파일 저장 경로가 존재하지 않으면 생성
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    try {
                        Files.createDirectories(uploadPath);
                    } catch (IOException e) {
                        throw new RuntimeException("Could not create upload directory!", e);
                    }
                }

                // 파일 저장 로직 추가
                try {
                    file.transferTo(new File(image.getFilePath()));
                } catch (IOException e) {
                    throw new RuntimeException("Failed to store file " + file.getOriginalFilename(), e);
                }
            }
        }
        // 이미지 엔티티 리스트를 데이터베이스에 저장
        return imageRepository.saveAll(images);
    }
}
