# Construir una simulación masa-resorte y empaquetarla como JAR

Esta guía crea una simulación OSP desde cero, construye un JAR ejecutable para ella y lo incorpora a un contenido portable creado con LaunchBuilder. La clase implementa la ecuación del oscilador armónico y usa el integrador RK4 de OSP.

## Resultado esperado

Al terminar tendrás:

```text
osp/ant/build/jar/
├── osp.jar
├── resorte-app.jar
├── resorte-portable.xset
├── resorte-portable.jar
└── resorte-contenido/
    └── introduccion.html
```

- `osp.jar`: biblioteca y herramientas OSP, incluida LaunchBuilder.
- `resorte-app.jar`: la simulación compilada, aún dependiente de `osp.jar` para ejecutarse por separado.
- `resorte-portable.jar`: Launcher final con OSP, la simulación y el contenido seleccionado.

## 1. Preparar OSP

Requisitos: JDK, Apache Ant y este repositorio. Desde la raíz de `osp`, construye la biblioteca:

```bash
ant -f ant/build_core.xml all
```

El JAR de OSP queda en `ant/build/jar/osp.jar`. El build heredado configura `source="1.5"`; si el JDK instalado ya no acepta ese nivel, ajusta la configuración de Ant a un nivel compatible antes de compilar.

## 2. Crear el archivo fuente

En este repositorio los ejemplos usan el paquete `demo` bajo `src/demo`. Crea exactamente:

```text
osp/src/demo/ResorteMasa.java
```

El archivo y la clase pública deben compartir el nombre `ResorteMasa`. Copia este código:

```java
package demo;

import org.opensourcephysics.controls.AbstractSimulation;
import org.opensourcephysics.controls.SimulationControl;
import org.opensourcephysics.frames.PlotFrame;
import org.opensourcephysics.numerics.ODE;
import org.opensourcephysics.numerics.ODESolver;
import org.opensourcephysics.numerics.RK4;

public class ResorteMasa extends AbstractSimulation implements ODE {
  private static final int POSITION = 0;
  private static final int VELOCITY = 1;
  private static final int TIME = 2;

  private final double[] state = new double[3];
  private double mass;
  private double springConstant;
  private double stepSize;
  private ODESolver solver;
  private PlotFrame plot;

  public ResorteMasa() {
    plot = new PlotFrame("Tiempo (s)", "Posición x (m)", "Sistema masa-resorte");
    plot.setSize(640, 420);
    plot.setPreferredMinMax(0, 20, -2, 2);
    plot.setAutoclear(false);
    plot.setVisible(true);
  }

  @Override
  public void reset() {
    getControl().setValue("masa", 1.0);
    getControl().setValue("constante del resorte", 4.0);
    getControl().setValue("posición inicial", 1.0);
    getControl().setValue("velocidad inicial", 0.0);
    getControl().setValue("paso de tiempo", 0.02);
  }

  @Override
  public void initialize() {
    mass = getControl().getDouble("masa");
    springConstant = getControl().getDouble("constante del resorte");
    stepSize = getControl().getDouble("paso de tiempo");

    if (mass <= 0 || springConstant <= 0 || stepSize <= 0) {
      solver = null;
      getControl().println("La masa, la constante del resorte y el paso deben ser positivos.");
      return;
    }

    state[POSITION] = getControl().getDouble("posición inicial");
    state[VELOCITY] = getControl().getDouble("velocidad inicial");
    state[TIME] = 0;

    solver = new RK4(this);
    solver.initialize(stepSize);

    plot.clearData();
    plot.setPreferredMinMax(0, 20, -2, 2);
    plot.append(0, state[TIME], state[POSITION]);
  }

  @Override
  public void doStep() {
    if (solver == null) {
      return;
    }

    solver.step();
    plot.append(0, state[TIME], state[POSITION]);
    if (state[TIME] >= 20) {
      stopSimulation();
    }
  }

  @Override
  public double[] getState() {
    return state;
  }

  @Override
  public void getRate(double[] currentState, double[] rate) {
    rate[POSITION] = currentState[VELOCITY];
    rate[VELOCITY] = -(springConstant / mass) * currentState[POSITION];
    rate[TIME] = 1;
  }

  public static void main(String[] args) {
    SimulationControl.createApp(new ResorteMasa(), args);
  }
}
```

El estado es $(x, v, t)$. `getRate` define $dx/dt=v$, $dv/dt=-(k/m)x$ y $dt/dt=1$. `RK4` estima el estado siguiente con el paso configurado.

## 3. Compilar la clase y crear `resorte-app.jar`

Desde la raíz `osp`, compila la clase contra la biblioteca OSP y coloca el bytecode en un directorio temporal fuera de `ant/build/jar`:

```bash
mkdir -p ant/build/resorte-classes
javac -cp ant/build/jar/osp.jar -d ant/build/resorte-classes src/demo/ResorteMasa.java
```

