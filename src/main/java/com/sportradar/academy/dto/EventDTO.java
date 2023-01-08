package com.sportradar.academy.dto;

import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
/**
 * Holds all the information of an event.
 */
public class EventDTO {
    private Integer id;
    private String sportName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDateTime;
    private String description;
    private String competitor1Name;
    private String competitor2Name;
    private String venueName;


    public EventDTO() {
    }

    public EventDTO(Integer id, String sportName, LocalDateTime startDateTime, String description, String competitor1Name, String competitor2Name, String venueName) {
        this.id = id;
        this.sportName = sportName;
        this.startDateTime = startDateTime;
        this.description = description;
        this.competitor1Name = competitor1Name;
        this.competitor2Name = competitor2Name;
        this.venueName = venueName;
    }

    public EventDTO(String sportName, LocalDateTime startDateTime, String description, String competitor1Name, String competitor2Name, String venueName) {
        this.sportName = sportName;
        this.startDateTime = startDateTime;
        this.description = description;
        this.competitor1Name = competitor1Name;
        this.competitor2Name = competitor2Name;
        this.venueName = venueName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }
    public LocalDateTime getStartDateTime(){
        return startDateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCompetitor1Name() {
        return competitor1Name;
    }

    public void setCompetitor1Name(String competitor1Name) {
        this.competitor1Name = competitor1Name;
    }

    public String getCompetitor2Name() {
        return competitor2Name;
    }

    public void setCompetitor2Name(String competitor2Name) {
        this.competitor2Name = competitor2Name;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }



    @Override
    public String toString(){
        return "Event{" +
                "id = " + id +
                ", sport = " + sportName +
                ", start time = " + startDateTime +
                ", description = " + description +
                ", competitor 1 = " + competitor1Name +
                ", competitor 2 = " + competitor2Name +
                ", venue name = " + venueName +
                '}';
    }
    
}
