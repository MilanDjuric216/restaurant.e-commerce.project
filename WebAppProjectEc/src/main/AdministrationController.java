package main;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import main.dao.CategoryDAO;
import main.dao.EmployeeDAO;
import main.dao.ProductDAO;
import main.dao.ReservationDAO;
import main.dao.TagDAO;
import main.dao.UserDAO;
import main.entity.Category;
import main.entity.Employee;
import main.entity.Product;
import main.entity.Reservation;
import main.entity.Tag;
import main.entity.User;

@Controller
@RequestMapping("/administration")
public class AdministrationController {

	@Autowired
	private CategoryDAO categoryDAO;
	
	@Autowired
	private TagDAO tagDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ReservationDAO reservationDAO;
	
	@Autowired
	private EmployeeDAO employeeDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	
	@RequestMapping({"/product-list", ""})
	public String getProductList(Model model) {
		
		List<Product> list = productDAO.getProductList();
		
		model.addAttribute("productList", list);
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		return "product-list";
	}
	
	
	@RequestMapping("/product-form")
	public String getProductForm(Model model) {
		Product product = new Product();
		List<Category> categoryList = categoryDAO.getCategoryList();
		List<Tag> tagList = tagDAO.getTagList();
		
		model.addAttribute("product", product);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("tagList", tagList);
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		return "product-form";
	}
	
	@RequestMapping("/product-form-update")
	public String getProductUpdate(@RequestParam int id, Model model) {
		Product product = productDAO.getProductWithTag(id);
		List<Category> list = categoryDAO.getCategoryList();
		List<Tag> tagList = tagDAO.getTagList();
		
		model.addAttribute("product", product);
		model.addAttribute("categoryList", list);
		model.addAttribute("tagList", tagList);
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		return "product-form";
	}
	
	@RequestMapping("/product-save")
	public String saveProduct(@ModelAttribute Product product) {
		
		Category category = categoryDAO.getCategory(product.getCategory().getId());
		
		List<Integer> ids = new ArrayList<Integer>();
		
		
		for(Tag tag : product.getTags()) {
			ids.add(Integer.parseInt(tag.getName()));
		}
		
		List<Tag> tags = tagDAO.getTagsById(ids);
		
		product.setCategory(category);
		product.setTags(tags);
		
		
		productDAO.saveProduct(product);
		
		return "redirect:/administration/product-list";
	}
	
	
	@RequestMapping("/product-delete")
	public String deleteProduct(@RequestParam int id) {
		
		productDAO.deleteProduct(id);
		
		return "redirect:/administration/product-list";
	}
	
	
	//---------------------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping("/category-list")
	public String getCategoryList(Model model) {
		
		List<Category> list = categoryDAO.getCategoryList();
		
		model.addAttribute("categoryList", list);
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		return "category-list";
	}
	
	@RequestMapping("/category-form")
	public String getCategoryForm(Model model) {
		Category category = new Category();
		
		model.addAttribute("category", category);
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		return "category-form";
	}
	
	@RequestMapping("/category-form-update")
	public String getCategoryUpdateForm(@RequestParam int id, Model model) {
		
		Category category = categoryDAO.getCategory(id);
		
		model.addAttribute("category", category);
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		
		return "category-form";
	}
	
	
	@RequestMapping("/category-save")
	public String saveCategoryForm(@Valid @ModelAttribute Category category, BindingResult result) {
		
		if (result.hasErrors()) {
			return "category-form";
		}
		
		categoryDAO.saveCategory(category);
		
		return "redirect:/administration/category-list";
	}
	
	@RequestMapping("/category-delete")
	public String deleteCategory(@RequestParam int id) {
		
		categoryDAO.deleteCategory(id);
		
		return "redirect:/administration/category-list";
	}
	
	//---------------------------------------------------------------------------------------------------------------------------------
	
	@RequestMapping("/tag-list")
	public String getTagList(Model model) {
		
		List<Tag> list = tagDAO.getTagList();
		
		model.addAttribute("tagList", list);
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		return "tag-list";
	}
	
	@RequestMapping("/tag-form")
	public String getTagForm(Model model) {
		
		Tag tag = new Tag();
		
		model.addAttribute("tag", tag);
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		return "tag-form";
	}
	
	@RequestMapping("/tag-form-update")
	public String getTagUpdateForm(@RequestParam int id, Model model) {
		
		Tag tag = tagDAO.getTag(id);
		
		model.addAttribute("tag", tag);
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		return "tag-form";
	}
	
	@RequestMapping("/tag-save")
	public String saveTagForm(@ModelAttribute Tag tag) {
		
		tagDAO.saveTag(tag);
		
		return "redirect:/administration/tag-list";
		
	}
	
	@RequestMapping("/tag-delete")
	public String deleteTag(@RequestParam int id) {
		
		tagDAO.deleteTag(id);
		
		return "redirect:/administration/tag-list";
	}
	
	
	//-----------------------------------------------------------------------------------------------------------
	
	
	@RequestMapping("/reservation-list")
	public String getReservationList(Model model) {
		
		model.addAttribute("reservationList", reservationDAO.getAllReservation());
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		return "reservation-list";
	}
	
	@RequestMapping("/reservation-seen")
	public String getMarkReservationSeen(@RequestParam int id) {
		
		Reservation r = reservationDAO.getReservation(id);
		
		r.setIsSeen(true);
		
		reservationDAO.saveReservation(r);
		
		return "redirect: reservation-list";
	}
	
	//EMPLOYEE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	@RequestMapping("/employee-list")
	public String getEmployeeList(Model model) {
		
		model.addAttribute("employeeList", employeeDAO.getEmployeeList());
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		return "employee-list";
	}
	
	@RequestMapping("/employee-form")
	public String getEmployeeForm(Model model) {
		Employee employee = new Employee();
		
		model.addAttribute("employee", employee);
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		return "employee-form";
	}
	
	@RequestMapping("/employee-form-update")
	public String getEmployeeUpdateForm(@RequestParam int id, Model model) {
		
		Employee employee = employeeDAO.getEmployee(id);
		
		model.addAttribute("employee", employee);
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		
		
		return "employee-form";
	}
	
	@RequestMapping("/employee-save")
	public String saveEmployeeForm(@Valid @ModelAttribute Employee employee, BindingResult result) {
		
		if (result.hasErrors()) {
			return "employee-form";
		}
		
		employeeDAO.saveEmployee(employee);
		
		return "redirect:/administration/employee-list";
	}
	
	@RequestMapping("/employee-delete")
	public String deleteEmployee(@RequestParam int id) {
		
		employeeDAO.deleteEmployee(id);
		
		return "redirect:/administration/employee-list";
	}
	
	//USERS --------------------------------------------------------------
	
	@RequestMapping("/user-list")
	public String getUserPage(Model model) {
		
		model.addAttribute("reservationCount", reservationDAO.getUnreadReservationCount());
		model.addAttribute("userList", userDAO.getUserList());
		
		return "user-list";
	}
	
	@RequestMapping("/user-enable")
	public String enableUser(@RequestParam String username) {
		
		userDAO.enableUser(username);
		
		return "redirect:/administration/user-list";
	}
	
}
