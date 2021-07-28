package com.siemens.sl.apigateway.utility;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.siemens.sl.apigateway.model.ErrorResponse;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * This class is used to parse "errors.json" file stored in resources and provide the its values using appropriate methods
 */
public class ErrorJsonParser {

    public static ErrorResponse getErrorResponse(String lng, int code, int subCode) {
        ErrorResponse errorResponse = new ErrorResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        //ResourceUtils.getFile("classpath:errors.json").exists();
        try {
            File errorsJson = ResourceUtils.getFile("classpath:errors.json");
            ArrayNode errorTree = (ArrayNode) objectMapper.readTree(errorsJson);

            JsonNode node = getErrorTreeForLanguage(errorTree, lng);
            if (node != null) {
                JsonNode errorNode = getErrorTreeForCodeAndSubCode(node, code, subCode);
                if (errorNode != null) {
                    ErrorResponse response = objectMapper.treeToValue(errorNode, ErrorResponse.class);
                    response.setTimestamp(new Date().toString());
                    return response;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JsonNode getErrorTreeForCodeAndSubCode(JsonNode node, int code, int subCode) {
        ArrayNode errorArray = (ArrayNode) node.get("errors");

        for (int i = 0; i < errorArray.size(); i++) {
            JsonNode error = errorArray.get(i);
            if (error.get("code").intValue() == code && error.get("subCode").intValue() == subCode) {
                return error;
            }
        }
        return null;
    }

    private static JsonNode getErrorTreeForLanguage(ArrayNode errorArray, String lng) {
        for (int i = 0; i < errorArray.size(); i++) {
            JsonNode node = errorArray.get(i);
            String language = node.get("lng").textValue();

            if (language.equalsIgnoreCase(lng)) {
                return node;
            }
        }
        return null;
    }
}
