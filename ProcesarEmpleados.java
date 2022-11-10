
//la clase Files permite leer o escribir archivos
//la clase Path es una representacion abstracta de un archivo o directorio en el sistema de archivos
//la clase Collectors especifica implementaciones de operaciones de recoleccion comunes
//la clase Stream representa una secuencia de elementos y soporta diversas operaciones para procesar los datos

//el metodo lines de la clase Files toma como argumento un objeto de la clase Path y devuelve un objeto Stream<String>
//el metodo of de la clase Path toma como argumento un String que representa el path de un archivo y devuelve un objeto Path
//el metodo collect de la clase Stream toma como argumento un objeto Collectors y devuelve un objeto
//el metodo groupingBy de la clase Collectors agrupa los elementos de un Stream en un Map<K, List<V>> siendo K la clave y V el valor
//el metodo forEach de la clase Stream itera sobre los elementos de un Stream y ejecuta la operacion indicada
//el metodo get de la clase Optional accede al valor de un objeto Optional si es presente, caso contrario arroja una excepcion

//el metodo compareTo de la clase Comparator compara dos objetos especificados y devuelve un valor negativo, cero o un valor positivo segun si el
//primer objeto es menor, igual o mayor que el segundo

//la clase ArrayList implementa la interfaz List y es un tipo de dato dinamico, es decir, el tamaño de la lista puede variar
//la clase TreeMap implementa la interfaz SortedMap y Maps permite almacenar datos en pares clave-valor, TreeMap ordena los datos segun la clave
//la clase Optional representa un valor presente o ausente, permite representar datos nulos de una forma segura

//En la primera línea se importan las librerías necesarias para el programa.
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.*;

// Se crea la clase ProcesarEmpleados, la cual contiene el método main, el cual es el que se ejecutará al inicio del programa.
// Se crean las variables empleados y path, la primera es una lista de objetos de la clase Empleado y la segunda es la ruta en donde se encuentra el archivo csv.

public class ProcesarEmpleados {


    public static void main(String[] args) {
        // inicializa arreglo de objetos Empleado

        cargarArchivoCsv();

        mostrarEmpleadosEntreValores();
        empleadosPorDpto();
        mostrarCantEmpleadosPorDpto();
        sumaNominaDpto();
        empleadoQueMasGana();
        NombreEmpleadoConSalarioMayor();
        NombreEmpleadoConSalarioMenor();
        promedioPorDpto();
        promedioGeneral();
        totalNomina();

    }

    // - La primera línea del código declara una lista de objetos de tipo Empleado, 
    //la cual se usará para almacenar los datos leídos del archivo csv. 

    private static List<Empleado> empleados = new ArrayList<>();




     // - Luego, se encuentra el método cargarArchivoCsv, el cual se encarga de leer el archivo csv y guardar los datos en la lista anteriormente declarada.

