package com.course.demo.course.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionEmailRequested {
    private String to;
    private String userFirstName;
    private String courseTitle;
}