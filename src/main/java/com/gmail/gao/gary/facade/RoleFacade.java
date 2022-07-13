package com.gmail.gao.gary.facade;

import com.gmail.gao.gary.entity.Result;

/**
 * Description:
 * Author: huanbasara
 * Date: 2022/7/13 10:02 PM
 */
public interface RoleFacade {

    Result createRole(String roleName);

    Result deleteRole(String roleName);
}
