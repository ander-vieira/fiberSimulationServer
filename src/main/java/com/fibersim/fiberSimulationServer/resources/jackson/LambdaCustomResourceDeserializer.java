package com.fibersim.fiberSimulationServer.resources.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.TextNode;
import com.fibersim.fiberSimulationServer.resources.LambdaCustomResource;
import org.springframework.boot.jackson.JsonComponent;

import javax.annotation.PostConstruct;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;

@JsonComponent
public class LambdaCustomResourceDeserializer extends JsonDeserializer<LambdaCustomResource> {
    ScriptEngineManager scriptEngineManager;

    @PostConstruct
    public void setupScriptEngineManager() {
        scriptEngineManager = new ScriptEngineManager();
    }

    @Override
    public LambdaCustomResource deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        TreeNode treeNode = jsonParser.getCodec().readTree(jsonParser);

        String expression = treeNode.get("expression") != null ? ((TextNode)treeNode.get("expression")).asText() : "0";
        ScriptEngine scriptEngine = new ScriptEngineManager().getEngineByName("js");

        return LambdaCustomResource.builder()
                .expression(expression)
                .scriptEngine(scriptEngine)
                .build();
    }
}
