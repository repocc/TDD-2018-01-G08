package monitoreo.data.dashboards;

import com.fasterxml.jackson.annotation.JsonProperty;
import monitoreo.data.tickets.Project;
import monitoreo.data.tickets.ProjectServer;

import java.util.HashMap;

public class DashboardServer {
    private static Integer globalId = 0;
    private ProjectServer projectServer;
    private HashMap<Integer, Dashboard> dashboards = new HashMap<Integer, Dashboard>();

    public DashboardServer(ProjectServer projectServer) {
        this.projectServer = projectServer;
    }

    public Dashboard createDashboard(Dashboard dashboard) {
        dashboard.setId(this.globalId);
        dashboards.put(this.globalId, dashboard);
        this.globalId += 1;
        return dashboard;
    }

    public Dashboard getDashboardById(Integer id) {
        return dashboards.get(id);
    }

    public Boolean addProjectToDashboard(Integer idProject, Integer idDashboard) {
        Dashboard dashboard = dashboards.get(idDashboard);
        Project project = projectServer.getProjectById(idProject);
        dashboard.setProject(project);
        return true;
    }
}
