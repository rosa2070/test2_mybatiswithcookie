package com.example.test2_mybatiswithcookie.service;


import com.example.test2_mybatiswithcookie.dto.Member;
import com.example.test2_mybatiswithcookie.mybatis.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class MemberService {
    @Autowired
    private MemberMapper mapper;

    public int insert(Member member) {
        return mapper.insert(member);
    }
    public int count() {
        return mapper.count();
    }
    public List<Member> list(HashMap<String, Object> map) {
        return mapper.list(map);
    }
    public int delete(String id) {
        return mapper.delete(id);
    }

    public int update(Member member) {
        return mapper.update(member);
    }

    public Member select(String id) {
        return mapper.select(id);
    }

    public Member isMember(HashMap<String, String> map) {
        return mapper.isMember(map);
    }
}
