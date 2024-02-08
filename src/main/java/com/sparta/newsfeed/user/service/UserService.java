package com.sparta.newsfeed.user.service;

import com.sparta.newsfeed.user.dto.CommonResponseDto;
import com.sparta.newsfeed.user.dto.SignupRequestDto;
import com.sparta.newsfeed.user.entity.User;
import com.sparta.newsfeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j(topic = "UserService")
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String verifyEmailKeys = "kudongkuIsGenius";

    public ResponseEntity<CommonResponseDto> createUser(SignupRequestDto signupRequestDto) {
        String username = signupRequestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        String emailVerifyKey = signupRequestDto.getEmailVerifyKey();
        String email = signupRequestDto.getEmail();

        if (userRepository.findByUsername(username).isPresent()) {
            log.error("동일한 아이디가 존재합니다.");
            return ResponseEntity.status(400).body(new CommonResponseDto("회원가입 실패", 400));
        }

        if (userRepository.findByEmail(email).isPresent()) {
            log.error("동일한 이메일이 존재합니다.");
            return ResponseEntity.status(400).body(new CommonResponseDto("회원가입 실패", 400));
        }

        if(!emailVerifyKey.equals(verifyEmailKeys)){
            log.error("이메일 인증 실패.");
            return ResponseEntity.status(400).body(new CommonResponseDto("회원가입 실패", 400));
        }

        User user = new User(signupRequestDto, encodedPassword);
        userRepository.save(user);
        return ResponseEntity.status(200).body(new CommonResponseDto("회원가입 성공", 200));
    }
}
