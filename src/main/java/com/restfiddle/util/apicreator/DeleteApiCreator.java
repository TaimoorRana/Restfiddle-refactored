package com.restfiddle.util.apicreator;

import com.restfiddle.controller.rest.ConversationController;
import com.restfiddle.controller.rest.NodeController;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.GenericEntity;

public class DeleteApiCreator extends ApiCreator {
  public DeleteApiCreator(BaseNode entityNode, ConversationController conversationController, NodeController nodeController, String hostUri){
    super(entityNode, conversationController, nodeController, hostUri);
  }
  public NodeDTO create(){
    String projectId = entityNode.getProjectId();

    // API to GENERATE >> Delete Entity Data By Id
    ConversationDTO conversationDTO = new ConversationDTO();
    RfRequestDTO rfRequestDTO = new RfRequestDTO();
    rfRequestDTO.setApiUrl(hostUri + "/api/" + projectId + "/entities/" + entityNode.getName() + "/{uuid}");
    rfRequestDTO.setMethodType("DELETE");
    conversationDTO.setRfRequestDTO(rfRequestDTO);

    ConversationDTO createdConversation = conversationController.create(conversationDTO);
    conversationDTO.setId(createdConversation.getId());

    String nodeName = "Delete " + entityNode.getName();
    NodeDTO createdNode = new NodeBuilder(nodeName)
      .setProjectId(projectId)
      .setConversationDTO(conversationDTO)
      .build();
    return createdNode;
  }
}