package com.milica.centralniserver;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthHelper {

    public static String[] parseBasicAuth(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Basic "))
            return null;
        String decoded = new String(
            Base64.getDecoder().decode(authHeader.substring(6)),
            StandardCharsets.UTF_8
        );
        return decoded.split(":", 2);
    }
}