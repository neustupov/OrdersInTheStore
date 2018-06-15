package ru.neustupov.ordersinthestore.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_SELLER,
    ROLE_MANAGER,
    ROLE_ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
