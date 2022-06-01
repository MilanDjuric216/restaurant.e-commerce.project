package main.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import main.entity.Reservation;

@Repository
public class ReservationDAOImpl implements ReservationDAO{

	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	@Override
	public void saveReservation(Reservation reservation) {
		
		Session session = sessionFactory.getCurrentSession();
		
		session.saveOrUpdate(reservation);
	}

	@Transactional
	@Override
	public List<Reservation> getAllReservation() {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("from Reservation");
		
		
		return query.getResultList();
	}

	@Transactional
	@Override
	public long getUnreadReservationCount() {
		
		Session session = sessionFactory.getCurrentSession();
		
		Query query = session.createQuery("select count(*) from Reservation r where r.isSeen = 0");
		
		return (long) query.uniqueResult();
	}

	@Transactional
	@Override
	public Reservation getReservation(int id) {
		Session session = sessionFactory.getCurrentSession();
		
		
		return session.get(Reservation.class, id);
	}

}
