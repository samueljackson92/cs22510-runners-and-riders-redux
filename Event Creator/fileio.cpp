/**
 * @file   fileio.cpp
 * @author Samuel Jackson (slj11@aber.ac.uk)
 * @date 09 March 2013
 * @brief class to read in data files and write out the created event.
 */

#include <iostream>
#include <fstream>
#include <sstream>
#include <stdlib.h> 
#include <sys/stat.h>

#include "fileio.h"
#include "entrant.h"
#include "course.h"
#include "event.h"

FileIO::FileIO() {
}

/**
 * Write an event to file. This makes the courses, entrants and
 * name files.
 * @param evt the event to be written to file
 */
void FileIO::WriteEvent(Event evt) {
	mkdir (evt.GetName().c_str(), 0755);

    WriteEventFile(evt.GetName() + "/name.txt", evt);
    WriteCoursesFile(evt.GetName() + "/courses.txt", evt.GetCourses());
    WriteEntrantsFile(evt.GetName() + "/entrants.txt", evt.GetEntrants());

}

/**
 * Member function to write a vector of courses to a file
 * @param filename the name and path to create the file
 * @param courses the vector of courses to write to file
 * @return whether the write operation was successful
 */
bool FileIO::WriteCoursesFile(std::string filename, 
        std::vector<Course> courses) {
    using namespace std;
    ofstream out(filename.c_str());
    bool success = false;
    
    if(out.is_open()) {
        for(std::vector<Course>::iterator it = courses.begin(); 
                it != courses.end(); ++it) {
            out << it->GetId() << " ";
            out << it->GetNodes().size() << " ";
            
            std::vector<int> nodes = it->GetNodes();
            for(std::vector<int>::iterator jt = nodes.begin();
                    jt != nodes.end(); ++jt) {
                out << *jt << " ";
            }
            
            out << endl;
        }
    }
    
    return success;
}

/**
 * Member function to write a vector of entrants to a file
 * @param filename the name and path to create the file
 * @param entrants the vector of entrants to write to file
 * @return whether the write operation was successful
 */
bool FileIO::WriteEntrantsFile(std::string filename, 
        std::vector<Entrant> entrants) {
    using namespace std;
    ofstream out(filename.c_str());
    bool success = false;
    if(out.is_open()) {
        for(std::vector<Entrant>::iterator it = entrants.begin(); 
                it != entrants.end(); ++it) {
            out << it->GetId() << " ";
            out << it->GetCourse() << " ";
            out << it->GetName() << endl;
        }
        
        out.close();
        success = true;
    }
    
    return success;
}

/**
 * Member function to read in a list of nodes for a given file
 * @param filename the name and path to the nodes file
 * @return vector of nodes read in from file.
 */
std::vector<int> FileIO::readNodesList(std::string filename) {
    using namespace std;
    string input = "";
    ifstream in(filename.c_str());
    int number;
    char buffer[5];
    int line = 0;
    vector<int> nodes;

    if(in.is_open()) {
        while(!in.eof()) {
            line++;
            getline(in, input);
            int matches = sscanf (input.c_str(),"%d %s", &number, buffer);
            if(matches != 2) {
                cout << "Error parsing nodes file on line: " << line << endl;
                exit(-1);
            }
            
            nodes.push_back(number);
        }
    }
    
    in.close();
    
    return nodes;
    
}

/**
 * Member function to write an event to a file
 * @param filename the name and path to create the file
 * @param event the event to write to file
 * @return whether the write operation was successful
 */
bool FileIO::WriteEventFile(std::string filename, Event event) {
    using namespace std;
    ostringstream outputDate;
    ostringstream timeString;
    char outputTime [6];
    char monthname[10];
    char year[5];
    ofstream out(filename.c_str());
        
    string name = event.GetName();
    tm date = event.GetDate();
    tm time = event.GetTime();
    
 
    if (out.is_open()) {
        strftime(monthname, 10, "%B", &date);
        strftime(year, 5, "%Y", &date);
        strftime(outputTime, 6, "%R", &time);
        
        outputDate << date.tm_mday;
        outputDate << GetDayPostfix(date.tm_mday) << " ";
        outputDate << monthname;
        outputDate << " ";
        outputDate << year;
        
        timeString << outputTime;
        
        out << name << endl;
        out << outputDate.str() << endl;
        out << timeString.str() << endl;
        
        out.close();
        return true;
    } else {
        return false;
    }

}

/**
 * Member function to get the postfix of the date's day
 * will return a string with either 'st', 'nd' or 'rd'.
 * @param day the day to get the postfix for
 * @return the postfix for the date's day
 */
std::string FileIO::GetDayPostfix(int day) {
    std::string postfix = "th";
    switch(day) {
        case 1:
        case 21:
        case 31:
            postfix = "st";
            break;
        case 2:
        case 22:
            postfix = "nd";
            break;
        case 3:
        case 23:
            postfix = "rd";
            break;
    }
    
    return postfix;
}

FileIO::~FileIO() {
}

