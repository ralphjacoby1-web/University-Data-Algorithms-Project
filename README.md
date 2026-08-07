# Sistema de Gestión Universitaria

Trabajo Práctico Integrador de **Estructuras de Datos**. Aplicación de consola en Java que integra nueve Tipos de Datos Abstractos (TDAs) implementados desde cero para administrar estudiantes, materias, profesores, la estructura curricular de las carreras y el régimen de correlatividades de una universidad.

**Grupo 67** — Alexis Muñoz · Martín Stöckl · Luciano Domínguez · Ralph Jacoby

![Demo](docs/demo.gif)

---

## Descripción

El sistema modela la gestión académica de una universidad. Cada necesidad del dominio se resuelve con la estructura de datos cuyo comportamiento se ajusta mejor al patrón de uso real, de modo que la combinación de TDAs tenga sentido operativo y cada uno aporte una ventaja concreta de complejidad u organización.

| Necesidad del dominio | TDA elegido |
|---|---|
| Índice de materias por código (búsqueda alfabética) | ABB |
| Índice de estudiantes por legajo, con altas/bajas frecuentes | AVL |
| Registro masivo de legajos (archivo simulado) | Árbol B (t=2) |
| Estructura jerárquica Carrera → Año → Materia | Árbol Genérico (n-ario) |
| Red de correlatividades entre materias | Grafo dirigido |
| Atención por orden de llegada en ventanilla | Cola (FIFO) |
| Deshacer la última operación realizada | Pila (LIFO) |
| Atención priorizada (urgencias administrativas) | Cola de Prioridad |
| Acceso O(1) por legajo / aprobadas sin duplicar | Diccionario / Conjunto |

---

## Requisitos

- **JDK 8 o superior** (probado con OpenJDK).
- No requiere dependencias externas ni librerías de terceros: todos los TDAs están implementados sin usar las colecciones del JDK.

---

## Compilación y ejecución

Desde la raíz del proyecto:

```bash
# Compilar todo el código fuente a la carpeta out/
javac -d out $(find src -name "*.java")

# Ejecutar
java -cp out ui.Main
```

En Windows (PowerShell), si `find` no está disponible:

```powershell
javac -d out (Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName })
java -cp out ui.Main
```

Al iniciar, elegí la opción **1 (Cargar datos de prueba)** para poblar el sistema con estudiantes, materias, correlatividades y aprobaciones de ejemplo. Desde ahí podés probar todas las consultas sin carga manual.

---

## Estructura del proyecto

```
src/
├── ui/                     Interfaz por consola
│   ├── Main.java           Punto de entrada
│   └── Menu.java           Menú interactivo jerárquico
├── service/                Lógica de negocio
│   ├── UniversitySystem.java   Altas/bajas e índices; delega consultas
│   └── ConsultasService.java   Las 4 consultas complejas (inyección de dependencias)
├── model/                  Entidades de dominio
│   ├── Student.java        Clave: legajo (Comparable + equals/hashCode)
│   ├── Subject.java        Clave: código (Comparable + equals/hashCode)
│   ├── Professor.java
│   ├── Career.java
│   └── Operation.java      Operación apilada en el historial (deshacer)
├── tda/                    Implementaciones propias de cada TDA
│   ├── Pila.java
│   ├── Cola.java
│   ├── ColaPrioridad.java
│   ├── Diccionario.java
│   ├── Abb.java
│   ├── Avl.java
│   ├── ArbolB.java
│   ├── ArbolGenerico.java
│   ├── Grafo.java
│   └── interfaces/         Contrato (interfaz) de cada estructura
│       ├── IPila.java   ICola.java   IColaPrioridad.java   IDiccionario.java
│       ├── IAbb.java    IAvl.java    IArbolB.java          IArbolGenerico.java
│       └── IGrafo.java
```

La arquitectura está en capas: `ui` depende de `service`, `service` depende de los TDAs (vía interfaces) y los TDAs dependen de `model`. Programar contra interfaces mantiene el acoplamiento bajo.

---

## Tipos de Datos Abstractos

| TDA | Clase | Operaciones públicas |
|---|---|---|
| Pila | `Pila<T>` | `apilar`, `desapilar`, `tope`, `estaVacia`, `tamanio` |
| Cola | `Cola<T>` | `encolar`, `desencolar`, `frente`, `estaVacia`, `tamanio` |
| Cola de Prioridad | `ColaPrioridad<T>` | `encolar(elem, prioridad)`, `desencolar`, `verFrente`, `verTamanio`, `estaVacio` |
| Diccionario | `Diccionario<K,V>` | `poner`, `obtener`, `eliminar`, `contieneClave`, `estaVacio`, `tamanio` |
| ABB | `Abb<T>` | `insertar`, `buscar`, `eliminar`, `enOrden`, `maximo`, `minimo` |
| AVL | `Avl<T>` | `insertar`, `buscar`, `eliminar`, `enOrden`, `obtenerAltura`, `obtenerFactorBalance` |
| Árbol B (t=2) | `ArbolB` | `insertar`, `buscar`, `enOrden` |
| Árbol Genérico | `ArbolGenerico<T>` | `agregarRaiz`, `agregarHijo`, `recorridoProfundidad`, `recorridoAmplitud` |
| Grafo dirigido | `Grafo` | `agregarVertice`, `agregarArista`, `recorridoAmplitud`, `recorridoProfundidad`, `existeCamino`, `cantidadVertices` |

---

## Consultas complejas

Cada consulta combina tres o más TDAs distintos. Se acceden desde el menú **8 (Consultas complejas)**.

1. **¿Puede cursar una materia?** — Diccionario + ABB + Grafo + Conjunto. Busca al estudiante, verifica la materia, recorre el grafo de correlatividades para obtener las previas y consulta el conjunto de aprobadas del estudiante.
2. **Camino de correlatividades entre dos materias** — Grafo + Cola + ABB. Halla el camino más corto (BFS) y consulta los datos de cada materia en el ABB.
3. **Atender al próximo alumno prioritario y registrarlo** — Cola de Prioridad + Diccionario + Conjunto. Extrae al de mayor urgencia, recupera sus datos y lo marca como atendido sin duplicar.
4. **Deshacer la última operación y ver el estado de los índices** — Pila + AVL + ABB + Árbol B. Desapila la última operación del historial y muestra el estado de los tres índices.

---

## Menú principal

```
1. Cargar datos de prueba
2. Gestionar estudiantes
3. Gestionar materias
4. Gestionar estructura academica
5. Atencion de alumnos
6. Gestionar correlatividades
7. Historial de operaciones
8. Consultas complejas
9. Estado general del sistema
0. Salir
```

---

## Documentación

El informe completo del trabajo (narrativa, arquitectura, justificación de cada TDA con análisis de complejidad, consultas complejas y ejemplos de ejecución) se encuentra en `Grupo67_TPIntegrador.pdf`.