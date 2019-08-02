package com.auth0.example.web;

import com.auth0.AuthenticationController;
import com.auth0.example.security.Auth0AuthenticationConfig;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Responsible for redirecting to Auth0 to initiate the authentication flow when a login request is made.
 */
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private final static Logger LOG = Logger.getLogger(LoginServlet.class);

    @Inject
    private Auth0AuthenticationConfig config;

    @Inject
    private AuthenticationController authenticationController;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("in LoginServlet");
        response.sendRedirect(buildAuthUrl(request));
    }

    private String buildAuthUrl(HttpServletRequest request) {
        String redirectUrl = String.format(
                "%s://%s:%s/callback",
                request.getScheme(),
                request.getServerName(),
                request.getServerPort()
        );

        return authenticationController.buildAuthorizeUrl(request, redirectUrl)
                .withAudience("https://" + config.getDomain() + "/userinfo")
                .withScope(config.getScope())
                .build();
    }
}
