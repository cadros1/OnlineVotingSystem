package top.cadros.onlinevotingsystem;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.JwtException;
import top.cadros.onlinevotingsystem.object.ApiResponse;
import top.cadros.onlinevotingsystem.service.JwtService;

import java.io.IOException;

@Component
public class MyFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 在这里编写你的过滤逻辑
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        if(req.getRequestURL().toString().contains("/login")||req.getRequestURL().toString().contains("/register")){
            chain.doFilter(request, response);
        }else{
            try{
                String token = req.getHeader("Authorization");
                if(token==null){
                    throw new JwtException("No token");
                }
                JwtService.getClaimsFromToken(token);
                chain.doFilter(request, response);
            }catch(JwtException e){
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
                ApiResponse apiResponse = new ApiResponse(40100, "Unauthorized", null, null);
                ObjectMapper mapper = new ObjectMapper();
                String jsonResponse = mapper.writeValueAsString(apiResponse);
                res.getWriter().write(jsonResponse);
            }catch(Exception e){
                res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 500
                ApiResponse apiResponse = new ApiResponse(50000, "Internet Server Error", null, null);
                ObjectMapper mapper = new ObjectMapper();
                String jsonResponse = mapper.writeValueAsString(apiResponse);
                res.getWriter().write(jsonResponse);
            }
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初始化代码
    }

    @Override
    public void destroy() {
        // 清理代码
    }
}