package my.edu.utem.ftmk.dad.restorderapp.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import my.edu.utem.ftmk.dad.restorderapp.model.OrderType;

@Controller
public class OrderTypeMenuController {
	
	private String defaultURI = "http://localhost:8080/orderapp/api/ordertypes";
	
	/**
	 * This method will update or add an order type
	 * @param orderType
	 * @return
	 */
	@RequestMapping("/ordertype/save")
	public String updateOrderType(@ModelAttribute OrderType orderType) {
		 // create new RestTemplate
		RestTemplate restTemplate = new RestTemplate();
		// create request body
		HttpEntity<OrderType> request = new HttpEntity<OrderType>(orderType);
		
		String orderTypeResponse = "";
		if(orderType.getOrderTypeId()> 0) {
			// update new order type
			// send request as put
			restTemplate.put(defaultURI,request,OrderType.class);
		} else {
			// create new order type
			// send request as POST
			orderTypeResponse = restTemplate.postForObject(defaultURI, request, String.class);
		}
		System.out.println(orderTypeResponse);
		return "redirect:/ordertype/list";
	}
	
	@GetMapping("/ordertype/list")
	public String getOrderTypes(Model model) {
		// URI for GET order types
		String uri = "http://localhost:8080/orderapp/api/ordertypes";
		// get list order types from web service
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<OrderType[]> response = restTemplate.getForEntity(uri, OrderType[].class);
		// parse JSON data to array of object
		OrderType orderTypes[] = response.getBody();
		
		// parse array to list object
		List<OrderType> orderTypeList = Arrays.asList(orderTypes);
		
		// attach list to model
		model.addAttribute("orderTypes", orderTypeList);
				
		return "ordertypes";
	}
	
	/**
	 * This method gets an order type
	 * @param orderTypeId
	 * @param model
	 * @return
	 */
	@GetMapping("/ordertype/{orderTypeId}")
	public String getOrderType(@PathVariable int orderTypeId, Model model) {
		String pageTitle = "New Order Type";
		OrderType orderType = new OrderType();
		
		// this block get an order type to be updated
		if(orderTypeId > 0) {
			// generate new URIand append orderTypeId to it
			String uri = defaultURI + "/" + orderTypeId;
			
			// get an order type from web service
			RestTemplate restTemplate = new RestTemplate();
			orderType = restTemplate.getForObject(uri, OrderType.class);
			
			// new title to the page
			pageTitle = "Edit Order Type";
		}
		// attach value to pass to front end
		model.addAttribute("orderType", orderType);
		model.addAttribute("pageTitle", pageTitle);
		
		return "ordertypeinfo";
	}
	
	/**
	 * This method deletes an order type
	 * @param orderTypeId
	 * @return
	 */
	@RequestMapping("/ordertype/delete/{orderTypeId}")
	public String deleteOrderType(@PathVariable int orderTypeId) {
		// generate new URI
		String uri = defaultURI + "/{orderTypeId}";
		// send delete request and attach the value of orderTypeId into URI
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.delete(uri,
				Map.of("orderTypeId", Integer.toString(orderTypeId)));
		return "redirect:/ordertype/list";
	}
}
