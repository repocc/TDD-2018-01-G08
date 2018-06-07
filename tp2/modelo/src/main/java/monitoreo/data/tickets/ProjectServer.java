package monitoreo.data.tickets;

import java.util.HashMap;

public class ProjectServer {
    private static Integer globalId = 0;
    private HashMap<Integer, Project> projects = new HashMap<Integer, Project>();

    public Project createProject(Project project) {
        project.setId(this.globalId);
        projects.put(this.globalId, project);
        this.globalId += 1;
        return project;
    }

    public Project getProjectById(Integer id) {
        return projects.get(id);
    }

    public String getProjectNameById(Integer id) {
        return projects.get(id).getName();
    }
}
