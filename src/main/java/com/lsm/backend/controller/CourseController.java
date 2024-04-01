package com.lsm.backend.controller;

import com.lsm.backend.model.Comment;
import com.lsm.backend.model.Course;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.payload.CourseDTO;
import com.lsm.backend.payload.CurriculumDTO;
import com.lsm.backend.repository.CurriculumRepository;
import com.lsm.backend.service.CourseServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseServiceImpl courseService;
    private final CurriculumRepository curriculumRepository;
    @PostMapping
    public ResponseEntity<?> createCourse(CourseDTO courseDTO,List<CurriculumDTO> curriculumDTOS){
        CourseDTO createdDTO = courseService.createaCourse(courseDTO, curriculumDTOS);
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
    public ResponseEntity<List<CourseDTO>> getAllCourse(){
        List<CourseDTO> courses =courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable Long id){
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editCourse(@PathVariable Long id,@RequestBody CourseDTO courseDTO){
        CourseDTO createdDTO = courseService.updateCourse(courseDTO);
        return ResponseEntity.status(201).body(createdDTO);
    }

}
