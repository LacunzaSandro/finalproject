package com.informatorio.finalproject.dto;

import java.time.LocalDateTime;

public interface VoteEmprendimientoResponse {
     Long getId();
     Long getUserId();
     String getFirstName();
     String getLastName();
     LocalDateTime getCreate_at();
}
