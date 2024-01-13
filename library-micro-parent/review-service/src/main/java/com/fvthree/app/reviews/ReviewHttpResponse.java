package com.fvthree.app.reviews;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReviewHttpResponse {
    protected String message;

    protected String reason;

    protected int statusCode;

    protected String status;

    protected Map<? , ?> data;
}
