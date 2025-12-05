package com.hulahoop.redback.auth.model.dao;

import com.hulahoop.redback.auth.model.dto.AuthDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthMapper {
    AuthDTO findAdminById(String id);
}

