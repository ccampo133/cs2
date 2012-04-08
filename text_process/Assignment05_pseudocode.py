import heapq
Heap = type("Heap", (list,), {item: (lambda item2: (lambda self, *args: getattr(heapq, "heap" + item2)(self, *args)))(item)  for item in ("pop", "push", "pushpop", "replace")})
    
'''
TEXT SEARCHING ALGORITHM ANALYSIS
=================================
For each algorithm:
    Inputs: t - string that contains sample text
            p - string that is the pattern to find
    
    Throughout all the algoritms, let n = len(t) and
    m = len(p).
'''
# simple brute force search algorithm
def BruteForceSearch(t, p):
    i   = 0
    cmp = 0    
    # outer loop goes at most n - m times
    while i <= (len(t) - len(p)):
        j = 0
        # inner loop goes at most m times
        while j < len(p) and t[i+j] == p[j]: # 
            cmp += 1
            j   += 1
        if j == len(p):
            return i    # match at t[i]
        cmp += 1
        i   += 1
    return -1   # no match
# ANALYSIS: since this is a nested loop, we have O((n-m)*m) = O(nm),
# assuming that n > m.

# knuth-morris-pratt algorithm
# kmp failure function    
def KMPFailFunc(p, size):
    F = [0] * size   # in java: int[] F = new int[size];
    i = 1
    j = 0
    # loop runs at most m times
    while i < len(p):
        if p[i] == p[j]:
            F[i] = j+1
            i += 1
            j += 1
        elif j > 0:
            j = F[j-1]
        else:
            F[i] = 0
            i += 1
    return F
    
# kmp text search
def KMPSearch(t, p):
    F   = KMPFailFunc(p, len(t))    # runtime is O(m)
    i   = 0
    j   = 0
    cmp = 0
    # outer loop runs at most n times
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
# ANALYSIS: since the failure function is generated in O(m) time
# and the while loop runs at most n times, we have O(n + m) for 
# the total runtime cost of the KMP algorithm.

# boyer-moore algorithm
# last occurance function
def LastOccuranceFunc(p):
    last = {}   # dictionary in python; hashmap in java
    # for loop runs m - 1 times = Theta(m-1) = O(m)
    for i in range(len(p)-1, -1, -1):
        c = p[i]
        if not last.has_key(c):
            last[c] = i
    return last

# boyer-moore text search
def BoyerMooreSearch(t, p):
    last = LastOccuranceFunc(p) # O(m)
    cmp  = 0
    i    = len(p)-1
    j    = len(p)-1
    # while loop runs at most n-1 times = O(n)
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
# ANALYSIS: since the last occurance function has a runtime of O(m)
# and the while loop runs at most n-1 times, the total runtime cost
# is O(n - 1 + m) = O(n + m), in the worst case.

'''
HUFFMAN ENCODING ALGORITHM ANALYSIS
===================================
For each algorithm:
    Inputs: t - string that contains sample text
    
    Throughout all the algoritms, let n = len(t) and
    m = len(p).
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
    # runs in n iterations
    for c in t:
        if freqs.has_key(c):
            freq = freqs[c]
        else:
            freq = 0
        freqs[c] = freq + 1
    return freqs
# ANALYSIS: since the for loop runs over the entire text, we have 
# a total runtime cost of O(n).

# build optimal huffman tree
def BuildHuffmanTree(freqs):
    heap  = Heap()  # create new heap, O(n)
    # number of iterations will be AT MOST, n
    for c in freqs.keys():
        heap.push((freqs[c], Node(c, freqs[c], None, None))) # push(new Node), O(lg n)
    # loops at most n times
    while len(heap) > 1:
        # insertion and deletion both run in O(lg n)
        right  = heap.pop()[1]
        left   = heap.pop()[1]
        parent = Node('\0', left.freq + right.freq, left, right)
        heap.push((parent.freq, parent))
    return heap.pop()[1]
# ANALYSIS: since the for loop runs at most n times, we can look at the
# while loop and see that it runs at most n times also.  Inside the while
# loop, the heap insert and delete methods are called which run in lg(n)
# times.  Therefore, the overall runtime cost is n + n*lg(n) = O(n*lg(n))

# recursive tree traversal    
def GenHuffmanCodes(codes, node, code):
    if not node.isLeaf():
        GenHuffmanCodes(codes, node.left, code + "1")
        GenHuffmanCodes(codes, node.right, code + "0")
    else:
        codes[node.ch] = code
# ANALYSIS: since each node is visited at most once, this algorithm
# runs in O(n) time, since in the worst case, each letter of the text
# is unique and there are n letters, and thus the tree must have 2n-1 
# nodes.

# generate and return huffman codes for the characters by traversing the tree
def GetHuffmanCodes(t):
    freqs = GetCharFreqs(t) # O(n)
    root  = BuildHuffmanTree(freqs); # O(n*lg(n))
    codes = {}
    GenHuffmanCodes(codes, root, "") # O(n)
    return codes
# ANALYSIS: the total runtime cost is just the sum of each individual cost 
# here, so the total runtime cost is 2n + nlg(n) = O(n*lg(n)).

# encode the text
def HuffmanEncode(t):
    codes   = GetHuffmanCodes(t)    # O(n*lg(n))
    encoded = ""
    # loops n times
    for c in t:
        encoded += codes[c]
    return encoded
# ANALYSIS: since the code generation runs in nlg(n) time, and the for loop
# runs n times, we have a total runtime cost of n + nlg(n) = O(n*lg(n))
    
# decode encoded text
def HuffmanDecode(encoded, freqs):
    root    = BuildHuffmanTree(freqs)   # O(n*lg(n))
    decoded = ""
    curNode = root
    # let the length of the encoded text be N
    for c in encoded:
        if c == "1":
            curNode = curNode.left
        else:
            curNode = curNode.right
        if curNode.isLeaf():
            decoded += curNode.ch
            curNode  = root
    return decoded
# ANALYSIS: since the for loop runs over all N characters of the encoded
# text, the decode algorithm runs in N + nlgn time which = O(N + n*lg(n)).