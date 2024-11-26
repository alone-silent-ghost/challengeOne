
import java.util.Random;
import java.util.Scanner;

public class SimuladorViajeInterplanetario {

    static String[] planetas = {"Mercurio", "Venus", "Marte", "Júpiter", "Saturno", "Urano", "Neptuno"};
    static double[] distancias = {91691000.0, 41400000.0, 78340000.0, 628730000.0, 1275000000.0, 2723950000.0, 4351400000.0};
    static double velocidad = 100000; // Velocidad en km/h
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();
    static double[] recursos = new double[2]; // Almacenar combustible y oxígeno
    static int planetaSeleccionado = -1;
//esto es un comentario
//esto es otro comentario
    // Variables para controlar si una opción ha sido completada
    static boolean destinoSeleccionado = false;
    static boolean naveSeleccionada = false;
    static boolean recursosIntroducidos = false;

    public static void main(String[] args) {
        while (true) {
            mostrarMenu();
            int opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    if (!destinoSeleccionado) {
                        planetaSeleccionado = seleccionarDestino();
                        destinoSeleccionado = true;
                    } else {
                        System.out.println("Destino ya seleccionado.");
                    }
                    break;
                case 2:
                    if (!naveSeleccionada) {
                        seleccionarNave();
                        naveSeleccionada = true;
                    } else {
                        System.out.println("Nave ya seleccionada.");
                    }
                    break;
                case 3:
                    if (!recursosIntroducidos) {
                        recursos = introducirRecursos();
                        recursosIntroducidos = true;
                    } else {
                        System.out.println("Recursos ya introducidos.");
                    }
                    break;
                case 4:
                    if (destinoSeleccionado && naveSeleccionada && recursosIntroducidos) {
                        iniciarSimulacion();
                    } else {
                        System.out.println("Asegúrate de seleccionar un destino, una nave y de introducir los recursos antes de iniciar la simulación.");
                    }
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Inténtalo de nuevo.");
            }
        }
    }

    static void mostrarMenu() {
        System.out.println("\nMenú Principal:");
        if (!destinoSeleccionado) {
            System.out.println("1. Seleccionar un planeta de destino");
        }
        if (!naveSeleccionada) {
            System.out.println("2. Seleccionar una nave espacial");
        }
        if (!recursosIntroducidos) {
            System.out.println("3. Introducir cantidades de combustible y oxígeno");
        }
        System.out.println("4. Iniciar la simulación del viaje");
        System.out.println("5. Salir del programa");
        System.out.print("Elige una opción: ");
    }

    static int seleccionarDestino() {
        System.out.println("\nSelecciona el planeta destino:");
        for (int i = 0; i < planetas.length; i++) {
            System.out.println((i + 1) + ". " + planetas[i]);
        }
        int opcion = scanner.nextInt() - 1;
        System.out.println("Has seleccionado " + planetas[opcion] + ". Distancia: " + distancias[opcion] + " km");

        // Mostrar sugerencia de recursos necesarios para el viaje
        double combustibleNecesario = calcularCombustible(distancias[opcion]);
        double oxigenoNecesario = calcularOxigeno(distancias[opcion]);
        System.out.println("Sugerencia de recursos para este viaje:");
        System.out.printf("Combustible: %.2f litros\n", combustibleNecesario);
        System.out.printf("Oxígeno: %.2f litros\n", oxigenoNecesario);

        return opcion;
    }

    static void seleccionarNave() {
        System.out.println("\nSelecciona una nave para el viaje:");
        String[] naves = {"Nave A (Velocidad: 100,000 km/h)", "Nave B (Velocidad: 200,000 km/h)"};
        for (int i = 0; i < naves.length; i++) {
            System.out.println((i + 1) + ". " + naves[i]);
        }
        int naveSeleccionada = scanner.nextInt() - 1;
        if (naveSeleccionada == 1) {
            velocidad = 200000; // Si selecciona la Nave B, aumenta la velocidad
        }
        System.out.println("Has seleccionado " + naves[naveSeleccionada]);
    }

    static double[] introducirRecursos() {
        System.out.print("\nIntroduce la cantidad de combustible en litros: ");
        double combustible = scanner.nextDouble();
        System.out.print("Introduce la cantidad de oxígeno en litros: ");
        double oxigeno = scanner.nextDouble();
        return new double[]{combustible, oxigeno};
    }

    static double calcularTiempo(double distancia) {
        double tiempo = distancia / velocidad; // tiempo en horas
        System.out.println("Tiempo estimado de viaje: " + tiempo + " horas");
        return tiempo;
    }

    static double calcularCombustible(double distancia) {
        return distancia / 10.0; // Combustible necesario en litros
    }

    static double calcularOxigeno(double distancia) {
        return distancia / 100.0; // Oxígeno necesario en litros
    }

    static boolean validarRecursos(double[] recursosIntroducidos, double[] recursosNecesarios) {
        return recursosIntroducidos[0] >= recursosNecesarios[0] && recursosIntroducidos[1] >= recursosNecesarios[1];
    }

    static void iniciarSimulacion() {
        if (planetaSeleccionado == -1) {
            System.out.println("Por favor, selecciona un planeta de destino primero.");
        } else {
            double tiempoViaje = calcularTiempo(distancias[planetaSeleccionado]);
            double[] recursosNecesarios = new double[]{calcularCombustible(distancias[planetaSeleccionado]), calcularOxigeno(distancias[planetaSeleccionado])};
            if (validarRecursos(recursos, recursosNecesarios)) {
                System.out.println("Iniciando simulación...");
                if (simularViaje(distancias[planetaSeleccionado], tiempoViaje, recursos)) {
                    System.out.println("¡Llegaste a " + planetas[planetaSeleccionado] + " con éxito!");
                } else {
                    System.out.println("El viaje fracasó.");
                }
            } else {
                System.out.println("Recursos insuficientes.");
            }
        }
    }

    static boolean simularViaje(double distancia, double tiempo, double[] recursos) {
        double combustible = recursos[0];
        double oxígeno = recursos[1];
        double progreso = 0;
        double estadoNave = 100.0; // Estado inicial de la nave en porcentaje

        while (progreso < distancia) {
            progreso += velocidad;
            combustible -= velocidad / 10.0;
            oxígeno -= velocidad / 100.0;
            estadoNave -= random.nextDouble() * 0.003; // Desgaste aleatorio de la nave

            // Mostrar progreso y recursos actuales
            double porcentajeProgreso = (progreso / distancia) * 100;
            System.out.printf("Progreso del viaje: %.2f%%\n", porcentajeProgreso);
            System.out.printf("Combustible restante: %.2f litros\n", combustible);
            System.out.printf("Oxígeno restante: %.2f litros\n", oxígeno);
            System.out.printf("Integridad de la nave: %.2f%%\n", estadoNave);

            // Fallos aleatorios con motivo y daño detallado
            if (random.nextDouble() < 0.0099) {
                System.out.println("¡Fallo en el motor! La velocidad de la nave ha disminuido.");
                velocidad = Math.max(50000, velocidad * 0.7);
            }
            if (random.nextDouble() < 0.0080) {
                System.out.println("¡Fallo en el sistema de oxígeno! El consumo de oxígeno se ha duplicado.");
                oxígeno -= velocidad / 50.0;
            }
            if (random.nextDouble() < 0.008) {
                double danio = 2;
                estadoNave -= danio;
                System.out.printf("¡Impacto de meteoritos! Daño: %.2f%%. Integridad restante: %.2f%%\n", danio, estadoNave);
            }

            // Verificar si los recursos están agotados o si la nave tiene problemas
            if (combustible < 0 || oxígeno < 0 || estadoNave <= 50) {
                System.out.println("El viaje ha fallado. La nave se quedó sin recursos o sufrió un fallo crítico.");
                return false;
            }
        }

        System.out.println("¡Viaje completado con éxito!");
        return true;
    }
}