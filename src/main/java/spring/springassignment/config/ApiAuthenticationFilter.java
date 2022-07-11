package spring.springassignment.config;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import spring.springassignment.entity.dto.CredentialDTO;
import spring.springassignment.entity.dto.LoginDTO;
import spring.springassignment.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ApiAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("checking login information");
        try {
            // lấy dữ liệu từ trong body của request
            String jsonData = request.getReader().lines().collect(Collectors.joining());
            // tạo đối tượng gson phục vụ cho việc parse (ép kiểu về loginDTO)
            Gson gson = new Gson();
            LoginDTO loginDTO = gson.fromJson(jsonData, LoginDTO.class);
            // chuyển dữ liệu từ loginDTO
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginDTO.getUsername(),
                                                            loginDTO.getPassword());
            // tiến hành check thông tin đăng nhập bằng AuthenticationManager
            return authenticationManager.authenticate(authenticationToken);
        }catch (IOException e) {
            return null;
        }
    }

    // xử lí khi đăng nhập thành công
    // trả về access token
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        //generate access token
        String accessToken = JwtUtil.generateToken(user.getUsername(),
                user.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURL().toString(),
                JwtUtil.ONE_DAY * 7);
        //generate refresh token
        String refreshToken = JwtUtil.generateToken(user.getUsername(),
                user.getAuthorities().iterator().next().getAuthority(),
                request.getRequestURL().toString(),
                JwtUtil.ONE_DAY * 14);
        CredentialDTO credential = new CredentialDTO(accessToken, refreshToken);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Gson gson = new Gson();
        response.getWriter().println(gson.toJson(credential));
    }

    // xử lí khi đăng nhập k thành công
    // trả về error ở dạng json
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        HashMap<String, String> errors = new HashMap<>();
        errors.put("messege", "Invalid Information");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        Gson gson = new Gson();
        response.getWriter().println(gson.toJson(errors));
    }
}
