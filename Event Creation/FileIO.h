/* 
 * File:   FileIO.h
 * Author: samuel
 *
 * Created on 09 March 2013, 15:56
 */

#ifndef FILEIO_H
#define	FILEIO_H

#include <vector>
#include <string>

#include "Entrant.h"
#include "Course.h"

class FileIO {
public:
    FileIO();
    bool WriteCoursesFile(std::string filename, std::vector<Course> courses);
    bool WriteEntrantsFile(std::string filename, std::vector<Entrant> entrants);
    bool WriteEventFile(std::string filename, std::string name, tm date, tm time);
    std::vector<int> readNodesList(std::string filename);
    virtual ~FileIO();
private:
    std::string GetDayPostfix(int day);
};

#endif	/* FILEIO_H */

