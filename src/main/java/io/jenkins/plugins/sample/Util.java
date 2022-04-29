package io.jenkins.plugins.sample;

import java.util.regex.Pattern;

public class Util {

    private static final Pattern alphabetSpaceRegex = Pattern.compile("^[ A-Za-z]+$");


    public static boolean validateWithRegex(String value) {
        return alphabetSpaceRegex.matcher(value).matches();
    }
}
