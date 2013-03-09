/* 
 * File:   console_input.h
 * Author: samuel
 *
 * Created on 09 March 2013, 12:54
 */

#ifndef IOSCANNER_H
#define	IOSCANNER_H

#include <string>

class IOScanner {
public:
    IOScanner();
    int ReadInt();
    std::string ReadString();
    tm ReadDate();
    tm ReadTime();
    virtual ~IOScanner();
private:

};

#endif	/* CONSOLE_INPUT_H */

