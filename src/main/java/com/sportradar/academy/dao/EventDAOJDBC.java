package com.sportradar.academy.dao;


import com.sportradar.academy.dto.EventDTO;
import com.sportradar.academy.exception.PersistenceException;
import com.sportradar.academy.util.DBUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.lang.invoke.MethodHandles;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import static java.sql.Types.NULL;

@Component
public class EventDAOJDBC implements EventDAO{
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String sportInsertString = "INSERT INTO sport(name) VALUES(?)";
    private String venueInsertString = "INSERT INTO venue(name) VALUES(?)";
    private String competitorInsertString = "INSERT INTO competitor(name, _sport_id) VALUES(?,?)";
    private String eventInsertString = "INSERT INTO event(_sport_id, date_time, description, _competitor1_id, _competitor2_id, _venue_id) VALUES(?,?,?,?,?,?)";

    @Override
    public void create(EventDTO event) throws PersistenceException {
        LOG.debug("saving event: {}", event);
        if (event == null) {
            LOG.error("Event to be created is null");
            throw new IllegalArgumentException();
        }
        try {
            DBUtil.getConnection().setAutoCommit(false);

            PreparedStatement ps;
            ResultSet generatedKeys;

            //create the sport of the event in the database if it does not exist yet
            Integer sportId;
            if (getSportId(event.getSportName()) == null) {
                ps = DBUtil.getConnection().prepareStatement(sportInsertString, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, event.getSportName());
                ps.executeUpdate();
                generatedKeys = ps.getGeneratedKeys();
                generatedKeys.next();
                sportId = generatedKeys.getInt(1);
            } else sportId = getSportId(event.getSportName());

            //create the venue of the event in the database if it is empty or not null and does not exist yet
            Integer venueId = null;
            if (!event.getVenueName().isEmpty() && event.getVenueName() != null) {
                if (getVenueId(event.getVenueName()) == null) {
                    ps = DBUtil.getConnection().prepareStatement(venueInsertString, Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, event.getVenueName());
                    ps.executeUpdate();
                    generatedKeys = ps.getGeneratedKeys();
                    generatedKeys.next();
                    venueId = generatedKeys.getInt(1);
                }
                //get the venue id from the database if it already exists
                else venueId = getVenueId(event.getVenueName());
            }

            Integer competitor1Id = null;
            Integer competitor2Id = null;
            if ((event.getCompetitor1Name() != null && !event.getCompetitor1Name().isEmpty()) && (event.getCompetitor2Name() != null && !event.getCompetitor2Name().isEmpty())) {
                //create competitor names in the database if they do not exist yet and are not null or empty
                //save the ids in the variable competitor1Id and competitor2Id
                //competitor 1
                if (getCompetitorId(event.getCompetitor1Name()) == null) {
                    competitor1Id = createCompetitor(event.getCompetitor1Name(), sportId);
                }
                else competitor1Id = getCompetitorId((event.getCompetitor1Name()));
                //competitor 2
                if (getCompetitorId(event.getCompetitor2Name()) == null) {
                    competitor2Id = createCompetitor(event.getCompetitor2Name(), sportId);
                }
                else competitor2Id = getCompetitorId((event.getCompetitor2Name()));
            }

            //create the event in the database
            ps = DBUtil.getConnection().prepareStatement(eventInsertString, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, sportId);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            ps.setTimestamp(2, Timestamp.valueOf(event.getStartDateTime().format(dateTimeFormatter)));
            if (event.getDescription() != null && !event.getDescription().isEmpty()) {
                ps.setString(3, event.getDescription());
            } else {
                ps.setNull(3, NULL);
            }
            if (competitor1Id != null && competitor2Id != null) {
                ps.setInt(4, competitor1Id);
                ps.setInt(5, competitor2Id);
            } else {
                ps.setNull(4, NULL);
                ps.setNull(5, NULL);
            }
            if (venueId != null) {
                ps.setInt(6, venueId);
            } else {
                ps.setNull(6, NULL);
            }
            ps.executeUpdate();
            generatedKeys = ps.getGeneratedKeys();
            generatedKeys.next();
            event.setId(generatedKeys.getInt(1));


            DBUtil.getConnection().commit();
            LOG.info("saved event: {}", event);
        } catch (SQLException e) {
            LOG.error("Error while saving event: {}", event);
            e.printStackTrace();
            throw new PersistenceException();

        }
        try {
            DBUtil.getConnection().rollback();
        } catch (SQLException e1) {
            LOG.error("Error while saving the event and trying to rollback the commit", event);
            throw new PersistenceException();

        }

        try {
            DBUtil.getConnection().setAutoCommit(true);
        } catch (
                SQLException e) {
            LOG.error("Error while setting auto commit back to 'true'", event);
            throw new PersistenceException();
        }
    }

