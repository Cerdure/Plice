package com.project.team.plice.dto.admin;

import lombok.Data;

@Data
public class BlockDto {
    Long id;
    String ip;
    Integer date;
    String reason;
    String blockType;
    String pageType;
}
