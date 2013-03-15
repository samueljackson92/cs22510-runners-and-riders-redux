/**
 * @file   eventcreator.cpp
 * @author Samuel Jackson (slj11@aber.ac.uk)
 * @date 09 March 2013
 * @brief class to create courses, entrants and events.
 * Also outputs and handles user navigation between menus.
 */

#include <iostream>
#include <string>
#include <ctime>
#include <algorithm>

#include "ioscanner.h"
#include "eventcreator.h"
#include "fileio.h"
#include "event.h"

/**
 * Initialises the event creator program and outputs startup message
 */
EventCreator::EventCreator() {
    using namespace std;
    
    cout << "----------------------" << endl;
    cout << "EVENT CREATION PROGRAM" << endl;
    cout << "----------------------" << endl << endl;
}

/**
 * Displays the main menu to the user and processes users choice
 */
void EventCreator::ShowMainMenu() {
    using namespace std;
    int input = 0;
    
    do {
        cout << "MAIN MENU" << endl;
        cout << "---------------------------" << endl;
        cout << "Enter an option: " << endl;
        cout << "1 - Make new event" << endl;
        cout << "2 - Add entrants to event" << endl;
        cout << "3 - Create course for event" << endl;
        cout << "4 - Write an event to file" << endl;
        cout << "5 - View an event in the system" << endl;
        cout << "6 - Exit Program" << endl;

        input = scanner.ReadInt();
        int evt_index;
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
            case 4: //save event to file
                evt_index = ChooseEvent();
                if(evt_index >= 0) {
                    Event e = events[evt_index];
                    fio.WriteEvent(e);
                }
                break;
            case 5:
                ViewEvent();
                break;
        }

    } while (input != 6);
}

/**
 * Member function to create a new event on the system.
 */
void EventCreator::MakeEvent() {
    using namespace std;
    string evt_name;
    tm date, time;
    
    cout << "Enter name of event:" << endl;
    evt_name = scanner.ReadString(80);

    cout << "Enter event date (DD/MM/YY):" << endl;
    date = scanner.ReadDate();
    
    cout << "Enter event start time (HH:MM):" << endl;
    time = scanner.ReadTime();
    
    cout << "Enter location of nodes file for event:" << endl;
    string nodesfile = scanner.ReadString(100);
    vector<int> nodes = fio.ReadNodesList(nodesfile);
    
    Event e(evt_name, date, time);
    e.SetNodes(nodes);
    events.push_back(e);
}

/**
 * Member function to add a new entrant to an event.
 */
void EventCreator::AddEntrants() {
    using namespace std;
    int eventIndex = ChooseEvent();
    int numEntrants = 0;
    string name;
    int id;
    char course;
    
    //if user picked an event
    if(eventIndex >= 0) {
        Event event = events[eventIndex];
        
        //check if we have some courses already.
        if(event.GetCourses().size() > 0) {
            cout << "Enter number of entrants to add: " << endl;

            do {
                numEntrants = scanner.ReadInt();
                if(numEntrants <=0) {
                    cout << "Not a valid number of entrants" << endl;
                } else if (numEntrants > 50) {
                	cout << "Too many entrants to create at once!" << endl;
                }
            } while (numEntrants <= 0);

            for(int i = 0; i < numEntrants; i++) {
                cout << "Enter entrant's name: " << endl;
                name = scanner.ReadString(50);
                course = ChooseCourse(event);
                id = event.GetEntrants().size()+1;
                event.AddEntrant(name, id, course);
                events[eventIndex] = event;
            }        
        } else {
            cout << "You must create at least one course first." << endl;
        }
    }
}

/**
 * Choose an event to work with if there are events on the system.
 * @return the id of the chosen event
 */
