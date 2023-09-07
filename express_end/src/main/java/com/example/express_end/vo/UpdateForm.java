package com.example.express_end.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateForm {
    @NonNull
    Integer uid;
    @Size(min=1,max=18)
    String nickname;
    String info;
    String face;
}
