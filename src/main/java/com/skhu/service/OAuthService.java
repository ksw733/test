package com.skhu.service;

import com.skhu.dto.OAuthDto;

public interface OAuthService {

    OAuthDto.LoginResponse getAccessToken(String code);
    OAuthDto.UserResponse getUserInfo(String accessToken);

}