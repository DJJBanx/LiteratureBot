from link import Link
from chain import MarkovChain

from os import listdir
from os.path import isfile, join
from sys import argv

punc = ".;,:\"\'()!?"

def wordComb(text, chain):
    acc = ""
    prev = ""
    for c in text:
        if c in punc or c == ' ':
            chain.add_transition(prev, acc)
            prev = acc
            acc = ""
        else:
            acc += c

def puncComb(text, chain):
    prev = "."
    for c in text:
        if c in punc:
            chain.add_transition(prev, c)
            prev = c
        else:
            pass # continue searching

def spaceComb(text, chain):
    prev = "."
    spaces = 0
    for c in text:
        if c in punc:
            chain.add_transition(prev, spaces)
            prev = c
            spaces = 0
        elif c == ' ':
            spaces += 1
        else:
            pass # continue searching

def ask_for_seed():
    options = [f for f in listdir(".") \
            if isfile(join(".", f)) and f[:4] == "seed"]
    return prompt_choice(options)

def prompt_choice(choices):
    choice = -2
    print("type -1 to exit")
    while choice < -1 or choice > (len(choices) - 1):
        for i in range(len(choices)):
            print("%d: %s" % (i, choices[i]))
        choice = int(input(": "))
        if choice == -1:
            return None, False
    return choices[choice], True

def train(seed):
    with open(seed, "r") as f:
            text = f.read()

            wordChain = MarkovChain()
            puncChain = MarkovChain()
            spaceChain = MarkovChain()
            
            wordComb(text, wordChain)
            puncComb(text, puncChain)
            spaceComb(text, spaceChain)

            print(spaceChain)

if __name__ == "__main__":
    if len(argv) > 1 and argv[1][:4] == "seed":
        train(argv[1])
    else:
        seed, success = ask_for_seed()
        if success:
            train(seed)