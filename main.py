from link import Link
from chain import MarkovChain

from os import listdir
from os.path import isfile, join
from sys import argv

punc = ".;,:\"\'()!?"

def word_comb(text, chain):
    acc = ""
    prev = ""
    for c in text:
        if c in punc or c == ' ':
            chain.add_transition(prev, acc)
            prev = acc
            acc = ""
        else:
            acc += c

def punc_comb(text, chain):
    prev = "."
    for c in text:
        if c in punc:
            chain.add_transition(prev, c)
            prev = c
        else:
            pass # continue searching

def space_comb(text, chain):
    prev = "."
    spaces = 1
    for c in text:
        if c in punc:
            chain.add_transition(prev, spaces)
            prev = c
            spaces = 1
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

def generate_text(words, puncs, spaces, depth=10):
    puncs.start(".")
    gen = ""
    for i in range(depth):
        gen += puncs.advance()

    new_gen = []
    for p in gen:
        new_gen.append((p, spaces[p].choose()))

    final = ""
    for item in new_gen:
        final += item[0]
        words.start("")
        # final += " "
        for i in range(item[1]):
            final += words.advance() + " "
        final = final[:-1]
    return final

# graph part?
def main(seed):
    with open(seed, "r") as f:
        text = f.read()
        text.replace("\n", "")

        word_chain = MarkovChain()
        punc_chain = MarkovChain()
        space_chain = MarkovChain()

        word_comb(text, word_chain)
        punc_comb(text, punc_chain)
        space_comb(text, space_chain)

        # print(word_chain)
        # print(punc_chain)
        # print(space_chain)
        print(generate_text(word_chain, punc_chain, space_chain))

if __name__ == "__main__":
    if len(argv) > 1 and argv[1][:4] == "seed":
        main(argv[1])
    else:
        seed, success = ask_for_seed()
        if success:
            main(seed)