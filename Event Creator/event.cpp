/**
 * @file   event.cpp
 * @author Samuel Jackson (slj11@aber.ac.uk)
 * @date 09 March 2013
 * @brief class to hold data about an event.
 */

#include <string>
#include "event.h"
#include "entrant.h"

/**
 * Create a new event and initilise it with a name, date and time.
 * @param name the name of the event
 * @param date the date of the event
 * @param time the time of the event
 */
Event::Event(std::string name, tm date, tm time) {
    this->time = time;
    this->date = date;
    this->name = name;
}

Event::~Event() {
}

/**
 * Add an entrant to this event.
 * @param name the name of the entrant
 * @param id the id of the entrant
 * @param course the if of the entrant's course
 */
void Event::AddEntrant(std::string name, int id, char course) {
    Entrant e;
    e.SetId(id);
    e.SetName(name);
    e.SetCourse(course);
    entrants.push_back(e);
}

/**
 * Add a course to this event.
 * @param id the id of the course
 * @param nodes the vector of nodes for the course
 */
void Event::AddCourse(char id, std::vector<int> nodes) {
    Course c;
    c.SetId(id);
    c.SetNodes(nodes);
    courses.push_back(c);
}

/**
 * Set the list of courses for this event
 * @param courses the vector of courses for an event
 */
void Event::SetCourses(std::vector<Course> courses) {
    this->courses = courses;
}

/**
 * Get the list of courses for this event
 * @return the vector of courses for an event
 */
std::vector<Course> Event::GetCourses() const {
    return courses;
}

/**
 * Set the list of entrants for this event
 * @param entrants the vector of entrants for an event
 */
void Event::SetEntrants(std::vector<Entrant> entrants) {
    this->entrants = entrants;
}

/**
 * Get the list of entrants for this event
 * @return the vector of entrants for an event
 */
std::vector<Entrant> Event::GetEntrants() const {
    return entrants;
}

/**
 * Set the name of this event
 * @param name the name of this event
 */
void Event::SetName(std::string name) {
    this->name = name;
}

/**
 * Get the name of this event
 * @return the name of this event
 */
std::string Event::GetName() const {
    return name;
}

/**
 * Set the date of this event
 * @param date the date of this event
 */
void Event::SetDate(tm date) {
    this->date = date;
}

/**
 * Get the date of this event
 * @return the date of this event
 */
tm Event::GetDate() const {
    return date;
}

/**
 * Set the time of this event
 * @param time the time of this event
 */
void Event::SetTime(tm time) {
    this->time = time;
}

/**
 * Get the time of this event
 * @return the time of this event
 */
tm Event::GetTime() const {
    return time;
}

/**
 * Set the list of nodes for this event
 * @param nodes the vector of nodes for this event
 */
void Event::SetNodes(std::vector<int> nodes) {
    this->nodes = nodes;
}

/**
 * Get the list of nodes for this event
 * @return the vector of nodes for this event
 */
std::vector<int> Event::GetNodes() const {
    return nodes;
}

