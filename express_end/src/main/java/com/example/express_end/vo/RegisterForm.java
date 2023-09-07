package com.example.express_end.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterForm {
    @NonNull
    @Size(max = 18)
    String username;
    @Size(min=6,max = 18)
    String password;
    @Email
    String email;
    @Size(min=6,max = 6)
    String code;
    String nickname;
}
