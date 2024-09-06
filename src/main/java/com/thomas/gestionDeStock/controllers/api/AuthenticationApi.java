package com.thomas.gestionDeStock.controllers.api;

import com.thomas.gestionDeStock.dto.auth.AuthenticationRequest;
import com.thomas.gestionDeStock.dto.auth.AuthenticationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import static com.thomas.gestionDeStock.utils.Constants.AUTHENTICATION_ENDPOINT;

@Tag(name = "authentication")
public interface AuthenticationApi {

    @PostMapping(AUTHENTICATION_ENDPOINT + "/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request);

}
