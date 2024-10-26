package com.cocotera.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class User {
    @Id
    private String userId;
    private String username;
    private String password;
    private String role;
}
