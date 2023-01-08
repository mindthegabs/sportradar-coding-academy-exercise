package com.sportradar.academy.service;

import com.sportradar.academy.dao.EventDAO;
import com.sportradar.academy.dto.EventDTO;
import com.sportradar.academy.exception.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SimpleEventService implements EventService {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private EventDAO eventDAO;

    public List<EventDTO> getEvents(Boolean orderedBySport) throws PersistenceException {
        return eventDAO.getEvents(orderedBySport);
    }

    public void createEvent(EventDTO event) throws PersistenceException{
        validateEvent(event);
        eventDAO.create(event);
    }

    /**
     * validates the user inputs
     * @param event to be validated
     */
    private void validateEvent(EventDTO event) {
        //check if date and time are in the future
        if (event.getStartDateTime() == null){
            LOG.error("StartDateTime is null.");
            throw new IllegalArgumentException("StartDateTime must not be empty!");
        }
        if (!event.getStartDateTime().isAfter(LocalDateTime.now())){
            LOG.error("Event Date and Time are in the past.");
            throw new IllegalArgumentException("Event Date and Time must be in the future!");
        }

        //check if sport is empty
        if (event.getSportName().isEmpty()){
            LOG.error("Sport is empty");
            throw new IllegalArgumentException("Sport must not be empty!");
        }

        //check if either competitors or description fields are filled
        if (event.getDescription().isEmpty() && (event.getCompetitor1Name().isEmpty() || event.getCompetitor2Name().isEmpty())){
            LOG.error("Description and competitors are empty");
            throw new IllegalArgumentException("Either description or both competitors must be filled!");
        }

    }
}
