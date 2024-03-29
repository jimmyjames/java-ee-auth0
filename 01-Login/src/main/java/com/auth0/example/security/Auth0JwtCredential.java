package com.auth0.example.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.security.enterprise.credential.Credential;
import java.util.Map;

/**
 * Represents a credential for authentication with Auth0.
 */
class Auth0JwtCredential implements Credential {

    private Auth0JwtPrincipal auth0JwtPrincipal;

    /**
     * Constructor
     *
     * @param token the JWT token representing this Credential.
     */
    Auth0JwtCredential(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        Map<String, Claim> claims = decodedJWT.getClaims();
        this.auth0JwtPrincipal = new Auth0JwtPrincipal(claims);
    }

    /**
     * @return the {@link Auth0JwtPrincipal} for this Credential.
     */
    Auth0JwtPrincipal getAuth0JwtPrincipal() {
        return auth0JwtPrincipal;
    }
}
