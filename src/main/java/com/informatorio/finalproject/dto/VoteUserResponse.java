package com.informatorio.finalproject.dto;

import java.time.LocalDateTime;

public interface VoteUserResponse {
    public Long getId();
    public Long getEmprendimientoId();
    public String getEmprendimientoName();
    public LocalDateTime getCreate_at();
}
