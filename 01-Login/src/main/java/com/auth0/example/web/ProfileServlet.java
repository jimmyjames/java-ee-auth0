package com.auth0.example.web;

import com.auth0.example.security.Auth0JwtPrincipal;
import com.auth0.jwt.interfaces.Claim;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.jboss.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Map;

/**
 * Handles requests for the profile page. If no authenticated principal for the request is found, redirects the user
 * to login.
 */
@WebServlet(urlPatterns = {"/profile"})
public class ProfileServlet extends HttpServlet {

    private final static Logger LOG = Logger.getLogger(ProfileServlet.class);

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Principal principal = request.getUserPrincipal();
        if (principal instanceof Auth0JwtPrincipal) {
            LOG.info("found Auth0JwtPrincipal, forwarding to view");
            Auth0JwtPrincipal auth0JwtPrincipal = (Auth0JwtPrincipal) principal;
            request.setAttribute("profile", auth0JwtPrincipal.getClaims());
            request.setAttribute("profileJson", claimsAsJson(auth0JwtPrincipal.getClaims()));
            request.getRequestDispatcher("/WEB-INF/jsp/profile.jsp").forward(request, response);
        } else {
            LOG.info("No principal found for this request, redirecting to login");
            request.getSession().setAttribute("Referer", request.getRequestURI());
            request.getRequestDispatcher("/login").forward(request, response);
        }
    }

    /**
     * Utility method to convert the JWT claims into JSON for use by the display of this sample.
     * @param claims
     * @return a JSON-formatted string used by the view for displaying profile information.
     */
    private static String claimsAsJson(Map<String, Claim> claims) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode node = objectMapper.createObjectNode();

        claims.forEach((key, value) -> {
            if (value.asMap() != null) {
                node.putPOJO(key, value.asMap());
            } else if (value.asList(String.class) != null) {
                JsonNode jsonNode = objectMapper.valueToTree(value.asList(String.class));
                node.set(key, jsonNode);
            } else if (value.asBoolean() != null) {
                node.put(key, value.asBoolean());
            } else if (value.asInt() != null) {
                node.put(key, value.asInt());
            } else if (value.as(String.class) != null) {
                node.put(key, value.as(String.class));
            } else if (value.isNull()) {
                node.putNull(key);
            }
        });

        String json = "";
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
        } catch (JsonProcessingException jpe) {
            LOG.error("Error processing json from profile", jpe);
        }

        return json;
    }
}
