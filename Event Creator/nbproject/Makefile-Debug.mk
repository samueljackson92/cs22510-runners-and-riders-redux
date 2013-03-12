#
# Generated Makefile - do not edit!
#
# Edit the Makefile in the project folder instead (../Makefile). Each target
# has a -pre and a -post target defined where you can add customized code.
#
# This makefile implements configuration specific macros and targets.


# Environment
MKDIR=mkdir
CP=cp
GREP=grep
NM=nm
CCADMIN=CCadmin
RANLIB=ranlib
CC=gcc
CCC=g++
CXX=g++
FC=gfortran
AS=as

# Macros
CND_PLATFORM=GNU-Linux-x86
CND_DLIB_EXT=so
CND_CONF=Debug
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/_ext/1856167428/event.o \
	${OBJECTDIR}/_ext/1856167428/entrant.o \
	${OBJECTDIR}/_ext/1856167428/course.o \
	${OBJECTDIR}/_ext/1856167428/ioscanner.o \
	${OBJECTDIR}/_ext/1856167428/fileio.o \
	${OBJECTDIR}/_ext/1856167428/eventcreator.o


# C Compiler Flags
CFLAGS=

# CC Compiler Flags
CCFLAGS=
CXXFLAGS=

# Fortran Compiler Flags
FFLAGS=

# Assembler Flags
ASFLAGS=

# Link Libraries and Options
LDLIBSOPTIONS=

# Build Targets
.build-conf: ${BUILD_SUBPROJECTS}
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/event_creation

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/event_creation: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${LINK.cc} -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/event_creation ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/_ext/1856167428/event.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/event.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/event.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/event.cpp

${OBJECTDIR}/_ext/1856167428/entrant.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/entrant.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/entrant.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/entrant.cpp

${OBJECTDIR}/_ext/1856167428/course.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/course.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/course.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/course.cpp

${OBJECTDIR}/_ext/1856167428/ioscanner.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/ioscanner.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/ioscanner.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/ioscanner.cpp

${OBJECTDIR}/_ext/1856167428/fileio.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/fileio.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/fileio.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/fileio.cpp

${OBJECTDIR}/_ext/1856167428/eventcreator.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/eventcreator.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -g -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/eventcreator.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/eventcreator.cpp

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/event_creation

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
