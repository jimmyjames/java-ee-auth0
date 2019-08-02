package com.auth0.example.web;

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
 * Logs the user out from the Auth0 authorization server and clears the session.
 */
@WebServlet(urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    private final static Logger LOG = Logger.getLogger(LogoutServlet.class);

    @Inject
    private Auth0AuthenticationConfig config;

    @Override
    protected void doGet(final HttpServletRequest request, final HttpServletResponse response) throws ServletException, IOException {
        clearSession(request);
        response.sendRedirect(getReturnUrl(request));
    }

    private void clearSession(HttpServletRequest request) {
        if (request.getSession() != null) {
            request.getSession().invalidate();
        }
    }

    private String getReturnUrl(HttpServletRequest request) {
        String returnUrl = String.format("%s://%s", request.getScheme(), request.getServerName());
        int port = request.getServerPort();
        String scheme = request.getScheme();

        if (("http".equals(scheme) && port != 80) ||
                ("https".equals(scheme) && port != 443)) {
            returnUrl += ":" + port;
        }

        returnUrl += "/";

        String logoutUrl = String.format(
                "https://%s/v2/logout?client_id=%s&returnTo=%s",
                config.getDomain(),
                config.getClientId(),
                returnUrl
        );

        LOG.debug("Logout URL: " + logoutUrl);
        return logoutUrl;
    }
}
