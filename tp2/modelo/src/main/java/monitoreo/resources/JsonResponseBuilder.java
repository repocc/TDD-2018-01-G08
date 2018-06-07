package monitoreo.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JsonResponseBuilder {
    private String code;
    private String message;

    public JsonResponseBuilder() {
        // Needed by Jackson deserialization
    }

    public JsonResponseBuilder(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @JsonProperty
    public String getCode() {
        return this.code;
    }

    @JsonProperty
    public String getMessage() {
        return this.message;
    }
}
