package com.example.express_end.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Size;

//实名认证的表
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EAuthForm {
    @NonNull
    Integer mid;
    @NonNull
    String phone;
    @NonNull
    String real_name;
    @Size(min = 18,max = 18)
    String id_card;
}
