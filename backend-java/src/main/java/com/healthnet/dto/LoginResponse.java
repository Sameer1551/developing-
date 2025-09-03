package com.healthnet.dto;

import com.healthnet.entity.UserRole;

import java.util.List;

/**
 * Data Transfer Object for login responses
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
public class LoginResponse {
    
    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private String email;
    private UserRole role;
    private String designation;
    private String village;
    private String district;
    private List<String> permissions;
    
    // Constructors
    public LoginResponse() {}
    
    public LoginResponse(String token, Long id, String name, String email, UserRole role, 
                        String designation, String village, String district, List<String> permissions) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.designation = designation;
        this.village = village;
        this.district = district;
        this.permissions = permissions;
    }
    
    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public String getDesignation() {
        return designation;
    }
    
    public void setDesignation(String designation) {
        this.designation = designation;
    }
    
    public String getVillage() {
        return village;
    }
    
    public void setVillage(String village) {
        this.village = village;
    }
    
    public String getDistrict() {
        return district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public List<String> getPermissions() {
        return permissions;
    }
    
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
