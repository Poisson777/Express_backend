package com.example.express_end;


import com.example.express_end.mapper.Express.ExpressMapper;
import com.example.express_end.mapper.UserMapper;
import com.example.express_end.vo.AuthForm;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class ExpressEndApplicationTests {

    @Test
    void test1(){
        String s="a;b;c;d;";
        String[] ss=s.split(";");
        System.out.println(ss.length);
        for (int i=0;i<ss.length;i++){
            System.out.println(ss[i]);
        }
    }

}
