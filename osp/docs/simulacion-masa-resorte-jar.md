# Construir una simulación masa-resorte y empaquetarla como JAR

Esta guía crea la simulación Java desde cero, construye su JAR y lo añade a un paquete HTML existente de LaunchBuilder. Para preparar OSP y crear el contenido HTML inicial, sigue primero [Crear contenido portable con OSP LaunchBuilder](contenido-portable-launchbuilder.md). Aquí se cubren la implementación, la compilación del programa y los pasos adicionales para integrarlo.

## Resultado esperado

Al terminar tendrás:

```text
osp/ant/build/jar/
├── osp.jar
├── resorte-app.jar
├── guia_resorte.xset
├── guia_resorte.jar
└── guia_resorte/
  ├── introduccion.html
  └── modelo.html
```

- `osp.jar`: biblioteca y herramientas OSP, incluida LaunchBuilder.
- `resorte-app.jar`: la simulación compilada, aún dependiente de `osp.jar` para ejecutarse por separado.
- `guia_resorte.jar`: el paquete portable anterior, actualizado para incluir también la simulación.

## 1. Reutilizar el paquete HTML

Completa los pasos de preparación y creación HTML de la [guía de contenido portable](contenido-portable-launchbuilder.md). Para continuar con este ejemplo, deben existir `ant/build/jar/osp.jar`, `guia_resorte.xset` y la carpeta `guia_resorte/` con sus dos páginas. Esa guía cubre los requisitos JDK/Ant y cómo crear el `.xset` inicial.

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

## 4. Añadir la simulación al conjunto existente

Abre el conjunto `guia_resorte.xset` que creaste en la guía portable. Si LaunchBuilder no está abierto, ejecútalo desde su carpeta base:

```bash
cd ant/build/jar
java -jar osp.jar
```

En LaunchBuilder:

1. Conserva la pestaña **Oscilaciones** y sus nodos HTML.
2. Añade un nodo llamado **Simulación**.
3. En el classpath/JAR del nodo, selecciona `resorte-app.jar`.
4. Usa el selector de clases para asignar `demo.ResorteMasa` como clase lanzable.
5. Guarda con **Save All** y verifica el nodo desde la vista previa.

Al ejecutarse desde el Launcher, la clase principal llama a `SimulationControl.createApp(...)`, así que aparecen los controles **Initialize**, **Start**, **Step** y **Reset**.

## 5. Actualizar y probar el JAR portable

Sigue el procedimiento **Create Jar** de la [guía portable](contenido-portable-launchbuilder.md), con estas diferencias para el ejemplo:

- selecciona explícitamente `resorte-app.jar` en el árbol de archivos; los JAR secundarios no se preseleccionan
- conserva `guia_resorte.xset` y la carpeta `guia_resorte/`
- genera de nuevo `guia_resorte.jar`, reemplazando el paquete anterior si hace falta

El empaquetador desempaqueta `resorte-app.jar` y mezcla sus clases en `guia_resorte.jar`; no lo deja como un JAR anidado. El Launcher incluye su propio JAR en el classpath, así que las clases de la simulación quedan disponibles al ejecutarla.

Prueba el paquete resultante fuera de la carpeta de build:

```bash
java -jar ant/build/jar/guia_resorte.jar
```

Comprueba que:

- abre automáticamente `guia_resorte.xset`
- se muestra la página HTML
- el nodo **Simulación** abre los controles OSP
- al inicializar e iniciar, aparece la curva del desplazamiento

Para probar portabilidad, copia únicamente `guia_resorte.jar` a otra carpeta y repite la prueba. Si falla, revisa el classpath del nodo, la inclusión de `resorte-app.jar` y que las rutas HTML sean relativas.

## Flujo completo

```text
src/demo/ResorteMasa.java
  ↓ javac -cp osp.jar
ant/build/jar/resorte-app.jar
  ↓ añadir al conjunto HTML existente
guia_resorte.xset + páginas HTML + nodo de simulación
  ↓ Create Jar (OSP JarTool desempaqueta resorte-app.jar)
ant/build/jar/guia_resorte.jar
  ↓ java -jar
OSP Launcher + guía HTML + simulación
```

> OSP se distribuye bajo GPL. Al distribuir el JAR final, revisa y cumple las obligaciones de licencia aplicables al código OSP y a cualquier dependencia incluida.
