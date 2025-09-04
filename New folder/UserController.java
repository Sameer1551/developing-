--- a/backend-java/src/main/java/com/healthnet/controller/UserController.java
+++ b/backend-java/src/main/java/com/healthnet/controller/UserController.java
@@ -4,6 +4,7 @@
 import com.healthnet.service.UserService;
 import io.swagger.v3.oas.annotations.Operation;
 import io.swagger.v3.oas.annotations.responses.ApiResponse;
 import io.swagger.v3.oas.annotations.responses.ApiResponses;
 import io.swagger.v3.oas.annotations.tags.Tag;
 import org.springframework.http.ResponseEntity;
 import org.springframework.security.access.prepost.PreAuthorize;
 import org.springframework.security.core.Authentication;
 import org.springframework.security.core.context.SecurityContextHolder;
 import org.springframework.web.bind.annotation.GetMapping;
 import org.springframework.web.bind.annotation.RequestMapping;
 import org.springframework.web.bind.annotation.RestController;
+
+import java.util.List;
 
 /**
  * REST Controller for User-related operations
@@ -28,4 +29,15 @@
         UserDto userDto = userService.getAuthenticatedUser(authentication.getName());
         return ResponseEntity.ok(userDto);
     }
+
+    @GetMapping("/users")
+    @PreAuthorize("hasAuthority('ADMIN')")
+    @Operation(summary = "Get all users", description = "Retrieve a list of all users. Accessible to ADMIN only.")
+    @ApiResponses(value = {
+            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of users"),
+            @ApiResponse(responseCode = "403", description = "Forbidden access")
+    })
+    public ResponseEntity<List<UserDto>> getAllUsers() {
+        return ResponseEntity.ok(userService.findAllUsers());
+    }
 }