int EventCreator::ChooseEvent() {
    using namespace std;
    int index = -1;
    bool validChoice = false;
    
    if(events.size() > 0 ) {
        cout << "Please choose an event:" << endl;
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

/**
 * Choose a course based on the selected event
 * @param event the currently selected event
 * @return the id of the chosen course
 */
char EventCreator::ChooseCourse(Event event) {
    using namespace std;
    bool validChoice = false;
    int index;
    char choice;
    std::vector<Course> courses = event.GetCourses();
    
    if(courses.size() > 0 ) {
        cout << "Please choose course for the entrant:" << endl;
        for(std::vector<int>::size_type i = 0; i != courses.size(); i++) {
            cout << i << " - " << courses[i].GetId() << endl;
        }
        
         do {
            index = scanner.ReadInt();
            if (index >= 0 && index < courses.size()) {
                validChoice = true;
            } else {
                cout << "Not a valid course choice." << endl;
            }
        } while(!validChoice);
        choice = courses[index].GetId();
    } else {
        cout << "You must create at least one course first." << endl;
    }
    
    return choice;
}

/**
 * Create a course based on the selected event
 */
void EventCreator::CreateCourse() {
    using namespace std;
    int eventIndex = ChooseEvent();
    int node;
    vector<int> courseNodes;
    vector<int> allowedNodes;
    if(eventIndex >= 0) {
        Event event = events[eventIndex];
        allowedNodes = event.GetNodes();
        
        if(event.GetCourses().size() <= 26) {
            cout << "Enter nodes for course. Enter 0 to finish: " << endl;

            do {
                node = scanner.ReadInt();
                if(find(allowedNodes.begin(), allowedNodes.end(), node)!=allowedNodes.end()) {
                    courseNodes.push_back(node);
                } else if (node != 0) {
                    cout << "Not a valid node number!" << endl;
                }
            } while(node != 0);
            
            //convert numerical index to character index
            // e.g. ASCII 'A' is 65, 'B' is 66 etc.
            char id = (int)event.GetCourses().size()+65;

            event.AddCourse(id, courseNodes);
            events[eventIndex] = event;

        } else {
            cout << "Events can not have more than 26 courses" << endl;
        }
    }
}

/**
 * View an event on the system. This will list all course and
 * entrants associated with the chosen event.
 */
void EventCreator::ViewEvent() {
    using namespace std;
    int eventIndex = ChooseEvent();
    if(eventIndex >= 0) {
        Event event = events[eventIndex];
        
        cout << "------------------------------------------" << endl;
        cout << event.GetName() << endl;
        cout << event.GetFormattedDate() << endl;
        cout << event.GetFormattedTime() << endl;
        cout << "------------------------------------------" << endl;
        cout << "COURSES" << endl;
        cout << "------------------------------------------" << endl;
        
        if(event.GetCourses().size() > 0) {
			for(std::vector<Course>::iterator it = event.GetCourses().begin();
					it != event.GetCourses().end(); ++it) {
				cout << it->GetId() << " ";
				cout << it->GetNodes().size() << " ";

				std::vector<int> nodes = it->GetNodes();
				for(std::vector<int>::iterator jt = nodes.begin();
						jt != nodes.end(); ++jt) {
					cout << *jt << " ";
				}

				cout << endl;
			}
        } else {
        	cout << "This event has no courses yet!" << endl;
        }
        
        cout << "------------------------------------------" << endl;
        cout << "ENRTANTS" << endl;
        cout << "------------------------------------------" << endl;
        
        if(event.GetEntrants().size() > 0) {
			for (vector<Entrant>::iterator it = event.GetEntrants().begin();
					it != event.GetEntrants().end(); ++it) {
				cout << it->GetId() << " " << it->GetCourse() << " ";
				cout << it->GetName() << endl;
			}
        } else {
        	cout << "This event has no entrants yet!" << endl;
        }
    }
}

EventCreator::~EventCreator() {
}

/**
 * Main method and application entry point.
 * Simply shows the main menu.
 *
 * @param argc the number of command line arguments
 * @param argv the char array of command line arguments
 * @return program exit status (0)
 */
int main(int argc, char** argv) {
    EventCreator ec;
    ec.ShowMainMenu();
    return 0;
}