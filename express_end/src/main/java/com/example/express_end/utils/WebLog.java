package com.example.express_end.utils;

import java.lang.annotation.*;

@Documented   //该注解表示支持javaDoc文档导出
@Retention(RetentionPolicy.RUNTIME) //该注解表示生命周期
@Target(ElementType.METHOD)  //该注解表示自定义的注解可以使用的对象
public @interface WebLog {
    String type() default "新增";
    String info() default "新增";
}
