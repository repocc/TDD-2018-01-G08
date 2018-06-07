package monitoreo.data.dashboards.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import monitoreo.data.dashboards.Dashboard;

import java.util.HashSet;
import java.util.Set;

public class User {
    private String userName;
    private String password;
    private Set<Dashboard> dashboards = new HashSet<Dashboard>();

    public User() {
        // Needed by Jackson deserialization
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    public void addDashboard(Dashboard dashboard) {
        dashboards.add(dashboard);
    }

    public Set<Dashboard> getDashboards() {
        return dashboards;
    }
}
