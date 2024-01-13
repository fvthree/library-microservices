package com.fvthree.app.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
@Getter
@Setter
public class JwtAccessToken {

    private String accessToken;

    private String refreshToken;

}