package com.jelastic.model;

import lombok.Data;

@Data
public class GetNodeGroupResponse {
    private int result;
    private NodeGroup nodeGroup;
}
