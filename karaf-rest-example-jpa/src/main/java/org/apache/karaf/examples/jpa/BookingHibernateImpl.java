package org.apache.karaf.examples.jpa;

import org.apache.karaf.examples.rest.api.Booking;
import org.apache.karaf.examples.rest.api.BookingService;
import org.osgi.service.component.annotations.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component(service = BookingService.class, immediate = true)
public class BookingHibernateImpl implements BookingService {

  private EntityManager entityManager;

  public BookingHibernateImpl() {
    EntityManagerFactory entityManagerFactory =
        Persistence.createEntityManagerFactory("booking-hibernate");
    this.entityManager = entityManagerFactory.createEntityManager();
  }

  @Override
  public List<Booking> list() {
    try {
      return entityManager.createQuery("SELECT b FROM Booking b", Booking.class).getResultList();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return new ArrayList<>();
  }

  @Override
  public void add(Booking booking) {
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(booking);
      entityManager.flush();
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void add(String flight, String customer) {
    Booking booking = new Booking();
    booking.setCustomer(customer);
    booking.setFlight(flight);
    try {
      entityManager.persist(booking);
      entityManager.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void update(Booking booking) {
    throw new RuntimeException();
  }

  @Override
  public Booking get(Long id) {
    throw new RuntimeException();
  }

  @Override
  public void remove(Long id) {
    throw new RuntimeException();
  }

  public Optional<Booking> save(Booking book) {
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(book);
      entityManager.getTransaction().commit();
      return Optional.of(book);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

}
