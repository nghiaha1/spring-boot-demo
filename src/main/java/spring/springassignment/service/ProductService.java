package spring.springassignment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import spring.springassignment.entity.Order;
import spring.springassignment.entity.Product;
import spring.springassignment.entity.search.OrderSpecification;
import spring.springassignment.entity.search.SearchBody;
import spring.springassignment.entity.search.SearchCriteria;
import spring.springassignment.repository.ProductRepository;
import spring.springassignment.util.ConvertDateHelper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static spring.springassignment.entity.search.SearchCriteriaOperator.*;

@Service
@Transactional
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public Map<String, Object> findAll(SearchBody searchBody){
        Specification specification = Specification.where(null);

        if (searchBody.getNameUser() != null && searchBody.getNameUser().length() > 0 ){
            specification = specification.and(new OrderSpecification(new SearchCriteria("fullName", JOIN_USER, searchBody.getNameUser())));
        }
        if (searchBody.getPhone() != null && searchBody.getPhone().length() > 0){
            specification = specification.and(new OrderSpecification(new SearchCriteria("phone",JOIN_USER, searchBody.getPhone())));
        }
        if (searchBody.getNameProduct() != null && searchBody.getNameProduct().length() > 0){
            specification = specification.and(new OrderSpecification(new SearchCriteria("name",JOIN_DETAIL_PRODUCT, searchBody.getNameProduct())));
        }
        if (searchBody.getOrderId() != null && searchBody.getOrderId().length() > 0){
            specification = specification.and(new OrderSpecification(new SearchCriteria("id", LIKE,searchBody.getOrderId())));
        }
        if (searchBody.getStart() != null && searchBody.getStart().length() > 0){
//            log.info("check start: " + orderSearchBody.getStart() );
//            log.info("Check Start begin" + searchBody.getStart());

            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getStart());
//            log.info("Check Start" + date);
//            log.info("check start convert date: " + date );
            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", GREATER_THAN_OR_EQUALS,date)));
        }
        if (searchBody.getEnd() != null && searchBody.getEnd().length() > 0){
            LocalDateTime date = ConvertDateHelper.convertStringToLocalDateTime(searchBody.getEnd());
            specification = specification.and(new OrderSpecification(new SearchCriteria("createdAt", LESS_THAN_OR_EQUALS,date)));
        }

        Sort sort= Sort.by(Sort.Order.asc("id"));
        if (searchBody.getSort() !=null && searchBody.getSort().length() >0){
            if (searchBody.getSort().contains("desc")){
                sort = Sort.by(Sort.Order.desc("id"));
            }
        }
        Pageable pageable = PageRequest.of(searchBody.getPage() -1, searchBody.getLimit(),sort );
        Page<Order> pageOrder = productRepository.findAll(specification,pageable);
        List<Order> orderList = pageOrder.getContent();
        Map<String, Object> responses = new HashMap<>();
        responses.put("content",orderList);
        responses.put("currentPage",pageOrder.getNumber() + 1);
        responses.put("totalItems",pageOrder.getTotalElements());
        responses.put("totalPage",pageOrder.getTotalPages());
        return responses;
    }

    public Product update(Product obj) {
        return productRepository.save(obj);
    }

    public Optional<Product> findById(String id) {
        return productRepository.findById(id);
    }

    public void deleteById(String id) {
        productRepository.deleteById(id);
    }
}
