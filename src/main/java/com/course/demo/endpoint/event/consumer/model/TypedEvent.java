package com.course.demo.endpoint.event.consumer.model;

import com.course.demo.PojaGenerated;
import com.course.demo.endpoint.event.model.PojaEvent;

@PojaGenerated
public record TypedEvent(String typeName, PojaEvent payload) {}
