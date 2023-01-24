package com.project.team.plice.domain.admin;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IP {

    @Id
    @GeneratedValue
    @Column(name = "IP_id")
    private Long id;

    private String ip;

    @OneToMany(mappedBy = "ip")
    private List<AccessLog> accessLogs;

    @OneToOne(mappedBy = "ip")
    private Blacklist blacklist;

    @Builder
    public IP(Long id, String ip, List<AccessLog> accessLogs, Blacklist blacklist) {
        this.id = id;
        this.ip = ip;
        this.accessLogs = accessLogs;
        this.blacklist = blacklist;
    }
}
