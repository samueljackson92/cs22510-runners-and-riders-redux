/* 
 * File:   Entrant.h
 * Author: samuel
 *
 * Created on 09 March 2013, 17:04
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

