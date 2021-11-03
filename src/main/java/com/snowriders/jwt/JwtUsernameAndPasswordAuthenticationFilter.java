package com.snowriders.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowriders.configuration.JwtConfig;
import com.snowriders.model.entities.AppUser;
import com.snowriders.model.request.UserRequest;
import com.snowriders.model.util.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RequiredArgsConstructor
public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfig jwtConfig;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            UserRequest authenticationRequest = new ObjectMapper()
                    .readValue(request.getInputStream(), UserRequest.class);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getUsername(),
                    authenticationRequest.getPassword()
            );

            return authenticationManager.authenticate(authentication);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {

        UserDetailsImpl userDetailsImpl = (UserDetailsImpl) authResult.getPrincipal();
        AppUser user = userDetailsImpl.getUser();
        Algorithm algorithm = Algorithm.HMAC256(jwtConfig.getSecretKey());
        String token = JWT.create()
                .withClaim("id", user.getId())
                .withClaim("firstName", user.getFirstName())
                .withClaim("lastName", user.getLastName())
                .withClaim("roles", user.getRoles().stream()
                        .map(r -> r.getRole().getName())
                        .collect(Collectors.toList()))
                .withExpiresAt(Date.valueOf(LocalDate.now().plusDays(jwtConfig.getTokenExpirationAfterDays())))
                .sign(algorithm);

        response.addHeader(AUTHORIZATION, jwtConfig.getTokenPrefix() + token);

    }
}

