import sys
from enum import Enum
import csv

def analyze(filename):
    lines = []
    with open(filename, 'r') as f:
        lines = [line.strip() for line in f]

    class State(Enum):
        OUT = 0
        IN = 1

    BEGIN_STRING = '[ru] begin'
    END_STRING = '[ru] end'
    state = State.OUT

    ur_cnt = 0
    enc_cnt = 0
    for line in lines:
        if line==BEGIN_STRING:
            state = State.IN
            continue

        if line==END_STRING:
            state = State.OUT
            ur_cnt += 1
            continue

        if state == State.OUT:
            continue

        if 'ENC_2' in line:
            enc_cnt += 1

    print('total # of encryptions in UR revocations:', enc_cnt)
    print('total # of UR revocations:', ur_cnt)
    print('encryption per UR revocation:', enc_cnt / ur_cnt if ur_cnt else 'NaN')
    return enc_cnt / ur_cnt if ur_cnt else -1

name = sys.argv[1]
cnt = int(sys.argv[2])
filenames = [f'output/{name}{i}.log' for i in range(1, cnt+1)]

results = [analyze(filename) for filename in filenames]
results = list(filter(lambda r : r!=-1, results))
print(len(results))

with open(f'output/enc_per_ur_revoke_{name}.txt', 'w') as f:
    f.write('\n'.join([str(r) for r in results]))