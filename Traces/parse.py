import csv
import sys

#Defines indexes for data in each trace
TRACEID = 0
START_TIME = 1
OP_NAME = 2
SOURCE = 3
TARGET = 4

#Header for case files
HEADER = "TraceID,Source,Target\n"

hasHeader = False
oneFile = False

#Creates a formatted row
def createRow(*args):
    line = ""
    for item in args:
       line += item + ","
    return line.rstrip(",") + "\n"
#Creates a case file and writes traces to it
def createTraceEntries(traceSet):
    entries = [ ]
    previousTarget = ""
    for trace in traceSet:
      #if trace[SOURCE] != previousTarget:
        #entries.append(createRow(trace[TRACEID], trace[SOURCE]))
      #entries.append(createRow(trace[TRACEID], trace[TARGET]))
      entries.append(createRow(trace[TRACEID], trace[SOURCE], trace[TARGET]))
      #previousTarget = trace[TARGET]
    return entries

def writeTraceFile(file, trace):
   caseFile = open("./cases/" + file, "w")
   caseFile.write(HEADER)
   caseFile.writelines(trace)
    
#Start of main
if len(sys.argv) < 2 or len(sys.argv) > 3:
    print("Error: Usage ./parse.py <log file> [options]")


if len(sys.argv) == 3:
  if sys.argv[2].find("h") != -1:
      hasHeader = True
  if sys.argv[2].find("o") != -1:
     oneFile = True
 
with open(sys.argv[1], "r") as traces:
    #TraceID, StartTime, OperationName, Source, Target
    allTraces = []
    #Skips header if necessary
    if hasHeader:
        traces.readline()
    #Parse through trace log and separate individual cases
    currSet = []
    for trace in traces:
      trace = trace.replace("\n", "").split(',')
      #If the current set is empty, start it
      if len(currSet) == 0: 
        currSet.append(trace)
      #If it is a new trace, append the current one and start a new one
      elif currSet[len(currSet)-1][TRACEID] != trace[TRACEID]:
        allTraces.append(currSet)
        currSet = [ trace ]
      #If it is a new trace, add it to the current set
      elif currSet[len(currSet)-1] != trace:
        currSet.append(trace)
           
if oneFile:
  allEntries = []
  for traceSet in allTraces:
    allEntries = allEntries + createTraceEntries(traceSet)
  writeTraceFile("traces.csv", allEntries)
else:
   for traceSet in allTraces:
      writeTraceFile(traceSet[0][TRACEID] + ".csv", createTraceEntries(traceSet))
    