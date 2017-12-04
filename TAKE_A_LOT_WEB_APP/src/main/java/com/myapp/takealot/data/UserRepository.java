/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.data;


import com.myapp.takealot.entity.UserTB;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Sifiso
 */
public interface UserRepository extends CrudRepository<UserTB, Long>{
    
    public List<UserTB> findByUserRole(String role);
    public UserTB findByEmail(String email);
    //public void updateUser(UserTB user);
    
}
