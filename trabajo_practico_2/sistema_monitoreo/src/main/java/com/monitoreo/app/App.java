package com.monitoreo.app;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.monitoreo.app.resources.AppResource;
import com.monitoreo.app.resources.MonitoreoResource;
import com.monitoreo.app.health.TemplateHealthCheck;

public class App extends Application<AppConfiguration> {
    public static void main(String[] args) throws Exception {
        new App().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        // nothing to do yet
    }

    @Override
	public void run(AppConfiguration configuration,
					Environment environment) {
		final AppResource resource = new AppResource(
			configuration.getTemplate(),
			configuration.getDefaultName()
		);
		final MonitoreoResource monitoreoresource = new MonitoreoResource(
			configuration.getTemplate(),
			"Monitoreo"
		);
		final TemplateHealthCheck healthCheck =
			new TemplateHealthCheck(configuration.getTemplate());
		environment.healthChecks().register("template", healthCheck);
		environment.jersey().register(resource);
		environment.jersey().register(monitoreoresource);
	}

}

