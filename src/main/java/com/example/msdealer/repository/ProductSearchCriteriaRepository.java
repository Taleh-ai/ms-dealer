package com.example.msdealer.repository;

import com.example.msdealer.dto.request.PageSizeDto;
import com.example.msdealer.dto.request.ProductFilterRequestDto;
import com.example.msdealer.entity.ProductCategoryEntity;
import com.example.msdealer.entity.ProductEntity;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ProductSearchCriteriaRepository {

    private final EntityManager entityManager;
    private final CriteriaBuilder criteriaBuilder;

    public ProductSearchCriteriaRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    public Page<ProductEntity> findAllWithFilters(PageSizeDto pageSizeDto,
                                                  ProductFilterRequestDto productFilterRequestDto){
        CriteriaQuery<ProductEntity> criteriaQuery = criteriaBuilder.createQuery(ProductEntity.class);
        Root<ProductEntity> productEntityRoot = criteriaQuery.from(ProductEntity.class);
        Predicate predicate = getPredicate(productFilterRequestDto, productEntityRoot);
        criteriaQuery.where(predicate);
        setOrder(pageSizeDto, criteriaQuery, productEntityRoot);

        TypedQuery<ProductEntity> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult(pageSizeDto.getPageNumber() * pageSizeDto.getPageSize());
        typedQuery.setMaxResults(pageSizeDto.getPageSize());

        Pageable pageable = getPageable(pageSizeDto);

        long productsCount = getProductsCount(predicate);

        return new PageImpl<>(typedQuery.getResultList(), pageable, productsCount);
    }

    private Predicate getPredicate(ProductFilterRequestDto productFilterRequestDto,
                                   Root<ProductEntity> productEntityRoot) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(productFilterRequestDto.getMaxQuantity())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(productEntityRoot.get("quantity"),
                            productFilterRequestDto.getMaxQuantity())
            );
        }
        if (Objects.nonNull(productFilterRequestDto.getMinQuantity())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(productEntityRoot.get("quantity"),
                            productFilterRequestDto.getMinQuantity())
            );
        }
        if (Objects.nonNull(productFilterRequestDto.getMinAmount())) {
            predicates.add(
                    criteriaBuilder.greaterThanOrEqualTo(productEntityRoot.get("price"),
                            productFilterRequestDto.getMinAmount())
            );
        }
        if (Objects.nonNull(productFilterRequestDto.getMaxAmount())) {
            predicates.add(
                    criteriaBuilder.lessThanOrEqualTo(productEntityRoot.get("price"),
                            productFilterRequestDto.getMaxAmount())
            );
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private void setOrder(PageSizeDto pageSizeDto,
                          CriteriaQuery<ProductEntity> criteriaQuery,
                          Root<ProductEntity> productEntityRoot) {
        if (pageSizeDto.getSortDirection().equals(Sort.Direction.ASC)) {
            criteriaQuery.orderBy(criteriaBuilder.asc(productEntityRoot.get(pageSizeDto.getSortBy())));
        } else {
            criteriaQuery.orderBy(criteriaBuilder.desc(productEntityRoot.get(pageSizeDto.getSortBy())));
        }
    }

    private Pageable getPageable(PageSizeDto pageSizeDto) {
        Sort sort = Sort.by(pageSizeDto.getSortDirection(), pageSizeDto.getSortBy());
        return PageRequest.of(pageSizeDto.getPageNumber(), pageSizeDto.getPageSize(), sort);
    }

    private long getProductsCount(Predicate predicate) {
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<ProductEntity> countRoot = countQuery.from(ProductEntity.class);
        countQuery.select(criteriaBuilder.count(countRoot)).where(predicate);
        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
