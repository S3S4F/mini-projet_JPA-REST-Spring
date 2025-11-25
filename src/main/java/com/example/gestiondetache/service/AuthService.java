package com.example.gestiondetache.service;


import com.example.gestiondetache.dto.AuthResponse;
import com.example.gestiondetache.dto.LoginRequest;
import com.example.gestiondetache.dto.RegisterRequest;
import com.example.gestiondetache.model.Role;
import com.example.gestiondetache.model.User;
import com.example.gestiondetache.repository.UserRepository;
import com.example.gestiondetache.security.CustomUserDetails;
import com.example.gestiondetache.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    /**
     * Inscription d'un nouvel utilisateur
     */
    @Transactional
    public AuthResponse register(RegisterRequest request) {
        // Vérifier si le username existe déjà
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Le nom d'utilisateur est déjà utilisé");
        }

        // Vérifier si l'email existe déjà
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("L'email est déjà utilisé");
        }

        // Créer le nouvel utilisateur
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        // Sauvegarder l'utilisateur
        User savedUser = userRepository.save(user);

        // Générer le token JWT
        CustomUserDetails userDetails = CustomUserDetails.build(savedUser);
        String jwt = jwtUtil.generateToken(userDetails);

        // Retourner la réponse avec le token
        return new AuthResponse(
                jwt,
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );
    }

    /**
     * Connexion d'un utilisateur
     */
    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {
        // Authentifier l'utilisateur
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        // Définir l'authentification dans le contexte
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Récupérer les détails de l'utilisateur
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        // Générer le token JWT
        String jwt = jwtUtil.generateToken(userDetails);

        // Retourner la réponse avec le token
        return new AuthResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail()
        );
    }
}