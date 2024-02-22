package com.sparta.newsfeed.service;

import com.sparta.newsfeed.dto.CommentRequestDto;
import com.sparta.newsfeed.entity.Comment;
import com.sparta.newsfeed.repository.CommentRepository;
import com.sparta.newsfeed.security.UserDetailsImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@SpringBootTest
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    UserDetailsImpl userDetails;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    public void setUp() {
        commentService = new CommentService(commentRepository);
    }

    @Test
    @DisplayName("updateComment() 테스트")
    public void test1() {
        // Given
        Long commentId = 1L;
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setPostId(1L);
        requestDto.setCommentContent("업데이트된 댓글");

        Comment beforeComment = new Comment();
        beforeComment.setId(commentId);
        beforeComment.setPostId(1L);
        beforeComment.setUsername("작성자");
        beforeComment.setCommentContent("업데이트 전 댓글");
        when(userDetails.getUsername()).thenReturn("작성자");
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(beforeComment));
        when(commentRepository.save(any(Comment.class))).thenReturn(beforeComment);

        // When
        Comment comment = commentService.updateComment(commentId, requestDto, userDetails);
        System.out.println("result = " + comment.toString());
        // Then
        assertEquals(requestDto.getCommentContent(), comment.getCommentContent());
    }



}