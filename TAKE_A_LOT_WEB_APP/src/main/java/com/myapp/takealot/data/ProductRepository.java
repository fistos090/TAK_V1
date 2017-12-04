/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.myapp.takealot.data;

import com.myapp.takealot.entity.Product;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.repository.CrudRepository;


/**
 *
 * @author MANDELACOMP3
 */
public interface ProductRepository extends CrudRepository<Product, Long>{
    
    public Product findById(Long id);
    public ArrayList<Product> findByCategory(String category);
}
