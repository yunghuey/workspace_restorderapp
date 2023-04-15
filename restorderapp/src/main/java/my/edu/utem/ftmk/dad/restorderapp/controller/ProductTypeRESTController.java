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

import my.edu.utem.ftmk.dad.restorderapp.model.ProductType;
import my.edu.utem.ftmk.dad.restorderapp.repository.ProductTypeRepository;

@RestController
@RequestMapping("/api/producttypes")
public class ProductTypeRESTController {
	@Autowired
	private ProductTypeRepository producttypeRepo;
	
	@GetMapping
	public List<ProductType> getProductTypes(){
		return producttypeRepo.findAll();
	}
	
	@GetMapping("{productTypeId}")
	public ProductType getProductType(@PathVariable long productTypeId) {
		ProductType producttype = producttypeRepo.findById(productTypeId).get();
		return producttype;
	}
	
	@PostMapping()
	public ProductType insertProductType(@RequestBody ProductType producttype) {
		return producttypeRepo.save(producttype);
	}
	
	@PutMapping()
	public ProductType updateProductType(@RequestBody ProductType producttype) {
		return producttypeRepo.save(producttype);
	}
	
	@DeleteMapping("{productTypeId}")
	public ResponseEntity<HttpStatus> deleteProductType(@PathVariable long productTypeId){
		producttypeRepo.deleteById(productTypeId);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
