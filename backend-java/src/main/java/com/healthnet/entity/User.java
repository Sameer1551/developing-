package com.healthnet.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * User entity representing health workers and administrators
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;
    
    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is required")
    @Column(nullable = false, unique = true)
    private String email;
    
    @Pattern(regexp = "^\\d{10}$", message = "Phone number must be 10 digits")
    @NotBlank(message = "Phone number is required")
    @Column(nullable = false)
    private String phone;
    
    @NotBlank(message = "Role is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserStatus status = UserStatus.ACTIVE;
    
    @NotBlank(message = "District is required")
    @Column(nullable = false)
    private String district;
    
    @NotBlank(message = "State is required")
    @Column(nullable = false)
    private String state;
    
    @Column(name = "original_role")
    private String originalRole;
    
    @ElementCollection
    @CollectionTable(name = "user_permissions", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "permission")
    private List<String> permissions;
    
    @CreatedDate
    @Column(name = "join_date", nullable = false, updatable = false)
    private LocalDateTime joinDate;
    
    @LastModifiedDate
    @Column(name = "last_active")
    private LocalDateTime lastActive;
    
    // Constructors
    public User() {}
    
    public User(String name, String email, String phone, UserRole role, String district, String state) {
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
    
    // Utility methods
    public boolean hasPermission(String permission) {
        return permissions != null && permissions.contains(permission);
    }
    
    public boolean isAdmin() {
        return role == UserRole.ADMIN;
    }
    
    public boolean isStaff() {
        return role == UserRole.DISTRICT_HEALTH_OFFICER || role == UserRole.HEALTH_STAFF ||
               role == UserRole.ASHA_WORKER || role == UserRole.ANM ||
               role == UserRole.NURSE || role == UserRole.GOVERNMENT_OFFICIAL;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", role=" + role +
                ", district='" + district + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
