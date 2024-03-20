package com.lsm.backend.service;

import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.Comment;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.payload.CommentDTO;
import com.lsm.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    @Override
    public CommentDTO createComment(CommentDTO commentDTO) {
        Comment comment = commentRepository.save(commentDTO.toEntity());
        return CommentDTO.fromEntity(comment);
    }

    @Override
    public CommentDTO editComment(CommentDTO commentDTO) {
        Comment comment = commentRepository.save(commentDTO.toEntity());
        return CommentDTO.fromEntity(comment);
    }

    @Override
    public Optional<CommentDTO> getComment(Long id) {

        try {
            Comment comment = commentRepository.findById(id)
                    .orElseThrow(() -> new Exception("없어용."));

            return Optional.of(CommentDTO.fromEntity(comment));

        } catch (Exception e) {
            return Optional.empty();
        }
    }


    @Override
    public List<CommentDTO> getAllComment() {
        List<CommentDTO> commentDTOList = new ArrayList<>();
        List<Comment> comments = commentRepository.findAll();

        for(Comment comment : comments){
            CommentDTO commentDTO = CommentDTO.fromEntity(comment);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
