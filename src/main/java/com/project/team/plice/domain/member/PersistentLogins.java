package com.project.team.plice.domain.member;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "persistent_logins")
public class PersistentLogins {

    @Id
    private String series;
    private String username;
    private String token;
    private LocalDateTime lastUsed;
}