package com.fibersim.fiberSimulationServer.resources.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.LambdaCustomResourceDeserializer;
import lombok.Builder;
import lombok.Getter;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

@Getter
@Builder
@JsonDeserialize(using = LambdaCustomResourceDeserializer.class)
public class LambdaCustomResource extends LambdaFunctionResource {
    private final String expression;
    private final ScriptEngine scriptEngine;

    @Override
    public double eval(double lambda) {
        double value = 0;

        try {
            scriptEngine.put("lambda", lambda);
            value = (double)scriptEngine.eval(expression);
        } catch(ScriptException e) {
            e.printStackTrace();
        }

        return value;
    }
}
