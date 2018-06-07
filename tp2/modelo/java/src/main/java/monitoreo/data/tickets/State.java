package monitoreo.data.tickets;

import com.fasterxml.jackson.annotation.JsonProperty;

public class State {
    private Integer order;
    private String name;

    public State() {
        // Needed by Jackson deserialization
    }

    public State(Integer order, String name ) {
        this.order = order;
        this.name = name;
    }

    @JsonProperty
    public String getName() {
        return this.name;
    }

    @JsonProperty
    public Integer getOrder() {
        return this.order;
    }
}
