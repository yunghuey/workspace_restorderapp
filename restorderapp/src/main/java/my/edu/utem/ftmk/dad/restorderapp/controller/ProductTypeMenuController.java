package my.edu.utem.ftmk.dad.restorderapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import my.edu.utem.ftmk.dad.restorderapp.model.ProductType;

@Controller
public class ProductTypeMenuController {
	private String defaultURI = "http://localhost:8080/orderapp/api/producttypes";

	@GetMapping("/producttype/list")
	public String getProductTypes(Model model) {
		// URI for GET product types
		String uri = "http://localhost:8080/orderapp/api/producttypes";
		// get list product types from web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ProductType[]> response = restTemplate.getForEntity(uri, ProductType[].class);
		// parse JSON data to array of object
		ProductType productTypes[] = response.getBody();
		
		// parse array to list object
		List<ProductType> productTypeList = Arrays.asList(productTypes);
		
		// attach list to model
		model.addAttribute("productTypes", productTypeList);
				
		return "producttypes";
	}
	
	/**
	 * this method will update or add an product type
	 * @param productType
	 * @return
	 */
	@RequestMapping("/producttype/save")
	public String updateProductType(@ModelAttribute ProductType productType) {
		// create new template 
		RestTemplate restTemplate = new RestTemplate();
		
		// request body
		HttpEntity<ProductType> request = new HttpEntity<ProductType>(productType);
		
		String productTypeResponse = "";

		if (productType.getProductTypeId() > 0) {
			//send request as PUT
			restTemplate.put(defaultURI, request,ProductType.class);
		} else {
			// send request as POST
			productTypeResponse = restTemplate.postForObject(defaultURI, request, String.class);
		}
		System.out.println(productTypeResponse);
		
		// redirect request
		return "redirect:/producttype/list";
	}
	
	/**
	 * this method gets an product type
	 * @param productTypeId
	 * @param model
	 * @return
	 */
	@GetMapping("/producttype/{productTypeId}")
	public String getProductType(@PathVariable Integer productTypeId, Model model) {
		String pageTitle = "New Product Type";
		ProductType productType = new ProductType();
		
		if(productTypeId > 0) {
			String uri = defaultURI + "/" + productTypeId;
			// get product type from web service
			RestTemplate restTemplate = new RestTemplate();
			productType = restTemplate.getForObject(uri, ProductType.class);
			
			// new title of the page
			pageTitle = "Edit Product Type";			
		}
		model.addAttribute("productType", productType);
		model.addAttribute("pageTitle", pageTitle);
		return "producttypeinfo";
	}
	
	/**
	 * this method delete product type
	 * @param productTypeId
	 * @return
	 */
	@RequestMapping("/producttype/delete/{productTypeId}")
	public String deleteProductType(@PathVariable int productTypeId) {
		// generate new URI
		String uri = defaultURI + "/{productTypeId}";
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri,Map.of("productTypeId",Integer.toString(productTypeId)));
		
		return "redirect:/ordertype/list";		
	}
}
