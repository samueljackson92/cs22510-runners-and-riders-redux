/* 
 * File:   Course.h
 * Author: samuel
 *
 * Created on 09 March 2013, 17:04
 */

#ifndef COURSE_H
#define	COURSE_H

#include <vector>

class Course {
    public:
        Course();
        virtual ~Course();
        void SetNodes(std::vector<int> nodes);
        std::vector<int> GetNodes() const;
        void SetId(char id);
        char GetId() const;
    private:
        char id;
        std::vector<int> nodes;
};

#endif	/* COURSE_H */

