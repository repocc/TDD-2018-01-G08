package monitoreo.data.tickets;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Field {
    private String name;
    private String value;
    private String type;

    public Field() {
        // Needed by Jackson deserialization
    }

    public Field(String name, String value, String type) {
        this.name = name;
        this.value = value;
        this.type = type;
    }

    @JsonProperty
    public String getName() {
        return this.name;
    }

    @JsonProperty
    public String getValue() {
        return this.value;
    }

    @JsonProperty
    public String getType() {
        String typeName = "Entero";
        if (type.equals("string")) {
            typeName = "Texto";
        }
        return typeName;

    }
}
