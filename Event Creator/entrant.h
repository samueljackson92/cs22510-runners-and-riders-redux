/**
 * @file   entrant.cpp
 * @author Samuel Jackson (slj11@aber.ac.uk)
 * @date 09 March 2013
 * @brief class to hold data about an entrant in an event.
 */

#ifndef ENTRANT_H
#define	ENTRANT_H

#include <string>

class Entrant {
    public:
        Entrant();
        virtual ~Entrant();
        void SetCourse(char course);
        char GetCourse() const;
        void SetName(std::string name);
        std::string GetName() const;
        void SetId(int id);
        int GetId() const;
    private:
        int id;
        std::string name;
        char course;
};

#endif	/* ENTRANT_H */

