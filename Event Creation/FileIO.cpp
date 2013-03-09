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

#include "FileIO.h"

FileIO::FileIO() {
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

bool FileIO::WriteEventFile(std::string filename, std::string name, tm date, tm time) {
    using namespace std;
    ostringstream outputDate;
    ostringstream timeString;
    char outputTime [6];
    char monthname[10];
    char year[5];
    ofstream out(filename.c_str());
    
 
    if (out) {
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
