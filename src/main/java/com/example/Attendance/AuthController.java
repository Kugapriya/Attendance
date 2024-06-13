package com.example.Attendance;

import org.springframework.web.bind.annotation.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final JwtUtil jwtUtil;

    public AuthController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/login")
    public String createToken(@RequestBody User user) throws Exception {
        if (validateUser(user)) {
            return jwtUtil.generateToken(user.getUsername());
        } else {
            throw new Exception("Invalid username/password");
        }
    }

    private boolean validateUser(User user) {
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader("D:/attendance-system/server/attendance-system/src/main/java/com/example/attendance_system/user_credentials.txt"))) {
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",");
                if (credentials[0].equals(user.getUsername()) && credentials[1].equals(user.getPassword())) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}