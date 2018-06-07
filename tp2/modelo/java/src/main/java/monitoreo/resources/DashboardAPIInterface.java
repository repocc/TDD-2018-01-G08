package monitoreo.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DashboardAPIInterface {
    private String name;
    private Integer id;

    public DashboardAPIInterface() {
        // Needed by Jackson deserialization
    }

    public DashboardAPIInterface(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public Integer getId() {
        return id;
    }
}
