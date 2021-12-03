package com.fibersim.fiberSimulationServer.resources.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fibersim.fiberSimulationServer.resources.LambdaConstantResource;

import java.io.IOException;

public class LambdaConstantResourceDeserializer extends JsonDeserializer<LambdaConstantResource> {
    @Override
    public LambdaConstantResource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        return null;
    }
}
