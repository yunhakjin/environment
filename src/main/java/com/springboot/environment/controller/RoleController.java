package com.springboot.environment.controller;

/**
 * Created by yww on 2018/10/13.
 */

import com.springboot.environment.bean.Norm;
import com.springboot.environment.bean.Role;
import com.springboot.environment.service.NormService;
import com.springboot.environment.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/role")
@Api("角色类相关api")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @ApiOperation(value="返回所有角色的信息",notes = "所有角色的信息")

    @GetMapping(value = "/getall")
    public List<Role> getAll(){
        return roleService.getAll();
    }

    @ApiOperation(value="增加某一个角色",notes = "需要定义角色名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role_name",value="角色名称",dataType = "String")
    }
    )
    @PostMapping(value = "/addone/{role_name}")
    public void addOne(@PathVariable String role_name){
        Role role=new Role();
        role.setRole_name(role_name);
        roleService.addOne(role);
    }

    @ApiOperation(value="修改某一个角色",notes = "需要定义角色id，角色名称")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role_id",value="角色id",dataType = "String"),
            @ApiImplicitParam(name = "role_name",value="角色名称",dataType = "String")
    }
    )
    @PostMapping(value = "/updateone/{role_id}/{role_name}")
    public void updateOne(@PathVariable String role_id,
                          @PathVariable String role_name){
        roleService.updateOne(role_id,role_name);
    }

    @ApiOperation(value="删除某一个角色",notes = "根据角色的id进行角色删除，同时删除user_role role_permission")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "role_id",value="角色id",dataType = "String")
    }
    )
    @DeleteMapping(value="/deleteone/{role_id}")
    public void deleteOne(@PathVariable String role_id){
        roleService.delOne(role_id);
    }


}
