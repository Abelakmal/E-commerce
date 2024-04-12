package com.abel.tokoonline.dtoTest;

import org.junit.jupiter.api.Test;

import com.abel.ecommerce.dto.SignupRequest;
import com.abel.tokoonline.validationTest.ValidationSetUpTest;

public class SignupRequestTest extends ValidationSetUpTest {

    @Test
    void testSignupRequest() {
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setName("sianu");
        signupRequest.setEmail("sianu@yahoo.com");
        signupRequest.setPassword("sianu0976");
        validateObject(signupRequest);

    }

}
