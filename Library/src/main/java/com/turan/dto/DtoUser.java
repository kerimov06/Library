package com.turan.dto;

import com.turan.entity.Role;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoUser {


    private String name;

    private String username;

    private String password;

    private Set<DtoRole> roles = new HashSet<>();

}
