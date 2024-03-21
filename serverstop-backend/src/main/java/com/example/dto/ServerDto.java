package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServerDto {

    private Long id;
    @NotNull
    private LocalDateTime serverStart;
    @NotNull
    @NotBlank
    private String chronicle;
    @NotNull
    @NotBlank
    private String serverRate;
    @NotNull
    private String domain;
    @NotNull
    private String status;

    @Override
    public String toString() {
        return "ServerDto{" +
                "id=" + id +
                ", serverStart=" + serverStart +
                ", chronicle=" + chronicle + '\'' +
                ", serverRate=" + serverRate + '\'' +
                ", domain=" + domain + '\'' +
                '}';
    }
}
