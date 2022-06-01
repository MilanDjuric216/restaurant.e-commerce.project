package main.dao;

import java.util.List;

import main.entity.Reservation;

public interface ReservationDAO {

	public void saveReservation(Reservation reservation);
	
	public List<Reservation> getAllReservation();
	
	public long getUnreadReservationCount();
	
	public Reservation getReservation(int id);
}
