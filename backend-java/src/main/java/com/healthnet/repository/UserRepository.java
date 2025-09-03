package com.healthnet.repository;

import com.healthnet.entity.User;
import com.healthnet.entity.UserRole;
import com.healthnet.entity.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User entity operations
 * 
 * @author NE HealthNet Team
 * @version 1.0.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by email
     */
    Optional<User> findByEmail(String email);
    
    /**
     * Find user by phone number
     */
    Optional<User> findByPhone(String phone);
    
    /**
     * Find users by role
     */
    List<User> findByRole(UserRole role);
    
    /**
     * Find users by status
     */
    List<User> findByStatus(UserStatus status);
    
    /**
     * Find users by district
     */
    List<User> findByDistrict(String district);
    
    /**
     * Find users by state
     */
    List<User> findByState(String state);
    
    /**
     * Find users by role and status
     */
    List<User> findByRoleAndStatus(UserRole role, UserStatus status);
    
    /**
     * Find users by district and role
     */
    List<User> findByDistrictAndRole(String district, UserRole role);
    
    /**
     * Check if email exists
     */
    boolean existsByEmail(String email);
    
    /**
     * Check if phone exists
     */
    boolean existsByPhone(String phone);
    
    /**
     * Find users with pagination
     */
    Page<User> findAll(Pageable pageable);
    
    /**
     * Find users by role with pagination
     */
    Page<User> findByRole(UserRole role, Pageable pageable);
    
    /**
     * Find users by status with pagination
     */
    Page<User> findByStatus(UserStatus status, Pageable pageable);
    
    /**
     * Find users by district with pagination
     */
    Page<User> findByDistrict(String district, Pageable pageable);
    
    /**
     * Find users by state with pagination
     */
    Page<User> findByState(String state, Pageable pageable);
    
    /**
     * Find users created after a specific date
     */
    List<User> findByJoinDateAfter(LocalDateTime date);
    
    /**
     * Find users who were last active after a specific date
     */
    List<User> findByLastActiveAfter(LocalDateTime date);
    
    /**
     * Find users by multiple districts
     */
    @Query("SELECT u FROM User u WHERE u.district IN :districts")
    List<User> findByDistricts(@Param("districts") List<String> districts);
    
    /**
     * Find users by multiple states
     */
    @Query("SELECT u FROM User u WHERE u.state IN :states")
    List<User> findByStates(@Param("states") List<String> states);
    
    /**
     * Find users with specific permission
     */
    @Query("SELECT u FROM User u JOIN u.permissions p WHERE p = :permission")
    List<User> findByPermission(@Param("permission") String permission);
    
    /**
     * Count users by role
     */
    long countByRole(UserRole role);
    
    /**
     * Count users by status
     */
    long countByStatus(UserStatus status);
    
    /**
     * Count users by district
     */
    long countByDistrict(String district);
    
    /**
     * Count users by state
     */
    long countByState(String state);
    
    /**
     * Find users by name containing (case insensitive)
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);
    
    /**
     * Find users by email containing (case insensitive)
     */
    @Query("SELECT u FROM User u WHERE LOWER(u.email) LIKE LOWER(CONCAT('%', :email, '%'))")
    List<User> findByEmailContainingIgnoreCase(@Param("email") String email);
    
    /**
     * Find inactive users (last active before a specific date)
     */
    @Query("SELECT u FROM User u WHERE u.lastActive < :date OR u.lastActive IS NULL")
    List<User> findInactiveUsers(@Param("date") LocalDateTime date);
    
    /**
     * Find users by role and district with pagination
     */
    Page<User> findByRoleAndDistrict(UserRole role, String district, Pageable pageable);
    
    /**
     * Find users by role and state with pagination
     */
    Page<User> findByRoleAndState(UserRole role, String state, Pageable pageable);
}
