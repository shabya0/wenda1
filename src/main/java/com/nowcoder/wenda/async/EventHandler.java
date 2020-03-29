package com.nowcoder.wenda.async;

import java.text.ParseException;
import java.util.List;

public interface EventHandler {
    void doHandle(EventModel model) throws ParseException;

    List<EventType> getSupportEventTypes();
}
