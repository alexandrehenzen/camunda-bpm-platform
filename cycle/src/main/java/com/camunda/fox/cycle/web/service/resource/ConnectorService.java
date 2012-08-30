package com.camunda.fox.cycle.web.service.resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.camunda.fox.cycle.api.connector.Connector;
import com.camunda.fox.cycle.api.connector.ConnectorNode;
import com.camunda.fox.cycle.connector.ConnectorRegistry;
import com.camunda.fox.cycle.web.dto.ConnectorDTO;
import com.camunda.fox.cycle.web.dto.ConnectorNodeDTO;

@Path("secured/resource/connector")
public class ConnectorService {
  
  @Inject
  protected ConnectorRegistry connectorRegistry;
  
  @GET
  @Path("/list")
  @Produces("application/json")
  public List<ConnectorDTO> list() {
    ArrayList<ConnectorDTO> result = new ArrayList<ConnectorDTO>();
    for (Connector c : connectorRegistry.getSessionConnectors()) {
      result.add(new ConnectorDTO(c));
    }
    return result;
  }
  
  @GET
  @Path("{id}/tree/root")
  @Produces("application/json")
  public List<ConnectorNodeDTO> root(@PathParam("id") Long connectorId) {
    Connector connector = connectorRegistry.getSessionConnectorMap().get(connectorId);
    List<ConnectorNode> rootList = new ArrayList<ConnectorNode>();
    rootList.add(connector.getRoot());
    return ConnectorNodeDTO.wrapAll(rootList);
  }
  
  @POST
  @Path("{id}/tree/children")
  @Produces("application/json")
  public List<ConnectorNodeDTO> children(@PathParam("id") Long connectorId, @FormParam("parent") String parent) {
    Connector connector = connectorRegistry.getSessionConnectorMap().get(connectorId);
    return ConnectorNodeDTO.wrapAll(connector.getChildren(new ConnectorNode(parent)));
  }
  
}
