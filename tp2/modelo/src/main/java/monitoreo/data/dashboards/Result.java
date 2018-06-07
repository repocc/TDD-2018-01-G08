package monitoreo.data.dashboards;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result {
    private String name;
    private Long value;

    public Result() {
        // Needed by Jackson deserialization
    }

    public Result(String name) {
        this.name = name;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
