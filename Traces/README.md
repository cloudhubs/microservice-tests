# Traces and Business Process Reconstruction

## Log Parsing

These are the resources used for performing business process reconstruction. The file [original_logs.csv](https://github.com/Retro5050/Capstone-Project5/blob/main/Traces/original_logs.csv) contains the original logs that were provided to us. Running those logs through [parse.py](https://github.com/Retro5050/Capstone-Project5/blob/main/Traces/parse.py) generates [modified_logs.csv](https://github.com/Retro5050/Capstone-Project5/blob/main/Traces/modified_logs.csv), which is a slightly modified version with unnecessary data removed. It also generates separate files for each trace, as well as a file for each unique business process, which can be found in the [all_logs](https://github.com/Retro5050/Capstone-Project5/tree/main/Traces/all_logs) and [unique_logs](https://github.com/Retro5050/Capstone-Project5/tree/main/Traces/unique_logs) folders respectively.

## Business Process Reconstruction

Disco: https://github.com/Retro5050/Capstone-Project5/tree/main/Traces/models

Next, by importing individual traces into the Disco software, we can easily generate a business process reconstruction. Images of the reconstructions generated from the trace sample that we were provided can be found in the [models](https://github.com/Retro5050/Capstone-Project5/tree/main/Traces/models) folder 
