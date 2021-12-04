package com.fibersim.fiberSimulationServer.resources.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fibersim.fiberSimulationServer.resources.DyeDopantResource;
import com.fibersim.fiberSimulationServer.resources.LambdaFunctionResource;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class DyeDopantResourceDeserializer extends JsonDeserializer<DyeDopantResource> {
    @Override
    public DyeDopantResource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        LambdaFunctionResource sigmaabs = treeNode.get("sigmaabs").traverse(jsonParser.getCodec()).readValueAs(LambdaFunctionResource.class);
        LambdaFunctionResource sigmaemi = treeNode.get("sigmaemi").traverse(jsonParser.getCodec()).readValueAs(LambdaFunctionResource.class);

        return DyeDopantResource.builder()
                .dopant(((TextNode)treeNode.get("dopant")).asText())
                .tauRad(((DoubleNode)treeNode.get("tauRad")).asDouble())
                .tauNR(((DoubleNode)treeNode.get("tauNR")).asDouble())
                .minLambda(((DoubleNode)treeNode.get("minLambda")).asDouble())
                .maxLambda(((DoubleNode)treeNode.get("maxLambda")).asDouble())
                .sigmaabs(sigmaabs)
                .sigmaemi(sigmaemi)
                .build();
    }
}
