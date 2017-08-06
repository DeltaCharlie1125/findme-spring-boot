/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.findme.repositories;

import com.findme.model.Product;
import com.findme.model.QProduct;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 *
 * @author Ali Muju
 */
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>,
        QueryDslPredicateExecutor<Product>, QuerydslBinderCustomizer<QProduct> {

    @Override
    default public void customize(QuerydslBindings bindings, QProduct root) {
        bindings.bind(String.class).first(
                (StringPath path, String value) -> path.containsIgnoreCase(value));
        bindings.bind(root.modifiedDate).first((path, value) -> path.goe(value));
                
        
        
    }

}
