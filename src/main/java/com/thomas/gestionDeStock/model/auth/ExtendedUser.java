package com.thomas.gestionDeStock.model.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


public class ExtendedUser extends User {

    @Setter
    @Getter
    private Integer idEntreprise;

    public ExtendedUser(String username, String password,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public ExtendedUser(String username, String password, Integer idEntreprise,
                        Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.idEntreprise = idEntreprise;
    }
}
