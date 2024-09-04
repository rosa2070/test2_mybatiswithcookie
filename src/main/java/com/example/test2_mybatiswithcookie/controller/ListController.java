package com.example.test2_mybatiswithcookie.controller;

import com.example.test2_mybatiswithcookie.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;

@Controller
public class ListController {
    @Autowired private MemberService service;

    @GetMapping("/member/list")
    public String list(@RequestParam(name="page", defaultValue = "1") int page, Model model) {
        //한번에 3페이지씩 보이게 설정함 (1,2,3 -> 4,5,6 ..)
        int start = (page - 1) * 3 + 1; // 현재 페이지의 시작 행
        int end = start + 2; //현재 페이지의 마지막 행

        int startPage=(page-1)/3* 3 + 1;
        int endPage = startPage+2;
        int pageCount = (int)Math.ceil(service.count()/3.0);
        if(endPage > pageCount) {
            endPage = pageCount;
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("startRow", start);
        map.put("endRow", end);

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("pageCount", pageCount);
        model.addAttribute("list", service.list(map));
        model.addAttribute("page", page);


        return "member/list";
    }
}
