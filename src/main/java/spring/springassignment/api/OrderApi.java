package spring.springassignment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.springassignment.entity.Order;
import spring.springassignment.entity.search.SearchBody;
import spring.springassignment.repository.OrderRepository;
import spring.springassignment.service.OrderService;

@RestController
@RequestMapping(path = "api/v1/orders")
@CrossOrigin(origins = "*")
public class OrderApi {

    final OrderService orderService;

    public OrderApi(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> findAll(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "orderId", required = false) String orderId,
            @RequestParam(name = "nameUser", required = false) String nameUser,
            @RequestParam(name = "phone", required = false) String phone,
            @RequestParam(name = "nameProduct", required = false) String nameProduct,
            @RequestParam(name = "sort", required = false) String sort,
            @RequestParam(name = "start", required = false) String start,
            @RequestParam(name = "end", required = false) String end
    ) {
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(limit)
                .withOrderId(orderId)
                .withPhone(phone)
                .withNameUser(nameUser)
                .withNameProduct(nameProduct)
                .withSort(sort)
                .withStart(start)
                .withEnd(end)
                .build();
        return ResponseEntity.ok(orderService.findAll(searchBody));
    }
}
