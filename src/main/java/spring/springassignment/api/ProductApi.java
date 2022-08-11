package spring.springassignment.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.springassignment.entity.Product;
import spring.springassignment.entity.search.SearchBody;
import spring.springassignment.service.ProductService;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1/products")
@CrossOrigin(origins = "*")
public class ProductApi {
    @Autowired
    ProductService productService;

    @RequestMapping(method = RequestMethod.POST, path = "list")
    public ResponseEntity<?> findAll(@RequestParam(name = "page", defaultValue = "1") int page,
                                     @RequestParam(name = "limit", defaultValue = "10") int limit) {
        SearchBody searchBody = SearchBody.SearchBodyBuilder.aSearchBody()
                .withPage(page)
                .withLimit(limit)
                .build();
        return ResponseEntity.ok(productService.findAll(searchBody));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Product obj) {
        return ResponseEntity.ok(productService.update(obj));
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<?> findById(@PathVariable String id) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (!optionalProduct.isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(optionalProduct.get());
    }

    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ResponseEntity<?> update(@PathVariable String id,
                                    @RequestBody Product obj) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isPresent()) {
            Product existingProduct = optionalProduct.get();
            existingProduct.setName(obj.getName());
            existingProduct.setDescription(obj.getDescription());
            existingProduct.setDetail(obj.getDetail());
            existingProduct.setPrice(obj.getPrice());
            existingProduct.setThumbnails(obj.getThumbnails());
            existingProduct.setCategories(obj.getCategories());
            return ResponseEntity.ok(productService.update(existingProduct));
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<?> deleteById(@PathVariable String id) {
        Optional<Product> optionalProduct = productService.findById(id);
        if (optionalProduct.isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
