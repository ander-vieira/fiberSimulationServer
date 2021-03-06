package com.fibersim.fiberSimulationServer.resources.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fibersim.fiberSimulationServer.resources.resource.LambdaConstantResource;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class LambdaConstantResourceDeserializer extends JsonDeserializer<LambdaConstantResource> {
    @Override
    public LambdaConstantResource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        return LambdaConstantResource.builder()
                .value(((DoubleNode)treeNode.get("value")).asDouble())
                .build();
    }
}
