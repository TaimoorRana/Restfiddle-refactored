package com.restfiddle.util.apicreator;

import java.util.Date;
import java.util.List;

import com.restfiddle.controller.rest.ConversationController;
import com.restfiddle.controller.rest.NodeController;

import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.RfRequestDTO;

import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.GenericEntity;
import com.restfiddle.entity.GenericEntityField;
import org.json.JSONArray;
import org.json.JSONObject;


public abstract class ApiCreator{
  BaseNode entityNode;

  ConversationController conversationController;
  NodeController nodeController;

  GenericEntity genericEntity;
  String hostUri;

  public ApiCreator(BaseNode entityNode, ConversationController conversationController, NodeController nodeController, String hostUri){
    this.entityNode = entityNode;
    this.conversationController = conversationController;
    this.nodeController = nodeController;
    this.genericEntity = entityNode.getGenericEntity();
    this.hostUri = hostUri;
  }

  public abstract NodeDTO create();
  public class NodeBuilder {
    private NodeDTO newNode;

    public NodeBuilder(String nodeName){
      newNode = new NodeDTO();
      newNode.setName(nodeName);
    }
    public NodeBuilder setProjectId(String projectId){
      newNode.setProjectId(projectId);
      return this;
    }
    public NodeBuilder setConversationDTO(ConversationDTO conversationDTO){
      newNode.setConversationDTO(conversationDTO);
      return this;
    }
    public NodeDTO build(){
      NodeDTO createdNode = nodeController.create(entityNode.getId(), newNode);
      return createdNode;
    }
  }

  public JSONObject getFieldJSON(GenericEntity genericEntity) {
    // Create JSON template for the entity data based on fields and set it as api body.
    List<GenericEntityField> fields = genericEntity.getFields();
    JSONObject jsonObject = new JSONObject();
    for (GenericEntityField genericEntityField : fields) {
      String type = genericEntityField.getType();
      if ("STRING".equalsIgnoreCase(type)) {
        jsonObject.put(genericEntityField.getName(), "");
      } else if ("NUMBER".equalsIgnoreCase(type)) {
        jsonObject.put(genericEntityField.getName(), Long.valueOf(1));
      } else if ("BOOLEAN".equalsIgnoreCase(type)) {
        jsonObject.put(genericEntityField.getName(), false);
      } else if ("DATE".equalsIgnoreCase(type)) {
        jsonObject.put(genericEntityField.getName(), new Date());
      } else if ("NUMBER".equalsIgnoreCase(type)) {
        jsonObject.put(genericEntityField.getName(), Long.valueOf(1));
      } else if ("OBJECT".equalsIgnoreCase(type)) {
        jsonObject.put(genericEntityField.getName(), new JSONObject());
      } else if ("ARRAY".equalsIgnoreCase(type)) {
        jsonObject.put(genericEntityField.getName(), new JSONArray());
      } else if ("Geographic point".equalsIgnoreCase(type)) {
        jsonObject.put(genericEntityField.getName(), new JSONObject("{\"lat\" : 18.5204303,\"lng\" : 73.8567437}"));
      } else if ("relation".equalsIgnoreCase(type)) {
        jsonObject.put(genericEntityField.getName(), new JSONObject("{\"_rel\":{\"entity\" : \"{Entity Name}\",\"_id\" : \"{Entity _id}\"}}"));
      }
    }
    return jsonObject;
  }
}