/* 
 * File:   Course.cpp
 * Author: samuel
 * 
 * Created on 09 March 2013, 17:04
 */

#include "Course.h"

Course::Course() {
}

Course::~Course() {
}

void Course::SetNodes(std::vector<int> nodes) {
    this->nodes = nodes;
}

std::vector<int> Course::GetNodes() const {
    return nodes;
}

void Course::SetId(char id) {
    this->id = id;
}

char Course::GetId() const {
    return id;
}

