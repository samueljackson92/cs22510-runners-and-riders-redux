/* 
 * File:   Menu.cpp
 * Author: Samuel Jackson
 * Created on 09 March 2013, 12:20
 */

#include <iostream>
#include <string>
#include <ctime>
#include <algorithm>

#include "ioscanner.h"
#include "eventcreator.h"
#include "FileIO.h"
#include "event.h"

EventCreator::EventCreator() {
    using namespace std;
    
    nodes = fio.readNodesList("/home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/event_3/nodes.txt");
    
    cout << "----------------------" << endl;
    cout << "EVENT CREATION PROGRAM" << endl;
    cout << "----------------------" << endl << endl;
}

void EventCreator::ShowMainMenu() {
    using namespace std;
    int input = 0;
    
    do {
        cout << "MAIN MENU" << endl;
        cout << "---------------------------" << endl;
        cout << "Enter an option: " << endl;
        cout << "1 - Create new event" << endl;
        cout << "2 - Add entrants to event" << endl;
        cout << "3 - Create course for event" << endl;
        cout << "4 - Exit Program" << endl;

        input = scanner.ReadInt();
        switch(input) {
            case 1:
                MakeEvent();
                break;
            case 2:
                AddEntrants();
                break;
            case 3:
                CreateCourse();
                break;
        }

    } while (input != 4);
}

void EventCreator::MakeEvent() {
    using namespace std;
    string evt_name;
    tm date, time;
    
    cout << "Enter name of event:" << endl;
    evt_name = scanner.ReadString();

    cout << "Enter event date (DD/MM/YY):" << endl;
    date = scanner.ReadDate();
    
    cout << "Enter event start time (HH:MM):" << endl;
    time = scanner.ReadTime();
    
    fio.WriteEventFile("myevent.txt", evt_name, date, time);
}

void EventCreator::AddEntrants() {
    using namespace std;
    int eventIndex = ChooseEvent();
    int numEntrants = 0;
    string name;
    int id;
    char course;
    
    if(eventIndex >= 0) {
        Event event = events[eventIndex];
        cout << "Enter number of entrants to add: " << endl;
        
        do {
            numEntrants = scanner.ReadInt();
            if(numEntrants <=0) {
                cout << "Not a valid number of entrants" << endl;
            }
        } while (numEntrants <= 0);
        
        for(int i = 0; i < numEntrants; i++) {
            Entrant entrant;
            cout << "Enter entrant's name: " << endl;
            name = scanner.ReadString();
            course = ChooseCourse(event);
            id = event.GetEntrants().size()+1;
            event.AddEntrant(name, id, course);
        }        
    }
}

int EventCreator::ChooseEvent() {
    using namespace std;
    int index = -1;
    bool validChoice = false;
    
    if(events.size() > 0 ) {
        cout << "Please choose an event to add entrants too:" << endl;
        for(std::vector<int>::size_type i = 0; i != events.size(); i++) {
            cout << i << " - " << events[i].GetName() << endl;
        }
        
         do {
            index = scanner.ReadInt();
            if (index >= 0 && index < events.size()) {
                validChoice = true;
            } else {
                cout << "Not a valid event choice." << endl;
            }
        } while(!validChoice);
        
    } else {
        cout << "You must create at least one event first." << endl;
    }
    
    return index;
}

char EventCreator::ChooseCourse(Event event) {
    using namespace std;
    bool validChoice = false;
    int index;
    std::vector<Course> courses = event.GetCourses();
    
    if(courses.size() > 0 ) {
        cout << "Please choose course for the entrant:" << endl;
        for(std::vector<int>::size_type i = 0; i != courses.size(); i++) {
            cout << i << " - " << courses[i].GetId() << endl;
        }
        
         do {
            index = scanner.ReadInt();
            if (index >= 0 && index < events.size()) {
                validChoice = true;
            } else {
                cout << "Not a valid course choice." << endl;
            }
        } while(!validChoice);
        
    } else {
        cout << "You must create at least one course first." << endl;
    }
}

void EventCreator::CreateCourse() {
    using namespace std;
    int eventIndex = ChooseEvent();
    int node;
    vector<int> courseNodes;
    if(eventIndex >= 0) {
        Event event = events[eventIndex];
        
        if(event.GetCourses().size() >= 26) {
            cout << "Enter nodes for course. Enter 0 to finish: " << endl;

            do {
                node = scanner.ReadInt();
                if(find(nodes.begin(), nodes.end(), node)!=nodes.end()) {
                    courseNodes.push_back(node);
                } else if (node < 0) {
                    cout << "Not a valid node number!" << endl;
                }
            } while(node != 0);
            
            //convert numerical index to character index
            char id = (int)event.GetCourses().size()+65;
            
            event.AddCourse(id, courseNodes);
        } else {
            cout << "Events can not have more than 26 courses" << endl;
        }
    }
}



EventCreator::~EventCreator() {
}

