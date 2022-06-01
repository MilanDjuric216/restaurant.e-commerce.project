package main;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dao.CategoryDAO;
import main.dao.EmployeeDAO;
import main.dao.ProductDAO;
import main.dao.ReservationDAO;
import main.dao.TagDAO;
import main.entity.Category;
import main.entity.Product;
import main.entity.Reservation;

@Controller
@RequestMapping("/")
public class FrontController {

	@Autowired
	private CategoryDAO categoryDAO;
	@Autowired
	private ReservationDAO reservationDAO;
	@Autowired
	private ProductDAO productDAO;
	@Autowired
	private TagDAO tagDAO;
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@RequestMapping({"/", "index-page"})
	public String getIndexPage(Model model) {
		
		model.addAttribute("categories", categoryDAO.getCategoriesOnMainPage());
		model.addAttribute("reservation", new Reservation());
		
		return "front/index-page";
	}
	
	
	
	@RequestMapping("reservation-save")
	public String saveReservation(@Valid @ModelAttribute Reservation reservation, BindingResult result, Model model) {
		
		if (result.hasErrors()) {
			model.addAttribute("categories", categoryDAO.getCategoriesOnMainPage());
			
			return "front/index-page";
		}
		
		reservationDAO.saveReservation(reservation);
		
		return "redirect: index-page";
	}
	
	
	@RequestMapping("/about-page")
	public String getAboutPage(Model model) {
		
		model.addAttribute("employeeList", employeeDAO.getEmployeeList());
		
		return "front/about-page";
	}
	
	@RequestMapping("/menu-page")
	public String getMenuPage(Model model) {
		
		model.addAttribute("categoryList", categoryDAO.getCategoriesOnMenu());
		
		return "front/menu-page";
	}
	
	@RequestMapping("/products-page")
	public String getProductsPage(Model model) {
		
		List<Product> list = productDAO.getProductList();
		
		model.addAttribute("productList", list);
		model.addAttribute("categoryList", categoryDAO.getCategoriesForFilter());
		model.addAttribute("tagList", tagDAO.getTagListWithProducts());
		
		return "front/products-page";
	}
	
	
	@RequestMapping("/products-page-filter")
	public String getProductPageFilter(@RequestParam int id, @RequestParam String type, Model model) {
		
		if (type.equalsIgnoreCase("category")) {
			model.addAttribute("productList", productDAO.getProductListByCategory(id));
			
			model.addAttribute("categoryList", categoryDAO.getCategoriesForFilter());
			model.addAttribute("tagList", tagDAO.getTagListWithProducts());
			
		}else if (type.equalsIgnoreCase("tag")) {
			model.addAttribute("productList", productDAO.getProductListByTag(id));
			
			model.addAttribute("categoryList", categoryDAO.getCategoriesForFilter());
			model.addAttribute("tagList", tagDAO.getTagListWithProducts());
		}
		
		return "front/products-page";
	}
	
	
	@RequestMapping("/products-page-order")
	public String getProductListOrderPage(@RequestParam int orderBy, Model model) {
		model.addAttribute("categoryList", categoryDAO.getCategoriesForFilter());
		model.addAttribute("tagList", tagDAO.getTagListWithProducts());
		
		model.addAttribute("productList", productDAO.getProductList(orderBy));
		
		return "front/products-page";
	}
	
	@RequestMapping("/product-item-page")
	public String getProductItem(@RequestParam int id, Model model) {
		
		Product p = productDAO.getProductWithTag(id);
		
		model.addAttribute("product", p);
		model.addAttribute("related", productDAO.getProductListByCategory(p.getCategory().getId()));
		
		
		return "front/product-item-page";
	}
	
}
