package com.healthnet.dto;

import com.healthnet.entity.UserRole;
import com.healthnet.entity.UserStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for User entity
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
public class UserDto {
    
    private Long id;
    
    @NotBlank(message = "Name is required")
    private String name;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    private String email;
    
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @NotBlank(message = "Phone number is required")
    private String phone;
    
    private UserRole role;
    private UserStatus status;
    private String district;
    private String state;
    private String originalRole;
    private List<String> permissions;
    private LocalDateTime joinDate;
    private LocalDateTime lastActive;
    
    // Constructors
    public UserDto() {}
    
    public UserDto(String name, String email, String phone, UserRole role, String district, String state) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.district = district;
        this.state = state;
    }
    
    // Getters and Setters
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
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public UserRole getRole() {
        return role;
    }
    
    public void setRole(UserRole role) {
        this.role = role;
    }
    
    public UserStatus getStatus() {
        return status;
    }
    
    public void setStatus(UserStatus status) {
        this.status = status;
    }
    
    public String getDistrict() {
        return district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public String getState() {
        return state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    public String getOriginalRole() {
        return originalRole;
    }
    
    public void setOriginalRole(String originalRole) {
        this.originalRole = originalRole;
    }
    
    public List<String> getPermissions() {
        return permissions;
    }
    
    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
    
    public LocalDateTime getJoinDate() {
        return joinDate;
    }
    
    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }
    
    public LocalDateTime getLastActive() {
        return lastActive;
    }
    
    public void setLastActive(LocalDateTime lastActive) {
        this.lastActive = lastActive;
    }
}
