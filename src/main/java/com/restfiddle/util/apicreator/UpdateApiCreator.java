package com.restfiddle.util.apicreator;

import com.restfiddle.controller.rest.ConversationController;
import com.restfiddle.controller.rest.NodeController;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.GenericEntity;

import org.json.JSONObject;


public class UpdateApiCreator extends ApiCreator {
  public UpdateApiCreator(BaseNode entityNode, ConversationController conversationController, NodeController nodeController, String hostUri){
    super(entityNode, conversationController, nodeController, hostUri);
  }
  public NodeDTO create(){
    String projectId = entityNode.getProjectId();
    // API to GENERATE >> Update Entity Data
    ConversationDTO conversationDTO = new ConversationDTO();
    RfRequestDTO rfRequestDTO = new RfRequestDTO();
    rfRequestDTO.setApiUrl(hostUri + "/api/" + projectId + "/entities/" + entityNode.getName() + "/{uuid}");
    rfRequestDTO.setMethodType("PUT");

    JSONObject jsonObject = getFieldJSON(genericEntity);
    // id is required in case of update.
    jsonObject.put("_id", "{uuid}");

    rfRequestDTO.setApiBody(jsonObject.toString(4));
    conversationDTO.setRfRequestDTO(rfRequestDTO);

    ConversationDTO createdConversation = conversationController.create(conversationDTO);
    conversationDTO.setId(createdConversation.getId());

    String nodeName = "Update " + entityNode.getName();
    NodeDTO createdNode = new NodeBuilder(nodeName)
      .setProjectId(projectId)
      .setConversationDTO(conversationDTO)
      .build();
    return createdNode;
  }
}