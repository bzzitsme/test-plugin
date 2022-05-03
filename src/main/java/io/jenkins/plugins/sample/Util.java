package io.jenkins.plugins.sample;

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
}
