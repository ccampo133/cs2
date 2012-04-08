import heapq
Heap = type("Heap", (list,), {item: (lambda item2: (lambda self, *args: getattr(heapq, "heap" + item2)(self, *args)))(item)  for item in ("pop", "push", "pushpop", "replace")})
    
'''
TEXT SEARCHING ALGORITHM ANALYSIS
=================================
For each algorithm:
    Inputs: t - string that contains sample text
            p - string that is the pattern to find
'''
# simple brute force search algorithm
def BruteForceSearch(t, p):
    i   = 0
    cmp = 0    
    while i <= (len(t) - len(p)):
        j = 0
        while j < len(p) and t[i+j] == p[j]:
            cmp += 1
            j   += 1
        if j == len(p):
            return i    # match at t[i]
        cmp += 1
        i   += 1
    return -1   # no match


# kmp algorithm
def KMPSearch(t, p):
    F   = KMPFailFunc(p, len(t))
    i   = 0
    j   = 0
    cmp = 0
    while i < len(t):
        cmp += 1
        if t[i] == p[j]:
            if j == len(p)-1:
                return i-j  # match at t[i-j]
            else:
                i += 1
                j += 1
        else:
            if j > 0:
                j = F[j-1]  # go to most recent prefix occurance
            else:
                i += 1
    return -1   # no match


# kmp failure function    
def KMPFailFunc(p, size):
    F = [None] * size   # in java: int[] F = new int[size];
    i = 1
    j = 0
    while i < len(p):
        if p[i] == p[j]:
            F[i] = j+1
            i += 1
            j += 1
        elif j > 0:
            j = F[j-1]
        else
            F[i] = 0
            i += 1
    return F


# boyer moore search
def BoyerMoore(t, p):
    last = LastOccuranceFunc(p)
    cmp  = 0
    i    = len(p)-1
    j    = len(p)-1
    while i <= len(t)-1:
        cmp += 1
        if t[i] == p[j]:
            if j == 0:
                return i
            else:
                i -= 1
                j -= 1
        else:
            l = -1
            if t[i] in last:
                l = last[t[i]]
            i = i + len(p) - min(j, 1+l)
            j = len(p)-1
    return -1


# last occurance function
def LastOccuranceFunc(p):
    last = {}   # dictionary in python; hashmap in java
    for i in range(len(p)-1, -1, -1):
        c = p[i]
        if not last.has_key(c):
            last[c] = i
    return last
    
'''
HUFFMAN ENCODING ALGORITHM ANALYSIS
===================================
For each algorithm:
    Inputs: t - string that contains sample text
'''
# huffman tree class
class Node:
    def __init__(self, ch, freq, left, right):
        self.ch    = ch
        self.freq  = freq
        self.left  = left
        self.right = right
    def isLeaf(self):
        return (self.left == None) and (self.right == None)


# compute frequencies of characters; return as dictionary (hash map)
def GetCharFreqs(t):
    freqs = {}
    for c in t:
        if freqs.has_key(c):
            freq = freqs[c]
        else:
            freq = 0
        freqs[c] = freq + 1
    return freqs


# build optimal huffman tree
def BuildHuffmanTree(freqs):
    heap  = Heap()  # create new heap
    for c in freqs.keys():
        heap.push((freqs[c], Node(c, freqs[c], None, None)))    # push(new Node)
    while len(heap) > 1:
        right  = heap.pop()[1]
        left   = heap.pop()[1]
        parent = Node('\0', left.freq + right.freq, left, right)
        heap.push((parent.freq, parent))
    return heap.pop()[1]


# generate and return huffman codes for the characters by traversing the tree
def GetHuffmanCodes(t):
    freqs = GetCharFreqs(t)
    root  = BuildHuffmanTree(freqs);
    codes = {}
    GenHuffmanCodes(codes, root, "")
    return codes


# recursive tree traversal    
def GenHuffmanCodes(codes, node, code):
    if not node.isLeaf():
        GenHuffmanCodes(codes, node.left, code + "1")
        GenHuffmanCodes(codes, node.right, code + "0")
    else:
        codes[node.ch] = code


# encode the text
def HuffmanEncode(t):
    codes   = GetHuffmanCodes(t)
    encoded = ""
    for c in t:
        encoded += codes[c]
    return encoded


# decode encoded text
def HuffmanDecode(encoded, freqs):
    root    = BuildHuffmanTree(freqs)
    decoded = ""
    curNode = root
    for c in encoded:
        if c == "1":
            curNode = curNode.left
        else:
            curNode = curNode.right
        if curNode.isLeaf():
            decoded += curNode.ch
            curNode  = root
    return decoded

