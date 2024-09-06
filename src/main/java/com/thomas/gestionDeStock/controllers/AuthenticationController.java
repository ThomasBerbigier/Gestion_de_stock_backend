package com.thomas.gestionDeStock.controllers;

import com.thomas.gestionDeStock.controllers.api.AuthenticationApi;
import com.thomas.gestionDeStock.dto.auth.AuthenticationRequest;
import com.thomas.gestionDeStock.dto.auth.AuthenticationResponse;
import com.thomas.gestionDeStock.model.auth.ExtendedUser;
import com.thomas.gestionDeStock.services.auth.ApplicationUserDetailsService;
import com.thomas.gestionDeStock.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class AuthenticationController implements AuthenticationApi {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ApplicationUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                )
        );
        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());

        final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);

        return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
    }
}
