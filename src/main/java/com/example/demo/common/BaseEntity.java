package com.example.demo.common;

import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class BaseEntity {

    @CreationTimestamp
    @Column(name= "createAt", nullable = false, updatable = false)
    private LocalDateTime createAt;
    @UpdateTimestamp
    @Column(name= "updateAt", nullable = false, updatable = true)
    private LocalDateTime updateAt;
    @Enumerated(EnumType.STRING)
    @Column(name= "state", nullable = false, length = 10)
    protected State state = State.ACTIVE;

    public enum State {
        ACTIVE, INACTIVE
    }
}
