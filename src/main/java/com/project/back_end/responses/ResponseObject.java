package com.project.back_end.responses;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseObject {

    @JsonProperty("message")
    private String  message;

    @JsonProperty("status")
    private String  status;

    @JsonProperty("data")
    private Object  data;
    
}
