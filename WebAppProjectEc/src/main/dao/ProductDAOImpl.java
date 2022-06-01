package main.dao;

import java.util.List;

import org.hibernate.query.Query;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import main.entity.Product;
import main.entity.Tag;

@Repository
public class ProductDAOImpl implements ProductDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public List<Product> getProductList() {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query<Product> query = session.createQuery("from Product", Product.class);
		
		List<Product> list = query.getResultList();
		
		return list;
	}

	@Transactional
	@Override
	public Product getProduct(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Product product = session.get(Product.class, id);
		
		return product;
	}
	
	@Transactional
	@Override
	public Product getProductWithTag(int id) {
		
		Session session = sessionFactory.getCurrentSession();
		
		Product product = session.get(Product.class, id);
		Hibernate.initialize(product.getTags());
		
		return product;
	}
	
	@Transactional
	@Override
	public void saveProduct(Product product) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(product);
	}
	
	@Transactional
	@Override
	public void deleteProduct(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("delete from Product where id=:id");
		query.setParameter("id", id);
		
		query.executeUpdate();
		
	}

	@Transactional
	@Override
	public List<Product> getProductListByCategory(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Product> query = session.createQuery("from Product product where product.category.id = :id");
		query.setParameter("id", id);
		
		return query.getResultList();
	}
	
	
	@Transactional
	@Override
	public List<Product> getProductListByTag(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		Tag tag = session.get(Tag.class, id);
		Hibernate.initialize(tag.getProducts());
		
		return tag.getProducts();
	}
	
	@Transactional
	@Override
	public List<Product> getProductList(int orderBy) {
		Session session = sessionFactory.getCurrentSession();
		
		Query<Product> query;
		
		if (orderBy == 1) {
			query = session.createQuery("from Product p order by p.name", Product.class);
		}else if (orderBy == 2) {
			query = session.createQuery("from Product p order by p.price", Product.class);
		}else{
			query = session.createQuery("from Product p order by p.id", Product.class);
		}
		
		List<Product> list = query.getResultList();
		
		return list;
	}

}
