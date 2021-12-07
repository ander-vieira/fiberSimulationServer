package com.fibersim.fiberSimulationServer.resources.resource;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fibersim.fiberSimulationServer.core.util.LambdaFunction;
import com.fibersim.fiberSimulationServer.resources.jackson.LambdaFunctionResourceDeserializer;

@JsonDeserialize(using = LambdaFunctionResourceDeserializer.class)
public abstract class LambdaFunctionResource extends LambdaFunction { }
