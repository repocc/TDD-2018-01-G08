package monitoreo.data.tickets;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

import monitoreo.data.tickets.users.User;

public class Project {
    private static final int MIN_ESTADOS = 2;
    private static final int MIN_REQ_FIELDS = 1;
    private static Integer ticketId = 0;
    private Integer id;
    private String name;
    private String authUserName;
    private String createdDate;
    private HashMap<Integer, Ticket> tickets = new HashMap<Integer, Ticket>();
    private HashMap<String, User> users = new HashMap<String, User>();
    private HashMap<String, TicketType> ticketsType = new HashMap<String, TicketType>();

    public Project() {
        // Needed by Jackson deserialization
    }

    public Project(Integer id, String name, String authUserName, String createdDate) {
        this.id = id;
        this.name = name;
        this.authUserName = authUserName;
        this.createdDate = createdDate;
    }

    @JsonProperty
    public Integer getId() {
        return this.id;
    }

    @JsonProperty
    public String getName() {
        return this.name;
    }

    @JsonProperty
    public String getauthUserName() {
        return this.authUserName;
    }

    @JsonProperty
    public String getCreatedDate() {
        return this.createdDate;
    }
   
    @JsonProperty
    public Set<String> getUsers() {
        return this.users.keySet();
    }

    @JsonProperty
    public HashMap<Integer,Ticket> getTickets() {
        return this.tickets;
    }

    @JsonProperty
    public HashMap<String, TicketType> getTicketsType() {
        return this.ticketsType;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String addUserToProject(User user) {
        String message = "";
        String userName = user.getUserName();
        if (existsUser(userName)) {
            message = "Error: El usuario que esta intentando agregar forma parte del proyecto";
            return message;
        }
        users.put(userName, user);
        user.addProject(this);
        return message;
    }

    private boolean existsUser(String userName) {
        return users.containsKey(userName);
    }

    public String addTicket(Ticket ticket) {
        String message = "";
        String authorUserName = ticket.getAuthorUserName();
        String ticketTypeName = ticket.getType();
        TicketType ticketType = ticketsType.get(ticketTypeName);
        String firstState = ticketType.getFirstState();
        copyRequiredFields(ticket, ticketType.getRequiredFields());
        copyOptionalFields(ticket, ticketType.getRequiredFields());
        User user = users.get(authorUserName);
        ticket.setId(this.ticketId);
        ticket.setState(firstState);
        tickets.put(this.ticketId,ticket);
        this.ticketId++;
        return message;
    }

    private void copyRequiredFields(Ticket ticket, List<Field> requiredFields) {
        for (Field field : requiredFields) {
            String name = field.getName();
            String type = field.getType();
            Field newField = new Field(name,"",type);
            ticket.addRequiredField(newField);
        }
    }

    private void copyOptionalFields(Ticket ticket, List<Field> optionalFields) {
        for (Field field : optionalFields) {
            String name = field.getName();
            String type = field.getType();
            Field newField = new Field(name,"",type);
            ticket.addOptionalField(newField);
        }
    }

    public String addTicketType(TicketType ticket) {
        String message = "";
        String authorUserName = ticket.getAuthUserName();
        message = checkTicketType(ticket);
        if (!message.equals("")) return message;
        String ticketType = ticket.getType();
        ticketsType.put(ticketType,ticket);
        return message;
    }

    private String checkTicketType(TicketType ticket) {
        String message = "";
        String ticketType = ticket.getType();

        if (ticketsType.containsKey(ticketType)){
            message = "Error: El tipo de ticket ya existe en el proyecto";
            return message;
        }

        if (ticket.getStates().size() < MIN_ESTADOS){
            message = "Error: El tipo de ticket debe contar con al menos "+MIN_ESTADOS+" estados.";
            return message;
        }

        if (ticket.getRequiredFields().size() < MIN_REQ_FIELDS){
            message = "Error: El tipo de ticket debe contar con al menos "+MIN_REQ_FIELDS+" campo obligatorio";
            return message;
        }
        return message;
    }

    public String takeTicket(Integer ticketId, String userName) {
        String message = "";
        Ticket ticket = tickets.get(ticketId);
        String takenUserName = ticket.getTakenUserName();
        User user = users.get(userName);
        ticket.setTakenUserName(userName);
        return message;
    }

    public Ticket getTicket(Integer idTicket) {
        return this.tickets.get(idTicket);
    }

    public String endTicketState(Integer idTicket, String userName) {
        String message = "";
        Ticket ticket = getTicket(idTicket);
        String takenUserName = ticket.getTakenUserName();
        String ticketTypeName = ticket.getType();
        TicketType ticketType = ticketsType.get(ticketTypeName);
        String ticketState = ticket.getState();
        String nextState = ticketType.getNextState(ticketState);
        ticket.setState(nextState);
        ticket.setTakenUserName("");
        return message;
    }

    public boolean hasUser(String userName) {
        return users.containsKey(userName);
    }

    public Collection<Ticket> projectTickets() {
        return tickets.values();
    }

    public Collection<Ticket> projectFreeTickets() {
        Collection<Ticket> ticketsFree = new HashSet<Ticket>();
        Collection<Ticket> ticketsList = tickets.values();
        for (Ticket ticket : ticketsList) {
            String ticketTypeName = ticket.getType();
            TicketType ticketType = ticketsType.get(ticketTypeName);
            String ticketState = ticket.getState();
            String nextState = ticketType.getNextState(ticketState);
            if(ticket.isAvailable() && nextState != ticketState) {
                ticketsFree.add(ticket);
            }
        }
        return ticketsFree;
    }
}
