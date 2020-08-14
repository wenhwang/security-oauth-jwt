package org.wangwh.code.auth.services.order.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

	@GetMapping("/create")
	@PreAuthorize("hasAuthority('writer')")
	public String create(){
		return "create order";
	}

	@GetMapping("/list")
	@PreAuthorize("hasAuthority('read')")
	public String list(){
		return "Get all orders";
	}

	@GetMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('delete')")
	public String delete(@PathVariable("id") long id){
		return "delete orders" + id;
	}
}
