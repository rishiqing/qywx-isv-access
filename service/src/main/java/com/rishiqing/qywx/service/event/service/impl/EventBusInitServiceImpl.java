package com.rishiqing.qywx.service.event.service.impl;

import com.google.common.eventbus.EventBus;
import com.rishiqing.qywx.service.event.listener.EventListener;
import com.rishiqing.qywx.service.event.service.EventBusInitService;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EventBusInitServiceImpl implements EventBusInitService {
    private Map<EventBus, EventListener> eventMap;

    public Map<EventBus, EventListener> getEventMap() {
        return eventMap;
    }

    public void setEventMap(Map<EventBus, EventListener> eventMap) {
        this.eventMap = eventMap;
    }

    @Override
    public void register() {
        Set<Map.Entry<EventBus, EventListener>> entrySet = this.eventMap.entrySet();
        Iterator<Map.Entry<EventBus, EventListener>> it = entrySet.iterator();

        while(it.hasNext()){
            Map.Entry<EventBus, EventListener> entry = it.next();
            EventBus eventBus = entry.getKey();
            EventListener eventListener = entry.getValue();
            eventBus.register(eventListener);
        }
    }
}