    private static void cargarArchivoCsv() {

        // Para esto, primero se declaran dos variables, path y pattern, las cuales se usarán para leer el archivo y compilar un patrón respectivamente.
        // Luego, se usa el try-with-resources para que, una vez que se terminen de usar las variables dentro de este bloque, se liberen las mismas.
        // Se usa el método lines de la clase Files para leer el archivo linea por linea y luego se usa el método map de la interfaz Stream para transformar cada línea en un arreglo de String, donde cada elemento del arreglo es un dato de un empleado.
        // Luego, se usa otro método map para transformar cada arreglo en un objeto de tipo Empleado y finalmente se usa el método forEach de la interfaz Stream para agregar cada objeto Empleado a la lista anteriormente declarada. 

        Path path = Path.of("empleado.csv");
        Pattern pattern = Pattern.compile(";");
        try (Stream<String> stream = Files.lines(path)) {
            stream
                .map(line -> line.split(";"))
                .map(array -> new Empleado(
                    Integer.parseInt(array[0]),
                    array[1],
                    array[2],
                    Double.parseDouble(array[3]),
                    array[4]
                ))
                .forEach(empleados::add);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // - Luego, se encuentra el método mostrarEmpleadosEntreValores, el cual imprime en pantalla los nombres de los empleados que ganan entre 3000 y 7000 dólares.

    private static void mostrarEmpleadosEntreValores() {
        
        // Para esto, se usa el método stream de la interfaz List, se usa el método filter de la interfaz Stream para filtrar los empleados que cumplen con el criterio anterior y finalmente se usa el método forEach de la interfaz Stream para imprimir en pantalla los nombres de los empleados que cumplen con el criterio. 

        System.out.println("Lista de empleados que ganan entre $3000 y $7000: ");
        empleados.stream().filter(empleado -> empleado.getSalario() > 3000 && empleado.getSalario() < 7000)
            .forEach(System.out::println);
            System.out.println("");
    }


    // - Luego, se encuentra el método empleadosPorDpto, el cual imprime en pantalla la cantidad de empleados que hay por departamento.

    private static void empleadosPorDpto(){
        
        // Para esto, se usa el método stream de la interfaz List, se usa el método groupingBy de la clase Collectors para agrupar los empleados por departamento y finalmente se usa el método forEach de la interfaz Map para imprimir en pantalla el departamento y la cantidad de empleados que hay en el mismo.

    System.out.printf("%nEmpleados por departamento:%n");
    Map<String, List<Empleado>> agrupadoPorDepartamento =
            empleados.stream()
                    .collect(Collectors.groupingBy(Empleado::getDepartamento));
    agrupadoPorDepartamento.forEach(
            (departamento, empleadosEnDepartamento) ->
            {
                System.out.println(departamento);
                empleadosEnDepartamento.forEach(
                        empleado -> System.out.printf(" %s%n", empleado));
                        System.out.println("");
            }
    );
        }

        // - Luego, se encuentra el método mostrarCantEmpleadosPorDpto, el cual imprime en pantalla la cantidad de empleados que hay por departamento.

    private static void mostrarCantEmpleadosPorDpto() {
         
        //Para esto, se declara un objeto de tipo Map, se usa el método stream de la interfaz List, se usa el método groupingBy de la clase Collectors para agrupar los empleados por departamento,
        // se usa el método counting de la clase Collectors para contar la cantidad de empleados que hay en cada departamento y finalmente se usa un for each para imprimir en pantalla el departamento y la cantidad de empleados que hay en el mismo. 

        System.out.println("Cantidad de empleados por departamento: ");
        Map<String, Long> map = new TreeMap<>();
        map = empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento, Collectors.counting()));

        for(Map.Entry<String, Long> e: map.entrySet())
            System.out.println("Dpto: " + e.getKey() + " Cantidad Empleados: " + e.getValue());
            System.out.println("");
    }

    // - Luego, se encuentra el método sumaNominaDpto, el cual imprime en pantalla la suma de los salarios de los empleados de cada departamento. 

