package com.informatorio.finalproject.dto;

import java.time.LocalDateTime;

public interface VoteResponse {
    public Long getId();
    public Long getEmprendimientoId();
    public String getEmprendimientoName();
    Long getUserId();
    String getFullName();
    public LocalDateTime getCreate_at();
}
