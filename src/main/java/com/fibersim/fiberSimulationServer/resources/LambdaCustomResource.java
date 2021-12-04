package com.fibersim.fiberSimulationServer.resources;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.resources.jackson.LambdaCustomResourceDeserializer;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

@Getter
@Setter
@Builder
@JsonDeserialize(using = LambdaCustomResourceDeserializer.class)
public class LambdaCustomResource extends LambdaFunctionResource {
    private String expression;
    private ScriptEngine scriptEngine;

    @Override
    public double eval(double lambda) {
        double value = 0;

        try {
            value = (double)scriptEngine.eval(expression);
        } catch(ScriptException e) {
            e.printStackTrace();
        }

        return value;
    }
}
