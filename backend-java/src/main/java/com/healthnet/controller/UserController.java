package com.healthnet.controller;

import com.healthnet.dto.UserDto;
import com.healthnet.entity.User;
import com.healthnet.entity.UserRole;
import com.healthnet.entity.UserStatus;
import com.healthnet.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for User management operations
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "APIs for managing users in the system")
public class UserController {
    
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Get all users with pagination
     */
    @GetMapping
    @Operation(summary = "Get all users", description = "Retrieve all users with pagination support")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Page<UserDto>> getAllUsers(Pageable pageable) {
        Page<User> users = userService.getAllUsers(pageable);
        Page<UserDto> userDtos = users.map(this::convertToDto);
        return ResponseEntity.ok(userDtos);
    }
    
    /**
     * Get user by ID
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve a specific user by their ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or @userService.isCurrentUser(#id)")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(convertToDto(user));
    }
    

    
    /**
     * Update an existing user
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update user", description = "Update an existing user's information")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input data"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or @userService.isCurrentUser(#id)")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id, @Valid @RequestBody UserDto userDto) {
        User user = convertToEntity(userDto);
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(convertToDto(updatedUser));
    }
    
    /**
     * Delete a user
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user", description = "Delete a user from the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "User deleted successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
    
    /**
     * Get users by role
     */
    @GetMapping("/role/{role}")
    @Operation(summary = "Get users by role", description = "Retrieve users filtered by their role")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByRole(@PathVariable UserRole role) {
        List<User> users = userService.getUsersByRole(role);
        List<UserDto> userDtos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
    
    /**
     * Get users by district
     */
    @GetMapping("/district/{district}")
    @Operation(summary = "Get users by district", description = "Retrieve users filtered by district")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTH_OFFICER')")
    public ResponseEntity<List<UserDto>> getUsersByDistrict(@PathVariable String district) {
        List<User> users = userService.getUsersByDistrict(district);
        List<UserDto> userDtos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
    
    /**
     * Get users by state
     */
    @GetMapping("/state/{state}")
    @Operation(summary = "Get users by state", description = "Retrieve users filtered by state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getUsersByState(@PathVariable String state) {
        List<User> users = userService.getUsersByState(state);
        List<UserDto> userDtos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }
    
    /**
     * Update user status
     */
    @PatchMapping("/{id}/status")
    @Operation(summary = "Update user status", description = "Update the status of a user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User status updated successfully"),
        @ApiResponse(responseCode = "404", description = "User not found"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUserStatus(@PathVariable Long id, 
                                                   @RequestParam UserStatus status) {
        User updatedUser = userService.updateUserStatus(id, status);
        return ResponseEntity.ok(convertToDto(updatedUser));
    }
    
    /**
     * Get user statistics
     */
    @GetMapping("/statistics")
    @Operation(summary = "Get user statistics", description = "Retrieve statistics about users in the system")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Statistics retrieved successfully"),
        @ApiResponse(responseCode = "401", description = "Unauthorized"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
    })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserStatistics> getUserStatistics() {
        UserStatistics stats = userService.getUserStatistics();
        return ResponseEntity.ok(stats);
    }
    
 
    
    // Helper methods for conversion
    private UserDto convertToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setRole(user.getRole());
        dto.setStatus(user.getStatus());
        dto.setDistrict(user.getDistrict());
        dto.setState(user.getState());
        dto.setOriginalRole(user.getOriginalRole());
        dto.setPermissions(user.getPermissions());
        dto.setJoinDate(user.getJoinDate());
        dto.setLastActive(user.getLastActive());
        return dto;
    }
    
    private User convertToEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setRole(dto.getRole());
        user.setStatus(dto.getStatus());
        user.setDistrict(dto.getDistrict());
        user.setState(dto.getState());
        user.setOriginalRole(dto.getOriginalRole());
        user.setPermissions(dto.getPermissions());
        return user;
    }
    
    // Statistics DTO
    public static class UserStatistics {
        private long totalUsers;
        private long adminUsers;
        private long healthOfficers;
        private long staffUsers;
        private long activeUsers;
        private long inactiveUsers;
        
        // Constructors, getters, and setters
        public UserStatistics() {}
        
        public UserStatistics(long totalUsers, long adminUsers, long healthOfficers, 
                             long staffUsers, long activeUsers, long inactiveUsers) {
            this.totalUsers = totalUsers;
            this.adminUsers = adminUsers;
            this.healthOfficers = healthOfficers;
            this.staffUsers = staffUsers;
            this.activeUsers = activeUsers;
            this.inactiveUsers = inactiveUsers;
        }
        
        // Getters and setters
        public long getTotalUsers() { return totalUsers; }
        public void setTotalUsers(long totalUsers) { this.totalUsers = totalUsers; }
        
        public long getAdminUsers() { return adminUsers; }
        public void setAdminUsers(long adminUsers) { this.adminUsers = adminUsers; }
        
        public long getHealthOfficers() { return healthOfficers; }
        public void setHealthOfficers(long healthOfficers) { this.healthOfficers = healthOfficers; }
        
        public long getStaffUsers() { return staffUsers; }
        public void setStaffUsers(long staffUsers) { this.staffUsers = staffUsers; }
        
        public long getActiveUsers() { return activeUsers; }
        public void setActiveUsers(long activeUsers) { this.activeUsers = activeUsers; }
        
        public long getInactiveUsers() { return inactiveUsers; }
        public void setInactiveUsers(long inactiveUsers) { this.inactiveUsers = inactiveUsers; }
    }
}
