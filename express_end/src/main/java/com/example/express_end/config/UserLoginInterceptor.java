package com.example.express_end.config;
import com.example.express_end.Respond.RespondEnum;
import com.example.express_end.entity.exception.GlobalException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//登录拦截器
public class UserLoginInterceptor implements HandlerInterceptor {
        /***
         * 在请求处理之前进行调用(Controller方法调用之前)
         */
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

            try {
                String cookie = request.getHeader("Authorization");
                HttpSession session = request.getSession();
                //统一拦截（查询当前session是否存在user）(这里user会在每次登录成功后，写入session)
                String tokenid =  (String)session.getAttribute("tokenid");
                if (tokenid != null) {
                    return true;
                }
                response.sendRedirect(request.getContextPath() + "login");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("登录拦截");
            throw new GlobalException(RespondEnum.LOGIN_ERROR);

            //如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
            //如果设置为true时，请求将会继续执行后面的操作
        }

        /***
         * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
         */
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        }

        /***
         * 整个请求结束之后被调用，也就是在DispatchServlet渲染了对应的视图之后执行（主要用于进行资源清理工作）
         */
        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        }
}

