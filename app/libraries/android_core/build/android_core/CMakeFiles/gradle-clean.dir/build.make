# CMAKE generated file: DO NOT EDIT!
# Generated by "Unix Makefiles" Generator, CMake Version 3.5

# Delete rule output on recipe failure.
.DELETE_ON_ERROR:


#=============================================================================
# Special targets provided by cmake.

# Disable implicit rules so canonical targets will work.
.SUFFIXES:


# Remove some rules from gmake that .SUFFIXES does not remove.
SUFFIXES =

.SUFFIXES: .hpux_make_needs_suffix_list


# Suppress display of executed commands.
$(VERBOSE).SILENT:


# A target that is always out of date.
cmake_force:

.PHONY : cmake_force

#=============================================================================
# Set environment variables for the build.

# The shell in which to execute make rules.
SHELL = /bin/sh

# The CMake executable.
CMAKE_COMMAND = /usr/bin/cmake

# The command to remove a file.
RM = /usr/bin/cmake -E remove -f

# Escaping for special characters.
EQUALS = =

# The top-level source directory on which CMake was run.
CMAKE_SOURCE_DIR = /home/laurayg/android_core/src

# The top-level build directory on which CMake was run.
CMAKE_BINARY_DIR = /home/laurayg/android_core/build

# Utility rule file for gradle-clean.

# Include the progress variables for this target.
include android_core/CMakeFiles/gradle-clean.dir/progress.make

gradle-clean: android_core/CMakeFiles/gradle-clean.dir/build.make

.PHONY : gradle-clean

# Rule to build all files generated by this target.
android_core/CMakeFiles/gradle-clean.dir/build: gradle-clean

.PHONY : android_core/CMakeFiles/gradle-clean.dir/build

android_core/CMakeFiles/gradle-clean.dir/clean:
	cd /home/laurayg/android_core/build/android_core && $(CMAKE_COMMAND) -P CMakeFiles/gradle-clean.dir/cmake_clean.cmake
.PHONY : android_core/CMakeFiles/gradle-clean.dir/clean

android_core/CMakeFiles/gradle-clean.dir/depend:
	cd /home/laurayg/android_core/build && $(CMAKE_COMMAND) -E cmake_depends "Unix Makefiles" /home/laurayg/android_core/src /home/laurayg/android_core/src/android_core /home/laurayg/android_core/build /home/laurayg/android_core/build/android_core /home/laurayg/android_core/build/android_core/CMakeFiles/gradle-clean.dir/DependInfo.cmake --color=$(COLOR)
.PHONY : android_core/CMakeFiles/gradle-clean.dir/depend

