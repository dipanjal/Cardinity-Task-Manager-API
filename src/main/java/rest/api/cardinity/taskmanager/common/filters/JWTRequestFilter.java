package rest.api.cardinity.taskmanager.common.filters;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.service.auth.AuthenticationServiceFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dipanjal
 * @since 2/7/2021
 */
@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {
    private final AuthenticationServiceFactory authServiceFactory;

    @Value("${auth.header.name}")
    private String authHeaderName;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authToken = httpServletRequest.getHeader(authHeaderName);
        Response<UserDetails> userDetailsResponse = authServiceFactory
                .getBasicAuthService()
                .validateToken(authToken);

        if(ResponseCode.isSuccessful(userDetailsResponse)){
            UserDetails userDetails = userDetailsResponse.getItems();
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.getAuthorities()
            );
            usernamePasswordAuthenticationToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
            );
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
