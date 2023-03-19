package com.yupi.project.model.dto.interfaceinfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author yupi
 */
@Data
public class IdRequest implements Serializable {

    /**
     *
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}