Crea el JAR de la aplicación con `demo.ResorteMasa` como clase principal:

```bash
jar cfe ant/build/jar/resorte-app.jar demo.ResorteMasa -C ant/build/resorte-classes .
```

El JAR contiene el código de la simulación, no las clases de OSP. Para probarlo antes de usar LaunchBuilder, ejecútalo con ambos JAR en el classpath:

```bash
java -cp "ant/build/jar/resorte-app.jar:ant/build/jar/osp.jar" demo.ResorteMasa
```

En Windows, reemplaza `:` por `;` en el classpath. Verifica que aparece el control OSP, que **Initialize** toma los valores y que **Start** dibuja la evolución. No uses `java -jar resorte-app.jar` en esta fase: el manifiesto generado no incluye OSP como dependencia.

## 4. Preparar el contenido HTML para LaunchBuilder

Crea una página de introducción que acompañe el modelo:

```bash
mkdir -p ant/build/jar/resorte-contenido
```

Guarda lo siguiente como `ant/build/jar/resorte-contenido/introduccion.html`:

```html
<!doctype html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Sistema masa-resorte</title>
</head>
<body>
  <h1>Sistema masa-resorte</h1>
  <p>Un resorte ideal ejerce una fuerza restauradora F = -k x.</p>
  <p>En el modelo, x es el desplazamiento, m la masa y k la constante elástica.</p>
  <p>Abre el nodo Simulación para cambiar las condiciones iniciales y observar x(t).</p>
</body>
</html>
```

## 5. Registrar la simulación en LaunchBuilder

Ejecuta LaunchBuilder desde su carpeta base para que encuentre los archivos y JAR por rutas relativas:

```bash
cd ant/build/jar
java -jar osp.jar
```

En el Builder:

1. Crea un conjunto nuevo y guárdalo como `resorte-portable.xset` en `ant/build/jar`, junto a `osp.jar`.
2. Crea una pestaña llamada **Masa-resorte**.
3. Asocia `resorte-contenido/introduccion.html` a un nodo llamado **Introducción** usando el selector de contenido **Open HTML**.
4. Crea un nodo llamado **Simulación**.
5. En el campo de classpath/JAR del nodo, selecciona `resorte-app.jar`.
6. Usa el selector de clases para buscar y asignar `demo.ResorteMasa` como clase lanzable.
7. Guarda con **Save All** y prueba el nodo desde la vista previa.

Al ejecutarse desde el Launcher, la clase principal llama a `SimulationControl.createApp(...)`, así que aparecen los controles **Initialize**, **Start**, **Step** y **Reset**.

## 6. Incorporar el JAR de simulación al paquete portable

1. Elige **Create Jar** en LaunchBuilder.
2. Incluye `osp.jar`, `resorte-portable.xset`, los XML de los nodos y la carpeta `resorte-contenido/`.
3. El empaquetador no preselecciona JAR secundarios. En el árbol del diálogo, selecciona explícitamente `resorte-app.jar` como JAR completo.
4. Confirma la creación con el nombre `resorte-portable.jar`.

OSP `JarTool` desempaqueta los JAR fuente seleccionados y mezcla sus entradas en el JAR de salida. Por tanto, las clases de `resorte-app.jar` quedan dentro de `resorte-portable.jar`; no queda como un JAR anidado. El Launcher también incorpora su propio JAR al classpath al ejecutar la clase, de modo que el bytecode de la simulación y las clases OSP están disponibles en el paquete final.

El `.xset` debe quedar en la raíz del JAR final y compartir nombre base con el paquete: `resorte-portable.xset` dentro de `resorte-portable.jar`.

## 7. Probar el circuito completo

Prueba primero en la carpeta de build:

```bash
java -jar ant/build/jar/resorte-portable.jar
```

Luego copia únicamente `resorte-portable.jar` a otra carpeta y vuelve a ejecutarlo. Comprueba que:

- abre automáticamente `resorte-portable.xset`
- se muestra la página HTML
- el nodo **Simulación** abre los controles OSP
- al inicializar e iniciar, aparece la curva del desplazamiento

Si falla, revisa que el nodo tenga `demo.ResorteMasa`, que `resorte-app.jar` se seleccionó entero en **Create Jar**, que el `.xset` esté en la raíz y que las rutas HTML sean relativas.

## Flujo completo

```text
src/demo/ResorteMasa.java
        ↓ javac -cp osp.jar
ant/build/jar/resorte-app.jar
        ↓ LaunchBuilder: classpath + clase principal
resorte-portable.xset + HTML + recursos
        ↓ Create Jar (OSP JarTool desempaqueta resorte-app.jar)
ant/build/jar/resorte-portable.jar
        ↓ java -jar
OSP Launcher + guía HTML + simulación
```

> OSP se distribuye bajo GPL. Al distribuir el JAR final, revisa y cumple las obligaciones de licencia aplicables al código OSP y a cualquier dependencia incluida.
