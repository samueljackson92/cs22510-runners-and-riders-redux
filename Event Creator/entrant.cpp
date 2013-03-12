/**
 * @file   entrant.cpp
 * @author Samuel Jackson (slj11@aber.ac.uk)
 * @date 09 March 2013
 * @brief class to hold data about an entrant in an event.
 */

#include "entrant.h"

/**
 * Initilises a new instance of an entrant with an ID,
 * name and course.
 *
 * @param id the ID of the entrant
 * @param name the name of entrant
 * @param course the ID of the course the entrant is registered on.
 */
Entrant::Entrant(int id, std::string name, char course) {
	SetId(id);
	SetName(name);
	SetCourse(course);
}

Entrant::~Entrant() {
}

/**
 * Set the course the entrant is on.
 * @param course the course id
 */
void Entrant::SetCourse(char course) {
    this->course = course;
}

/**
 * Get the course the entrant is on.
 * @return the course id
 */
char Entrant::GetCourse() const {
    return course;
}

/**
 * Set the name of the entrant.
 * @param name the name of the entrant
 */
void Entrant::SetName(std::string name) {
    this->name = name;
}

/**
 * Get the name of the entrant.
 * @return the name of the entrant
 */
std::string Entrant::GetName() const {
    return name;
}

/**
 * Set the entrant's ID.
 * @param id the entrant id
 */
void Entrant::SetId(int id) {
    this->id = id;
}

/**
 * Get the entrant's ID.
 * @return the id of the entrant
 */
int Entrant::GetId() const {
    return id;
}

