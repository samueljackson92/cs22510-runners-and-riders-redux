/* 
 * File:   console_input.cpp
 * Author: samuel
 * 
 * Created on 09 March 2013, 12:54
 */

#include <iostream>
#include <limits>
#include <string>
#include <iostream>
#include <locale>

#include "ioscanner.h"

IOScanner::IOScanner() {
}

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

std::string IOScanner::ReadString() {
    using namespace std;
    string input = "";
    
    getline(cin, input);
    
    return input;
}

tm IOScanner::ReadDate() {
    using namespace std;
    string date;
    tm when;
    bool valid;
    
    do {
        valid = true;
        date = ReadString();
    
        if(!strptime(date.c_str(), "%d/%m/%y", &when)) {
            cout << "That wasn't a date!\n" << endl;
            valid = false;
        }
    } while (!valid);

    return when;
}

tm IOScanner::ReadTime() {
    using namespace std;
    string time;
    tm when;
    bool valid;
    do {
        valid = true;
        time =  ReadString();
        
        if(!strptime(time.c_str(), "%R", &when)) {
            cout << "That wasn't a time!" << endl;
            valid = false;
        }
    } while(!valid);
    
    
    return when;
}

IOScanner::~IOScanner() {
}

