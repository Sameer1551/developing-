package com.healthnet.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.healthnet.entity.User;
import com.healthnet.entity.UserRole;
import com.healthnet.entity.UserStatus;
import com.healthnet.repository.UserRepository;

/**
 * Service class for User operations
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@Service
@Transactional
public class UserService {
    
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    /**
     * Get all users with pagination
     */
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
    
    /**
     * Get user by ID
     */
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
    
    /**
     * Get user by email
     */
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    /**
     * Get user by phone
     */
    public Optional<User> getUserByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }
    

    
    /**
     * Update an existing user
     */
    public User updateUser(Long id, User userDetails) {
        User user = getUserById(id);
        
        // Check if email is being changed and if it already exists
        if (!user.getEmail().equals(userDetails.getEmail()) && 
            userRepository.existsByEmail(userDetails.getEmail())) {
            throw new RuntimeException("User with email already exists: " + userDetails.getEmail());
        }
        
        // Check if phone is being changed and if it already exists
        if (!user.getPhone().equals(userDetails.getPhone()) && 
            userRepository.existsByPhone(userDetails.getPhone())) {
            throw new RuntimeException("User with phone already exists: " + userDetails.getPhone());
        }
        
        // Update fields
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        user.setPhone(userDetails.getPhone());
        user.setRole(userDetails.getRole());
        user.setStatus(userDetails.getStatus());
        user.setDistrict(userDetails.getDistrict());
        user.setState(userDetails.getState());
        user.setOriginalRole(userDetails.getOriginalRole());
        user.setPermissions(userDetails.getPermissions());
        user.setLastActive(LocalDateTime.now());
        
        return userRepository.save(user);
    }
    
    /**
     * Delete a user
     */
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }
    
    /**
     * Get users by role
     */
    public List<User> getUsersByRole(UserRole role) {
        return userRepository.findByRole(role);
    }
    
    /**
     * Get users by status
     */
    public List<User> getUsersByStatus(UserStatus status) {
        return userRepository.findByStatus(status);
    }
    
    /**
     * Get users by district
     */
    public List<User> getUsersByDistrict(String district) {
        return userRepository.findByDistrict(district);
    }
    
    /**
     * Get users by state
     */
    public List<User> getUsersByState(String state) {
        return userRepository.findByState(state);
    }
    
    /**
     * Update user status
     */
    public User updateUserStatus(Long id, UserStatus status) {
        User user = getUserById(id);
        user.setStatus(status);
        user.setLastActive(LocalDateTime.now());
        return userRepository.save(user);
    }
    
    /**
     * Update user last active time
     */
    public void updateLastActive(Long userId) {
        User user = getUserById(userId);
        user.setLastActive(LocalDateTime.now());
        userRepository.save(user);
    }
    
    /**
     * Check if the current user is the same as the requested user
     */
    public boolean isCurrentUser(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User currentUser = (User) authentication.getPrincipal();
            return currentUser.getId().equals(userId);
        }
        return false;
    }
    
    /**
     * Get current authenticated user
     */
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        throw new RuntimeException("No authenticated user found");
    }
    
    /**
     * Get user statistics
     */
    public com.healthnet.controller.UserController.UserStatistics getUserStatistics() {
        long totalUsers = userRepository.count();
        long adminUsers = userRepository.countByRole(UserRole.ADMIN);
        long healthOfficers = userRepository.countByRole(UserRole.DISTRICT_HEALTH_OFFICER);
        long staffUsers = userRepository.countByRole(UserRole.HEALTH_STAFF);
        long activeUsers = userRepository.countByStatus(UserStatus.ACTIVE);
        long inactiveUsers = userRepository.countByStatus(UserStatus.INACTIVE);
        
        return new com.healthnet.controller.UserController.UserStatistics(
            totalUsers, adminUsers, healthOfficers, staffUsers, activeUsers, inactiveUsers
        );
    }
    
    /**
     * Search users by name
     */
    public List<User> searchUsersByName(String name) {
        return userRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * Search users by email
     */
    public List<User> searchUsersByEmail(String email) {
        return userRepository.findByEmailContainingIgnoreCase(email);
    }
    
    /**
     * Get inactive users
     */
    public List<User> getInactiveUsers(LocalDateTime cutoffDate) {
        return userRepository.findInactiveUsers(cutoffDate);
    }
    
    /**
     * Get users by multiple districts
     */
    public List<User> getUsersByDistricts(List<String> districts) {
        return userRepository.findByDistricts(districts);
    }
    
    /**
     * Get users by multiple states
     */
    public List<User> getUsersByStates(List<String> states) {
        return userRepository.findByStates(states);
    }
    
    /**
     * Get users with specific permission
     */
    public List<User> getUsersByPermission(String permission) {
        return userRepository.findByPermission(permission);
    }
}
