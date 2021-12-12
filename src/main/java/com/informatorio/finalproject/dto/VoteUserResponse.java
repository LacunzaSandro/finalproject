package com.informatorio.finalproject.dto;

import java.time.LocalDate;

public interface VoteUserResponse {
    public Long getId();
    public Long getEmprendimientoId();
    public String getEmprendimientoName();
    public LocalDate getCreate_at();
}
