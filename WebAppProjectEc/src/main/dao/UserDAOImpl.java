package main.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import main.entity.Product;
import main.entity.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public List<User> getUserList() {
		Session session = sessionFactory.getCurrentSession();
		
		Query<User> query = session.createQuery("from users", User.class);
		
		List<User> list = query.getResultList();
		
		return list;
	}

	@Override
	public User getUserByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteUser(String username) {
		// TODO Auto-generated method stub
		
	}

	@Transactional
	@Override
	public void enableUser(String username) {
		
		Session session = sessionFactory.getCurrentSession();
		
		User user = session.get(User.class, username);
		
		user.setEnabled(!user.getEnabled());
		
		session.saveOrUpdate(user);
		
	}

}
