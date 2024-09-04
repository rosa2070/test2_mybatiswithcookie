package com.example.test2_mybatiswithcookie.controller;

import com.example.test2_mybatiswithcookie.dto.Login;
import com.example.test2_mybatiswithcookie.dto.Member;
import com.example.test2_mybatiswithcookie.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;

@Controller
public class LoginController {

    @Autowired
    MemberService service;

    @GetMapping("/member/login")
    public String loginForm(@ModelAttribute Login login, HttpServletRequest request) {
        // 쿠키에서 로그인 정보 읽기
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("id".equals(cookie.getName())) {
                    login.setId(cookie.getValue());
                }
                if ("pwd".equals(cookie.getName())) {
                    login.setPwd(cookie.getValue()
                    );
                }
            }
        }
        return "member/login";
    }

    @PostMapping("/member/login")
    public String login(@ModelAttribute Login login, HttpServletRequest req, HttpServletResponse response, HttpSession httpSession) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", login.getId());
        map.put("pwd", login.getPwd());
        Member m = service.isMember(map);
        if (m == null) { //회원이 아닌경우
            return "member/login";
        } else {
            HttpSession session = req.getSession(true); //true false차이
            session.setAttribute("id", m.getId());

            // 자동 로그인 체크박스가 체크된 경우 쿠키 설정
            if (login.isRememberMe()) {
                Cookie idCookie = new Cookie("id", login.getId());
                Cookie pwdCookie = new Cookie("pwd", login.getPwd());

                // 쿠키 유효시간 설정
                idCookie.setMaxAge(60*3);
                pwdCookie.setMaxAge(60*3);

                //쿠키를 응답객체에 담기
                response.addCookie(idCookie);
                response.addCookie(pwdCookie);
            } else {
                // 자동 로그인 체크박스가 체크되지 않은 경우 쿠키 삭제
                Cookie idCookie = new Cookie("id", "");
                Cookie pwdCookie = new Cookie("pwd", "");

                idCookie.setMaxAge(0);
                pwdCookie.setMaxAge(0);

                response.addCookie(idCookie);
                response.addCookie(pwdCookie);
            }

            return "redirect:/";

        }
    }

    @GetMapping("/member/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }


        return "redirect:/";
    }


}
