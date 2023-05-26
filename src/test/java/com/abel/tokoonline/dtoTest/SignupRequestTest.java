package com.abel.tokoonline.dtoTest;

import org.junit.jupiter.api.Test;

import com.abel.tokoonline.dto.SignupRequest;
import com.abel.tokoonline.validationTest.ValidationSetUpTest;

public class SignupRequestTest extends ValidationSetUpTest {

    @Test
    void testSignupRequest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setNama("sianu");
        signupRequest.setUsername("sianu14");
        signupRequest.setEmail("sianu@yahoo.com");
        signupRequest.setPassword("sianu0976");
        validateObject(signupRequest);

    }

}
