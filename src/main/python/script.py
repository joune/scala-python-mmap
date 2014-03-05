import sys
import mmap

def helloWorld():
  with open(sys.argv[1], "r+b") as f:
    # memory-map the file, size 0 means whole file
    mm = mmap.mmap(f.fileno(), 0)
    # read content via standard file methods
    content = mm.readline()
    mm.seek(0)
    mm.write(content.upper())
    print(len(content))
    mm.close()


def main():
  print helloWorld()

if __name__ == '__main__':
  main()
