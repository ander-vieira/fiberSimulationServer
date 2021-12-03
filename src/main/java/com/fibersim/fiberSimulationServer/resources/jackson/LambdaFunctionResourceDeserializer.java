package com.fibersim.fiberSimulationServer.resources.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fibersim.fiberSimulationServer.resources.LambdaCsvResource;
import com.fibersim.fiberSimulationServer.resources.LambdaConstantResource;
import com.fibersim.fiberSimulationServer.resources.LambdaFunctionResource;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class LambdaFunctionResourceDeserializer extends JsonDeserializer<LambdaFunctionResource> {
    @Override
    public LambdaFunctionResource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        String type = ((TextNode)treeNode.get("type")).asText();

        if(type.equalsIgnoreCase("constant")) {
            return treeNode.traverse(jsonParser.getCodec()).readValueAs(LambdaConstantResource.class);
        } else if(type.equalsIgnoreCase("csv")) {
            return treeNode.traverse(jsonParser.getCodec()).readValueAs(LambdaCsvResource.class);
        } else {
            return null;
        }
    }
}
