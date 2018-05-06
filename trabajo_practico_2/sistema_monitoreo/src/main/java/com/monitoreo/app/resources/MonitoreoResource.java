package com.monitoreo.app.resources;

import com.monitoreo.app.core.MonitoreoSaying;
import com.google.common.base.Optional;
import com.codahale.metrics.annotation.Timed;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.atomic.AtomicLong;

@Path("/monitoreo")
@Produces(MediaType.APPLICATION_JSON)
public class MonitoreoResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public MonitoreoResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @GET
    @Timed
    public MonitoreoSaying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.or(defaultName));
        return new MonitoreoSaying(counter.incrementAndGet(), value);
    }
}
