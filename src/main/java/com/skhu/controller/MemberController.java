//package com.skhu.controller;
//
//import com.skhu.dto.ApiResponse;
//import com.skhu.dto.MemberRequestDto;
//import com.skhu.dto.MemberResponseDto;
//import com.skhu.repository.MemberRepository;
//import com.skhu.service.MemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/member")
//@RequiredArgsConstructor
//public class MemberController {
//    private final MemberRepository memberRepository;
//    private final MemberService memberService;
//
//    @PostMapping("/signup")
//    public ApiResponse<MemberResponseDto> signup(@RequestBody MemberRequestDto memberRequestDto){
//        return ApiResponse.ok(memberService.signup(memberRequestDto));
//    }
//
//    @RequestMapping("/callback")
//    public ApiResponse<MemberResponseDto> kakaoLogin(@RequestParam String code){
//
//    }
//
//}
