package monitoreo.data.tickets.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import monitoreo.data.tickets.Project;

import java.util.Set;
import java.util.HashSet;

public class User {
    private String userName;
    private String password;
    private Set<Project> projects = new HashSet<Project>();

    public User() {
        // Needed by Jackson deserialization
    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    @JsonProperty
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public String getUserName() {
        return userName;
    }

    public void addProject(Project project) {
        projects.add(project);
    }
}
