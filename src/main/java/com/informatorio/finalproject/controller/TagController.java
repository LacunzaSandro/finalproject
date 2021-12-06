package com.informatorio.finalproject.controller;

import com.informatorio.finalproject.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.persistence.Column;

@Controller
public class TagController {
    @Autowired
    private TagService tagService;


}
