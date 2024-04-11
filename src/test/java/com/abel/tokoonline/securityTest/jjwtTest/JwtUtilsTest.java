package com.abel.tokoonline.securityTest.jjwtTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.abel.tokoonline.security.jjwt.JwtUtils;
import com.abel.tokoonline.security.service.UserDetailImpl;

public class JwtUtilsTest {

    @Test
    void testGenerateRefreshToken() {
        JwtUtils jwtUtils = new JwtUtils();
        UserDetailImpl userDetails = new UserDetailImpl("duwdwd", "nife", "wufwfwi", "fhwfwfo", "user");

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null);

        String refreshToken = jwtUtils.generateJwtToken(authentication);

        System.out.println(refreshToken);
        assertEquals(true, refreshToken.length() > 0);
    }

}
