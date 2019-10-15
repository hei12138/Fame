package com.zbw.fame.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zbw.fame.exception.NotLoginException;
import com.zbw.fame.util.ErrorCode;
import com.zbw.fame.util.FameUtil;
import com.zbw.fame.util.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.stream.Stream;

/**
 * 管理后台 拦截器
 *
 * @author zbw
 * @since 2017/10/11 14:10
 */
@Slf4j
@Component
public class AdminInterceptor implements HandlerInterceptor {

    private static final String AUTH_URIS = "/admin";

    private static final String[] IGNORE_URIS = {"/admin/login", "/admin/logout"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        String ip = FameUtil.getIp();

        log.info("用户访问地址: {}, Http类型: {}, ip地址: {}", url, request.getMethod(), ip);

        //登录拦截
        if (url.contains(AUTH_URIS) && isAuthUrl(url)) {
            // 调用方法查看是否有登录的用户
            try {
                FameUtil.getLoginUser();
            } catch (NotLoginException e) {
                // 要设置跨域，不然输出信息没有
                if (request.getHeader(HttpHeaders.ORIGIN) != null) {
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN));
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, PUT, DELETE");
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
                    response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "x-requested-with");
                }
                PrintWriter out = response.getWriter();
                String json = new ObjectMapper().writeValueAsString(RestResponse.fail(ErrorCode.NOT_LOGIN.getCode(), ErrorCode.NOT_LOGIN.getMsg()));
                out.print(json);
                out.flush();
                return false;
            }
        }

        return true;
    }


    /**
     * 判定是否要验证登录
     *
     * @param url 访问url
     * @return 是否要验证
     */
    private boolean isAuthUrl(String url) {
        return Stream.of(IGNORE_URIS)
                .noneMatch(ignore -> StringUtils.endsWithIgnoreCase(url, ignore));
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {
    }
}
