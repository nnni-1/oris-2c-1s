package ru.alexeev.javafxchat.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.Map;

public class Message {

    private final Code code;
    @JsonIgnore
    private int parametersLength;
    private final Map<String, String> parameters;

    @JsonCreator
    public Message(@JsonProperty("code") Code code,
                   @JsonProperty("parameters") Map<String, String> parameters) {
        this.code = code;
        this.parameters = parameters;
        parametersLength = parameters.size();
    }

    public Code getCode() {
        return code;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters.clear();
        this.parameters.putAll(parameters);
        parametersLength = parameters.size();
    }

    public int getParametersLength() {
        return parametersLength;
    }

    @Override
    public String toString(){
        return "[Code: %s, Parameters: %s]".formatted(code.toString(), parameters.toString());
    }
}
