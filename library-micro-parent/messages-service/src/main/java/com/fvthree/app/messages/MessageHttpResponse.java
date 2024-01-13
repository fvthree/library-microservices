package com.fvthree.app.messages;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageHttpResponse {

    private String status;

    private Integer code;

    private Map<?,?> data;

    private String message;
}
