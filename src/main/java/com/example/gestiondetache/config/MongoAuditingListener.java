package com.example.gestiondetache.config;


import com.example.gestiondetache.model.Task;
import com.example.gestiondetache.model.User;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MongoAuditingListener extends AbstractMongoEventListener<Object> {

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Object> event) {
        Object source = event.getSource();
        LocalDateTime now = LocalDateTime.now();

        if (source instanceof User user) {
            if (user.getCreatedAt() == null) {
                user.setCreatedAt(now);
            }
            user.setUpdatedAt(now);
        } else if (source instanceof Task task) {
            if (task.getCreatedAt() == null) {
                task.setCreatedAt(now);
            }
            task.setUpdatedAt(now);
        }
    }
}
