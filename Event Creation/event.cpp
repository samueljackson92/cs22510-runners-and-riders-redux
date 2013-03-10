/* 
 * File:   event.cpp
 * Author: samuel
 * 
 * Created on 09 March 2013, 17:02
 */

#include <string>
#include "event.h"
#include "Entrant.h"

Event::Event(std::string name, tm date, tm time) {
    this->time = time;
    this->date = date;
    this->name = name;
}

Event::~Event() {
}

void Event::AddEntrant(std::string name, int id, char course) {
    Entrant e;
    e.SetId(id);
    e.SetName(name);
    e.SetCourse(course);
    entrants.push_back(e);
}
void Event::AddCourse(char id, std::vector<int> nodes) {
    Course c;
    c.SetId(id);
    c.SetNodes(nodes);
    courses.push_back(c);
}

void Event::SetCourses(std::vector<Course> courses) {
    this->courses = courses;
}

std::vector<Course> Event::GetCourses() const {
    return courses;
}

void Event::SetEntrants(std::vector<Entrant> entrants) {
    this->entrants = entrants;
}

std::vector<Entrant> Event::GetEntrants() const {
    return entrants;
}

void Event::SetName(std::string name) {
    this->name = name;
}

std::string Event::GetName() const {
    return name;
}

void Event::SetDate(tm date) {
    this->date = date;
}

tm Event::GetDate() const {
    return date;
}

void Event::SetTime(tm time) {
    this->time = time;
}

tm Event::GetTime() const {
    return time;
}

void Event::SetNodes(std::vector<int> nodes) {
    this->nodes = nodes;
}

std::vector<int> Event::GetNodes() const {
    return nodes;
}

