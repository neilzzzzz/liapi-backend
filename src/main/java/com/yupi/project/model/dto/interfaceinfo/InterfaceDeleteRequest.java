package com.yupi.project.model.dto.interfaceinfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 删除请求
 *
 * @author yupi
 */
@Data
public class InterfaceDeleteRequest implements Serializable {

    /**
     *
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}