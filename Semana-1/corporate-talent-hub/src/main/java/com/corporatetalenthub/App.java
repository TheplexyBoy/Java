package com.corporatetalenthub;
import com.corporatetalenthub.modelo.Empleado;
import com.corporatetalenthub.modelo.EmpresaRecord;

/**
 *
 * @author c5
 */

public class App {

    public static void main(String[] args) {
        String encabezado = """
                =====================================
                     CORPORATE TALENT HUB
                   Gestión del talento humano
                =====================================
                """;
        System.out.println(encabezado);

        Empleado empleado = crearEmpleadoDePrueba();
        EmpresaRecord empresa = new EmpresaRecord(
                "CodeUp Solutions",
                "900123456-7",
                2015);

        System.out.println(empleado);
        System.out.println("Empresa: " + empresa.nombre());
        System.out.println("Salario final: " + empleado.calcularSalarioFinal());
        System.out.println("¿ID par con bono extra?: " + empleado.tieneBonoExtra());
        System.out.println("¿Empleado elegible?: " + empleado.validarEligibilidad());

        if (empleado.tieneBonoExtra()) {
            empleado.actualizarbonoMensual(100_000.0);
            System.out.println("Bono actualizado con +=: " + empleado.getBonoMensual());
        }

        compararReferencias();
        ejecutarLaboratorioDeNulos(empleado);
    }

    private static Empleado crearEmpleadoDePrueba() {
        return new Empleado(
                (byte) 3,             // byte
                (short) 2024,         // short
                102,                  // int: ID par
                1_023_456_789L,       // long: sufijo L
                92.5f,                // float: sufijo f
                3_000_000.0,          // double
                'I',                  // char: contrato indefinido
                true,                 // boolean
                "Laura Gómez",        // String
                27,
                2,
                500_000.0);
    }

    private static void compararReferencias() {
        Empleado primero = crearEmpleadoDePrueba();
        Empleado segundo = crearEmpleadoDePrueba();
        Empleado aliasDelPrimero = primero;

        System.out.println("primero == segundo: " + (primero == segundo));
        System.out.println("primero == aliasDelPrimero: "
                + (primero == aliasDelPrimero));

        // == no compara los atributos de los objetos: comprueba si ambas variables
        // se refieren exactamente al mismo objeto. primero y segundo se crearon con
        // new por separado; aliasDelPrimero recibió la misma referencia de primero.
        // Conceptualmente los objetos viven en el Heap, pero == no debe entenderse
        // como una comparación manual de direcciones físicas de memoria.
    }

    private static void ejecutarLaboratorioDeNulos(Empleado empleado) {
        empleado.setNombre(null);

        try {
            System.out.println(empleado.getNombre().toUpperCase());
        } catch (NullPointerException excepcion) {
            System.out.println("NPE controlada: " + excepcion.getMessage());
        }

        // Java 8 normalmente informa que ocurrió una NullPointerException y señala
        // la línea mediante el stack trace, pero una expresión encadenada puede hacer
        // difícil reconocer cuál referencia era null.
        // Desde Java 14, Helpful NullPointerExceptions puede indicar que no se pudo
        // invocar toUpperCase() porque el resultado de getNombre() era null.
        // El try/catch es solo para que el laboratorio no detenga toda la aplicación;
        // la solución real es validar el dato o impedir nombres nulos según el dominio.
    }
}