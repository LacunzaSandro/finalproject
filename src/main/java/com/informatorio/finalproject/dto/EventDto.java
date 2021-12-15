package com.informatorio.finalproject.dto;

import com.informatorio.finalproject.entity.EventEnum;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


public class EventDto {
    @NotBlank(message = "details must not be empty")
    private String details;
    @NotNull(message = "details must not be empty")
    private LocalDate finish_at;
    @NotNull(message = "details must not be empty")
    private EventEnum state;
    @NotNull(message = "details must not be empty")
    private Integer award;

    public EventDto() {
    }

    public EventDto(String details, LocalDate finish_at, EventEnum state) {
        this.details = details;
        this.finish_at = finish_at;
        this.state = state;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public LocalDate getFinish_at() {
        return finish_at;
    }

    public void setFinish_at(LocalDate finish_at) {
        this.finish_at = finish_at;
    }

    public EventEnum getState() {
        return state;
    }

    public void setState(EventEnum state) {
        this.state = state;
    }

    public Integer getAward() {
        return award;
    }

    public void setAward(Integer award) {
        this.award = award;
    }
}
