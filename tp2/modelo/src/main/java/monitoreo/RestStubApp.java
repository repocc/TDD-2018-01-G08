package monitoreo;

import monitoreo.data.dashboards.DashboardServer;
import monitoreo.data.dashboards.users.DashboardAuthorizationServer;
import monitoreo.data.dashboards.users.DashboardUserRegisterServer;
import monitoreo.data.tickets.ProjectServer;
import monitoreo.data.tickets.users.TicketAuthorizationServer;
import monitoreo.data.tickets.users.TicketUserRegisterServer;
import monitoreo.resources.*;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class RestStubApp extends Application<RestStubConfig>
{
    public static void main(String[] args) throws Exception {
       
        new RestStubApp().run(args);
    }

    @Override
    public void run(RestStubConfig config, Environment env) {
        TicketUserRegisterServer userRegisterServer = new TicketUserRegisterServer();
        TicketAuthorizationServer authServer = new TicketAuthorizationServer(userRegisterServer);
        ProjectServer projectServer = new ProjectServer();
        final TicketUsersService ticketUserService = new TicketUsersService(authServer, userRegisterServer, projectServer);
        env.jersey().register(ticketUserService);
        final ProjectService projectService = new ProjectService(projectServer, userRegisterServer);
        env.jersey().register(projectService);

        DashboardServer dashboardServer = new DashboardServer(projectServer);
        DashboardUserRegisterServer userDashboardRegisterServer = new DashboardUserRegisterServer();
        DashboardAuthorizationServer authDashboardServer = new DashboardAuthorizationServer(userDashboardRegisterServer);
        final DashboardUsersService dashboardUserService = new DashboardUsersService(authDashboardServer, userDashboardRegisterServer);
        env.jersey().register(dashboardUserService);
        final DashboardService dashboardService = new DashboardService(dashboardServer, userDashboardRegisterServer);
        env.jersey().register(dashboardService);

        env.healthChecks().register("template", new RestStubCheck(config.getVersion()));
    }
}
