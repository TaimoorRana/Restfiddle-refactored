package com.restfiddle.util.apicreator;

import com.restfiddle.controller.rest.ConversationController;
import com.restfiddle.controller.rest.NodeController;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.GenericEntity;
import java.util.ArrayList;

public class RestApiCreator extends ApiCreator {
  private ArrayList<ApiCreator> creators;
  public RestApiCreator(BaseNode entityNode, ConversationController conversationController, NodeController nodeController, String hostUri){
    super(entityNode, conversationController, nodeController, hostUri);
    creators = new ArrayList<ApiCreator>();
    ApiCreator apicreator = new ShowApiCreator(entityNode, conversationController, nodeController, hostUri);
    creators.add(apicreator);
    apicreator = new CreateApiCreator(entityNode, conversationController, nodeController, hostUri);
    creators.add(apicreator);
    apicreator = new UpdateApiCreator(entityNode, conversationController, nodeController, hostUri);
    creators.add(apicreator);
    apicreator = new DeleteApiCreator(entityNode, conversationController, nodeController, hostUri);
    creators.add(apicreator);
    apicreator = new IndexApiCreator(entityNode, conversationController, nodeController, hostUri);
    creators.add(apicreator);
  }
  public NodeDTO create(){
    NodeDTO node = null;
    for (ApiCreator creator : creators){
      node = creator.create();
    }
    return node;
  }
}