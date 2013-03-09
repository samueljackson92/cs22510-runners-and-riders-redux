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
CND_CONF=Release
CND_DISTDIR=dist
CND_BUILDDIR=build

# Include project Makefile
include Makefile

# Object Directory
OBJECTDIR=${CND_BUILDDIR}/${CND_CONF}/${CND_PLATFORM}

# Object Files
OBJECTFILES= \
	${OBJECTDIR}/main.o \
	${OBJECTDIR}/_ext/1856167428/event.o \
	${OBJECTDIR}/_ext/1856167428/Course.o \
	${OBJECTDIR}/_ext/1856167428/Entrant.o \
	${OBJECTDIR}/_ext/1856167428/ioscanner.o \
	${OBJECTDIR}/_ext/1856167428/eventcreator.o \
	${OBJECTDIR}/_ext/1856167428/FileIO.o


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

${OBJECTDIR}/main.o: main.cpp 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.cc) -O2 -MMD -MP -MF $@.d -o ${OBJECTDIR}/main.o main.cpp

${OBJECTDIR}/_ext/1856167428/event.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/event.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -O2 -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/event.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/event.cpp

${OBJECTDIR}/_ext/1856167428/Course.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/Course.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -O2 -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/Course.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/Course.cpp

${OBJECTDIR}/_ext/1856167428/Entrant.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/Entrant.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -O2 -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/Entrant.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/Entrant.cpp

${OBJECTDIR}/_ext/1856167428/ioscanner.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/ioscanner.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -O2 -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/ioscanner.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/ioscanner.cpp

${OBJECTDIR}/_ext/1856167428/eventcreator.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/eventcreator.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -O2 -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/eventcreator.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/eventcreator.cpp

${OBJECTDIR}/_ext/1856167428/FileIO.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/FileIO.cpp 
	${MKDIR} -p ${OBJECTDIR}/_ext/1856167428
	${RM} $@.d
	$(COMPILE.cc) -O2 -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/1856167428/FileIO.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Creation/FileIO.cpp

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
