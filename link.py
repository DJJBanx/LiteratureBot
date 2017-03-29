from random import random

class Link():
    def __init__(self):
        self.links = dict()
        self.totalLinks = 0
    def add_transition(self, key):
        if key not in self.links:
            self.links[key] = 0
        self.links[key] += 1
        self.totalLinks += 1
    def getProb(self, key):
        if key in self.links:
            return float(self.links[key])/float(self.size())
        else:
            return 0
    def size(self):
        return self.totalLinks
    def transitions(self):
        return self.links.keys()
    def sorted_links(self):
        probs = []
        choices = []
        for key in self.transitions():
            # append a tuple (key, prob_of_key)
            probs.append((key, self.getProb(key)))
        return sorted(probs, key = lambda x: x[1])
        
    def choose(self):
        probs = self.sorted_links()
        prev = 0
        c = random()
        for i in reversed(range(0, len(probs))):
            upper = prev + probs[i][1]
            if prev <= c or c <= upper:
                return probs[i][0]
            prev = upper
        return prev[-1][0] # should never reach this point
    # string dump
    def sdump(self):
        s = []
        for key in self.links.keys():
            s.append("'%s' --> %d links" % (key, self.links[key]))
        return "\n".join(s)
    def __str__(self):
        probs = self.sorted_links()
        length = 3
        extra = ""
        if len(probs) <= 3:
            length = len(probs)
        else:
            extra = ", ... %d choices" % (len(self.links.keys()))
        acc = "["
        for i in range(length):
            acc += "'%s': %f, " % (probs[i][0], probs[i][1])
        return acc[:-3] + extra + "]"
    
