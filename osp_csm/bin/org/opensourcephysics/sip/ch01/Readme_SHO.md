# Comparacion entre SHOApp y SHOApp2

Estos ejemplos muestran un oscilador armonico simple (SHO, por sus siglas en ingles) usando la biblioteca Open Source Physics.

## Aplicaciones

| Clase | Vista utilizada | Controles | Funcion adicional |
| --- | --- | --- | --- |
| `SHOApp` | `SHOView` | Ingles: Start, Stop, Initialize | Simulacion basica |
| `SHOApp2` | `SHOView2` | Espanol: Iniciar, Pausar, Inicializar | Registra las vueltas completas |

Ambas aplicaciones crean un `OSPControl`, conectan los botones de control con una animacion y ejecutan el mismo modelo fisico: `SHOModel`.

### Conexion de cada aplicacion con su vista

En `SHOApp`, la aplicacion original conserva la vista original:

```java
Animation animation = new SHOView();
```

En `SHOApp2`, la nueva version utiliza la copia ampliada:

```java
Animation animation = new SHOView2();
```

## Vistas

### `SHOView`

Es la version original. En cada paso:

- Mueve la particula mediante `SHOModel`.
- Actualiza el grafico de posicion `x(t)`.
- Muestra el tiempo transcurrido.

El mensaje de la simulacion tiene el formato:

```text
t=...
```

### `SHOView2`

Es una copia ampliada de `SHOView`, utilizada exclusivamente por `SHOApp2`. Ademas de las funciones originales:

- Conserva la posicion anterior de la particula.
- Detecta cada cambio de signo de `x`, que representa un cruce del punto de equilibrio.
- Cuenta una vuelta completa cada dos cruces.
- Reinicia el contador al inicializar o restablecer la simulacion.
- Muestra el tiempo y el numero de vueltas.

El mensaje de la simulacion tiene el formato:

```text
t=..., vueltas=...
```

## Codigo incorporado en la version 2

`SHOView2` agrega variables para conservar la posicion anterior, contar los cruces del equilibrio y calcular las vueltas completas:

```java
double previousX;
int equilibriumCrossings;
int completedOscillations;
```

Al inicializar la simulacion, estos valores se reinician:

```java
previousX = x;
equilibriumCrossings = 0;
completedOscillations = 0;
```

En cada paso se compara la posicion anterior con la actual. Un cambio de signo indica que la particula cruzo el punto de equilibrio:

```java
double currentX = sho.getX();
if (currentX != 0 && previousX != 0 &&
		((previousX < 0 && currentX > 0) ||
		 (previousX > 0 && currentX < 0))) {
	equilibriumCrossings++;
	completedOscillations = equilibriumCrossings / 2;
}
if (currentX != 0) {
	previousX = currentX;
}
```

Finalmente, el numero de vueltas se agrega al mensaje mostrado en la simulacion:

```java
drawing.setMessage("t="+decimalFormat.format(sho.getTime())+
		", vueltas="+completedOscillations);
```

## Ejecucion

Desde el directorio del proyecto, ejecuta la aplicacion que quieras iniciar:

```bash
java -cp <classpath> org.opensourcephysics.sip.ch01.SHOApp
java -cp <classpath> org.opensourcephysics.sip.ch01.SHOApp2
```

`SHOApp` conserva el comportamiento original. `SHOApp2` permite observar cuantas oscilaciones completas realiza la particula alrededor del equilibrio.
