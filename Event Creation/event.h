/* 
 * File:   event.h
 * Author: samuel
 *
 * Created on 09 March 2013, 17:02
 */

#ifndef EVENT_H
#define	EVENT_H

#include <string>
#include <vector>

#include "Entrant.h"
#include "Course.h"

class Event {
    public:
        Event(std::string name, tm date, tm time);
        void AddEntrant(std::string name, int id, char course);
        void AddCourse(char id, std::vector<int> nodes);
        virtual ~Event();
        void SetCourses(std::vector<Course> courses);
        std::vector<Course> GetCourses() const;
        void SetEntrants(std::vector<Entrant> entrants);
        std::vector<Entrant> GetEntrants() const;
        void SetName(std::string name);
        std::string GetName() const;
        void SetDate(tm date);
        tm GetDate() const;
        void SetTime(tm time);
        tm GetTime() const;
    private:
        tm time;
        tm date;
        std::string name;
        std::vector<Entrant> entrants;
        std::vector<Course> courses;
};

#endif	/* EVENT_H */

