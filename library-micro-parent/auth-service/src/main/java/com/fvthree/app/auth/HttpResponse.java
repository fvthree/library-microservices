package com.fvthree.app.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@ToString
public class HttpResponse {
    private String status;
    private int statusCode;
    private String message;
    private Map<?,?> data;
}
