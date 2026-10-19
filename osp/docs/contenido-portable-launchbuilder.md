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

## 3. Abrir LaunchBuilder

Desde la carpeta `osp/ant/build/jar`, abre el Builder incluido en el JAR:

```bash
java -jar osp.jar
```

En LaunchBuilder crea un conjunto nuevo (tab set). Guárdalo en la raíz de la carpeta base con el nombre `mi_contenido.xset`. El nombre del conjunto es importante: el JAR final se nombrará usando ese nombre, con extensión `.jar`, y el Launcher buscará ese `.xset` junto al nombre del JAR.

## 4. Organizar el contenido

En LaunchBuilder, crea pestañas y nodos para representar la navegación del material. Puedes usar nodos para agrupar temas y nodos secundarios para páginas o programas.

Para asociar una página HTML con el nodo seleccionado:

1. Selecciona o crea el nodo.
2. En la barra de edición del nodo, abre el selector junto al campo de contenido/display.
3. Elige la opción **Open HTML** y selecciona el archivo HTML desde la carpeta de trabajo.
4. Asigna un título y, si corresponde, una descripción al nodo.
5. Repite el proceso para las demás páginas o recursos que quieras mostrar.

Usa el botón de vista previa del Builder para comprobar cómo queda la navegación y si cada HTML se abre correctamente.

> El panel HTML de OSP Launcher está basado en `JEditorPane`, no en un navegador web completo. Para máxima compatibilidad, usa HTML sencillo y comprueba el resultado en el Launcher. Funciones modernas de JavaScript, CSS avanzado, multimedia embebida y contenido que requiere conexión pueden no comportarse como en Chrome o Firefox.

## 5. Añadir programas o simulaciones

Para crear un programa ejecutable, compilarlo como JAR y conectarlo a un nodo del Launcher, sigue la guía [Construir una simulación masa-resorte y empaquetarla como JAR](simulacion-masa-resorte-jar.md). Allí se construye `resorte-app.jar`, se configura como classpath del nodo y se selecciona `demo.ResorteMasa` como clase principal.

El JAR de simulación no se preselecciona automáticamente en **Create Jar**: selecciónalo explícitamente en el árbol del diálogo. OSP `JarTool` desempaqueta los JAR fuente seleccionados y mezcla sus archivos en el Launcher JAR final. Al ejecutarse el paquete, el propio JAR del Launcher se añade al classpath; por eso las clases de la simulación incluidas en el paquete quedan disponibles sin distribuir un JAR anidado. Si la aplicación depende de otros JAR, inclúyelos también y prueba el resultado en una instalación limpia.

## 6. Guardar el conjunto

Usa **Save All** para guardar el conjunto `.xset` y los nodos XML asociados. Comprueba que:

- el `.xset` y los XML de nodos están en la carpeta de trabajo
- las páginas y los recursos referenciados existen
- las rutas almacenadas son relativas a esa carpeta o a una subcarpeta
- la vista previa no muestra rutas inválidas

El `.xset` guarda la organización de pestañas y nodos; por sí solo no garantiza que todos los archivos referenciados estén incluidos. Los recursos deben copiarse y seleccionarse para el empaquetado.

## 7. Crear el JAR portable

1. Selecciona la acción **Create Jar** en LaunchBuilder.
2. En el diálogo, incluye `osp.jar`, el JAR de la simulación (selección explícita) y todos los recursos necesarios: `.xset`, XML de nodos, HTML, imágenes, hojas de estilo, datos y carpetas que los contienen.
3. Revisa la selección antes de aceptarla; no incluyas archivos temporales ni materiales innecesarios.
4. Confirma la creación.

El JAR de salida se crea en la carpeta base de LaunchBuilder con el nombre del conjunto, por ejemplo:

```text
osp/ant/build/jar/mi_contenido.jar
```

LaunchBuilder genera el manifiesto del paquete para iniciar `org.opensourcephysics.tools.Launcher`.
El paquete debe contener `mi_contenido.xset` en su raíz; selecciona también la carpeta `recursos/` y cualquier otro archivo o carpeta referenciado por los nodos o las páginas HTML. Los JAR de simulación seleccionados se desempaquetan dentro del JAR final.

## 8. Probar la portabilidad

Prueba el artefacto resultante, no solo el conjunto abierto en LaunchBuilder:

```bash
cd osp/ant/build/jar
java -jar mi_contenido.jar
```

Para una prueba de portabilidad más fiable:

1. Copia únicamente `mi_contenido.jar` a otra carpeta, sin los archivos fuente del conjunto.
2. Ejecútalo con Java.
3. Revisa cada pestaña y cada nodo HTML.
4. Abre los datos y ejecuta cada simulación incluida.
5. Repite la prueba en otro equipo o usuario, si es posible.

Si algo falla, revisa primero las rutas relativas, los recursos omitidos, el classpath de las simulaciones y las dependencias externas.

## Flujo resumido

```text
fuentes HTML, datos y programas
        ↓
LaunchBuilder: pestañas, nodos y referencias
        ↓
Save All: conjunto .xset y nodos XML
        ↓
Create Jar: OSP Launcher + recursos seleccionados
        ↓
probar el JAR fuera de la carpeta de desarrollo
```

## Qué significa “portable”

El paquete es portable cuando el JAR puede moverse y abrirse en otro directorio sin depender de rutas absolutas ni de archivos que solo existen en el equipo del autor. También requiere un entorno Java compatible. Las dependencias externas que no estén dentro del JAR o disponibles en la máquina destino deben distribuirse y configurarse por separado.
