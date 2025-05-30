package com.loontao.utilityservice.entity;
import java.time.LocalDateTime;
import java.util.UUID;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "verification_tokens")
public class VerificationToken {
    
    private static final int EXPIRATION_TIME = 30; // 30 minutes

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expiryDate;

    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_phone_number", referencedColumnName = "phone_number")
    private User user;

    public VerificationToken() {
    }

    public VerificationToken(User user) {
        this.user = user;
        this.token = UUID.randomUUID().toString();
        // Set the expiry date to 30 minutes from now
        this.expiryDate = calculateExpiryDate(EXPIRATION_TIME);
    }

    private LocalDateTime calculateExpiryDate(int expiryTimeInMinutes) {
        return LocalDateTime.now().plusMinutes(expiryTimeInMinutes);
    }

    public void resetToken() {
    this.token = UUID.randomUUID().toString();
    this.expiryDate = calculateExpiryDate(EXPIRATION_TIME); // Assuming your calculateExpiryDate method exists
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }   
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

}
