package com.patryk.mech.manageitup.shared;

import java.util.Arrays;
import java.util.stream.Collectors;

public class NameUtils {

    public static String capitalizeWords(String string) {
        return Arrays.stream(string.split(" "))
                .map(NameUtils::capitalize)
                .collect(Collectors.joining(" "));
    }

    public static String capitalize(String string) {
        return Character.toUpperCase(string.charAt(0)) + string.substring(1);
    }
}
