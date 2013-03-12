/**
 * @file   fileio.h
 * @author Samuel Jackson (slj11@aber.ac.uk)
 * @date 09 March 2013
 * @brief class to read in data files and write out the created event.
 */

#ifndef FILEIO_H
#define	FILEIO_H

#include <vector>
#include <string>

#include "event.h"
#include "entrant.h"
#include "course.h"

class FileIO {
public:
    FileIO();
    bool WriteCoursesFile(std::string filename, std::vector<Course> courses);
    bool WriteEntrantsFile(std::string filename, std::vector<Entrant> entrants);
    bool WriteEventFile(std::string filename, Event e);
	void WriteEvent(Event e);
    std::vector<int> readNodesList(std::string filename);
    virtual ~FileIO();
private:
    std::string GetDayPostfix(int day);
};

#endif	/* FILEIO_H */

