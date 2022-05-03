package io.jenkins.plugins.sample;

import hudson.util.Secret;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

public class Util {

    private static final Pattern alphabetSpaceRegex = Pattern.compile("^[ A-Za-z]+$");
    private static final Pattern alphabetRegex = Pattern.compile("^[A-Za-z]+$");

    public static boolean validateAlphaSpace(String value) {
        return alphabetSpaceRegex.matcher(value).matches();
    }

    public static boolean validateAlphabet(String value) {
        return alphabetRegex.matcher(value).matches();
    }

    public static String toBasicAuth(String username, Secret password) {
        String encodedCredentials = Base64.getEncoder().encodeToString((username + ":" + password.getPlainText()).getBytes(StandardCharsets.UTF_8));
        return "Basic " + encodedCredentials;
    }
}
