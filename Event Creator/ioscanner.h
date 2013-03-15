
/**
 * @file   ioscanner.h
 * @author Samuel Jackson (slj11@aber.ac.uk)
 * @date 09 March 2013
 * @brief class to read user input in from the command line in a variety of formats.
 */

#ifndef IOSCANNER_H
#define	IOSCANNER_H

#include <string>

class IOScanner {
public:
    IOScanner();
    virtual ~IOScanner();

    int ReadInt();
    std::string ReadString(int limit);
    tm ReadDate();
    tm ReadTime();
};

#endif	/* CONSOLE_INPUT_H */

