package com.example.express_end.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Email {
    @javax.validation.constraints.Email
    String email;
    @NonNull
    String role;
}
