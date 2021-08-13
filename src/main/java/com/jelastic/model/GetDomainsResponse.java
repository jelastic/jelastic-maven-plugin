package com.jelastic.model;

import lombok.Data;

import java.util.List;

@Data
public class GetDomainsResponse {
    private int                 result;
    private List<String>        env;
    private List<NodeGroupName> nodeGroups;
}
