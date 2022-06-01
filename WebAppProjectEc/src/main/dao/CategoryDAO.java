package main.dao;

import java.util.List;

import main.entity.Category;

public interface CategoryDAO {

	public List<Category> getCategoryList();
	
	public void saveCategory(Category category);
	
	public Category getCategory(int id);
	
	public void deleteCategory(int id);
	
	public List<Category> getCategoriesOnMainPage();
	
	public List<Category> getCategoriesOnMenu();
	
	public List<Category> getCategoriesForFilter();
}
