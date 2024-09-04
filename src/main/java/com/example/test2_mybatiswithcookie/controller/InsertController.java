package com.example.test2_mybatiswithcookie.controller;


import com.example.test2_mybatiswithcookie.dto.Member;
import com.example.test2_mybatiswithcookie.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class InsertController {

    @Autowired
    MemberService service;

    @GetMapping("/member/insert")
    public String insertForm(@ModelAttribute Member member) {

        return "member/insert";
    }

    @PostMapping("/member/insert")
    public String insert(@ModelAttribute Member dto, BindingResult bindingResult, Model model) {
        if (!StringUtils.hasText(dto.getId())) { // id 텍스트가 비어있으면
            //bindingResult에 에러담기 new FieldError("객체명", "필드명", "에러메시지")
            bindingResult.addError(new FieldError("member", "id", "아이디를 입력하세요"));
        }
        if(!StringUtils.hasText(dto.getPwd())) {
            bindingResult.addError(new FieldError("member", "pwd", "비밀번호를 입력하세요"));
        }
        //나이 0~150살까지
        if(dto.getAge()>150 || dto.getAge() < 0) {
            bindingResult.addError(new FieldError("member", "age", "나이를 잘못 입력했습니다."));
        }

        if (bindingResult.hasErrors()) {
            return "member/insert";
        }

        try {
            service.insert(dto);
            model.addAttribute("result", "회원가입성공!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            model.addAttribute("result", "회원가입실패");

        }
        return "member/result";
    }

}
