package com.project.team.plice.domain.member;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity @Data
@Table(name = "persistent_logins")
public class PersistentLogins {    // 자동 로그인에 사용

    @Id
    private String series;
    private String username;
    private String token;
    private LocalDateTime lastUsed;
}