package com.fibersim.fiberSimulationServer.resources.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fibersim.fiberSimulationServer.resources.resource.LambdaFunctionResource;
import com.fibersim.fiberSimulationServer.resources.resource.PowerSourceResource;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class PowerSourceResourceDeserializer extends JsonDeserializer<PowerSourceResource> {
    @Override
    public PowerSourceResource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        LambdaFunctionResource irradiance = treeNode.get("irradiance").traverse(jsonParser.getCodec()).readValueAs(LambdaFunctionResource.class);

        return PowerSourceResource.builder()
                .source(((TextNode)treeNode.get("source")).asText())
                .irradiance(irradiance)
                .build();
    }
}
