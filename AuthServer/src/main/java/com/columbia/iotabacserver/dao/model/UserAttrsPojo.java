package com.columbia.iotabacserver.dao.model;

import lombok.*;

import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonProperty;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@ToString


public class UserAttrsPojo implements Serializable {
    private static final long serialVersionUID = -7586961713491327731L;
    @JsonProperty("user_id")
    String userId = "void";
    @JsonProperty("password")
    String password = "void";
    @JsonProperty("attrs")
    String attrs = "void";
}
