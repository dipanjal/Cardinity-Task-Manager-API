package rest.api.cardinity.taskmanager.service.auth;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import rest.api.cardinity.taskmanager.common.enums.ResponseCode;
import rest.api.cardinity.taskmanager.common.utils.JWTUtils;
import rest.api.cardinity.taskmanager.common.utils.ResponseUtils;
import rest.api.cardinity.taskmanager.models.request.auth.AuthenticationRequest;
import rest.api.cardinity.taskmanager.models.response.Response;
import rest.api.cardinity.taskmanager.models.view.AuthenticationModel;
import rest.api.cardinity.taskmanager.service.CardinityUserDetailService;

/**
 * @author dipanjal
 * @since 2/7/2021
 */
@Service
@RequiredArgsConstructor
public class BasicAuthServiceImpl implements IAuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final CardinityUserDetailService userDetailService;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    @Override
    public Response<AuthenticationModel> authenticate(AuthenticationRequest request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword())
            );
        }catch (BadCredentialsException e){
            return ResponseUtils.createResponse(ResponseCode.UNAUTHORIZED.getCode(), "Authentication Failed! Incorrect Username or Password");
        }

        UserDetails userDetails = userDetailService.loadUserByUsername(request.getUsername());
        String token = JWTUtils.generateToken(userDetails);
        return ResponseUtils.createSuccessResponse(new AuthenticationModel(token));
    }

    @Override
    public Response<UserDetails> validateToken(String bearerToken) throws ExpiredJwtException {
        if(StringUtils.isBlank(bearerToken) || !StringUtils.startsWith(bearerToken, tokenPrefix))
            return ResponseUtils.createResponse(ResponseCode.BAD_REQUEST.getCode(), "Invalid Bearer Token");

        String jwtToken = JWTUtils.trimToken(bearerToken);
        String userName = JWTUtils.extractUserName(jwtToken);

        UserDetails userDetails = userDetailService.loadUserByUsername(userName);
        if(!JWTUtils.validateToken(jwtToken, userDetails))
            return ResponseUtils.createResponse(ResponseCode.RECORD_NOT_FOUND.getCode(), "Invalid Invalid or Expired!");

        return ResponseUtils.createSuccessResponse(userDetails);
    }
}
