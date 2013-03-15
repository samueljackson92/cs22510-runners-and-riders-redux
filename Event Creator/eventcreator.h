/**
 * @file   eventcreator.h
 * @author Samuel Jackson (slj11@aber.ac.uk)
 * @date 09 March 2013
 * @brief class to create courses, entrants and events.
 */

#ifndef MENU_H
#define	MENU_H

#include <vector>
#include "ioscanner.h"
#include "fileio.h"
#include "event.h"

class EventCreator {
public:
    EventCreator();
    virtual ~EventCreator();

    void ShowMainMenu();
private:
    FileIO fio;
    IOScanner scanner;
    std::vector<Event> events;

    void MakeEvent();
    void AddEntrants();
    void CreateCourse();
    int ChooseEvent();
    char ChooseCourse(Event event);
    void ViewEvent();
};

#endif	/* MENU_H */

