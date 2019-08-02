package com.auth0.example.security;

import com.auth0.jwt.interfaces.Claim;

import javax.security.enterprise.CallerPrincipal;
import java.util.Map;

/**
 * Represents the caller principal associated with the request.
 */
public class Auth0JwtPrincipal extends CallerPrincipal {

    private final Map<String, Claim> claims;

    Auth0JwtPrincipal(Map<String, Claim> claims) {
        super(claims.get("name").asString());
        this.claims = claims;
    }

    /**
     * @return the claims for this authenticated principal.
     */
    public Map<String, Claim> getClaims() {
        return this.claims;
    }
}
