package com.example.msdealer.repository;

import com.example.msdealer.dto.request.PageSizeDto;
import com.example.msdealer.dto.request.ProductFilterRequestDto;
import com.example.msdealer.entity.ProductEntity;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductSeacrchCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ProductSeacrchCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<ProductEntity> findAllWithFilters(PageSizeDto pageSizeDto,
                                                  ProductFilterRequestDto productFilterRequestDto){
        CriteriaQuery<ProductEntity> criteriaQuery = criteriaBuilder.createQuery(ProductEntity.class);
        Root<ProductEntity> employeeRoot = criteriaQuery.from(ProductEntity.class);
        Predicate predicate = getPredicate(productFilterRequestDto, employeeRoot);
        criteriaQuery.where(predicate);
        setOrder(pageSizeDto, criteriaQuery, employeeRoot);

        TypedQuery<ProductEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageSizeDto.getPageNumber() * pageSizeDto.getPageSize());
        typedQuery.setMaxResults(pageSizeDto.getPageSize());

        Pageable pageable = getPageable(pageSizeDto);

        long employeesCount = getEmployeesCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, employeesCount);
    }

    private Predicate getPredicate(ProductFilterRequestDto productFilterRequestDto,
                                   Root<ProductEntity> employeeRoot) {
        List<Predicate> predicates = new ArrayList<>();
        if(Objects.nonNull(productFilterRequestDto.getName())){
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("name"),
                            "%" + productFilterRequestDto.getName() + "%")
            );
        }
        if(Objects.nonNull(productFilterRequestDto.getBrand())){
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("brand"),
                            "%" + productFilterRequestDto.getBrand() + "%")
            );
        }
        if(Objects.nonNull(productFilterRequestDto.getCategoryName())){
            predicates.add(
                    criteriaBuilder.like(employeeRoot.get("categoryName"),
                            "%" + productFilterRequestDto.getCategoryName()+ "%")
            );
        }
        if(Objects.nonNull(productFilterRequestDto.getMaxQuantity())){
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(employeeRoot.get("maxQuantity"),
                              productFilterRequestDto.getMaxQuantity())
            );
        }
        if(Objects.nonNull(productFilterRequestDto.getMinQuantity())){
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(employeeRoot.get("minQuantity"),
                            productFilterRequestDto.getMinQuantity())
            );
        }
        if(Objects.nonNull(productFilterRequestDto.getMinAmount())){
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(employeeRoot.get("minAmount"),
                            productFilterRequestDto.getMinAmount())
            );
        }
        if(Objects.nonNull(productFilterRequestDto.getMaxAmount())){
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(employeeRoot.get("maxAmount"),
                            productFilterRequestDto.getMaxAmount())
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(PageSizeDto pageSizeDto,
                          CriteriaQuery<ProductEntity> criteriaQuery,
                          Root<ProductEntity> productEntityRoot) {
        if(pageSizeDto.getSortDirection().equals(Sort.Direction.ASC)){
            criteriaQuery.orderBy(criteriaBuilder.asc(productEntityRoot.get(pageSizeDto.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(productEntityRoot.get(pageSizeDto.getSortBy())));
        }
    }

    private Pageable getPageable(PageSizeDto employeePage) {
        Sort sort = Sort.by(employeePage.getSortDirection(), employeePage.getSortBy());
        return PageRequest.of(employeePage.getPageNumber(),employeePage.getPageSize(), sort);
    }

    private long getEmployeesCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<ProductEntity> countRoot = countQuery.from(ProductEntity.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
