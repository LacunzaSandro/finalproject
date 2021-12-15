package com.informatorio.finalproject.dto;

import org.springframework.util.ReflectionUtils;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

public class EmprendimientoDto {

    @NotBlank(message = "name must not be empty")
    private String name;
    private String description;
    private String content;
    private Long target;
    private boolean published;
    private String snapshot;
    private String tags;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTarget() {
        return target;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public String getSnapshot() {
        return snapshot;
    }

    public void setSnapshot(String snapshot) {
        this.snapshot = snapshot;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
    public static Boolean analyze(Object obj){
        List<Object> list = new ArrayList<>();
        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            list.add(field.get(obj));
            System.out.println("Field value: "+ field.get(obj));
        });
        boolean isNoCorrect = list.stream().anyMatch(s -> (s == null || s.equals("")));
        return isNoCorrect;
    }
}
