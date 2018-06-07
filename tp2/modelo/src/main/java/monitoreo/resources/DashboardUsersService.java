package monitoreo.resources;

import com.codahale.metrics.annotation.Timed;
import monitoreo.data.dashboards.Dashboard;
import monitoreo.data.dashboards.users.DashboardAuthorizationServer;
import monitoreo.data.dashboards.users.DashboardUserRegisterServer;
import monitoreo.data.tickets.Project;
import monitoreo.data.tickets.users.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

@Path("/dashboards/users")
public class DashboardUsersService {
    private DashboardAuthorizationServer authServer;
    private DashboardUserRegisterServer usersServer;

    public DashboardUsersService(DashboardAuthorizationServer authServer, DashboardUserRegisterServer usersServer) {
        this.authServer = authServer;
        this.usersServer = usersServer;
    }

    @POST
    @Timed
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonResponseBuilder addUser(User user) {
        String entryUserName = user.getUserName();
        String entryUserPassword = user.getPassword();
        Boolean addResponse = usersServer.addUser(entryUserName, entryUserPassword);
        String code = "500";
        String message = "Error: Usuario existente. Intente con otro usuario.";
        if (addResponse) {
            code = "200";
            message = "Usuario creado con exito.";
        }
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(code, message);
        return jsonResponseBuilder;
    }

    @POST
    @Timed
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonResponseBuilder login(User user) {
        String entryUserName = user.getUserName();
        String entryUserPassword = user.getPassword();
        Boolean authResponse = authServer.auth(entryUserName, entryUserPassword);
        String code = "500";
        String message = "Error: Usuario y contraseña no validos.";
        if (authResponse) {
            code = "200";
            message = "Usuario inicio sesion exitosamente.";

        }
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(code, message);
        return jsonResponseBuilder;
    }

    @GET
    @Timed
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<String> getUsers() {
        return usersServer.getAllUsers();
    }

    @GET
    @Timed
    @Path("/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<DashboardAPIInterface> getDashboardsByUserId(@PathParam("userName") String userName) {
        Set<Dashboard> dashboards = usersServer.getUser(userName).getDashboards();
        Set<DashboardAPIInterface> dashboardsName = new HashSet<DashboardAPIInterface>();
        for (Dashboard dashboard : dashboards) {
            String dashboardName = dashboard.getName();
            Integer dashboardId = dashboard.getId();
            dashboardsName.add(new DashboardAPIInterface(dashboardId, dashboardName));
        }
        return dashboardsName;
    }
}
