package monitoreo.data.tickets.users;


public class TicketAuthorizationServer {
    private TicketUserRegisterServer userRegisterServer;

    public TicketAuthorizationServer(TicketUserRegisterServer userRegisterServer) {
        this.userRegisterServer = userRegisterServer;
    }

    public Boolean auth(String entryUserName, String entryUserPassword) {
        if (!userRegisterServer.existsUser(entryUserName)) {
            return false;
        }

        String password = userRegisterServer.getUserPassword(entryUserName);
        return (password.equals(entryUserPassword));
    }
}
