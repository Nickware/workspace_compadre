# Crear contenido portable con OSP LaunchBuilder

OSP LaunchBuilder permite organizar programas, archivos de datos y páginas HTML en un conjunto de lanzamiento (`.xset`) y empaquetar el conjunto y sus recursos en un JAR ejecutable con OSP Launcher.

La guía se refiere al LaunchBuilder incluido en este repositorio. La clase que genera el paquete es `org.opensourcephysics.tools.LaunchBuilder`; el JAR principal de OSP se construye con un manifiesto que usa esa clase como punto de entrada. La acción **Create Jar** del Builder crea el paquete portable con `org.opensourcephysics.tools.Launcher` como punto de entrada.

## 1. Preparar el entorno

Necesitas:

- un JDK instalado (no basta con un JRE antiguo si vas a compilar OSP)
- Apache Ant
- este repositorio

Desde la carpeta del proyecto OSP, compila la biblioteca:

```bash
cd osp
ant -f ant/build_core.xml all
```

El archivo `build_core.xml` define como directorio base `ant/`; por eso el JAR de desarrollo se genera en:

```text
osp/ant/build/jar/osp.jar
```

El manifiesto de ese JAR declara `org.opensourcephysics.tools.LaunchBuilder` como clase principal. El build también declara `source="1.5"`; con un JDK moderno puede ser necesario actualizar esa configuración a un nivel Java compatible o usar un JDK que la soporte. Si Ant o el JDK no están instalados o no están en el `PATH`, instala/configura esas herramientas antes de continuar.

## 2. Crear una carpeta de trabajo

LaunchBuilder usa como base para **Create Jar** la carpeta que contiene el JAR de OSP desde el cual se inició. Crea una carpeta de trabajo allí y guarda dentro los documentos y recursos que quieras distribuir.

Ejemplo de estructura antes de empaquetar:

```text
osp/ant/build/jar/
├── osp.jar
├── mi_contenido.xset
└── recursos/
        ├── introduccion.html
        ├── paginas/
        │   └── teoria.html
        ├── imagenes/
        │   └── diagrama.png
        └── datos/
                └── mediciones.csv
```

Guarda `mi_contenido.xset` en la raíz de la carpeta base, junto a `osp.jar`: el Launcher busca por defecto un `.xset` cuyo nombre coincide con el JAR ejecutado. Mantén las páginas, imágenes, hojas de estilo y datos en una subcarpeta como `recursos/`. Usa referencias relativas entre ellos, por ejemplo `imagenes/diagrama.png` desde una página dentro de `recursos/`, en lugar de rutas absolutas como `/home/usuario/...` o `C:\Users\...`.

## Ejemplo desde cero: guía HTML sobre un resorte

En este ejemplo construiremos un paquete portable que presenta una introducción y el modelo físico de un oscilador masa-resorte. No requiere escribir una aplicación Java: OSP Launcher mostrará las páginas como contenido de sus nodos.

### A. Crear la carpeta y los archivos HTML

Desde la raíz `osp` del repositorio, crea la carpeta de recursos:

```bash
mkdir -p ant/build/jar/guia_resorte
```

Crea dentro de `guia_resorte` estos dos archivos. Puedes hacerlo con VS Code usando **File > New Text File** y guardarlos con los nombres indicados.

`ant/build/jar/guia_resorte/introduccion.html`:

```html
<!doctype html>
<html>
<head>
        <meta charset="UTF-8">
        <title>El sistema masa-resorte</title>
</head>
<body>
        <h1>El sistema masa-resorte</h1>
        <p>Una masa unida a un resorte puede oscilar alrededor de su posición de equilibrio.</p>
        <h2>Pregunta de exploración</h2>
        <p>¿Cómo cambia el movimiento si aumentamos la masa o la rigidez del resorte?</p>
        <p>Abre el nodo <strong>Modelo físico</strong> para revisar las variables y la ecuación.</p>
</body>
</html>
```

`ant/build/jar/guia_resorte/modelo.html`:

