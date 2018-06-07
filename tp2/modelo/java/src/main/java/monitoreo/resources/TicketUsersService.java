package monitoreo.resources;

import com.codahale.metrics.annotation.Timed;

import monitoreo.data.tickets.Project;
import monitoreo.data.tickets.ProjectServer;
import monitoreo.data.tickets.users.TicketAuthorizationServer;
import monitoreo.data.tickets.users.TicketUserRegisterServer;
import monitoreo.data.tickets.users.User;

import javax.ws.rs.*;

import javax.ws.rs.core.MediaType;
import java.util.HashSet;
import java.util.Set;

@Path("/tickets/users")
public class TicketUsersService {

    private ProjectServer projectServer;
    private TicketAuthorizationServer authServer;
    private TicketUserRegisterServer usersServer;

    public TicketUsersService(TicketAuthorizationServer authServer, TicketUserRegisterServer usersServer, ProjectServer projectServer) {
        this.authServer = authServer;
        this.usersServer = usersServer;
        this.projectServer = projectServer;
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
        String message = "Error: Nombre de usuario existente. Intente con otro nombre de usuario.";
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
        String message = "Error: Nombre de usuario y contraseña no validos.";
        if (authResponse) {
            code = "200";
            message = "Usuario inicio sesion correctamente.";

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
    @Path("/{userName}/projects")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<ProjectAPIInterface> getProjectsByUserId(@PathParam("userName") String userName) {
        Set<Project> projects = usersServer.getUser(userName).getProjects();
        Set<ProjectAPIInterface> projectsList = new HashSet<ProjectAPIInterface>();
        for (Project project : projects) {
            ProjectAPIInterface pi = new ProjectAPIInterface(project.getId(),project.getName());
            projectsList.add(pi);
        }
        return projectsList;
    }

}
