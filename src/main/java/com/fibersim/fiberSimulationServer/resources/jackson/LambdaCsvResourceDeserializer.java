package com.fibersim.fiberSimulationServer.resources.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.DoubleNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fibersim.fiberSimulationServer.resources.resource.LambdaCsvResource;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;

@JsonComponent
public class LambdaCsvResourceDeserializer extends JsonDeserializer<LambdaCsvResource> {
    @Override
    public LambdaCsvResource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        int llColumn = treeNode.get("llColumn") != null ? ((IntNode)treeNode.get("llColumn")).asInt() : 0;
        int valueColumn = treeNode.get("valueColumn") != null ? ((IntNode)treeNode.get("valueColumn")).asInt() : 1;

        return LambdaCsvResource.builder()
                .filename(((TextNode)treeNode.get("filename")).asText())
                .peakLL(((DoubleNode)treeNode.get("peakLL")).asDouble())
                .peakValue(((DoubleNode)treeNode.get("peakValue")).asDouble())
                .llColumn(llColumn)
                .valueColumn(valueColumn)
                .build();
    }
}
