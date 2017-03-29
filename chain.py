from link import Link

# a markov chain is just a dictionary of keys to links
class MarkovChain(dict):
    def __init__(self):
        self.pos = ""
    def add_transition(self, one, two):
        if one not in self:
            self[one] = Link()
        self[one].add_transition(two)
    def __str__(self):
        s = []
        for key in self:
            s.append("'%s' --> %s" % (key, self[key]))
        return "\n".join(s)
    def start(self, s):
        self.pos = s
    def advance(self):
        self.pos = self[self.pos].choose()
        return self.pos