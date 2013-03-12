/* 
 * File:   FileIO.cpp
 * Author: samuel
 * 
 * Created on 09 March 2013, 15:56
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

void FileIO::WriteEvent(Event e) {
	mkdir (e.GetName().c_str(), 0755);

    WriteEventFile(e.GetName() + "/name.txt", e);
    WriteCoursesFile(e.GetName() + "/courses.txt", e.GetCourses());
    WriteEntrantsFile(e.GetName() + "/entrants.txt", e.GetEntrants());

}

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

bool FileIO::WriteEventFile(std::string filename, Event e) {
    using namespace std;
    ostringstream outputDate;
    ostringstream timeString;
    char outputTime [6];
    char monthname[10];
    char year[5];
    ofstream out(filename.c_str());
        
    string name = e.GetName();
    tm date = e.GetDate();
    tm time = e.GetTime();
    
 
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

