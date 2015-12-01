package com.restfiddle.util.apicreator;

import com.restfiddle.controller.rest.ConversationController;
import com.restfiddle.controller.rest.NodeController;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.GenericEntity;

import org.json.JSONObject;

public class LoginApiCreator extends ApiCreator {
  public LoginApiCreator(BaseNode entityNode, ConversationController conversationController, NodeController nodeController, String hostUri){
    super(entityNode, conversationController, nodeController, hostUri);
  }
  public NodeDTO create(){
    String projectId = entityNode.getProjectId();

    // API to GENERATE >> Login Entity
    ConversationDTO conversationDTO = new ConversationDTO();
    RfRequestDTO rfRequestDTO = new RfRequestDTO();
    rfRequestDTO.setApiUrl(hostUri + "/api/" + projectId + "/entities/login");
    rfRequestDTO.setMethodType("POST");

    JSONObject json = new JSONObject();
    json.put("username", "");
    json.put("password", "");

    rfRequestDTO.setApiBody(json.toString(4));
    conversationDTO.setRfRequestDTO(rfRequestDTO);

    ConversationDTO createdConversation = conversationController.create(conversationDTO);
    conversationDTO.setId(createdConversation.getId());

    String nodeName = "Login " + entityNode.getName();
    NodeDTO createdNode = new NodeBuilder(nodeName)
      .setProjectId(projectId)
      .setConversationDTO(conversationDTO)
      .build();
    return createdNode;
  }
}