    /**
     *
     * @param venueName name of the venue
     * @return venue id if venue name already exists and null if venue does not exist yet
     */
    private Integer getVenueId(String venueName) throws PersistenceException, SQLException {
        String queryVenue = "SELECT venue_id FROM event_calendar.venue WHERE name = ?";
        PreparedStatement ps = DBUtil.getConnection().prepareStatement(queryVenue);
        ps.setString(1, venueName);
        ResultSet resultSet = ps.executeQuery();
        if (!resultSet.next()){
            return null;
        }
        return resultSet.getInt(1);
    }

    /**
     *
     * @param sportName name of the sport
     * @return sport id if sport name already exists and null if sport does not exist yet
     */
    private Integer getSportId(String sportName) throws PersistenceException, SQLException {
        String querySport = "SELECT sport_id FROM event_calendar.sport WHERE name = ?";
        PreparedStatement ps = DBUtil.getConnection().prepareStatement(querySport);
        ps.setString(1, sportName);
        ResultSet resultSet = ps.executeQuery();
        if (!resultSet.next()){
            return null;
        }
        return resultSet.getInt(1);
    }

    /**
     *
     * @param competitorName name of the competitor
     * @return competitor id if competitor name already exists and null if competitor does not exist yet
     */
    private Integer getCompetitorId(String competitorName) throws PersistenceException, SQLException {
        String queryCompetitor = "SELECT competitor_id FROM event_calendar.competitor WHERE name = ?";
        PreparedStatement ps = DBUtil.getConnection().prepareStatement(queryCompetitor);
        ps.setString(1, competitorName);
        ResultSet resultSet = ps.executeQuery();
        if (!resultSet.next()){
            return null;
        }
        return resultSet.getInt(1);
    }

    /**
     * creates a competitor row in the database and returns the competitor id
     * @param competitorName name of the competitor
     * @param sportId id of the sport
     * @return competitor id
     */
    private Integer createCompetitor(String competitorName, int sportId) throws SQLException, PersistenceException {
        PreparedStatement ps = DBUtil.getConnection().prepareStatement(competitorInsertString, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, competitorName);
        ps.setInt(2, sportId);
        ps.executeUpdate();
        ResultSet generatedKeys = ps.getGeneratedKeys();
        generatedKeys.next();
        return generatedKeys.getInt(1);
    }


    @Override
    public List<EventDTO> getEvents(boolean orderedBySport) throws PersistenceException {
        LOG.debug("get all events from the database");
        List<EventDTO> events = new LinkedList<>();
        Statement st;
        try {
            st = DBUtil.getConnection().createStatement();

            String queryEvents;
            queryEvents = "SELECT event_id, date_time, sport.name as 'sport_name', description, c1.name as 'competitor_1', c2.name as 'competitor_2', venue.name as 'venue_name' " +
                    "FROM event " +
                    "JOIN sport " +
                    "ON event._sport_id = sport.sport_id " +
                    "LEFT JOIN competitor c1 " +
                    "ON event._competitor1_id = c1.competitor_id " +
                    "LEFT JOIN competitor c2 " +
                    "ON event._competitor2_id = c2.competitor_id " +
                    "LEFT JOIN venue " +
                    "ON event._venue_id = venue.venue_id ";
            if (orderedBySport) {
                queryEvents += "ORDER BY sport_name, date_time;";
            } else {
                queryEvents += "ORDER BY date_time;";
            }

            ResultSet resultSet;
            resultSet = st.executeQuery(queryEvents);


            while (resultSet.next()) {
                EventDTO e = new EventDTO();

                e.setId(resultSet.getInt(1));
                e.setStartDateTime(resultSet.getTimestamp(2).toLocalDateTime());
                e.setSportName(resultSet.getString(3));
                e.setDescription(resultSet.getString(4));
                e.setCompetitor1Name(resultSet.getString(5));
                e.setCompetitor2Name(resultSet.getString(6));
                e.setVenueName(resultSet.getString(7));

                events.add(e);
            }
        } catch (SQLException e) {
            LOG.error("SQL Exception during getEvents()");
            throw new PersistenceException();
        }
        return events;
    }

}
