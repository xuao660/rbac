package org.xa.rbac.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.xa.rbac.model.RespBean;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		 response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		 	RespBean result=RespBean.build();
		 	result.setStatus(HttpServletResponse.SC_FORBIDDEN);
		 	result.setMsg(accessDeniedException.getMessage());
	        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	        response.getWriter().write(new ObjectMapper().writeValueAsString(result));
	
		
	}

}
