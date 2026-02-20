package ai.pribela.glossapp.security;

import ai.pribela.glossapp.dto.LoginDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

import java.io.IOException;

public class JsonUsernamePasswordAuthFilter extends AbstractAuthenticationProcessingFilter {

    private final ObjectMapper objectMapper;
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    public JsonUsernamePasswordAuthFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        super(PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.POST, "/api/login"));
        this.objectMapper = objectMapper;
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (request.getContentType() == null || !request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)) {
            throw new IllegalArgumentException("Content-Type must be application/json");
        }
        LoginDto body = objectMapper.readValue(request.getInputStream(), LoginDto.class);
        var authRequest = new UsernamePasswordAuthenticationToken(body.username(), body.password());
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authResult);
        SecurityContextHolder.setContext(context);

        securityContextRepository.saveContext(context, request, response);

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
