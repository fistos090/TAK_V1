/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.data;

import com.myapp.takealot.entity.ClientOrder;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


/**
 *
 * @author MANDELACOMP3
 */
public interface ClientOrderRepository extends CrudRepository<ClientOrder, Long>{
    
    public List<ClientOrder> findByUserID(Long id);
}
