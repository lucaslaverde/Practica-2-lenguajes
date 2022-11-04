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


public class ProcesarEmpleados {

    

    public static void main(String[] args) {
        // inicializa arreglo de objetos Emplead

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
    private static List<Empleado> empleados = new ArrayList<>();
    private static void cargarArchivoCsv() {
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

    private static void mostrarEmpleadosEntreValores() {
        System.out.println("Lista de empleados que ganan entre $3000 y $7000: ");
        empleados.stream().filter(empleado -> empleado.getSalario() > 3000 && empleado.getSalario() < 7000)
            .forEach(System.out::println);
            System.out.println("");
    }

    private static void empleadosPorDpto(){
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

    private static void mostrarCantEmpleadosPorDpto() {

        System.out.println("Cantidad de empleados por departamento: ");
        Map<String, Long> map = new TreeMap<>();
        map = empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento, Collectors.counting()));

        for(Map.Entry<String, Long> e: map.entrySet())
            System.out.println("Dpto: " + e.getKey() + " Cantidad Empleados: " + e.getValue());
            System.out.println("");
    }

    private static void sumaNominaDpto() {
        System.out.println("Total de la nómina por departamento: ");
        empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento, Collectors.summingDouble(Empleado::getSalario)))
            .forEach((k, v) -> System.out.println("Departamento: " + k + " Total nómina: " + v));
            System.out.println("");
    }

    private static void empleadoQueMasGana() {
        System.out.println("Empleado que más gana en cada departamento: ");
        empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento, Collectors.maxBy(Comparator.comparing(Empleado::getSalario))))
            .forEach((k, v) -> System.out.println("Departamento: " + k + " Empleado: " + v.get()));
            System.out.println("");
    }

    private static void NombreEmpleadoConSalarioMayor() {
        System.out.println("Nombre del empleado que más gana del departamento: ");
        empleados.stream().max(Comparator.comparing(Empleado::getSalario)).ifPresent(System.out::println);
        System.out.println("");
    }

    private static void NombreEmpleadoConSalarioMenor() {
        System.out.println("Nombre del empleado que menos gana del departamento: ");
        empleados.stream().min(Comparator.comparing(Empleado::getSalario)).ifPresent(System.out::println);
        System.out.println("");
    }

    private static void promedioPorDpto() {
        System.out.println("Promedio salarial por departamento: ");
        empleados.stream().collect(Collectors.groupingBy(Empleado::getDepartamento, Collectors.averagingDouble(Empleado::getSalario)))
            .forEach((k, v) -> System.out.println("Departamento: " + k + " Promedio salarial: " + v));
            System.out.println("");
    }

    private static void promedioGeneral() {
        System.out.println("Promedio general de salarios: ");
        double salarios = empleados.stream().collect(Collectors.averagingDouble(Empleado::getSalario));
        System.out.println("Promedio: " + salarios);
        System.out.println("");
    }

    private static void totalNomina() {
        System.out.println("Total nómina: ");
        double salarios = empleados.stream().collect(Collectors.summingDouble(Empleado::getSalario));
        System.out.println("Total nómina: " + salarios);
    }

}
