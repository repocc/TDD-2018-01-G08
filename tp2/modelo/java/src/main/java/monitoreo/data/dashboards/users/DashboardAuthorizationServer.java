package monitoreo.data.dashboards.users;


public class DashboardAuthorizationServer {
    private DashboardUserRegisterServer userRegisterServer;

    public DashboardAuthorizationServer(DashboardUserRegisterServer userRegisterServer) {
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
