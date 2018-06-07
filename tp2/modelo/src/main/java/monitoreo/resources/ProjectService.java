package monitoreo.resources;

import com.codahale.metrics.annotation.Timed;
import monitoreo.data.tickets.*;
import monitoreo.data.tickets.users.User;
import monitoreo.data.tickets.users.TicketUserRegisterServer;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.*;

@Path("/tickets/projects")
public class ProjectService {
    private ProjectServer projectServer;
    private TicketUserRegisterServer usersServer;

    public ProjectService(ProjectServer projectServer, TicketUserRegisterServer usersServer) {
        this.projectServer = projectServer;
        this.usersServer = usersServer;
    }

    @POST
    @Timed
    @Path("/create")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public Project createProject(Project project) {
        Project projectCreated = projectServer.createProject(project);
        return projectCreated;
    }

    @POST
    @Timed
    @Path("/{idProject}/addUser")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonResponseBuilder addUser(@PathParam("idProject") Integer idProject, User jsonUser) {
        Project project = projectServer.getProjectById(idProject);
        String userName = jsonUser.getUserName();
        User user = usersServer.getUser(userName);
        String response = project.addUserToProject(user);
        String code = "200";
        String message = "Se agrego al usuario con exito.";
        if (!response.equals("")) {
            code = "500";
            message = response;
        }
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(code, message);
        return jsonResponseBuilder;
    }

    @POST
    @Timed
    @Path("/{idProject}/addTicket")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonResponseBuilder addTicket(@PathParam("idProject") Integer idProject, Ticket ticket) {
        Project project = projectServer.getProjectById(idProject);
        String response = project.addTicket(ticket);
        String code = "200";
        String message = "Se agrego al ticket con exito.";
        if (!response.equals("")) {
            code = "500";
            message = response;
        }
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(code, message);
        return jsonResponseBuilder;
    }


    @GET
    @Timed
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Project getProjectById(@PathParam("id") Integer id) {
        return projectServer.getProjectById(id);
    }

    @POST
    @Timed
    @Path("/{idProject}/take/{idTicket}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonResponseBuilder takeTicketByUser(@PathParam("idProject") Integer idProject, @PathParam("idTicket") Integer idTicket, User jsonUser) {
        String userName = jsonUser.getUserName();
        Project project = projectServer.getProjectById(idProject);
        String response = project.takeTicket(idTicket,userName);
        String code = "200";
        String message = "Se asigno el ticket con exito.";
        if (!response.equals("")) {
            code = "500";
            message = response;
        }
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(code, message);
        return jsonResponseBuilder;
    }

    @POST
    @Timed
    @Path("/{idProject}/ticket/{idTicket}/addComment")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonResponseBuilder addComment(@PathParam("idProject") Integer idProject,@PathParam("idTicket") Integer idTicket, Comment comment) {
        Project project = projectServer.getProjectById(idProject);
        Ticket ticket = project.getTicket(idTicket);
        ticket.addComment(comment);
        String code = "200";
        String message = "Comment a sido agregado con exito.";
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(code, message);
        return jsonResponseBuilder;
    }

    @POST
    @Timed
    @Path("/{idProject}/addTicketType")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonResponseBuilder addTicketType(@PathParam("idProject") Integer idProject, TicketType ticketType) {
        Project project = projectServer.getProjectById(idProject);
        String response = project.addTicketType(ticketType);
        String code = "200";
        String message = "Se agrego al tipo de ticket con exito.";

        if (!response.equals("")) {
            code = "500";
            message = response;
        }
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(code, message);
        return jsonResponseBuilder;
    }

    @POST
    @Timed
    @Path("/{idProject}/endState/{idTicket}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    public JsonResponseBuilder saveTicket(@PathParam("idProject") Integer idProject, @PathParam("idTicket") Integer idTicket, User jsonUser) {
        String userName = jsonUser.getUserName();
        Project project = projectServer.getProjectById(idProject);
        String response = project.endTicketState(idTicket,userName);
        String  code = "200";
        String message = "Se finalizo el estado del ticket con exito.";
        if (!response.equals("")) {
            code = "500";
            message = response;
        }
        JsonResponseBuilder jsonResponseBuilder = new JsonResponseBuilder(code, message);
        return jsonResponseBuilder;
    }

    @GET
    @Timed
    @Path("/{idProject}/ticketsCreated/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Ticket> getProjectTicketsCreatedByUserId(@PathParam("idProject") Integer idProject, @PathParam("userName") String userName) {
        Project project = projectServer.getProjectById(idProject);
        Set<Ticket> tickets = new HashSet<Ticket>();
        if (!project.hasUser(userName)) return tickets;
        Collection<Ticket> ticketsProject = project.projectTickets();
        for (Ticket ticket : ticketsProject) {
            if (ticket.getAuthorUserName().equals(userName))tickets.add(ticket);
        }
        return tickets;
    }

    @GET
    @Timed
    @Path("/{idProject}/ticketsTaken/{userName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Set<Ticket> getProjectTicketsTakenByUserId(@PathParam("idProject") Integer idProject, @PathParam("userName") String userName) {
        Project project = projectServer.getProjectById(idProject);
        Set<Ticket> tickets = new HashSet<Ticket>();
        Collection<Ticket> ticketsProject = project.projectTickets();
        for (Ticket ticket : ticketsProject) {
            if (ticket.getTakenUserName().equals(userName))tickets.add(ticket);
        }
        return tickets;
    }

    @GET
    @Timed
    @Path("/{idProject}/tickets/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Ticket> getProjectTickets(@PathParam("idProject") Integer idProject) {
        Project project = projectServer.getProjectById(idProject);
        Collection<Ticket> ticketsProject = project.projectTickets();
        return ticketsProject;
    }

    @GET
    @Timed
    @Path("/{idProject}/tickets/free")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Ticket> getProjectTicketsFree(@PathParam("idProject") Integer idProject) {
        Project project = projectServer.getProjectById(idProject);
        Collection<Ticket> ticketsProject = project.projectFreeTickets();
        return ticketsProject;
    }
}
