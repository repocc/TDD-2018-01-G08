package monitoreo.resources;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProjectAPIInterface {
    private String name;
    private Integer id;

    public ProjectAPIInterface() {
        // Needed by Jackson deserialization
    }

    public ProjectAPIInterface(Integer id, String name) {
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
