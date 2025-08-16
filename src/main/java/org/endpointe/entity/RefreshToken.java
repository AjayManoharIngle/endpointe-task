package org.endpointe.entity;

import java.time.Instant;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "TBL_REFRESH_TOKENS")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "N_ID")
    private Long id;

    @Column(name = "S_TOKEN_ID", unique = true, nullable = false)
    private String tokenId;

    @Column(name = "S_SECRET_HASH", nullable = false)
    private String secretHash;

    @Column(name = "N_USER_ID", nullable = false)
    private Long userId;

    @Column(name = "DT_CREATED_AT")
    private Instant createdAt;

    @Column(name = "DT_LAST_USED_AT")
    private Instant lastUsedAt;

    @Column(name = "DT_EXPIRES_AT")
    private Instant expiresAt;

    @Column(name = "N_REVOKED")
    private boolean revoked;
}
