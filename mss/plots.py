import numpy as np
import matplotlib.pyplot as plt

n = np.arange(1,9, dtype=np.float64)
N = 10**n

lin   = N
quad  = N**2
cubic = N**3

tlin = np.array([
                0.002138,
                0.003421,
                0.028223,
                0.290357,
                4.106898,
                1.595036,
                15.528725,
                159.222238,
                ])
                
tquad = np.array([
                 0.004276,
                 0.113320,
                 3.799436,
                 54.942795,
                 2210.584540,
                 236177.050295,
                 np.nan,
                 np.nan
                 ])

tcubic = np.array([
                  0.008552,
                  3.126356,
                  127.810289,
                  61025.25716,
                  np.nan,
                  np.nan,
                  np.nan,
                  np.nan
                  ])
                  
                  
# make plots
plt.semilogx(N, tlin/lin, lw=2, basex=10)
plt.semilogx(N, tlin/lin, 'o', basex=10)
plt.xlabel("n")
plt.ylabel("T(n)/f(n)")
plt.title("Linear Algorithm")
plt.gcf().subplots_adjust(left=0.15)
plt.savefig("mss_linear.png")

plt.clf()
plt.semilogx(N, tquad/quad, lw=2, basex=10)
plt.semilogx(N, tquad/quad, 'o', basex=10)
plt.xlabel("n")
plt.ylabel("T(n)/f(n)")
plt.title("Quadratic Algorithm")
plt.gcf().subplots_adjust(left=0.15)
plt.savefig("mss_quadratic.png")

plt.clf()
plt.semilogx(N, tcubic/cubic, lw=2, basex=10)
plt.semilogx(N, tcubic/cubic, 'o', basex=10)
plt.xlabel("n")
plt.ylabel("T(n)/f(n)")
plt.title("Cubic Algorithm")
plt.gcf().subplots_adjust(left=0.15)
plt.savefig("mss_cubic.png")
