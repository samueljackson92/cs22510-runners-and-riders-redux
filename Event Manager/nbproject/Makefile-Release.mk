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
	${OBJECTDIR}/_ext/2102134000/linked_list.o \
	${OBJECTDIR}/_ext/2102134000/fileio.o \
	${OBJECTDIR}/_ext/2102134000/util.o \
	${OBJECTDIR}/main.o


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
	"${MAKE}"  -f nbproject/Makefile-${CND_CONF}.mk ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/event_manager

${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/event_manager: ${OBJECTFILES}
	${MKDIR} -p ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}
	${LINK.c} -o ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/event_manager ${OBJECTFILES} ${LDLIBSOPTIONS} 

${OBJECTDIR}/_ext/2102134000/linked_list.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Manager/linked_list.c 
	${MKDIR} -p ${OBJECTDIR}/_ext/2102134000
	${RM} $@.d
	$(COMPILE.c) -O2 -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/2102134000/linked_list.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Manager/linked_list.c

${OBJECTDIR}/_ext/2102134000/fileio.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Manager/fileio.c 
	${MKDIR} -p ${OBJECTDIR}/_ext/2102134000
	${RM} $@.d
	$(COMPILE.c) -O2 -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/2102134000/fileio.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Manager/fileio.c

${OBJECTDIR}/_ext/2102134000/util.o: /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Manager/util.c 
	${MKDIR} -p ${OBJECTDIR}/_ext/2102134000
	${RM} $@.d
	$(COMPILE.c) -O2 -MMD -MP -MF $@.d -o ${OBJECTDIR}/_ext/2102134000/util.o /home/samuel/Dropbox/Aber/uni_docs/Paradigms/cs22510-runners-and-riders-redux/Event\ Manager/util.c

${OBJECTDIR}/main.o: main.c 
	${MKDIR} -p ${OBJECTDIR}
	${RM} $@.d
	$(COMPILE.c) -O2 -MMD -MP -MF $@.d -o ${OBJECTDIR}/main.o main.c

# Subprojects
.build-subprojects:

# Clean Targets
.clean-conf: ${CLEAN_SUBPROJECTS}
	${RM} -r ${CND_BUILDDIR}/${CND_CONF}
	${RM} ${CND_DISTDIR}/${CND_CONF}/${CND_PLATFORM}/event_manager

# Subprojects
.clean-subprojects:

# Enable dependency checking
.dep.inc: .depcheck-impl

include .dep.inc
