package com.springboot.environment.serviceImpl;

import com.springboot.environment.bean.Role;
import com.springboot.environment.dao.RoleDao;
import com.springboot.environment.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by yww on 2018/9/11.
 */
@Transactional
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public List< Object[]> getAll() {
        return roleDao.getAll();
    }

    @Override
    public void addOne(Role role) {
        roleDao.save(role);
    }

    @Override
    public void delOne(String role_id) {
        roleDao.deleteRoleUser(Integer.parseInt(role_id));
        roleDao.deleteRolePermission(Integer.parseInt(role_id));
        roleDao.deleteById(Integer.parseInt(role_id));
    }

    @Override
    public void updateOne(String role_id, String role_name) {
        roleDao.updateOne(Integer.parseInt(role_id),role_name);
    }

    @Override
    public List<Role> getRoleByUserID(Integer user_id) {
        List<Role> roles = roleDao.getRoleByUserID(user_id);
        return roles;
    }

    @Override
    public Map addRole(Map params) {
        String type=(String)params.get("type");
        String name=(String)params.get("name");
        Map<String,String> resultMap=new LinkedHashMap<String,String>();
        if(type.equals("add")){
            int t=roleDao.addOne(name);
            System.out.println(t);
            if(t==1){
                resultMap.put("addRoleFlag","true");
            }else{
                resultMap.put("addRoleFlag","false");
            }

        }else if(type.equals("edit")){
            String id=(String)params.get("id");
            roleDao.updateOne(Integer.parseInt(id),name);
            if(roleDao.findById(Integer.parseInt(id)).get().getRole_name().equals(name)){
                resultMap.put("editRoleFlag","true");
            }else{
                resultMap.put("editRoleFlag","false");
            }
        }
        return resultMap;
    }

    @Override
    public Map rolePermissionDistribute(Map params) {
        Map<String,String> resultMap=new LinkedHashMap<String,String>();
        String role_id=(String)params.get("role_id");
        String role_permissions=(params.get("role_permissions")+"").replaceAll("[\\[\\]]", "");
        System.out.println(role_permissions);
        int flag=roleDao.updatePermission(role_id,role_permissions);
        if(flag==1){
            resultMap.put("authorizeFlag","true");
        }else{
            resultMap.put("authorizeFlag","false");
        }
        return resultMap;
    }

    @Override
    public Map deleteRole(Map params) {
        Map<String,String> resultMap=new LinkedHashMap<String,String>();
        String role_id=(String)params.get("deleteRoleList");
        int flag=roleDao.deleteOne(Integer.parseInt(role_id));
        if(flag==1){
            resultMap.put("deleteFlag","true");
        }else{
            resultMap.put("deleteFlag","false");
        }
        return resultMap;
    }

    @Override
    public Map queryRole() {
        Map<String,List> resultMap=new LinkedHashMap<String,List>();
        List<Role> roles= roleDao.getAllRoles();
        List<Map> list=new ArrayList<Map>();
        for(int i=0;i<roles.size();i++){
            Map<String,String> map=new HashMap<String, String>();
            map.put("id",roles.get(i).getRole_id()+"");
            map.put("name",roles.get(i).getRole_name());
            list.add(map);
        }
        resultMap.put("roleList",list);
        return resultMap;
    }

    @Override
    public Map queryRoleByRoleID(Map params) {
        Map<String,List> resultMap=new LinkedHashMap<String,List>();
        String selectedRoleId = (String)params.get("selectedRoleId");
        Role role=roleDao.getOne(Integer.parseInt(selectedRoleId));
        List<Role> roles= roleDao.getAllRoles();
        List<Map> list=new ArrayList<Map>();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("id",role.getRole_id()+"");
        map.put("name",role.getRole_name());
        String[] permissions=role.getPermission_list().split(",");
        List<String> permissionsList=new ArrayList<String>();
        for(int i =0;i<permissions.length;i++){
            permissionsList.add(permissions[i]);
        }
        map.put("permissions",permissionsList);
        list.add(map);
        resultMap.put("roleList",list);
        return resultMap;
    }
}
