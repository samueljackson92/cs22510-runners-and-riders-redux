/* 
 * File:   Menu.h
 * Author: Samuel Jackson
 * Created on 09 March 2013, 12:20
 */

#ifndef MENU_H
#define	MENU_H

#include <vector>
#include "ioscanner.h"
#include "FileIO.h"
#include "event.h"

class EventCreator {
public:
    EventCreator();
    void ShowMainMenu(void);
    virtual ~EventCreator();
private:
    FileIO fio;
    IOScanner scanner;
    std::vector<Event> events;
    void MakeEvent(void);
    void AddEntrants(void);
    void CreateCourse();
    int ChooseEvent(void);
    char ChooseCourse(Event event);
    void ViewEvent(void);
};

#endif	/* MENU_H */

