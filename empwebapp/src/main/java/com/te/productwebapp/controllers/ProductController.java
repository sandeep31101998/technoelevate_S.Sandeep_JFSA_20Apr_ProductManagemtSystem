package com.te.productwebapp.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.te.productwebapp.beans.Admin;
import com.te.productwebapp.beans.Products;
import com.te.productwebapp.service.ProductService;

@Controller
public class ProductController {

	
	//date formatter
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		System.out.println("init Binder");
		CustomDateEditor editor = new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true);
		binder.registerCustomEditor(Date.class, editor);
	}

	@Autowired
	private ProductService service;

	@GetMapping("/login")
	public String getEmpForm() {
		return "AdminLogin";
	}// getEmpForm

	@PostMapping("/login")
	public String authenticate(int id, String password, HttpServletRequest request, ModelMap map) {
		Admin admin = service.authenticate(id, password);
		System.out.println(admin);
		if (admin != null) {
			HttpSession session = request.getSession();
			session.setAttribute("loggedIn", admin);
//			session.setMaxInactiveInterval(3600);
			map.addAttribute("name", admin.getAname());
			return "AdminHome";
		} else {
			map.addAttribute("errMsg", "Invalid Credentials");
			return "AdminLogin";
		}
	}// authenticate

	@GetMapping("/searchPage")
	public String getSearchPage(HttpSession session, ModelMap map) {
		Admin admin = (Admin) session.getAttribute("loggedIn");
		System.out.println(admin);
		if (admin != null) {
			System.out.println("valid");
			return "ProductSearchPage";
		} else {
			map.addAttribute("errMsg", "Please Login First");
			return "AdminLogin";
		}
	}// getSearchPage

	@GetMapping("/search")
	public String getProductData(int id,
			@SessionAttribute(name = "loggedIn", required = false) Admin admin, ModelMap map) {
		System.out.println(id);
		if (admin != null) {
			Products product = service.getProductData(id);
			if (admin != null) {
				map.addAttribute("id", product);
			} else {
				map.addAttribute("errMsg", "Product not Found");
			}
			return "ProductSearchPage";
		} else {
			map.addAttribute("errMsg", "Please Login First");
			return "AdminLogin";
		}
	}

	@GetMapping("/logout")
	public String logout(HttpSession session, ModelMap map) {
		session.invalidate();
		map.addAttribute("msg", "Admin logout successfull");
		return "AdminLogin";
	}// logout

	@GetMapping("/getDeleteForm")
	public String getDeleteForm(@SessionAttribute(name = "loggedIn", required = false) Admin admin,
			ModelMap map) {
		if (admin != null) {
			return "deleteProduct";
		} else {
			map.addAttribute("errMsg", "Please Login First");
			return "AdminLogin";
		}
	}//

	@GetMapping("/delete")
	public String deleteData(int id, @SessionAttribute(name = "loggedIn", required = false) Admin admin,
			ModelMap map) {
		if (admin != null) {
			if (service.deleteProductData(id)) {
				map.addAttribute("msg", "Product Deleted successfully for id : " + id);
			} else {
				map.addAttribute("msg", "Could not find Product for id : " + id);
			}
			return "deleteProduct";
		} else {
			map.addAttribute("errMsg", "Please Login First");
			return "AdminLogin";
		}

	}//

	@GetMapping("/addProducts")
	public String getAddFrom(@SessionAttribute(name = "loggedIn", required = false)  Admin admin,
			ModelMap map) {
		if (admin != null) {
			return "insertProduct";
		} else {
			map.addAttribute("errMsg", "Please Login First");
			return "AdminLogin";
		}

	}//

	@PostMapping("/add")
	public String addProduct(Products product,
			@SessionAttribute(name = "loggedIn", required = false) Admin admin, ModelMap map) {
		if (admin != null) {
			if (service.addProduct(product)) {
				map.addAttribute("msg", "Product Successfully Inserted");
			} else {
				map.addAttribute("msg", "Failed to Insert Product");
			}
			return "insertProduct";
		} else {
			map.addAttribute("errMsg", "Please Login First");
			return "AdminLogin";
		}

	}// add Employee

	@GetMapping("/updateProducts")
	public String getUpadatePage(@SessionAttribute(name = "loggedIn", required = false) Admin admin,
			ModelMap map) {
		if (admin != null) {
			map.addAttribute("id", admin);
			return "updateProduct";
		} else {
			map.addAttribute("errMsg", "Please Login First");
			return "AdminLogin";

		}
	}//

	@PostMapping("/update")
	public String updateProductData(@SessionAttribute(name = "loggedIn", required = false) Admin admin,
			ModelMap map, Products product) {
		System.out.println(product);
		if (admin != null) {
			if (service.updateRecord(product)) {
				map.addAttribute("msg", "Updated Product Successfully");
				map.addAttribute("id", product);
			} else {
				map.addAttribute("msg", "Updation Failed");
				map.addAttribute("id", product);
			}
			return "updateProduct";
		} else {
			map.addAttribute("errMsg", "Please Login First");
			return "AdminLogin";
		}
	}//

	@GetMapping("/getAll")
	public String getAllRecords(@SessionAttribute(name = "loggedIn", required = false) Admin admin,
			ModelMap map) {
		if (admin != null) {
			List<Products> product = service.getAllProduct();
			if (product != null) {
				
				map.addAttribute("infos", product);
			}else {
				map.addAttribute("errMsg", "No Products Found");
			}
			map.addAttribute("name", admin.getAname());	
			return "AdminHome";
		} else {
			map.addAttribute("errMsg", "Please Login First");
			return "AdminLogin";
		}
	}

}
