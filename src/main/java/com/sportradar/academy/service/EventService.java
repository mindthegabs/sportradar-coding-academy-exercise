package com.sportradar.academy.service;

import com.sportradar.academy.dto.EventDTO;
import com.sportradar.academy.exception.PersistenceException;

import java.util.List;

/**
 * The EventService interface holds the methods of the service Layer.
 * This is mainly for validation and to call the persistence layer.
 */
public interface EventService {


    /**
     * gets all events of the database
     * @param orderedBySport true if the events should be ordered by sport
     * @return a list of all events in the database
     * @throws PersistenceException if an error in the database occurs
     */
    List<EventDTO> getEvents(Boolean orderedBySport) throws PersistenceException;

    /**
     * validates inputs and creates a new event in the database
     * @param event event which should be added to the database
     * @throws PersistenceException if an error caused by the database occurs
     */
    void createEvent(EventDTO event) throws PersistenceException;


}
