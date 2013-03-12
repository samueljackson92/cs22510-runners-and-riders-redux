/* 
 * File:   Entrant.cpp
 * Author: samuel
 * 
 * Created on 09 March 2013, 17:04
 */

#include "entrant.h"

Entrant::Entrant() {
}

Entrant::~Entrant() {
}

void Entrant::SetCourse(char course) {
    this->course = course;
}

char Entrant::GetCourse() const {
    return course;
}

void Entrant::SetName(std::string name) {
    this->name = name;
}

std::string Entrant::GetName() const {
    return name;
}

void Entrant::SetId(int id) {
    this->id = id;
}

int Entrant::GetId() const {
    return id;
}

