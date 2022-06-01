package main.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.entity.Category;
import main.entity.Product;

@Repository
public class CategoryDAOUImpl implements CategoryDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public List<Category> getCategoryList() {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Category> query = session.createQuery("from Category", Category.class);
		
		List<Category> categoryList = query.getResultList();
		
		
		return categoryList;
	}
	
	@Transactional
	@Override
	public void saveCategory(Category category) {
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(category);
		
	}

	
	@Transactional
	@Override
	public Category getCategory(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Category category = session.get(Category.class, id);
		
		return category;
	}
	
	@Transactional
	@Override
	public void deleteCategory(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("delete from Category where id=:id");
		query.setParameter("id", id);
		
		query.executeUpdate();
	}

	@Transactional
	@Override
	public List<Category> getCategoriesOnMainPage() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Category> query = session.createQuery("select c from Category c where c.isOnMainPage = 1");
		
		List<Category> list = query.getResultList();
		
		for (Category cat : list) {
			
			Query<Product> prodQuery = session.createQuery("select p from Product p where p.isOnMainPage = 1 AND p.category.id = :id");
			prodQuery.setParameter("id", cat.getId());
			
			cat.setProductsOnMainPage(prodQuery.getResultList());
		}
		
		return list;
	}

	@Transactional
	@Override
	public List<Category> getCategoriesOnMenu() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Category> query = session.createQuery("from Category");
		
		List<Category> list = query.getResultList();
		
		for (Category cat : list) {
			
			Query<Product> prodQuery = session.createQuery("select p from Product p where p.isOnMenu = 1 AND p.category.id = :id");
			prodQuery.setParameter("id", cat.getId());
			
			cat.setProductsOnMainPage(prodQuery.getResultList());
		}
		
		return list;
	}

	@Transactional
	@Override
	public List<Category> getCategoriesForFilter() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Category> query = session.createQuery("from Category");
		
		List<Category> list = query.getResultList();
		
		for(Category cat : list) {
			
			Query queryCount = session.createQuery("select count(product.id) from Product product where product.category.id = :id");
			queryCount.setParameter("id", cat.getId());
			
			cat.setCount((long) queryCount.uniqueResult());
		}
		
		return list;
	}

}
