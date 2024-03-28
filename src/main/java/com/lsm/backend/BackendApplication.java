package com.lsm.backend;

import com.lsm.backend.config.AppProperties;
import com.lsm.backend.model.Board;
import com.lsm.backend.repository.BoardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EnableConfigurationProperties(AppProperties.class)
@SpringBootApplication
public class BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(BoardRepository repository) {
        return (args) -> {
            List<Board> boards = new ArrayList<>();
            for (int i = 1; i <= 10; i++) {
                Board board = new Board();
                board.setBoardType("질문/답변");
                board.setTitle("Title " + i);
                board.setWriter("Writer " + i);
                board.setContents("샘플내용샘플내용" + i);
                board.setTag("Tag" + i);
                board.setLikeCount(Long.valueOf(i * 10));
                board.setViewCounter(Long.valueOf(i * 5));
                board.setCreatedAt(LocalDateTime.now());
                board.setModifiedAt(LocalDateTime.now());
                boards.add(board);
            }
            for (int i = 11; i <= 20; i++) {
                Board board = new Board();
                board.setBoardType("수강평");
                board.setTitle("Title " + i);
                board.setWriter("Writer " + i);
                board.setContents("샘플내용샘플내용" + i);
                board.setTag("Tag" + i);
                board.setLikeCount(Long.valueOf(i * 10));
                board.setViewCounter(Long.valueOf(i * 5));
                board.setCreatedAt(LocalDateTime.now());
                board.setModifiedAt(LocalDateTime.now());
                boards.add(board);
            }
            for (int i = 21; i <= 30; i++) {
                Board board = new Board();
                board.setBoardType("개선요구");
                board.setTitle("Title " + i);
                board.setWriter("Writer " + i);
                board.setContents("샘플내용샘플내용 " + i);
                board.setTag("Tag" + i);
                board.setLikeCount(Long.valueOf(i * 10));
                board.setViewCounter(Long.valueOf(i * 5));
                board.setCreatedAt(LocalDateTime.now());
                board.setModifiedAt(LocalDateTime.now());
                boards.add(board);
            }
            repository.saveAll(boards);
        };
    }
}
