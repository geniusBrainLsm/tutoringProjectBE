package com.lsm.backend.controller;

import com.lsm.backend.payload.board.ImageDTO;
import com.lsm.backend.payload.course.CourseDTO;
import com.lsm.backend.service.CourseServiceImpl;
import com.lsm.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseServiceImpl courseService;
    private final ImageService imageService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> createCourse(@RequestPart("courseDTO") CourseDTO courseDTO,
                                          @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        // Course 생성 서비스 호출
        CourseDTO createdDTO = courseService.createaCourse(courseDTO);

        // 저장된 코스 ID를 이용해 이미지 엔티티를 저장합니다.
        if (createdDTO.getId() != null && files != null && !files.isEmpty()) {
            imageService.saveImages(files, createdDTO.getId());
        }

        return ResponseEntity.status(201).body(createdDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourse(@PathVariable Long id){
        Optional<CourseDTO> courseDTO = courseService.getCourse(id);

        if(courseDTO.isPresent()){
            return ResponseEntity.ok(courseDTO);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<CourseDTO>> getAllCourses(@PageableDefault(page = 1) Pageable pageable){
        Page<CourseDTO> courses = courseService.getAllCourses(pageable);
        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> updateCourse(@PathVariable Long id,
                                          @RequestPart("courseDTO") CourseDTO courseDTO,
                                          @RequestPart(value = "files", required = false) List<MultipartFile> files) {
        // Course 업데이트 서비스 호출
        CourseDTO updatedDTO = courseService.updateCourse(courseDTO);

        // 저장된 코스 ID를 이용해 이미지 엔티티를 저장합니다.
        if (updatedDTO.getId() != null && files != null && !files.isEmpty()) {
            imageService.saveImages(files, updatedDTO.getId());
        }

        return ResponseEntity.status(201).body(updatedDTO);
    }
}
