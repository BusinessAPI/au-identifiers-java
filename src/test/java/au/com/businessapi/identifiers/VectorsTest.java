package au.com.businessapi.identifiers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class VectorsTest {
    private static Stream<JsonNode> cases(String key) throws IOException {
        JsonNode root = new ObjectMapper().readTree(VectorsTest.class.getResourceAsStream("/vectors.json"));
        return StreamSupport.stream(root.get(key).spliterator(), false);
    }

    static Stream<JsonNode> validCases() throws IOException {
        return cases("valid");
    }

    static Stream<JsonNode> formatCases() throws IOException {
        return cases("format");
    }

    @ParameterizedTest
    @MethodSource("validCases")
    void valid(JsonNode c) {
        String value = c.get("value").asText();
        boolean got;
        switch (c.get("type").asText()) {
            case "abn": got = Abn.isValid(value); break;
            case "acn": got = Acn.isValid(value); break;
            case "tfn": got = Tfn.isValid(value, c.has("individual") && c.get("individual").asBoolean()); break;
            case "did": got = DirectorId.isValid(value); break;
            case "vin": got = Vin.isValid(value); break;
            default: throw new IllegalArgumentException(c.toString());
        }
        assertEquals(c.get("valid").asBoolean(), got, c.toString());
    }

    @ParameterizedTest
    @MethodSource("formatCases")
    void format(JsonNode c) {
        String value = c.get("value").asText();
        String expected = c.get("expected").isNull() ? null : c.get("expected").asText();
        String got;
        switch (c.get("type").asText()) {
            case "abn": got = Abn.format(value).orElse(null); break;
            case "acn": got = Acn.format(value).orElse(null); break;
            case "vin": got = Vin.normalise(value).orElse(null); break;
            default: throw new IllegalArgumentException(c.toString());
        }
        assertEquals(expected, got, c.toString());
    }

    @Test
    void otherInputs() {
        assertTrue(Arbn.isValid("000 000 019"));
        assertFalse(Arbn.isValid("000 000 018"));
        assertFalse(Abn.isValid(null));
        assertFalse(Tfn.isValid(null));
        assertFalse(Acn.isValid("٠٠٤٠٨٥٦١٦"));
    }
}
