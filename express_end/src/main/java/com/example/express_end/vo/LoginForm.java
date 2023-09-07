package com.example.express_end.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

//前端传送过来的表单数据
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginForm {
    @NonNull
    @Size(max = 18)
    private String username;

    @Size(min=6,max = 18)
    private String password;
}
