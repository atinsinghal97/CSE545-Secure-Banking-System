package bankApp.repositories;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;

import bankApp.model.*;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

	public Iterable<Appointment> findAppointmentsByDate(Date date);
	public Iterable<Appointment> findAppointmentsByUser(String username);
	
}
