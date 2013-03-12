
#include <iostream>
#include <limits>
#include <string>
#include <iostream>
#include <locale>

#include "ioscanner.h"

/**
 * @file   ioscanner.cpp
 * @Author Samuel Jackson (slj11@aber.ac.uk)
 * @date 09 March 2013
 * @brief class to read user input in from the command line in a variety of formats.
 */

IOScanner::IOScanner() {
}

/**
 * Member function to read a single integer from standard in.
 * @return The integer that was read in
 */
int IOScanner::ReadInt() {
    using namespace std;
    
    int input;
    while (!(cin >> input)) {
        cout << "Input wasn't a number!\n" ;
        cin.clear();
        cin.ignore(std::numeric_limits<streamsize>::max(), '\n') ;
    }
    cin.ignore(std::numeric_limits<streamsize>::max(), '\n');
    
    return input;
}

/**
 * Member function to read a string from standard in.
 * @param limit the limit of the number of characters to read in.
 * @return The string that was read in
 */
std::string IOScanner::ReadString(int limit) {
    using namespace std;
    string input = "";
    
    do {
    	getline(cin, input);

    	if(input.size() >= limit) {
    		cout << "Input too long!" << endl;
    	}
    } while(input.size() >= limit);
    
    return input;
}

/**
 * Member function to read a date from standard in. Dates must be entered in
 * the format DD/MM/YY
 * @return time structure containing the date that was read in
 */
tm IOScanner::ReadDate() {
    using namespace std;
    string date;
    tm when;
    bool valid;
    
    do {
        valid = true;
        date = ReadString(10);
    
        if(!strptime(date.c_str(), "%d/%m/%y", &when)) {
            cout << "That wasn't a date!\n" << endl;
            valid = false;
        }
    } while (!valid);

    return when;
}

/**
 * Member function to read a time from standard in. Dates must be entered in
 * the format HH:mm
 * @return time structure containing the time that was read in
 */
tm IOScanner::ReadTime() {
    using namespace std;
    string time;
    tm when;
    bool valid;
    do {
        valid = true;
        time =  ReadString(7);
        
        if(!strptime(time.c_str(), "%R", &when)) {
            cout << "That wasn't a time!" << endl;
            valid = false;
        }
    } while(!valid);
    
    
    return when;
}

IOScanner::~IOScanner() {
}

