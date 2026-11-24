package au.com.businessapi.identifiers;

final class Digits {
    private Digits() {}

    static String of(String value, int... lengths) {
        if (value == null) return null;
        StringBuilder sb = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (c == '-' || isWhitespace(c)) continue;
            if (c < '0' || c > '9') return null;
            sb.append(c);
        }
        for (int length : lengths) if (sb.length() == length) return sb.toString();
        return null;
    }

    static boolean isWhitespace(char c) {
        return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == 0x0B || c == '\f';
    }

    static String trim(String value) {
        int start = 0, end = value.length();
        while (start < end && isWhitespace(value.charAt(start))) start++;
        while (end > start && isWhitespace(value.charAt(end - 1))) end--;
        return value.substring(start, end);
    }

    static int sum(String digits, int[] weights, int offset) {
        int sum = 0;
        for (int i = 0; i < weights.length - offset; i++) sum += (digits.charAt(i) - '0') * weights[i + offset];
        return sum;
    }

    static String stripLeadingZeros(String digits) {
        int i = 0;
        while (i < digits.length() && digits.charAt(i) == '0') i++;
        return digits.substring(i);
    }
}
