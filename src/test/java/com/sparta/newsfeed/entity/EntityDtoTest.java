package com.sparta.newsfeed.entity;

import com.sparta.newsfeed.dto.CommentRequestDto;
import com.sparta.newsfeed.dto.PostRequsetDto;
import com.sparta.newsfeed.dto.SignupRequestDto;
import com.sparta.newsfeed.security.UserDetailsImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


public class EntityDtoTest {

    @Nested
    class SignupRequestDtoTest {

        private static Validator validator;

        @BeforeAll
        static void setUp() {
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
            //위처럼 validator를 생성하고
            //Set<ConstraintViolation<SignupRequestDto>> validator = validator.validate(signupRequestDto);
            //로 선언하여 @Pattern에 대한 검증이 가능
        }

        @Test
        @DisplayName("잘못된 username Pattern")
        void username1() {
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setPassword("a");

            //when
            Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

            //then
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("올바른 username Pattern")
        void username2() {
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setUsername("user1");

            //when
            Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

            //then
            assertTrue(violations.isEmpty());
        }
        @Test
        @DisplayName("잘못된 password Pattern")
        void password1() {
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setPassword("a");

            //when
            Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

            //then
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("올바른 password Pattern")
        void password2() {
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setPassword("qlalfqjsgh1");

            //when
            Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

            //then
            assertTrue(violations.isEmpty());
        }

        @Test
        @DisplayName("잘못된 email Pattern")
        void email1() {
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setEmail("a");

            //when
            Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

            //then
            assertFalse(violations.isEmpty());
        }

        @Test
        @DisplayName("올바른 password Pattern")
        void email2() {
            //given
            SignupRequestDto signupRequestDto = new SignupRequestDto();
            signupRequestDto.setEmail("a@a.com");

            //when
            Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(signupRequestDto);

            //then
            assertTrue(violations.isEmpty());
        }



    }


    @Nested
    class UserTest{

        User user;
        @BeforeEach
        void setUp() {
            user = new User();
        }

        @Test
        @DisplayName("자기소개 수정 메서드 확인")
        void testUpdate(){
            //given
            String info = "자기소개 수정 메서드 확인";

            //when
            user.update(info);

            //then
            assertEquals(user.getInfo(), info);
        }

        @Test
        @DisplayName("비밀번호 수정 메서드 확인")
        void updatePasswordTest(){
            //given
            String password = "비밀번호 수정 메서드 확인";

            //when
            user.updatePassword(password);

            //then
            assertEquals(user.getPassword(),password);
        }
    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    class PostTest{

        Post post;

        @Mock
        private PostRequsetDto dto;//클레스명에 오류가 있지만 일단 그래도 실행

        @BeforeEach
        void setUp() {
            post = new Post();
        }


        @Test
        @DisplayName("Post 수정 테스트")
        void updateTest(){
            //given
            when(dto.getTitle()).thenReturn("제목 수정");
            when(dto.getContent()).thenReturn("내용 수정");

            //when
            post.update(dto);

            //then
            assertEquals(post.getTitle(), "제목 수정");
            assertEquals(post.getContent(), "내용 수정");

        }

    }

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class CommentTest {

        private Comment comment;

        @Mock
        private CommentRequestDto commentRequestDto;

        @Mock
        private UserDetailsImpl userDetails;

        @BeforeEach
        void setUp() {
            comment = new Comment();
        }

        @Test
        @DisplayName("댓글 내용 수정 메서드 테스트")
        void updateComment() {
            // given
            when(commentRequestDto.getPostId()).thenReturn(1L);
            when(commentRequestDto.getCommentContent()).thenReturn("댓글 내용 수정");
            when(userDetails.getUsername()).thenReturn("아이디");

            // when
            comment.update(commentRequestDto, userDetails);

            // then
            assertEquals(1L, comment.getPostId());
            assertEquals("아이디", comment.getUsername());
            assertEquals("댓글 내용 수정", comment.getCommentContent());
        }
    }

}
