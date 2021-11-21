package org.xa.rbac.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.xa.rbac.model.RespBean;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CustomAuthExceptionEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthExceptionEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {
        Throwable casuse = authException.getCause();
        response.setStatus(HttpStatus.OK.value());
        response.setContentType("application/json;charset=UTF-8");
        try {
            if(casuse instanceof InvalidTokenException){
                RespBean respBean = RespBean.build();
                respBean.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                respBean.setMsg(casuse.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(new ObjectMapper().writeValueAsString(respBean));
            }else {
                String message = "AUTHORIZE FAILED!";
                if(casuse!=null){
                    message = casuse.getMessage();
                }
                RespBean respBean = RespBean.build();
                respBean.setStatus(HttpServletResponse.SC_FORBIDDEN);
                respBean.setMsg(message);
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.getWriter().write(new ObjectMapper().writeValueAsString(respBean));
            }
        }catch (Exception e){
            logger.info(e.getMessage(),e);
        }

    }
}
