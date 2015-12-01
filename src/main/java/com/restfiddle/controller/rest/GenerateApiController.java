/*
 * Copyright 2015 Ranjan Kumar
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.restfiddle.controller.rest;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.restfiddle.dao.GenericEntityDataRepository;
import com.restfiddle.dao.GenericEntityRepository;
import com.restfiddle.dto.ConversationDTO;
import com.restfiddle.dto.NodeDTO;
import com.restfiddle.dto.RfRequestDTO;
import com.restfiddle.dto.StatusResponse;
import com.restfiddle.entity.BaseNode;
import com.restfiddle.entity.GenericEntity;
import com.restfiddle.entity.GenericEntityField;
import com.restfiddle.util.apicreator.*;
@RestController
@Transactional
public class GenerateApiController {
    Logger logger = LoggerFactory.getLogger(GenerateApiController.class);
    
    @Value("${application.host-uri}")
    private String hostUri;
    
    @Autowired
    private NodeController nodeController;

    @Autowired
    private ConversationController conversationController;

    @Autowired
    private GenericEntityRepository genericEntityRepository;

    @Autowired
    private GenericEntityDataRepository genericEntityDataRepository;

    @RequestMapping(value = "/api/entities/{id}/generate-api", method = RequestMethod.POST)
    public @ResponseBody
    StatusResponse generateApiByEntityId(@PathVariable("id") String id) {
	logger.debug("Generating APIs for entity with id: " + id);

	GenericEntity entity = genericEntityRepository.findOne(id);

	String baseNodeId = entity.getBaseNodeId();
	BaseNode entityNode = nodeController.findById(baseNodeId);

	return generateApi(entityNode);
    }

  public StatusResponse generateApi(BaseNode entityNode) {

    ApiCreator creator = new RestApiCreator(entityNode, conversationController, nodeController, hostUri);
    creator.create();

  	if(entityNode.getName().equals("User")){
      creator = new LoginApiCreator(entityNode, conversationController, nodeController, hostUri);
      creator.create();
      creator = new LogoutApiCreator(entityNode, conversationController, nodeController, hostUri);
      creator.create();

  	}

	  return null;
  }

    private NodeDTO createNode(String nodeName, String parentId, String projectId, ConversationDTO conversationDTO) {
	NodeDTO childNode = new NodeDTO();
	childNode.setName(nodeName);
	childNode.setProjectId(projectId);
	childNode.setConversationDTO(conversationDTO);
	NodeDTO createdNode = nodeController.create(parentId, childNode);
	return createdNode;
    }
}
