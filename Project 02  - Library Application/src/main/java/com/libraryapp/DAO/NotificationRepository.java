package com.libraryapp.DAO;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.libraryapp.entities.Notification;

@Repository
public interface NotificationRepository extends CrudRepository<Notification, Long> {

}
