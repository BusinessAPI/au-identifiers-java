package au.com.businessapi.identifiers;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

public final class Vin {
    private static final Pattern VIN = Pattern.compile("[A-HJ-NPR-Z0-9]{17}");

    private Vin() {}

    public static boolean isValid(String value) {
        return normalise(value).isPresent();
    }

    public static Optional<String> normalise(String value) {
        if (value == null) return Optional.empty();
        String trimmed = Digits.trim(value);
        for (char c : trimmed.toCharArray()) if (c > 0x7f) return Optional.empty();
        String vin = trimmed.toUpperCase(Locale.ROOT);
        return VIN.matcher(vin).matches() ? Optional.of(vin) : Optional.empty();
    }
}
