/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.findme.controller;

import com.findme.model.Product;
import com.findme.repositories.ProductRepository;
import com.querydsl.core.types.Predicate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.querydsl.QuerydslPredicateArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Ali Muju
 */
@RepositoryRestController
public class ProductController {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private ProductRepository repo;

    @Autowired
    private QuerydslPredicateArgumentResolver querydslPredicateArgumentResolver;

    @RequestMapping(method = GET, value = "products/{id}/audits")
    public @ResponseBody
    ResponseEntity<?> getProducers(@PathVariable Long id) {
        AuditReader reader = AuditReaderFactory.get(entityManager);
        AuditQuery query = reader.createQuery()
                .forRevisionsOfEntity(Product.class, false, true)
                .add(AuditEntity.id().eq(id));

        List<?> list = query.getResultList();

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

//    @RequestMapping(method = GET, value = "products/search/findByProduct")
//    public @ResponseBody
//    ResponseEntity<?> findByProduct(@QuerydslPredicate(root = Product.class) Predicate predicate,
//            Pageable pageable, PagedResourcesAssembler assembler,
//            @RequestParam MultiValueMap<String, String> parameters) {
//
//        Page<Product> list = repo.findAll(predicate, pageable);
//
//        return new ResponseEntity<>(assembler.toResource(list), HttpStatus.OK);
//    }

}
