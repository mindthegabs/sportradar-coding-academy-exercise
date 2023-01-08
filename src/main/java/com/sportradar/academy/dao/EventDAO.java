package com.sportradar.academy.dao;


import com.sportradar.academy.dto.EventDTO;
import com.sportradar.academy.exception.PersistenceException;

import java.util.List;

/**
 * Interface holds methods for the Event data access object.
 */
public interface EventDAO {

    /**
     * creates a new event and saves it to the database
     *
     * @param event is the EventDTO object which contains all the values for the creation
     * @throws PersistenceException in case the there is an error during the creating in the database
     */
    void create(EventDTO event) throws PersistenceException;

    /**
     * returns all events
     *
     * @param orderedBySport if true the events will be sorted alphabetically and ascending after the column sport
     *                        if not they will be ordered by date
     * @return a list of EventDTOs
     */
    List<EventDTO> getEvents(boolean orderedBySport) throws PersistenceException;
}