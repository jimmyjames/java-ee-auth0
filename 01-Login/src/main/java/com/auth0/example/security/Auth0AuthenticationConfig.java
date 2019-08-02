package com.auth0.example.security;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

/**
 * Bean to provide access for configuration settings.
 */
@ApplicationScoped
public class Auth0AuthenticationConfig {

    @Inject
    @ConfigurationValue("auth0.domain")
    private String domain;

    @Inject
    @ConfigurationValue("auth0.clientId")
    private String clientId;

    @Inject
    @ConfigurationValue("auth0.clientSecret")
    private String clientSecret;

    @Inject
    @ConfigurationValue("auth0.scope")
    private String scope;

    public String getDomain() {
        return domain;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public String getScope() {
        return scope;
    }
}
