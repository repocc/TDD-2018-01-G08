package monitoreo.data.dashboards;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rule {
    private String authUserName;
    private String name;
    private String query;
    private Boolean enable;

    public Rule() {
        // Needed by Jackson deserialization
    }

    public Rule(String authUserName, String name, String query, Boolean enable) {
        this.authUserName = authUserName;
        this.name = name;
        this.query = query;
        this.enable = enable;
    }

    @JsonProperty
    public String getQuery() {
        return query;
    }

    @JsonProperty
    public String getAuthUserName() {
        return authUserName;
    }

    @JsonProperty
    public Boolean getEnable() {
        return enable;
    }

    @JsonProperty
    public String getName() {
       return name;
    }
}
