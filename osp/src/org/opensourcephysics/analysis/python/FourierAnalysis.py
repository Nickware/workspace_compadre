import numpy as np
import matplotlib.pyplot as plt
from matplotlib.backends.backend_tkagg import FigureCanvasTkAgg
import tkinter as tk

def calculate_fft():
    N = int(entry_n.get())
    f = float(entry_freq.get())
    t = np.linspace(0, 1, N, endpoint=False)
    y = np.cos(2*np.pi*f*t) + 0.5*np.sin(2*np.pi*3*f*t)
    freqs = np.fft.rfftfreq(N, 1/N)
    fft_vals = np.fft.rfft(y)
    magnitude = np.abs(fft_vals)
    ax1.clear()
    ax2.clear()
    ax1.plot(t, y)
    ax1.set_title("Señal Original")
    ax1.set_xlabel("Tiempo")
    ax1.set_ylabel("Amplitud")
    ax2.stem(freqs, magnitude, use_line_collection=True)
    ax2.set_title("Magnitud FFT")
    ax2.set_xlabel("Frecuencia")
    ax2.set_ylabel("|Magnitud|")
    fig.tight_layout()
    canvas.draw()

root = tk.Tk()
root.title("Análisis Fourier con Tkinter")

# Permite que el grid crezca al redimensionar
root.columnconfigure(0, weight=1)
root.rowconfigure(3, weight=1)

tk.Label(root, text="N° muestras (N):").grid(row=0, column=0, sticky="w")
entry_n = tk.Entry(root)
entry_n.insert(0, "500")
entry_n.grid(row=0, column=1, sticky="ew")
tk.Label(root, text="Frecuencia base (Hz):").grid(row=1, column=0, sticky="w")
entry_freq = tk.Entry(root)
entry_freq.insert(0, "5")
entry_freq.grid(row=1, column=1, sticky="ew")
btn = tk.Button(root, text="Calcular FFT", command=calculate_fft)
btn.grid(row=2, column=0, columnspan=2)

fig, (ax1, ax2) = plt.subplots(2, 1, figsize=(5,5))
canvas = FigureCanvasTkAgg(fig, master=root)
canvas_widget = canvas.get_tk_widget()
canvas_widget.grid(row=3, column=0, columnspan=2, sticky="nsew")

# Configura expansión de columnas y filas para el canvas
root.grid_columnconfigure(0, weight=1)
root.grid_columnconfigure(1, weight=1)
root.grid_rowconfigure(3, weight=1)

root.mainloop()
