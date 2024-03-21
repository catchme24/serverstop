package com.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Server extends BaseEntity {

    private LocalDateTime serverStart;
    private String chronicle;
    private String serverRate;
    private String domain;

    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    private User owner;

    @Override
    public String toString() {
        return "Server{" +
                "id=" + getId() +
                ", createdAt=" + getCreatedAt() +
                ", updatedAt=" + getUpdatedAt() +
                ", serverStart=" + serverStart +
                ", chronicle=" + chronicle +
                ", serverRate=" + serverRate +
                ", domain=" + domain +
                ", owner=" + owner +
                '}';
    }
}
