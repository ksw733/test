package com.skhu.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.skhu.dto.OAuthDto;
import com.skhu.error.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

import static com.skhu.error.ErrorCode.FAILED_TO_KAKAO_LOGIN;
import static org.springframework.http.HttpMethod.POST;

@Service
@RequiredArgsConstructor
public class KakaoService implements OAuthService {

    @Value("${oauth.kakao.rest-api-key}")
    private String restApiKey;

    @Value("${oauth.kakao.redirect-url}")
    private String redirectUri;
    private final RestTemplate restTemplate;

    private HttpEntity<MultiValueMap<String, String>> createRequestEntityToGetAccessToken(String code) {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("grant_type", "authorization_code");
        requestParams.add("client_id", restApiKey);
        requestParams.add("redirect_uri", redirectUri);
        requestParams.add("code", code);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        return new HttpEntity<>(requestParams, headers);
    }

    private OAuthDto.LoginResponse parseAccessToken(ResponseEntity<String> response) {
        JsonElement element = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();

        String accessToken = element.getAsJsonObject()
                .get("access_token")
                .getAsString();

        String refreshToken = element.getAsJsonObject()
                .get("refresh_token")
                .getAsString();

        return OAuthDto.LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public OAuthDto.LoginResponse getAccessToken(String code) {
        String requestUrl = "https://kauth.kakao.com/oauth/token";
        HttpEntity<MultiValueMap<String, String>> requestEntity = createRequestEntityToGetAccessToken(code);

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, POST, requestEntity, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(FAILED_TO_KAKAO_LOGIN);
        }

        return parseAccessToken(responseEntity);
    }

    private HttpEntity<MultiValueMap<String, String>> createRequestEntityToGetUserInfo() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        return new HttpEntity<>(headers);
    }

    private OAuthDto.UserResponse parseUserInfo(ResponseEntity<String> response) {
        JsonElement element = JsonParser.parseString(Objects.requireNonNull(response.getBody())).getAsJsonObject();

        System.out.println(element);

        String socialUid = element.getAsJsonObject()
                .get("id")
                .getAsString();

        String email = element.getAsJsonObject()
                .get("kakao_account")
                .getAsJsonObject()
                .get("email")
                .getAsString();

        String nickname = element.getAsJsonObject()
                .get("properties")
                .getAsJsonObject()
                .get("nickname")
                .getAsString();

        return OAuthDto.UserResponse.builder()
                .socialUid(socialUid)
                .socialType("KAKAO")
                .email(email)
                .nickname(nickname)
                .build();
    }

    @Override
    public OAuthDto.UserResponse getUserInfo(String accessToken) {
        String requestUrl = "https://kapi.kakao.com/v2/user/me?access_token=" + accessToken;

        HttpEntity<MultiValueMap<String, String>> requestEntity = createRequestEntityToGetUserInfo();
        ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, POST, requestEntity, String.class);

        if (!responseEntity.getStatusCode().is2xxSuccessful()) {
            throw new CustomException(FAILED_TO_KAKAO_LOGIN);
        }

        return parseUserInfo(responseEntity);
    }

}