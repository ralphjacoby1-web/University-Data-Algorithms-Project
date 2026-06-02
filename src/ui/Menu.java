package ui;

import java.util.Scanner;

public class Menu {

    private Scanner scanner;

    public Menu() {
        scanner = new Scanner(System.in);
    }

    public void iniciar() {

        int opcionPrincipal = -1;

        while (opcionPrincipal != 0) {

            System.out.println("\n========================================");
            System.out.println(" SISTEMA DE GESTION UNIVERSITARIA");
            System.out.println("========================================");
            System.out.println("1. Cargar datos de prueba");
            System.out.println("2. Gestionar estudiantes");
            System.out.println("3. Gestionar materias");
            System.out.println("4. Gestionar estructura academica");
            System.out.println("5. Gestionar atencion de alumnos");
            System.out.println("6. Gestionar correlatividades");
            System.out.println("7. Operaciones por TDA");
            System.out.println("8. Consultas complejas");
            System.out.println("9. Mostrar estado general del sistema");
            System.out.println("0. Salir");
            System.out.println("========================================");
            System.out.print("Seleccione una opcion: ");

            opcionPrincipal = scanner.nextInt();
            scanner.nextLine();

            if (opcionPrincipal == 1) {

                System.out.println("\n--- CARGA DE DATOS DE PRUEBA ---");
                System.out.println("Aca se cargarian datos de prueba.");
                System.out.println("Estudiantes, materias, carreras, correlatividades y operaciones.");

            } else if (opcionPrincipal == 2) {

                int opcionEstudiantes = -1;

                while (opcionEstudiantes != 0) {

                    System.out.println("\n--- GESTION DE ESTUDIANTES ---");
                    System.out.println("1. Alta de estudiante");
                    System.out.println("2. Baja de estudiante");
                    System.out.println("3. Buscar estudiante por legajo");
                    System.out.println("4. Mostrar altura del AVL");
                    System.out.println("5. Mostrar factor de equilibrio");
                    System.out.println("6. Listar estudiantes ordenados");
                    System.out.println("0. Volver");
                    System.out.print("Seleccione una opcion: ");

                    opcionEstudiantes = scanner.nextInt();
                    scanner.nextLine();

                    if (opcionEstudiantes == 1) {

                        System.out.println("\nAlta de estudiante");

                        System.out.print("Ingrese legajo: ");
                        int legajo = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Ingrese nombre: ");
                        String nombre = scanner.nextLine();

                        System.out.println("Estudiante cargado: " + legajo + " - " + nombre);
                        System.out.println("Luego se insertaria en AVL, Dictionary, Stack y Btree.");

                    } else if (opcionEstudiantes == 2) {

                        System.out.println("\nBaja de estudiante");

                        System.out.print("Ingrese legajo: ");
                        int legajo = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Estudiante a eliminar: " + legajo);
                        System.out.println("Luego se eliminaria del AVL y Dictionary.");

                    } else if (opcionEstudiantes == 3) {

                        System.out.println("\nBuscar estudiante");

                        System.out.print("Ingrese legajo: ");
                        int legajo = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Buscando estudiante con legajo: " + legajo);

                    } else if (opcionEstudiantes == 4) {

                        System.out.println("\nMostrar altura del AVL");
                        System.out.println("Luego se mostraria la altura del AVL.");

                    } else if (opcionEstudiantes == 5) {

                        System.out.println("\nMostrar factor de equilibrio");

                        System.out.print("Ingrese legajo del nodo: ");
                        int legajo = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Luego se mostraria el factor de equilibrio del nodo: " + legajo);

                    } else if (opcionEstudiantes == 6) {

                        System.out.println("\nListar estudiantes ordenados");
                        System.out.println("Luego se haria recorrido ordenado.");

                    } else if (opcionEstudiantes == 0) {

                        System.out.println("Volviendo al menu principal...");

                    } else {

                        System.out.println("Opcion invalida.");
                    }
                }

            } else if (opcionPrincipal == 3) {

                int opcionMaterias = -1;

                while (opcionMaterias != 0) {

                    System.out.println("\n--- GESTION DE MATERIAS ---");
                    System.out.println("1. Alta de materia");
                    System.out.println("2. Baja de materia");
                    System.out.println("3. Buscar materia por codigo");
                    System.out.println("4. Listar materias ordenadas");
                    System.out.println("5. Asociar profesor a materia");
                    System.out.println("0. Volver");
                    System.out.print("Seleccione una opcion: ");

                    opcionMaterias = scanner.nextInt();
                    scanner.nextLine();

                    if (opcionMaterias == 1) {

                        System.out.println("\nAlta de materia");

                        System.out.print("Ingrese codigo de materia: ");
                        String codigo = scanner.nextLine();

                        System.out.print("Ingrese nombre de materia: ");
                        String nombre = scanner.nextLine();

                        System.out.println("Materia cargada: " + codigo + " - " + nombre);
                        System.out.println("Luego se insertaria en BST, Dictionary, Graph, Stack y Btree.");

                    } else if (opcionMaterias == 2) {

                        System.out.println("\nBaja de materia");

                        System.out.print("Ingrese codigo de materia: ");
                        String codigo = scanner.nextLine();

                        System.out.println("Materia a eliminar: " + codigo);

                    } else if (opcionMaterias == 3) {

                        System.out.println("\nBuscar materia");

                        System.out.print("Ingrese codigo de materia: ");
                        String codigo = scanner.nextLine();

                        System.out.println("Buscando materia con codigo: " + codigo);

                    } else if (opcionMaterias == 4) {

                        System.out.println("\nListar materias ordenadas");
                        System.out.println("Luego se usaria el recorrido inorden del BST.");

                    } else if (opcionMaterias == 5) {

                        System.out.println("\nAsociar profesor a materia");

                        System.out.print("Ingrese codigo de materia: ");
                        String codigoMateria = scanner.nextLine();

                        System.out.print("Ingrese nombre del profesor: ");
                        String nombreProfesor = scanner.nextLine();

                        System.out.println("Profesor " + nombreProfesor + " asociado a materia " + codigoMateria);

                    } else if (opcionMaterias == 0) {

                        System.out.println("Volviendo al menu principal...");

                    } else {

                        System.out.println("Opcion invalida.");
                    }
                }

            } else if (opcionPrincipal == 4) {

                int opcionEstructura = -1;

                while (opcionEstructura != 0) {

                    System.out.println("\n--- ESTRUCTURA ACADEMICA ---");
                    System.out.println("1. Crear carrera");
                    System.out.println("2. Agregar anio a carrera");
                    System.out.println("3. Agregar materia a un anio");
                    System.out.println("4. Recorrido en profundidad");
                    System.out.println("5. Recorrido en amplitud");
                    System.out.println("0. Volver");
                    System.out.print("Seleccione una opcion: ");

                    opcionEstructura = scanner.nextInt();
                    scanner.nextLine();

                    if (opcionEstructura == 1) {

                        System.out.print("Ingrese nombre de carrera: ");
                        String carrera = scanner.nextLine();

                        System.out.println("Carrera creada: " + carrera);

                    } else if (opcionEstructura == 2) {

                        System.out.print("Ingrese carrera: ");
                        String carrera = scanner.nextLine();

                        System.out.print("Ingrese anio: ");
                        String anio = scanner.nextLine();

                        System.out.println("Anio " + anio + " agregado a carrera " + carrera);

                    } else if (opcionEstructura == 3) {

                        System.out.print("Ingrese carrera: ");
                        String carrera = scanner.nextLine();

                        System.out.print("Ingrese anio: ");
                        String anio = scanner.nextLine();

                        System.out.print("Ingrese materia: ");
                        String materia = scanner.nextLine();

                        System.out.println("Materia " + materia + " agregada a " + anio + " de " + carrera);

                    } else if (opcionEstructura == 4) {

                        System.out.println("Luego se recorreria el arbol generico en profundidad.");

                    } else if (opcionEstructura == 5) {

                        System.out.println("Luego se recorreria el arbol generico en amplitud.");

                    } else if (opcionEstructura == 0) {

                        System.out.println("Volviendo al menu principal...");

                    } else {

                        System.out.println("Opcion invalida.");
                    }
                }

            } else if (opcionPrincipal == 5) {

                int opcionAtencion = -1;

                while (opcionAtencion != 0) {

                    System.out.println("\n--- ATENCION DE ALUMNOS ---");
                    System.out.println("1. Agregar alumno a cola comun");
                    System.out.println("2. Atender proximo alumno comun");
                    System.out.println("3. Ver proximo alumno comun");
                    System.out.println("4. Agregar alumno a cola prioritaria");
                    System.out.println("5. Atender alumno prioritario");
                    System.out.println("6. Verificar si las colas estan vacias");
                    System.out.println("0. Volver");
                    System.out.print("Seleccione una opcion: ");

                    opcionAtencion = scanner.nextInt();
                    scanner.nextLine();

                    if (opcionAtencion == 1) {

                        System.out.print("Ingrese legajo: ");
                        int legajo = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Alumno agregado a cola comun: " + legajo);

                    } else if (opcionAtencion == 2) {

                        System.out.println("Luego se usaria dequeue.");

                    } else if (opcionAtencion == 3) {

                        System.out.println("Luego se usaria front.");

                    } else if (opcionAtencion == 4) {

                        System.out.print("Ingrese legajo: ");
                        int legajo = scanner.nextInt();

                        System.out.print("Ingrese prioridad: ");
                        int prioridad = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Alumno agregado a cola prioritaria: " + legajo);
                        System.out.println("Prioridad: " + prioridad);

                    } else if (opcionAtencion == 5) {

                        System.out.println("Luego se usaria extractMax o extractMin.");

                    } else if (opcionAtencion == 6) {

                        System.out.println("Luego se verificaria isEmpty en las colas.");

                    } else if (opcionAtencion == 0) {

                        System.out.println("Volviendo al menu principal...");

                    } else {

                        System.out.println("Opcion invalida.");
                    }
                }

            } else if (opcionPrincipal == 6) {

                int opcionCorrelativas = -1;

                while (opcionCorrelativas != 0) {

                    System.out.println("\n--- CORRELATIVIDADES ---");
                    System.out.println("1. Agregar materia como vertice");
                    System.out.println("2. Agregar correlatividad entre materias");
                    System.out.println("3. Recorrer correlatividades con BFS");
                    System.out.println("4. Recorrer correlatividades con DFS");
                    System.out.println("5. Verificar si una materia habilita a otra");
                    System.out.println("0. Volver");
                    System.out.print("Seleccione una opcion: ");

                    opcionCorrelativas = scanner.nextInt();
                    scanner.nextLine();

                    if (opcionCorrelativas == 1) {

                        System.out.print("Ingrese codigo de materia: ");
                        String materia = scanner.nextLine();

                        System.out.println("Vertice agregado: " + materia);

                    } else if (opcionCorrelativas == 2) {

                        System.out.print("Ingrese materia previa: ");
                        String materiaPrevia = scanner.nextLine();

                        System.out.print("Ingrese materia posterior: ");
                        String materiaPosterior = scanner.nextLine();

                        System.out.println("Correlatividad agregada: " + materiaPrevia + " -> " + materiaPosterior);

                    } else if (opcionCorrelativas == 3) {

                        System.out.print("Ingrese materia inicial: ");
                        String materiaInicial = scanner.nextLine();

                        System.out.println("Luego se recorreria con BFS desde: " + materiaInicial);

                    } else if (opcionCorrelativas == 4) {

                        System.out.print("Ingrese materia inicial: ");
                        String materiaInicial = scanner.nextLine();

                        System.out.println("Luego se recorreria con DFS desde: " + materiaInicial);

                    } else if (opcionCorrelativas == 5) {

                        System.out.print("Ingrese materia origen: ");
                        String origen = scanner.nextLine();

                        System.out.print("Ingrese materia destino: ");
                        String destino = scanner.nextLine();

                        System.out.println("Luego se verificaria si existe camino de " + origen + " a " + destino);

                    } else if (opcionCorrelativas == 0) {

                        System.out.println("Volviendo al menu principal...");

                    } else {

                        System.out.println("Opcion invalida.");
                    }
                }

            } else if (opcionPrincipal == 7) {

                int opcionTda = -1;

                while (opcionTda != 0) {

                    System.out.println("\n--- OPERACIONES POR TDA ---");
                    System.out.println("1. Probar Stack");
                    System.out.println("2. Probar Queue");
                    System.out.println("3. Probar PriorityQueue");
                    System.out.println("4. Probar Dictionary");
                    System.out.println("5. Probar BST");
                    System.out.println("6. Probar AVL");
                    System.out.println("7. Probar Btree");
                    System.out.println("8. Probar GenericTree");
                    System.out.println("9. Probar Graph");
                    System.out.println("0. Volver");
                    System.out.print("Seleccione una opcion: ");

                    opcionTda = scanner.nextInt();
                    scanner.nextLine();

                    if (opcionTda == 1) {
                        System.out.println("Luego se probaria push, pop, peek e isEmpty.");
                    } else if (opcionTda == 2) {
                        System.out.println("Luego se probaria enqueue, dequeue, front e isEmpty.");
                    } else if (opcionTda == 3) {
                        System.out.println("Luego se probaria insert y extractMax o extractMin.");
                    } else if (opcionTda == 4) {
                        System.out.println("Luego se probaria put, get, remove y contains.");
                    } else if (opcionTda == 5) {
                        System.out.println("Luego se probaria insertar, buscar, eliminar e inorden.");
                    } else if (opcionTda == 6) {
                        System.out.println("Luego se probaria insertar, buscar, eliminar, altura y factor.");
                    } else if (opcionTda == 7) {
                        System.out.println("Luego se probaria insertar y buscar.");
                    } else if (opcionTda == 8) {
                        System.out.println("Luego se probaria agregar hijo y recorridos.");
                    } else if (opcionTda == 9) {
                        System.out.println("Luego se probaria agregar vertice, agregar arista, BFS y DFS.");
                    } else if (opcionTda == 0) {
                        System.out.println("Volviendo al menu principal...");
                    } else {
                        System.out.println("Opcion invalida.");
                    }
                }

            } else if (opcionPrincipal == 8) {

                int opcionConsulta = -1;

                while (opcionConsulta != 0) {

                    System.out.println("\n--- CONSULTAS COMPLEJAS ---");
                    System.out.println("1. Verificar si un estudiante puede cursar una materia");
                    System.out.println("2. Deshacer ultima operacion y actualizar indices");
                    System.out.println("3. Atender alumno prioritario y registrar operacion");
                    System.out.println("4. Mostrar recorrido academico y materias ordenadas");
                    System.out.println("5. Buscar historial de operaciones de un estudiante");
                    System.out.println("0. Volver");
                    System.out.print("Seleccione una opcion: ");

                    opcionConsulta = scanner.nextInt();
                    scanner.nextLine();

                    if (opcionConsulta == 1) {

                        System.out.print("Ingrese legajo: ");
                        int legajo = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Ingrese codigo de materia: ");
                        String materia = scanner.nextLine();

                        System.out.println("Luego se combinaria AVL + Dictionary + Graph + Btree.");
                        System.out.println("Legajo: " + legajo);
                        System.out.println("Materia: " + materia);

                    } else if (opcionConsulta == 2) {

                        System.out.println("Luego se combinaria Stack + AVL + Dictionary + Btree.");

                    } else if (opcionConsulta == 3) {

                        System.out.println("Luego se combinaria PriorityQueue + Dictionary + Btree + Stack.");

                    } else if (opcionConsulta == 4) {

                        System.out.println("Luego se combinaria GenericTree + Queue + BST + Dictionary.");

                    } else if (opcionConsulta == 5) {

                        System.out.print("Ingrese legajo: ");
                        int legajo = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Luego se combinaria Btree + AVL + Dictionary.");
                        System.out.println("Legajo: " + legajo);

                    } else if (opcionConsulta == 0) {

                        System.out.println("Volviendo al menu principal...");

                    } else {

                        System.out.println("Opcion invalida.");
                    }
                }

            } else if (opcionPrincipal == 9) {

                System.out.println("\n--- ESTADO GENERAL DEL SISTEMA ---");
                System.out.println("Luego se mostraria:");
                System.out.println("- Cantidad de estudiantes");
                System.out.println("- Cantidad de materias");
                System.out.println("- Cantidad de operaciones");
                System.out.println("- Estado de colas");
                System.out.println("- Estado de arboles");
                System.out.println("- Estado del grafo");

            } else if (opcionPrincipal == 0) {

                System.out.println("\nSaliendo del sistema...");

            } else {

                System.out.println("\nOpcion invalida.");
            }
        }

        scanner.close();
    }
}