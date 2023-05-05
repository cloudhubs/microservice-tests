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
    for trace in traceSet:
      entries.append(createRow(trace[TRACEID], trace[SOURCE], trace[TARGET]))
    return entries

def writeTraceFile(file, trace):
   caseFile = open(file, "w")
   caseFile.write(HEADER)
   caseFile.writelines(trace)
#Sees if a given trace has a duplicate in a list of traces
def hasDuplicate(traces, newTrace):
   for trace in traces:
      if len(trace) != len(newTrace):
         continue
      same = True
      for eventa, eventb in zip(trace, newTrace):
         if eventa[2:] != eventb[2:]:
            same = False
            break
      if same:
         return same 
    
#Start of main
if len(sys.argv) != 2:
    print("Error: Usage ./parse.py <log file>")
 
with open(sys.argv[1], "r") as traces:
    #Contains all traces from the original log
    allTraces = []
    #Contains a list of unique traces. The first trace to match a case will be the one in this list
    uniqueTraces=[]
    #Skips header
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
        #Check if the case is new before adding to the unique case list
        if not hasDuplicate(allTraces, currSet):
          uniqueTraces.append(currSet)
        #Append to list of all traces
        allTraces.append(currSet)
        currSet = [ trace ]
      #If it is a new trace, add it to the current set
      elif currSet[len(currSet)-1] != trace:
        currSet.append(trace)
           
#Write all entries to large trace file
allEntries = []
for traceSet in allTraces:
  allEntries = allEntries + createTraceEntries(traceSet)
writeTraceFile("./modified_logs.csv", allEntries)
#Write unique traces to individual file
for traceSet in uniqueTraces:
  fileName = "./logs/{0}.csv"
  writeTraceFile(fileName.format(traceSet[0][TRACEID]), createTraceEntries(traceSet))
    