package com.cocotera.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Client {
    @Id
    private String clientId;  // This should map to the 'client_id' column in the database
    private String name;
    private String ruc;
    private String address;

    // Other fields and methods
}
