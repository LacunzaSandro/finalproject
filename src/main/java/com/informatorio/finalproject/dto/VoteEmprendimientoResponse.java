package com.informatorio.finalproject.dto;

import java.time.LocalDate;

public interface VoteEmprendimientoResponse {
     Long getId();
     Long getUserId();
     String getFirstName();
     String getLastName();
     LocalDate getCreate_at();
}
