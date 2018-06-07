package monitoreo.resources;

import monitoreo.data.dashboards.Dashboard;
import com.codahale.metrics.annotation.Timed;
import monitoreo.data.dashboards.DashboardServer;
import monitoreo.data.dashboards.Result;
import monitoreo.data.dashboards.Rule;
import monitoreo.data.dashboards.users.DashboardUserRegisterServer;
import monitoreo.data.dashboards.users.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@Path("/dashboards")
public class DashboardService {
	private DashboardServer dashboardServer;
    private DashboardUserRegisterServer usersServer;

	public DashboardService(DashboardServer dashboardServer, DashboardUserRegisterServer usersServer) {
        this.dashboardServer = dashboardServer;
        this.usersServer = usersServer;
    }

    @POST
    @Timed
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public Dashboard addDashboard(Dashboard dashboard) {
        return dashboardServer.createDashboard(dashboard);
    }

    @POST
    @Timed
    @Path("/{idDashboard}/addUser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonResponseBuilder addUser(@PathParam("idDashboard") Integer idDashboard, User jsonUser) {
        Dashboard dashboard = dashboardServer.getDashboardById(idDashboard);
        String userName = jsonUser.getUserName();
        User user = usersServer.getUser(userName);
        Boolean resAdded = dashboard.addUserToDashboard(user);
        String code = "500";
        String message = "Error: Usuario ya se encuentra en el proyecto.";
        if (resAdded) {
            code = "200";
            message = "Usuario agregado con exito.";
        }
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(code, message);
        return jsonResponseBuilder;
    }

    @POST
    @Timed
    @Path("/{idDashboard}/addRule")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonResponseBuilder addRule(@PathParam("idDashboard") Integer idDashboard, Rule rule) {
        Dashboard dashboard = dashboardServer.getDashboardById(idDashboard);
        Boolean resAdded = dashboard.addRule(rule);
        String code = "200";
        String message = "La consulta ha sido agregada con exito.";
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(code, message);
        return jsonResponseBuilder;
    }

    @GET
    @Timed
    @Path("/{idDashboard}/rules/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Rule> getDashboardRules(@PathParam("idDashboard") Integer idDashboard) {
        Dashboard dashboard  = dashboardServer.getDashboardById(idDashboard);
        List<Rule> rules = dashboard.getRules();
        return rules;
    }

    @GET
    @Timed
    @Path("/{idDashboard}/users/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<User> getDashboardUsers(@PathParam("idDashboard") Integer idDashboard) {
        Dashboard dashboard  = dashboardServer.getDashboardById(idDashboard);
        Collection<User> users = dashboard.getUsers();
        return users;
    }

    @GET
    @Timed
    @Path("/{idDashboard}/results")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Result> getDashboardResults(@PathParam("idDashboard") Integer idDashboard) {
        Dashboard dashboard  = dashboardServer.getDashboardById(idDashboard);
        List<Result> res = dashboard.rulesResults();
        return res;
    }

    @POST
    @Timed
    @Path("/{idDashboard}/addProject")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonResponseBuilder addProject(@PathParam("idDashboard") Integer idDashboard, ProjectAPIInterface projectApi) {
        dashboardServer.addProjectToDashboard(projectApi.getId(),idDashboard);
        String code = "200";
        String message = "La consulta ha sido agregada con exito.";
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(code, message);
        return jsonResponseBuilder;
    }

}
