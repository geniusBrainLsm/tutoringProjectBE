package com.lsm.backend.repository;

import com.lsm.backend.model.Board;
import com.lsm.backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Long countCommentsById(Long id);
    
}
