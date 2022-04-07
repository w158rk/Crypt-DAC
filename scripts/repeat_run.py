from subprocess import Popen
import sys

name = sys.argv[1]
cnt = int(sys.argv[2])

cmd = f'gradle run -q --args {name}'
# cmd = f'echo {name}'
outputs = [f'output/{name}{i}.log' for i in range(1, cnt+1)]
processes = [Popen(cmd, shell=True, stdout=open(output, 'w')) for output in outputs]
for process in processes:
    process.wait()
