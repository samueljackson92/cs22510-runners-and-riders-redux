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
    virtual ~FileIO();

	void WriteEvent(Event event);
    std::vector<int> ReadNodesList(std::string filename);
private:
    bool WriteCoursesFile(std::string filename, std::vector<Course> courses);
    bool WriteEntrantsFile(std::string filename, std::vector<Entrant> entrants);
    bool WriteEventFile(std::string filename, Event event);
};

#endif	/* FILEIO_H */

