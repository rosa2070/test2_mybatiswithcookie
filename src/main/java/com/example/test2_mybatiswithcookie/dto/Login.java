package com.example.test2_mybatiswithcookie.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Login {
    private String id;
    private String pwd;
    private boolean rememberMe; // 체크박스 상태를 나타내는 필드
}
