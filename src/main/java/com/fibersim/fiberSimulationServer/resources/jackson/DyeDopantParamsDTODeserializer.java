package com.fibersim.fiberSimulationServer.resources.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fibersim.fiberSimulationServer.resources.dto.CSVInterpolatorParamsDTO;
import com.fibersim.fiberSimulationServer.resources.dto.DyeDopantParamsDTO;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class DyeDopantParamsDTODeserializer extends JsonDeserializer<DyeDopantParamsDTO> {
    @Override
    public DyeDopantParamsDTO deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        return DyeDopantParamsDTO.builder()
                .dopant(((TextNode)treeNode.get("dopant")).asText())
                .tauRad(((DoubleNode)treeNode.get("tauRad")).asDouble())
                .tauNR(((DoubleNode)treeNode.get("tauNR")).asDouble())
                .minLambda(((DoubleNode)treeNode.get("minLambda")).asDouble())
                .maxLambda(((DoubleNode)treeNode.get("maxLambda")).asDouble())
                .sigmaabs(treeNode.get("sigmaabs").traverse(jsonParser.getCodec()).readValueAs(CSVInterpolatorParamsDTO.class))
                .sigmaemi(treeNode.get("sigmaemi").traverse(jsonParser.getCodec()).readValueAs(CSVInterpolatorParamsDTO.class))
                .build();
    }
}
