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
import java.util.Map;

@RestController
@RequestMapping(value="/role")
@Api("角色类相关api")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @ApiOperation(value = "添加某一个角色",notes = "添加角色")
    @ApiImplicitParam(name = "params",value="角色name",dataType = "JSON")
    @RequestMapping(value = "/addRole",method = RequestMethod.POST)
    public Map addOrUdateRole(@RequestBody Map<String,Object> params){
        return roleService.addRole(params);
    }

    @ApiOperation(value = "角色授权",notes = "角色授权")
    @ApiImplicitParam(name = "params",value="角色id和权限",dataType = "JSON")
    @RequestMapping(value = "/rolePermissionDistribute",method = RequestMethod.POST)
    public Map rolePermissionDistribute(@RequestBody Map<String,Object> params){
        return roleService.rolePermissionDistribute(params);
    }

    @ApiOperation(value = "删除角色",notes = "删除角色")
    @ApiImplicitParam(name = "params",value="删除角色的id",dataType = "JSON")
    @RequestMapping(value = "/deleteRole",method = RequestMethod.POST)
    public Map deleteRole(@RequestBody Map<String,Object> params){
        return roleService.deleteRole(params);
    }

    @ApiOperation(value = "查询所有角色",notes = "查询所有角色")
    @RequestMapping(value = "/queryRole",method = RequestMethod.POST)
    public Map queryRole(){
        return roleService.queryRole();
    }

    @ApiOperation(value = "查询某个角色",notes = "查询某个角色")
    @RequestMapping(value = "/queryRoleByRoleID",method = RequestMethod.POST)
    public Map queryRoleByRoleID(@RequestBody Map<String,Object> params){
        return roleService.queryRoleByRoleID(params);
    }

    @ApiOperation(value = "添加menuList和permission_list",notes = "添加menuList和permission_list")
    @RequestMapping(value = "/updateMenulistAndPermissionList",method = RequestMethod.POST)
    public Map updateMenulistAndPermissionList(@RequestBody Map<String,Object> params){
        return roleService.updateMenulistAndPermissionList(params);
    }

}
