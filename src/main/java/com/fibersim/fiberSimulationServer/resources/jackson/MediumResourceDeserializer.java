package com.fibersim.fiberSimulationServer.resources.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fibersim.fiberSimulationServer.resources.resource.LambdaFunctionResource;
import com.fibersim.fiberSimulationServer.resources.resource.MediumResource;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class MediumResourceDeserializer extends JsonDeserializer<MediumResource> {
    @Override
    public MediumResource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        LambdaFunctionResource refractionIndex = treeNode.get("refractionIndex").traverse(jsonParser.getCodec()).readValueAs(LambdaFunctionResource.class);
        LambdaFunctionResource attenuation = treeNode.get("attenuation").traverse(jsonParser.getCodec()).readValueAs(LambdaFunctionResource.class);

        return MediumResource.builder()
                .medium(((TextNode)treeNode.get("medium")).asText())
                .refractionIndex(refractionIndex)
                .attenuation(attenuation)
                .build();
    }
}
