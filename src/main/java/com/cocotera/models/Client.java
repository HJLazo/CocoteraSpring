package com.cocotera.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Client {
    @Id
    private String clientId;
    private String name;
    private String ruc;
    private String address;

}
