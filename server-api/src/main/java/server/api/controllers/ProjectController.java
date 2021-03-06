package server.api.controllers;

import com.purbon.kafka.topology.model.Project;
import com.purbon.kafka.topology.model.Topology;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.Post;
import java.util.List;
import javax.inject.Inject;
import server.api.services.TopologyService;

@Controller( value = "/topologies/{team}/projects")
public class ProjectController {

  @Value("${micronaut.application.topology}")
  protected String topology;

  @Inject
  private TopologyService service;

  @Get(processes = MediaType.APPLICATION_JSON)
  public HttpResponse indexProject(@PathVariable String team) throws Throwable {
    List<Topology> all = service.all();
    return HttpResponse.ok().body(all);
  }

  @Post(uri = "/{name}", processes = MediaType.APPLICATION_JSON)
  public HttpResponse createProject(@PathVariable String team, @PathVariable String name) {

    Topology topology = service.findByTeam(team);

    Project project = new Project();
    project.setName(name);
    topology.addProject(project);

    service.update(topology);

    return HttpResponse.ok().body(topology);
  }

}
