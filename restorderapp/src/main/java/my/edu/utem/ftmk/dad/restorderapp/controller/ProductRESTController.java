package my.edu.utem.ftmk.dad.restorderapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import my.edu.utem.ftmk.dad.restorderapp.model.Product;
import my.edu.utem.ftmk.dad.restorderapp.repository.ProductRepository;

@RestController
@RequestMapping("/api/products")
public class ProductRESTController {
	@Autowired
	private ProductRepository productRepo;
	
	@GetMapping
	public List<Product> getProducts(){
		 return productRepo.findAll();
	}
		
	@GetMapping("{productId}")
	public Product getProduct(@PathVariable long productId) {
		Product product = productRepo.findById(productId).get();
		return product;
	}
		
	@PostMapping
	public Product insertProduct(@RequestBody Product product) {
		return productRepo.save(product);
	}
	
	@PutMapping()
	public Product updateProduct(@RequestBody Product product) {
		return productRepo.save(product);
	}
	
	@DeleteMapping("{productId}")
	public ResponseEntity<HttpStatus> deleteProduct(@PathVariable long productId){
		productRepo.deleteById(productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
