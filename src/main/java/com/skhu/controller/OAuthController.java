package com.skhu.controller;

import com.skhu.dto.OAuthDto;
import com.skhu.service.GoogleService;
import com.skhu.service.KakaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/oauth")
public class OAuthController {

    private final KakaoService kakaoService;

    private final GoogleService googleService;

    @GetMapping("kakao/callback")
    public ResponseEntity<OAuthDto.LoginResponse> kakaoCallback(@RequestParam String code) {
        return ResponseEntity.ok(kakaoService.getAccessToken(code));
    }

    @PostMapping("kakao/user")
    public ResponseEntity<OAuthDto.UserResponse> kakaoUser(@RequestParam String accessToken) {
        return ResponseEntity.ok(kakaoService.getUserInfo(accessToken));
    }

    @GetMapping("google/callback")
    public ResponseEntity<OAuthDto.LoginResponse> googleCallback(@RequestParam String code) {
        return ResponseEntity.ok(googleService.getAccessToken(code));
    }

    @PostMapping("google/user")
    public ResponseEntity<OAuthDto.UserResponse> googleUser(@RequestParam String accessToken) {
        return ResponseEntity.ok(googleService.getUserInfo(accessToken));
    }

}