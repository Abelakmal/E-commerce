package com.abel.tokoonline.dtoTest;

import org.junit.jupiter.api.Test;

import com.abel.tokoonline.dto.SigninRequest;
import com.abel.tokoonline.validationTest.ValidationSetUpTest;

public class SigninRequestTest extends ValidationSetUpTest {

    @Test
    void loginRequestDataTest() {
        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setUsername("hiij909");
        signinRequest.setPassword("ih8h88yh8yh");
        validateObject(signinRequest);
    }

}
