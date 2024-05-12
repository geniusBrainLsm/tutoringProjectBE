package com.lsm.backend.service;

import com.lsm.backend.exception.ForbiddenException;
import com.lsm.backend.exception.ResourceNotFoundException;
import com.lsm.backend.model.Board;
import com.lsm.backend.model.Comment;
import com.lsm.backend.model.User;
import com.lsm.backend.payload.board.CommentDTO;
import com.lsm.backend.payload.board.CommentRequestDTO;
import com.lsm.backend.payload.board.CommentResponseDTO;
import com.lsm.backend.repository.BoardRepository;
import com.lsm.backend.repository.CommentRepository;
import com.lsm.backend.repository.UserRepository;
import com.lsm.backend.security.UserPrincipal;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public CommentResponseDTO createComment(CommentRequestDTO commentDTO, UserPrincipal userPrincipal, Long id) {
        System.out.println(userPrincipal);
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Board", "id", id));

        User user = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

        Comment comment = commentDTO.toEntity();
        comment.setBoard(board);
        comment.setUser(user);
        //이거 대댓글 Long인데 Comment타입으로 변환하는거
        if (commentDTO.getParentId() != null) {
            Comment parentComment = commentRepository.findById(commentDTO.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentDTO.getParentId()));
            comment.setParent(parentComment);
        }

        comment = commentRepository.save(comment);


        return CommentResponseDTO.fromEntity(comment);
    }

    @Override
    public CommentDTO updateComment(CommentDTO commentDTO, Long id, UserPrincipal userPrincipal) {

        Comment comment = commentRepository.findById(commentDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", commentDTO.getId()));
        if (!comment.getUser().getId().equals(userPrincipal.getId())) {
            throw new ForbiddenException("본인 댓글만 수정 할 수있습니다.");
        }
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
    public void deleteComment(Long id, UserPrincipal userPrincipal) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        if (!comment.getUser().getId().equals(userPrincipal.getId())) {
            throw new ForbiddenException("본인 댓글만 삭제 할 수있습니다.");
        }
        commentRepository.deleteById(id);
    }
}
