package com.lsm.backend.service;

import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.Board;
import com.lsm.backend.model.Comment;
import com.lsm.backend.model.User;
import com.lsm.backend.payload.BoardDTO;
import com.lsm.backend.payload.CommentDTO;
import com.lsm.backend.payload.CommentRequestDTO;
import com.lsm.backend.repository.BoardRepository;
import com.lsm.backend.repository.CommentRepository;
import com.lsm.backend.repository.UserRepository;
import com.lsm.backend.security.UserPrincipal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public CommentDTO createComment(CommentRequestDTO commentDTO, Long id, UserPrincipal userPrincipal) {
        System.out.println(id);
        System.out.println(userPrincipal);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", id));
        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(()->new ResourceNotFoundException("user", "userName", userPrincipal.getId()));
        Comment comment = commentRepository.save(commentDTO.toEntity());
        comment.setBoard(board);
        comment.setUser(user);
        comment.setParent(commentDTO.getParent());
        return CommentDTO.fromEntity(comment);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, Long id) {

        Comment comment = commentRepository.findById(commentDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentDTO.getId()));

        comment.setContent(commentDTO.getContent());

        comment = commentRepository.save(comment);

        return CommentDTO.fromEntity(comment);
    }

//    @Override
//    public Optional<CommentDTO> getComment(Long id) {
//
//        try {
//            Comment comment = commentRepository.findById(id)
//                    .orElseThrow(() -> new Exception("없어용."));
//
//            return Optional.of(CommentDTO.fromEntity(comment));
//
//        } catch (Exception e) {
//            return Optional.empty();
//        }
//    }
//
//
//    @Override
//    public List<CommentDTO> getAllComment() {
//        List<CommentDTO> commentDTOList = new ArrayList<>();
//        List<Comment> comments = commentRepository.findAll();
//
//        for(Comment comment : comments){
//            CommentDTO commentDTO = CommentDTO.fromEntity(comment);
//            commentDTOList.add(commentDTO);
//        }
//        return commentDTOList;
//    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
