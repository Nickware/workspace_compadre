# Análisis de Fourier sobre una señal matemática

Este script en Python es una aplicación gráfica simple que realiza un análisis de Fourier sobre una señal matemática y visualiza tanto la señal original como su espectro de frecuencias utilizando una interfaz creada con Tkinter.

## Funcionamiento general

- El usuario puede ingresar dos parámetros: el número de muestras de la señal (N) y la frecuencia base (Hz).
- Al hacer clic en "Calcular FFT", el programa genera una señal compuesta (una combinación de un coseno y un seno con frecuencias base y armónicas).
- Se calcula la Transformada Rápida de Fourier (FFT) de la señal generada usando la función `np.fft.rfft` de NumPy.
- La interfaz grafica consta de dos subgráficos:
  1. **Señal Original:** Muestra la evolución temporal de la señal compuesta.
  2. **Magnitud FFT:** Muestra la magnitud del espectro de frecuencias resultante, identificando las frecuencias presentes y su intensidad.
- El programa utiliza Matplotlib para crear las gráficas, y estas se integran dentro de la ventana de Tkinter mediante un canvas embebido.
- Se configura para que la ventana, y con ella los gráficos, sean redimensionables y se ajusten dinámicamente al tamaño de la ventana.

## Puntos clave

- Es una herramienta educativa accesible para estudiar señales y su análisis espectral.
- El uso de Tkinter facilita la creación de interfaces ligeras y multiplataforma.
- El código es extensible para incorporar diferentes tipos de señales, ventanas de análisis, o exportación de datos y gráficos.
- El diseño modular (separación clara entre generación de datos, FFT y visualización) facilita su mantenimiento y ampliación.

En resumen, el script es un ejemplo práctico y didáctico que combina conceptos matemáticos (análisis de Fourier), computación científica (NumPy, Matplotlib) y desarrollo de interfaz gráfica (Tkinter), apto para introducir análisis de señales y programación con interfaz visual en Python.