```html
<!doctype html>
<html>
<head>
        <meta charset="UTF-8">
        <title>Modelo físico</title>
</head>
<body>
        <h1>Modelo físico</h1>
        <p>Para un resorte ideal, la fuerza restauradora es proporcional al desplazamiento:</p>
        <p><strong>F = -k x</strong></p>
        <p>Con la segunda ley de Newton se obtiene:</p>
        <p><strong>d²x/dt² = -(k/m) x</strong></p>
        <ul>
                <li><strong>x</strong>: desplazamiento desde el equilibrio</li>
                <li><strong>m</strong>: masa del objeto</li>
                <li><strong>k</strong>: constante elástica del resorte</li>
        </ul>
        <p>La solución ideal es periódica. Una simulación numérica aproxima su evolución en pasos de tiempo.</p>
</body>
</html>
```

La carpeta queda así; el archivo `.xset` se creará después desde LaunchBuilder:

```text
ant/build/jar/
├── osp.jar
└── guia_resorte/
                ├── introduccion.html
                └── modelo.html
```

### B. Crear la navegación en LaunchBuilder

1. Abre una terminal en `osp/ant/build/jar` y ejecuta `java -jar osp.jar`.
2. Crea un conjunto nuevo y guárdalo como `guia_resorte.xset` en `osp/ant/build/jar`, junto a `osp.jar`.
3. Crea una pestaña llamada **Oscilaciones**.
4. Crea o selecciona el nodo raíz de la pestaña y asígnale el título **Guía del resorte**.
5. Usa el selector de contenido/display del nodo, elige **Open HTML** y selecciona `guia_resorte/introduccion.html`.
6. Añade un nodo hijo llamado **Modelo físico** y asígnale `guia_resorte/modelo.html` mediante el mismo selector.
7. Abre la vista previa y selecciona ambos nodos para verificar que cada página se muestra.
8. Usa **Save All** para guardar `guia_resorte.xset` y los XML de los nodos.

### C. Empaquetar y probar

1. En LaunchBuilder elige **Create Jar**.
2. En la selección de archivos, incluye `osp.jar`, `guia_resorte.xset`, los XML de los nodos y la carpeta `guia_resorte/`.
3. Confirma la creación. El resultado debe llamarse `guia_resorte.jar` y quedar en `osp/ant/build/jar`.
4. Comprueba que el paquete contiene `guia_resorte.xset` en su raíz y que conserva la carpeta `guia_resorte/` con ambas páginas.
5. Ejecuta el paquete:

```bash
cd ant/build/jar
java -jar guia_resorte.jar
```

6. Copia solamente `guia_resorte.jar` a otra carpeta y vuelve a ejecutarlo. Comprueba la pestaña **Oscilaciones** y los nodos **Guía del resorte** y **Modelo físico**.

Si una página no aparece, revisa que su ruta se guardó como relativa, que los XML de los nodos y los dos HTML están incluidos y que el `.xset` está en la raíz del JAR con el mismo nombre base que el archivo JAR.

## Añadir una simulación Java

El ejemplo de arriba cubre un paquete portable compuesto por páginas HTML y recursos. Para añadirle un programa Java, usa [Construir una simulación masa-resorte y empaquetarla como JAR](simulacion-masa-resorte-jar.md). Esa guía reutiliza el conjunto HTML `guia_resorte`, construye el JAR de la simulación y explica solo los pasos adicionales para conectarlo al Launcher.

## Portabilidad y compatibilidad

Antes de crear el JAR, confirma en la vista previa que funcionan los nodos y que los recursos usan rutas relativas. Incluye todos los archivos referenciados. El `.xset` debe quedar en la raíz del JAR con el mismo nombre base que el paquete.

El panel HTML de OSP Launcher está basado en `JEditorPane`, no en un navegador web completo. Usa HTML sencillo; JavaScript moderno, CSS avanzado, multimedia embebida y contenido que requiere conexión pueden no comportarse como en un navegador actual.

La prueba final consiste en mover únicamente el JAR a otra carpeta y ejecutarlo con Java. Si no abre o faltan recursos, revisa rutas relativas, archivos omitidos y dependencias externas.