    private static void sumaNominaDpto() {

        //Para esto, se usa el método stream de la interfaz List, se usa el método groupingBy de la clase Collectors para agrupar los empleados por departamento, se usa el método summingDouble de la clase Collectors para sumar los salarios de los empleados de cada departamento y finalmente se usa el método forEach de la interfaz Map para imprimir en pantalla el departamento y la suma de los salarios de los empleados que trabajan en el mismo.

        System.out.println("Total de la nómina por departamento: ");
        empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento, Collectors.summingDouble(Empleado::getSalario)))
            .forEach((k, v) -> System.out.println("Departamento: " + k + " Total nómina: " + v));
            System.out.println("");
    }

    // Luego, se encuentra el método empleadoQueMasGana, el cual imprime en pantalla el empleado que más gana de cada departamento

    private static void empleadoQueMasGana() {

        //Para esto, se usa el método stream de la interfaz List, se usa el método groupingBy de la clase Collectors para agrupar los empleados por departamento, se usa el método maxBy de la clase Collectors para encontrar al empleado que más gana de cada departamento y finalmente se usa el método forEach de la interfaz Map para imprimir en pantalla el departamento y el empleado que más gana en el mismo. 

        System.out.println("Empleado que más gana en cada departamento: ");
        empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento, Collectors.maxBy(Comparator.comparing(Empleado::getSalario))))
            .forEach((k, v) -> System.out.println("Departamento: " + k + " Empleado: " + v.get()));
            System.out.println("");
    }

    // - Luego, se encuentra el método NombreEmpleadoConSalarioMayor, el cual imprime en pantalla el nombre del empleado que más gana en general. 

    private static void NombreEmpleadoConSalarioMayor() {

        //Para esto, se usa el método stream de la interfaz List, se usa el método max de la interfaz Stream para encontrar al empleado que más gana en general y finalmente se usa el método ifPresent de la clase Optional para imprimir en pantalla el empleado que más gana en general.

        System.out.println("Nombre del empleado que más gana del departamento: ");
        empleados.stream().max(Comparator.comparing(Empleado::getSalario)).ifPresent(System.out::println);
        System.out.println("");
    }

    // - Luego, se encuentra el método NombreEmpleadoConSalarioMenor, el cual imprime en pantalla el nombre del empleado que menos gana en general. 

    private static void NombreEmpleadoConSalarioMenor() {

        //Para esto, se usa el método stream de la interfaz List, se usa el método min de la interfaz Stream para encontrar al empleado que menos gana en general y finalmente se usa el método ifPresent de la clase Optional para imprimir en pantalla el empleado que menos gana en general. 

        System.out.println("Nombre del empleado que menos gana del departamento: ");
        empleados.stream().min(Comparator.comparing(Empleado::getSalario)).ifPresent(System.out::println);
        System.out.println("");
    }

    // - Luego, se encuentra el método promedioPorDpto, el cual imprime en pantalla el promedio de los salarios de los empleados de cada departamento.

    private static void promedioPorDpto() {
 
        //Para esto, se usa el método stream de la interfaz List, se usa el método groupingBy de la clase Collectors para agrupar los empleados por departamento, 
        //se usa el método averagingDouble de la clase Collectors para calcular el promedio de los salarios de los empleados de cada departamento y finalmente se usa el método forEach de la interfaz Map para imprimir en pantalla el departamento y el promedio de los salarios de los empleados que trabajan en el mismo. 

        System.out.println("Promedio salarial por departamento: ");
        empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento, Collectors.averagingDouble(Empleado::getSalario)))
            .forEach((k, v) -> System.out.println("Departamento: " + k + " Promedio salarial: " + v));
            System.out.println("");
    }

    // - Luego, se encuentra el método promedioGeneral, el cual imprime en pantalla el promedio general de los salarios de todos los empleados. 

    private static void promedioGeneral() {

        //Para esto, se usa el método stream de la interfaz List, se usa el método averagingDouble de la clase Collectors para calcular el promedio general de los salarios de todos los empleados y finalmente se usa el método ifPresent de la clase Optional para imprimir en pantalla el promedio general de los salarios de todos los empleados. 

        System.out.println("Promedio general de salarios: ");
        double salarios = empleados.stream().collect(Collectors.averagingDouble(Empleado::getSalario));
        System.out.println("Promedio: " + salarios);
        System.out.println("");
    }

    // - Luego, se encuentra el método totalNomina, el cual imprime en pantalla la suma total de los salarios de todos los empleados. 

    private static void totalNomina() {

        //Para esto, se usa el método stream de la interfaz List, se usa el método summingDouble de la clase Collectors para sumar todos los salarios de todos los empleados y finalmente se usa el método ifPresent de la clase Optional para imprimir en pantalla la suma total de los salarios de todos los empleados.

        System.out.println("Total nómina: ");
        double salarios = empleados.stream().collect(Collectors.summingDouble(Empleado::getSalario));
        System.out.println("Total nómina: " + salarios);
    }

}

