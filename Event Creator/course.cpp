/**
 * @file   course.cpp
 * @author Samuel Jackson (slj11@aber.ac.uk)
 * @date 09 March 2013
 * @brief class to hold data about a course in an event.
 */

#include "course.h"

/**
 * Initialises an instance of a course with an id
 * and a set of nodes
 * @param id the id of the course
 * @param nodes the nodes in the course
 */
Course::Course(char id, std::vector<int> nodes) {
	SetId(id);
	SetNodes(nodes);
}

Course::~Course() {
}

/**
 * Set the list of nodes in this course
 * @param nodes the vector of nodes.
 */
void Course::SetNodes(std::vector<int> nodes) {
    this->nodes = nodes;
}

/**
 * Get the list of nodes in this course
 * @return the vector of nodes.
 */
std::vector<int> Course::GetNodes() const {
    return nodes;
}

/**
 * Set the ID of this course
 * @param id the ID of the course
 */
void Course::SetId(char id) {
    this->id = id;
}

/**
 * Set the list of nodes in this course
 * @return the ID of the course.
 */
char Course::GetId() const {
    return id;
}

